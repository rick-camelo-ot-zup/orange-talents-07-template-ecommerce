package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.Usuario;
import br.rickcm.mercadolivre.util.SenhaLimpa;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AuthDto {

    @NotBlank
    @Email
    private String login;
    @NotBlank
    @Length(min = 6, message = "senha inválida")
    private String senha;

    public AuthDto() {
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
