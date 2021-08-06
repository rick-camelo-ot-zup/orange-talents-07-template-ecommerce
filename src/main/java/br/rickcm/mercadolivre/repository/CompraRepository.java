package br.rickcm.mercadolivre.repository;

import br.rickcm.mercadolivre.model.CompraProduto;
import org.springframework.data.repository.CrudRepository;

public interface CompraRepository extends CrudRepository<CompraProduto, Long> {
}
