package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.enums.GatewayPagamento;
import br.rickcm.mercadolivre.enums.StatusCompra;
import br.rickcm.mercadolivre.model.CompraProduto;
import br.rickcm.mercadolivre.model.Produto;
import br.rickcm.mercadolivre.model.Usuario;
import br.rickcm.mercadolivre.rest.validator.ExistsGateway;
import br.rickcm.mercadolivre.rest.validator.ExistsId;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CompraRequest {

    @NotNull
    @ExistsGateway
    private Integer gateway;
    @NotNull
    @ExistsId(
            target = Produto.class,
            field = "id",
            message = "produto não encontrado."
    )
    private Long idProduto;
    @NotNull
    @Min(1)
    private Integer quantidade;

    public CompraProduto toModel(GatewayPagamento gateway, Produto produto, Usuario comprador){
        return new CompraProduto(gateway, StatusCompra.INICIADA, produto, comprador, quantidade, produto.getValor());
    }

    public Integer getGateway() {
        return gateway;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
