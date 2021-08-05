package br.rickcm.mercadolivre.model;

import br.rickcm.mercadolivre.util.SenhaLimpa;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario implements UserDetails {

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_permission", joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_permissions")})
    private List<Permission> permissions;
    @OneToMany(mappedBy ="usuario", fetch = FetchType.LAZY)
    private List<Produto> produtos;
    @OneToMany
    @JoinColumn(name="usuario_id")
    private List<OpiniaoProduto> opinioes;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(login, usuario.login) && Objects.equals(senha, usuario.senha) && Objects.equals(instante, usuario.instante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, senha, instante, permissions, produtos);
    }

    public String getLogin() {
        return login;
    }

    public String getSenha(){
        return this.senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
