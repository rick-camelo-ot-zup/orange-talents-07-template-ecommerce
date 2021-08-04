package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.Caracteristica;
import br.rickcm.mercadolivre.model.Categoria;
import br.rickcm.mercadolivre.model.Produto;
import br.rickcm.mercadolivre.repository.CategoriaRepository;
import br.rickcm.mercadolivre.rest.validator.ExistsId;
import br.rickcm.mercadolivre.rest.validator.ListSize;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ProdutoRequest {
    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotNull
    @PositiveOrZero
    private Integer quantidade;
    @NotBlank
    @Length(max = 1000)
    private String descricao;
    @NotNull
    @ExistsId(
            target = Categoria.class,
            field = "id",
            message = "Categoria não encontada."
    )
    private Long categoria;
    @ListSize(min = 3, message = "deve ter no mínimo três características.")
    private Set<CaracteristicaDTO> caracteristicas;

    public Produto toModel(CategoriaRepository categoriaRepository){
        Optional<Categoria> categoria = categoriaRepository.findById(this.categoria);
        Set<Caracteristica> caracteristicas = this.caracteristicas.stream().map(caracteristicaDTO -> {
            return new Caracteristica(caracteristicaDTO.getNome(), caracteristicaDTO.getDescricao());
        }).collect(Collectors.toSet());
        return new Produto(nome, valor, quantidade, descricao, categoria.get(),caracteristicas);
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getCategoria() {
        return categoria;
    }

    public Set<CaracteristicaDTO> getCaracteristicas() {
        return caracteristicas;
    }
}
