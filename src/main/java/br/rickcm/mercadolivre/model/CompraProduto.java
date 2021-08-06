package br.rickcm.mercadolivre.model;

import br.rickcm.mercadolivre.enums.GatewayPagamento;
import br.rickcm.mercadolivre.enums.StatusCompra;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
public class CompraProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GatewayPagamento gateway;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCompra status;
    @NotNull
    @ManyToOne
    private Produto produto;
    @NotNull
    @ManyToOne
    private Usuario usuario;
    @NotNull
    @Column(nullable = false)
    private int quantidade;
    @NotNull
    @Column(nullable = false)
    private BigDecimal valorProduto;
    @NotNull
    @Column(nullable = false)
    private UUID identificador;

    @Deprecated
    public CompraProduto() {
    }

    public CompraProduto(@NotNull GatewayPagamento gateway,
                         @NotNull StatusCompra status,
                         @NotNull Produto produto,
                         @NotNull Usuario comprador,
                         @NotNull int quantidade,
                         @NotNull BigDecimal valorProduto) {
        this.gateway = gateway;
        this.status = status;
        this.produto = produto;
        this.usuario = comprador;
        this.quantidade = quantidade;
        this.valorProduto = valorProduto;
        this.identificador = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompraProduto that = (CompraProduto) o;
        return quantidade == that.quantidade && Objects.equals(id, that.id) && gateway == that.gateway && status == that.status && Objects.equals(produto, that.produto) && Objects.equals(usuario, that.usuario) && Objects.equals(valorProduto, that.valorProduto) && Objects.equals(identificador, that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gateway, status, produto, usuario, quantidade, valorProduto, identificador);
    }

    public UUID getIdentificador() {
        return identificador;
    }

    public Long getId() {
        return id;
    }
}
