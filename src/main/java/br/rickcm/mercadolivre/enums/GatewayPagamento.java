package br.rickcm.mercadolivre.enums;

import java.net.URI;
import java.util.UUID;

public enum GatewayPagamento {
    PAYPAL(1){
        @Override
        public URI montaUrl(UUID uuid, URI uri) {
            return URI.create("paypal.com?buyerId="+uuid+"&redirectUrl="+uri+"");
        }

        @Override
        public String getEndpoint() {
            return "/retorno-paypal/";
        }
    },
    PAGSEGURO(2){
        @Override
        public URI montaUrl(UUID uuid, URI uri) {
            return URI.create("pagseguro.com?returnId="+uuid+"&redirectUrl="+uri+"");
        }
        @Override
        public String getEndpoint() {
            return "/retorno-pagseguro/";
        }
    };

    private int identificador;

    public int getIdentificador() {
        return identificador;
    }

    GatewayPagamento(int identificador){
        this.identificador = identificador;
    };

    public static GatewayPagamento forInt(int id) {
        for (GatewayPagamento gatewayPagamento : values()) {
            if (gatewayPagamento.identificador == id) {
                return gatewayPagamento;
            }
        }
        throw new IllegalArgumentException("Gateway inválido: " + id);
    }

    public abstract URI montaUrl(UUID uuid, URI uri);
    public abstract String getEndpoint();
}
