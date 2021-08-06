package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.OpiniaoProduto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OpiniaoResponse {

    @NotNull
    private Integer nota;
    @NotBlank
    private String titulo;
    @NotBlank
    private String descricao;

    public OpiniaoResponse(@NotNull Integer nota,@NotBlank String titulo,@NotBlank String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public OpiniaoResponse(OpiniaoProduto opiniaoProduto){
        this.titulo = opiniaoProduto.getTitulo();
        this.nota = opiniaoProduto.getNota();
        this.descricao = opiniaoProduto.getDescricao();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getNota() {
        return nota;
    }
}
