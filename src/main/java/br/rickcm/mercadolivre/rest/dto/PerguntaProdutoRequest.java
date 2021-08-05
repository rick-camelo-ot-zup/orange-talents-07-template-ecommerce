package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.PerguntaProduto;
import br.rickcm.mercadolivre.model.Produto;
import br.rickcm.mercadolivre.model.Usuario;

import javax.validation.constraints.NotBlank;

public class PerguntaProdutoRequest {

    @NotBlank
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return "PerguntaProdutoRequest{" +
                "titulo='" + titulo + '\'' +
                '}';
    }

    public PerguntaProduto toModel(Produto produto, Usuario usuario) {
        return new PerguntaProduto(titulo,usuario,produto);
    }
}
