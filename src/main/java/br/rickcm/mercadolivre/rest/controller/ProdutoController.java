package br.rickcm.mercadolivre.rest.controller;

import br.rickcm.mercadolivre.model.Produto;
import br.rickcm.mercadolivre.repository.CategoriaRepository;
import br.rickcm.mercadolivre.rest.dto.ProdutoRequest;
import br.rickcm.mercadolivre.repository.CaracteristicaRepository;
import br.rickcm.mercadolivre.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoRepository repository;
    private CategoriaRepository categoriaRepository;
    private CaracteristicaRepository caracteristicaRepository;

    public ProdutoController(ProdutoRepository repository,
                             CategoriaRepository categoriaRepository,
                             CaracteristicaRepository caracteristicaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.caracteristicaRepository = caracteristicaRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid ProdutoRequest request){
        Produto produto = request.toModel(categoriaRepository);
        caracteristicaRepository.saveAll(produto.getCaracteristicas());
        repository.save(produto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Iterable<Produto> getAll(){
        Iterable<Produto> all = repository.findAll();
        return all;
    }
}
