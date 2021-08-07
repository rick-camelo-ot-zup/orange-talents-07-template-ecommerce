package br.rickcm.mercadolivre.rest.dto.impl;

import br.rickcm.mercadolivre.enums.StatusTransacao;
import br.rickcm.mercadolivre.model.CompraProduto;
import br.rickcm.mercadolivre.model.TransacaoCompra;
import br.rickcm.mercadolivre.rest.dto.TransacaoRequest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransacaoPaypalRequest implements TransacaoRequest {

    @NotBlank
    private String idCompraGateway;
    @Min(0)
    @Max(1)
    @NotNull
    private Integer status;

    public String getIdCompraGateway() {
        return idCompraGateway;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public TransacaoCompra toModel(CompraProduto compra) {
        return new TransacaoCompra(idCompraGateway, getStatusTransacao(), compra);
    }

    @Override
    public boolean sucesso() {
        return status == 1;
    }

    public StatusTransacao getStatusTransacao(){
        if(sucesso())return StatusTransacao.CONCLUIDA;
        return StatusTransacao.PENDENTE;
    }
}
