package br.rickcm.mercadolivre.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Caracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String nome;
    @NotBlank
    @Column(nullable = false)
    private String descricao;

    @Deprecated
    public Caracteristica() {
    }

    public Caracteristica(@NotBlank String nome, @NotBlank String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}
