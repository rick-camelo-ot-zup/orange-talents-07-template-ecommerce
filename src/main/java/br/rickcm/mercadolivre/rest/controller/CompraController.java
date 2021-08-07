package br.rickcm.mercadolivre.rest.controller;

import br.rickcm.mercadolivre.enums.GatewayPagamento;
import br.rickcm.mercadolivre.model.CompraProduto;
import br.rickcm.mercadolivre.model.Produto;
import br.rickcm.mercadolivre.model.Usuario;
import br.rickcm.mercadolivre.processor.EnviadorEmail;
import br.rickcm.mercadolivre.repository.CompraRepository;
import br.rickcm.mercadolivre.repository.ProdutoRepository;
import br.rickcm.mercadolivre.rest.dto.CompraRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class CompraController {

    private CompraRepository repository;
    private ProdutoRepository produtoRepository;
    private EnviadorEmail enviadorEmail;

    public CompraController(CompraRepository repository, ProdutoRepository produtoRepository, EnviadorEmail enviadorEmail) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.enviadorEmail = enviadorEmail;
    }

    @PostMapping("/compras")
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid CompraRequest request,
                                    @AuthenticationPrincipal Usuario comprador,
                                    UriComponentsBuilder uriBuilder){

        GatewayPagamento gatewayPagamento = GatewayPagamento.forInt(request.getGateway());
        Produto produto = produtoRepository.findById(request.getIdProduto()).get();
        boolean abatido = produto.abateQuantidade(request.getQuantidade());

        if(!abatido){
            return ResponseEntity.status(400).build();
        }

        CompraProduto compra = request.toModel(gatewayPagamento, produto, comprador);

        repository.save(compra);

        enviadorEmail.envia(produto.getEmailDono(), "Compra registrada para o produto"+produto.getNome());

        URI uri = uriBuilder.path(gatewayPagamento.getEndpoint()+"{id}").buildAndExpand(compra.getId()).toUri();
        URI url = gatewayPagamento.montaUrl(compra.getIdentificador(),uri);

        return ResponseEntity.status(302).location(url).build();
    }

}
