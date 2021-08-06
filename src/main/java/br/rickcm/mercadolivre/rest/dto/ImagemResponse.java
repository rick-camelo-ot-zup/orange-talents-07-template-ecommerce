package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.ImagemProduto;

import javax.validation.constraints.NotBlank;

public class ImagemResponse {

    @NotBlank
    private String url;

    public ImagemResponse(@NotBlank String url) {
        this.url = url;
    }

    public ImagemResponse(ImagemProduto imagem){
        this.url = imagem.getUrl();
    }

    public String getUrl() {
        return url;
    }
}
