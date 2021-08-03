package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.Categoria;

public class CategoriaResponse {

    private String nome;
    private String categoriaMae;

    public CategoriaResponse(Categoria categoria) {
        this.nome = categoria.getNome();
        if(categoria.getCategoriaMae() != null){
            this.categoriaMae = categoria.getCategoriaMae().getNome();
        }
    }

    public CategoriaResponse(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoriaMae() {
        return categoriaMae;
    }
}
