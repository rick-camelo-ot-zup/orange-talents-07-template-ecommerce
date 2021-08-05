package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.OpiniaoProduto;
import br.rickcm.mercadolivre.model.Produto;
import br.rickcm.mercadolivre.model.Usuario;
import br.rickcm.mercadolivre.rest.validator.ExistsId;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class OpiniaoProdutoRequest {
    @NotNull
    @Min(1)
    @Max(5)
    private Integer nota;
    @NotBlank
    private String titulo;
    @NotBlank
    @Length(max = 500)
    private String descricao;
    @NotNull
    @ExistsId(
            target = Produto.class,
            field = "id",
            message = "Produto inexistente."
    )
    private Long idProduto;

    public OpiniaoProduto toModel(Produto produto, Usuario usuario){
        return new OpiniaoProduto(nota, titulo, descricao, produto, usuario);
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getIdProduto() {
        return idProduto;
    }
}
