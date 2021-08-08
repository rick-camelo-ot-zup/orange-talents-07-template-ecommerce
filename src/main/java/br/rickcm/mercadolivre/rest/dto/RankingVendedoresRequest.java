package br.rickcm.mercadolivre.rest.dto;

import javax.validation.constraints.NotNull;

public class RankingVendedoresRequest {

    @NotNull
    private Long idCompra;
    @NotNull
    private Long idVendedor;

    public RankingVendedoresRequest(@NotNull Long idCompra,@NotNull Long idVendedor) {
        this.idCompra = idCompra;
        this.idVendedor = idVendedor;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdVendedor() {
        return idVendedor;
    }

    @Override
    public String toString() {
        return "RankingVendedoresRequest{" +
                "idCompra=" + idCompra +
                ", idVendedor=" + idVendedor +
                '}';
    }
}
