package br.rickcm.mercadolivre.model;

import br.rickcm.mercadolivre.enums.StatusTransacao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class TransacaoCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String idCompraGateway;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusTransacao status;
    @ManyToOne
    private CompraProduto compra;
    @NotNull
    private LocalDateTime instante = LocalDateTime.now();

    @Deprecated
    public TransacaoCompra() {
    }

    public TransacaoCompra(@NotBlank String idCompraGateway,@NotNull StatusTransacao status,@NotNull CompraProduto compra) {
        this.idCompraGateway = idCompraGateway;
        this.status = status;
        this.compra = compra;
    }

    public boolean estahConcluida(){
        return status.name().equals("CONCLUIDA");
    }

    public void finalizaTransacao(){
        this.status = StatusTransacao.CONCLUIDA;
    }

    public String dadosEmail(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(compra.dadosEmail());
        stringBuilder.append("\nPagamento: "+status.name());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
        stringBuilder.append("\nData e Hora: "+ instante.format(formatter));
        return stringBuilder.toString();
    }
}
