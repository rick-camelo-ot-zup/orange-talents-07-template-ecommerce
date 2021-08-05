package br.rickcm.mercadolivre.rest.controller;

import br.rickcm.mercadolivre.error.ResourceNotFoundException;
import br.rickcm.mercadolivre.model.Produto;
import br.rickcm.mercadolivre.model.Usuario;
import br.rickcm.mercadolivre.processor.EnviadorEmail;
import br.rickcm.mercadolivre.repository.ProdutoRepository;
import br.rickcm.mercadolivre.rest.dto.PerguntaProdutoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class PerguntaController {

    private ProdutoRepository produtoRepository;
    private EnviadorEmail enviadorEmail;

    public PerguntaController(ProdutoRepository produtoRepository, EnviadorEmail enviadorEmail) {
        this.produtoRepository = produtoRepository;
        this.enviadorEmail = enviadorEmail;
    }

    @PostMapping("/produtos/{id}/perguntas")
    @Transactional
    public ResponseEntity<?> create(@PathVariable("id") long idProduto,
                                    @RequestBody @Valid PerguntaProdutoRequest perguntaRequest,
                                    @AuthenticationPrincipal Usuario usuario
    ){
        Optional<Produto> possivelProduto = produtoRepository.findById(idProduto);
        if(possivelProduto.isEmpty()){
            throw new ResourceNotFoundException("Não encontrado produto com o id informado.");
        }
        Produto produto = possivelProduto.get();
        produto.adicionaPergunta(perguntaRequest.toModel(produto, usuario));
        enviadorEmail.envia(produto.getEmailDono(), perguntaRequest.getTitulo());

        return ResponseEntity.ok().build();
    }
}
