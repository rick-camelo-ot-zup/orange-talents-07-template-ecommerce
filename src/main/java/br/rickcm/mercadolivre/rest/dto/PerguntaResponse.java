package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.PerguntaProduto;

import javax.validation.constraints.NotBlank;

public class PerguntaResponse {

    @NotBlank
    private String titulo;

    public PerguntaResponse(@NotBlank String titulo) {
        this.titulo = titulo;
    }

    public PerguntaResponse(PerguntaProduto perguntaProduto){
        this.titulo = perguntaProduto.getTitulo();
    }

    public String getTitulo() {
        return titulo;
    }
}
