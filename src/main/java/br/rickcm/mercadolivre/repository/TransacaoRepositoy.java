package br.rickcm.mercadolivre.repository;

import br.rickcm.mercadolivre.enums.GatewayPagamento;
import br.rickcm.mercadolivre.enums.StatusTransacao;
import br.rickcm.mercadolivre.model.CompraProduto;
import br.rickcm.mercadolivre.model.TransacaoCompra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepositoy extends CrudRepository<TransacaoCompra, Long> {

    Optional<TransacaoCompra> findByIdCompraGatewayAndCompra_Gateway(String idCompraGateway, GatewayPagamento gateway);
    List<TransacaoCompra> findByCompraAndStatus(CompraProduto compra, StatusTransacao status);
}
