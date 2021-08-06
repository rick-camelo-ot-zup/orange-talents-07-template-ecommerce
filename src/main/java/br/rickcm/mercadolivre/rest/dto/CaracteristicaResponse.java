package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.Caracteristica;

import javax.validation.constraints.NotBlank;

public class CaracteristicaResponse {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;

    public CaracteristicaResponse(@NotBlank String nome, @NotBlank String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public CaracteristicaResponse (Caracteristica caracteristica){
        this.nome = caracteristica.getNome();
        this.descricao = caracteristica.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
