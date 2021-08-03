package br.rickcm.mercadolivre.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Permission implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Override
    public String getAuthority() {
        return this.description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
