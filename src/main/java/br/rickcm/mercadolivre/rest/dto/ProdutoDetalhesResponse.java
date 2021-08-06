package br.rickcm.mercadolivre.rest.dto;

import br.rickcm.mercadolivre.model.OpiniaoProduto;
import br.rickcm.mercadolivre.model.Produto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

public class ProdutoDetalhesResponse {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Double mediaNotas;
    private Integer quantidadeNotas;
    private List<CaracteristicaResponse> caracteristicas;
    private List<ImagemResponse> imagens;
    private List<OpiniaoResponse> opinioes;
    private List<PerguntaResponse> perguntas;

    @Deprecated
    public ProdutoDetalhesResponse() {
    }

    public ProdutoDetalhesResponse(@NotNull Produto produto) {
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getValor();
        this.quantidadeNotas = produto.getOpinioes().size();
        this.mediaNotas = calculaMediaNotas(produto.getOpinioes());
        this.caracteristicas = produto.mapCaracteristica();
        this.imagens = produto.mapImagens();
        this.opinioes = produto.mapOpiniao();
        this.perguntas = produto.mapPerguntas();
    }

    private Double calculaMediaNotas(List<OpiniaoProduto> opinioes) {
        IntStream intStream = opinioes.stream().mapToInt(OpiniaoProduto::getNota);
        OptionalDouble average = intStream.average();
        if(average.isEmpty()){
            return Double.valueOf(0);
        }
        return average.getAsDouble();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Double getMediaNotas() {
        return mediaNotas;
    }

    public Integer getQuantidadeNotas() {
        return quantidadeNotas;
    }

    public List<CaracteristicaResponse> getCaracteristicas() {
        return caracteristicas;
    }

    public List<ImagemResponse> getImagens() {
        return imagens;
    }

    public List<OpiniaoResponse> getOpinioes() {
        return opinioes;
    }

    public List<PerguntaResponse> getPerguntas() {
        return perguntas;
    }
}
