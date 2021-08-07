package br.rickcm.mercadolivre.rest.controller;

import br.rickcm.mercadolivre.enums.GatewayPagamento;
import br.rickcm.mercadolivre.enums.StatusTransacao;
import br.rickcm.mercadolivre.error.ErrorDto;
import br.rickcm.mercadolivre.model.CompraProduto;
import br.rickcm.mercadolivre.model.TransacaoCompra;
import br.rickcm.mercadolivre.repository.CompraRepository;
import br.rickcm.mercadolivre.repository.TransacaoRepositoy;
import br.rickcm.mercadolivre.rest.dto.impl.TransacaoPagseguroRequest;
import br.rickcm.mercadolivre.rest.dto.impl.TransacaoPaypalRequest;
import br.rickcm.mercadolivre.rest.dto.TransacaoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@RestController
public class TransacaoController {

    private CompraRepository compraRepository;
    private TransacaoRepositoy repository;

    public TransacaoController(CompraRepository compraRepository, TransacaoRepositoy repository) {
        this.compraRepository = compraRepository;
        this.repository = repository;
    }

    @PostMapping("/retorno-paypal/{id}")
    public ResponseEntity<?> paypal(@PathVariable("id") long id, @RequestBody @Valid TransacaoPaypalRequest request) {
        return processaTransacao(GatewayPagamento.PAYPAL, id, request);
    }

    @PostMapping("/retorno-pagseguro/{id}")
    public ResponseEntity<?> pagseguro(@PathVariable("id") long id, @RequestBody @Valid TransacaoPagseguroRequest request) {
        return processaTransacao(GatewayPagamento.PAGSEGURO, id, request);
    }

    @Transactional
    public ResponseEntity<?> processaTransacao(GatewayPagamento gateway, long id, TransacaoRequest request){
        TransacaoCompra transacao;
        CompraProduto compra;

        //Verifica se a compra existe
        Optional<CompraProduto> possivelCompra = compraRepository.findById(id);
        if(possivelCompra.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        compra = possivelCompra.get();

        //verifica se o retorno é de sucesso
        if(request.sucesso()){
            //Restricao - Uma compra não pode ter mais de duas transações concluídas com sucesso associada a ela.
            List<TransacaoCompra> listTransacoes = repository.findByCompraAndStatus(compra, StatusTransacao.CONCLUIDA);
            if(!listTransacoes.isEmpty()){
                return retornaMensagemErro("status", "já existe transação concluida para esta compra");
            }

            //verifica se a transacao já foi processada (cadastro no banco)
            Optional<TransacaoCompra> possivelTransacao = repository.
                    findByIdCompraGatewayAndCompra_Gateway(request.getIdCompraGateway(), gateway);
            if(possivelTransacao.isPresent()){
                transacao = possivelTransacao.get();

                //Restrição - O id de uma transação que vem de alguma plataforma de pagamento só pode ser processado com sucesso uma vez.
                if(transacao.estahConcluida()){
                    return retornaMensagemErro("idCompraGateway", "Essa transação já foi processada com sucesso.");
                }
                compra.finalizaCompra();
                transacao.finalizaTransacao();
                repository.save(transacao);
                return ResponseEntity.ok(StatusTransacao.CONCLUIDA);
            }else{
                //TODO transacao não existe, é necessario instanciar e salvar.
            }
            //

        }

        System.out.println(request);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> retornaMensagemErro(String campo, String mensagem){
        Map<String, List<String>> error = new HashMap();
        error.put(campo, Arrays.asList(mensagem));
        return ResponseEntity.badRequest().body(new ErrorDto(error));
    }
}
