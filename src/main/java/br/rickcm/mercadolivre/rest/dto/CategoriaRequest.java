package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.Categoria;
import br.rickcm.mercadolivre.repository.CategoriaRepository;
import br.rickcm.mercadolivre.rest.validator.ExistsIdOrNull;
import br.rickcm.mercadolivre.rest.validator.UniqueValue;

import javax.validation.constraints.NotBlank;

public class CategoriaRequest {

    @NotBlank
    @UniqueValue(
            target = Categoria.class,
            field = "nome",
            message = "essa categoria já está cadastrada."
    )
    private String nome;

    @ExistsIdOrNull(
            target = Categoria.class,
            field = "id",
            message = "categoria não encontrada."
    )
    private Long categoriaMae;

    public Categoria toModel(CategoriaRepository repository){
        Categoria categoria = new Categoria(nome);
        if(this.categoriaMae != null){
            categoria.setCategoriaMae(repository.findById(categoriaMae).get());
        }
        return categoria;
    }

    public String getNome() {
        return nome;
    }

    public Long getCategoriaMae() {
        return categoriaMae;
    }
}
