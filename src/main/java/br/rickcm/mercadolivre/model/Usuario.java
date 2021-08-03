package br.rickcm.mercadolivre.model;

import br.rickcm.mercadolivre.util.SenhaLimpa;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Base64;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    @Email
    @Column(nullable = false)
    private String login;
    @NotBlank
    @NotNull
    @Size(min = 6)
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false)
    private LocalDateTime instante;

    @PrePersist
    private void onCreate(){
        this.instante = LocalDateTime.now();
    }

    @Deprecated
    public Usuario(){
    }

    /**
     *
     * @param login Não pode ser nula, nem em branco. Deve ter formato de e-mail.
     * @param senha deve ser limpa, sem encriptação. Não pode ser nula, nem em branco e deve ter no mínimo 6 caracteres.
     */
    public Usuario(@NotBlank @NotNull @Email String login, @NotNull SenhaLimpa senha) {
        this.login = login;
        this.senha = senha.encripta();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", instante=" + instante +
                '}';
    }

    public String getSenha(){
        return this.senha;
    }
}
