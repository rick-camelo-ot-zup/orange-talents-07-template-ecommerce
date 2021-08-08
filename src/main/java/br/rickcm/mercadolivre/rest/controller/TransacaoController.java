package br.rickcm.mercadolivre.rest.controller;

import br.rickcm.mercadolivre.enums.GatewayPagamento;
import br.rickcm.mercadolivre.enums.StatusTransacao;
import br.rickcm.mercadolivre.error.ErrorDto;
import br.rickcm.mercadolivre.model.CompraProduto;
import br.rickcm.mercadolivre.model.TransacaoCompra;
import br.rickcm.mercadolivre.processor.EnviadorEmail;
import br.rickcm.mercadolivre.repository.CompraRepository;
import br.rickcm.mercadolivre.repository.TransacaoRepositoy;
import br.rickcm.mercadolivre.rest.controller.externo.SistemaNotaFiscal;
import br.rickcm.mercadolivre.rest.controller.externo.SistemaRankingVendedores;
import br.rickcm.mercadolivre.rest.dto.impl.TransacaoPagseguroRequest;
import br.rickcm.mercadolivre.rest.dto.impl.TransacaoPaypalRequest;
import br.rickcm.mercadolivre.rest.dto.TransacaoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
public class TransacaoController {

    private CompraRepository compraRepository;
    private TransacaoRepositoy repository;
    private SistemaNotaFiscal sistemaNotaFiscal;
    private SistemaRankingVendedores sistemaRankingVendedores;
    private EnviadorEmail enviadorEmail;

    public TransacaoController(CompraRepository compraRepository,
                               TransacaoRepositoy repository,
                               SistemaNotaFiscal sistemaNotaFiscal,
                               SistemaRankingVendedores sistemaRankingVendedores, EnviadorEmail enviadorEmail) {
        this.compraRepository = compraRepository;
        this.repository = repository;
        this.sistemaNotaFiscal = sistemaNotaFiscal;
        this.sistemaRankingVendedores = sistemaRankingVendedores;
        this.enviadorEmail = enviadorEmail;
    }

    @PostMapping("/retorno-paypal/{id}")
    public ResponseEntity<?> paypal(@PathVariable("id") long id, @RequestBody @Valid TransacaoPaypalRequest request,
                                    UriComponentsBuilder uriBuilder) {
        return processaTransacao(GatewayPagamento.PAYPAL, id, request, uriBuilder);
    }

    @PostMapping("/retorno-pagseguro/{id}")
    public ResponseEntity<?> pagseguro(@PathVariable("id") long id,
                                       @RequestBody @Valid TransacaoPagseguroRequest request,
                                       UriComponentsBuilder uriBuilder) {
        return processaTransacao(GatewayPagamento.PAGSEGURO, id, request, uriBuilder);
    }

    @Transactional
    public ResponseEntity<?> processaTransacao(GatewayPagamento gateway,
                                               long id,
                                               TransacaoRequest request,
                                               UriComponentsBuilder uriBuilder){
        TransacaoCompra transacao;
        CompraProduto compra;

        //Verifica se a compra existe
        Optional<CompraProduto> possivelCompra = compraRepository.findById(id);
        if(possivelCompra.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        compra = possivelCompra.get();

        //verifica se a transacao já foi processada (cadastro no banco)
        Optional<TransacaoCompra> possivelTransacao = repository.
                findByIdCompraGatewayAndCompra_Gateway(request.getIdCompraGateway(), gateway);
        if(possivelTransacao.isPresent()){
            transacao = possivelTransacao.get();
            //Restrição - O id de uma transação que vem de alguma plataforma de pagamento só pode ser processado com sucesso uma vez.
            if(transacao.estahConcluida()){
                return retornaMensagemErro("idCompraGateway", "Essa transação já foi processada com sucesso.");
            }
        }else{
            transacao = request.toModel(compra);
        }

        //verifica se o retorno é de sucesso
        if(request.sucesso()){
            //Restricao - Uma compra não pode ter mais de duas transações concluídas com sucesso associada a ela.
            List<TransacaoCompra> listTransacoes = repository.findByCompraAndStatus(compra, StatusTransacao.CONCLUIDA);
            if(!listTransacoes.isEmpty()){
                return retornaMensagemErro("status", "já existe transação concluida para esta compra");
            }

            compra.finalizaCompra();
            transacao.finalizaTransacao();
            repository.save(transacao);
            sistemaNotaFiscal.notaFiscal(compra.dadosNota());
            sistemaRankingVendedores.atualizaRanking(compra.dadosRanking());
            enviadorEmail.envia(compra.emailComprador(), transacao.dadosEmail());
        }else{
            repository.save(transacao);
            URI uri = uriBuilder.path(gateway.getEndpoint()+"{id}").buildAndExpand(compra.getId()).toUri();
            URI url = gateway.montaUrl(compra.getIdentificador(),uri);
            enviadorEmail.envia(compra.emailComprador(),
                    "Houve erro no processamento do pagamento, você pode tentar efetuar o " +
                            "pagamento novamente através do link: "+url);
            return ResponseEntity.ok(StatusTransacao.PENDENTE);
        }
        return ResponseEntity.ok(StatusTransacao.CONCLUIDA);
    }

    public ResponseEntity<?> retornaMensagemErro(String campo, String mensagem){
        Map<String, List<String>> error = new HashMap();
        error.put(campo, Arrays.asList(mensagem));
        return ResponseEntity.badRequest().body(new ErrorDto(error));
    }
}
