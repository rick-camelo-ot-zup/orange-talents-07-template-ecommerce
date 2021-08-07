package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.CompraProduto;
import br.rickcm.mercadolivre.model.TransacaoCompra;

public interface TransacaoRequest {

    TransacaoCompra toModel(CompraProduto compra);
    boolean sucesso();

    String getIdCompraGateway();
}
