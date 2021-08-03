package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.Usuario;
import br.rickcm.mercadolivre.util.SenhaLimpa;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UsuarioRequest {

    @NotBlank
    @NotNull
    @Email
    private String login;
    @NotBlank
    @NotNull
    @Length(min = 6, message = "deve ter no mínimo 6 caracteres.")
    private String senha;

    public UsuarioRequest() {
    }

    public Usuario toModel(){
        return new Usuario(login, new SenhaLimpa(senha));
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
