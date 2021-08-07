package br.rickcm.mercadolivre.rest.dto.impl;

import br.rickcm.mercadolivre.enums.StatusTransacao;
import br.rickcm.mercadolivre.enums.StatusTransacaoPagseguro;
import br.rickcm.mercadolivre.model.CompraProduto;
import br.rickcm.mercadolivre.model.TransacaoCompra;
import br.rickcm.mercadolivre.rest.dto.TransacaoRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class TransacaoPagseguroRequest implements TransacaoRequest {
    @NotBlank
    private String idCompraGateway;
    @NotNull
    private StatusTransacaoPagseguro status;

    public String getIdCompraGateway() {
        return idCompraGateway;
    }

    public StatusTransacaoPagseguro getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "TransacaoPagseguroRequest{" +
                "idCompraGateway='" + idCompraGateway + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public TransacaoCompra toModel(CompraProduto compra) {
        return new TransacaoCompra(idCompraGateway, getStatusTransacao(), compra);
    }

    @Override
    public boolean sucesso() {
        return status.name().equals("SUCESSO");
    }

    public StatusTransacao getStatusTransacao(){
        if(sucesso())return StatusTransacao.CONCLUIDA;
        return StatusTransacao.PENDENTE;
    }
}
