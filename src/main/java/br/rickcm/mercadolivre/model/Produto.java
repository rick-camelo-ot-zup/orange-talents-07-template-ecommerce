package br.rickcm.mercadolivre.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String nome;
    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal valor;
    @PositiveOrZero
    @NotNull
    @Column(nullable = false)
    private Integer quantidade;
    @NotBlank
    @Length(max = 1000)
    @Column(nullable = false)
    private String descricao;
    @NotNull
    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;
    @OneToMany
    @JoinColumn(name="produto_id")
    private Set<Caracteristica> caracteristicas;
    @Column(nullable = false)
    private LocalDateTime instante;
    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name="produto_id")
    private List<ImagemProduto> imagens;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name="produto_id")
    private List<OpiniaoProduto> opinioes;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name="produto_id")
    private List<PerguntaProduto> perguntas;

    @PrePersist
    protected void onCreate() {
        instante = LocalDateTime.now();
    }

    @Deprecated
    public Produto() {
    }

    public Produto(@NotBlank String nome,
                   @NotNull @Positive BigDecimal valor,
                   @PositiveOrZero @NotNull Integer quantidade,
                   @NotBlank String descricao,
                   @NotNull Categoria categoria,
                   Set<Caracteristica> caracteristicas,
                   Usuario usuario) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.caracteristicas = caracteristicas;
        this.usuario = usuario;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void adicionaOpiniao(OpiniaoProduto opiniao) {
        this.opinioes.add(opiniao);
    }

    public void adicionaPergunta(PerguntaProduto pergunta) {
        this.perguntas.add(pergunta);
    }

    public boolean mesmoId(Long id) {
        return this.id.equals(id);
    }

    public String getEmailDono(){
        return this.usuario.getLogin();
    }

    public boolean ehDono(Usuario usuario){
        return this.usuario.equals(usuario);
    }

}
