package br.rickcm.mercadolivre.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String url;
    @NotNull
    @ManyToOne
    private Produto produto;

    @Deprecated
    public ImagemProduto() {
    }

    public ImagemProduto(String url, Produto produto) {
        this.url = url;
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "ImagemProduto{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", produto=" + produto +
                '}';
    }

    public String getUrl() {
        return url;
    }
}
