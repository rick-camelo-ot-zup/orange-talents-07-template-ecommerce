package br.rickcm.mercadolivre.rest.controller;

import br.rickcm.mercadolivre.error.ResourceNotFoundException;
import br.rickcm.mercadolivre.model.ImagemProduto;
import br.rickcm.mercadolivre.model.Produto;
import br.rickcm.mercadolivre.model.Usuario;
import br.rickcm.mercadolivre.repository.CategoriaRepository;
import br.rickcm.mercadolivre.rest.dto.ImagensRequest;
import br.rickcm.mercadolivre.rest.dto.OpiniaoProdutoRequest;
import br.rickcm.mercadolivre.rest.dto.ProdutoRequest;
import br.rickcm.mercadolivre.repository.CaracteristicaRepository;
import br.rickcm.mercadolivre.repository.ProdutoRepository;
import br.rickcm.mercadolivre.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoRepository repository;
    private CategoriaRepository categoriaRepository;
    private CaracteristicaRepository caracteristicaRepository;
    private UploadService servicoUpload;

    public ProdutoController(ProdutoRepository repository,
                             CategoriaRepository categoriaRepository,
                             CaracteristicaRepository caracteristicaRepository, UploadService servicoUpload) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.caracteristicaRepository = caracteristicaRepository;
        this.servicoUpload = servicoUpload;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid ProdutoRequest request, @AuthenticationPrincipal Usuario usuario){
        System.out.println(usuario.getLogin());
        Produto produto = request.toModel(categoriaRepository, usuario);
        caracteristicaRepository.saveAll(produto.getCaracteristicas());
        repository.save(produto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/imagens")
    public ResponseEntity<?> uploadImage(@PathVariable("id") long idProduto, @Valid ImagensRequest imagens, @AuthenticationPrincipal Usuario usuario){
        Optional<Produto> possivelProduto = repository.findById(idProduto);
        if(possivelProduto.isEmpty()){
            throw new ResourceNotFoundException("Não encontrado produto com o id informado.");
        }
        Produto produto = possivelProduto.get();
        if(!produto.ehDono(usuario)){
            return ResponseEntity.status(403).build();
        }
        List<ImagemProduto> listImagens = servicoUpload.envia(imagens.getImagens(), produto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/opinioes")
    @Transactional
    public ResponseEntity<?> adicionaOpiniao(@PathVariable("id") long idProduto, @RequestBody @Valid OpiniaoProdutoRequest opiniao, @AuthenticationPrincipal Usuario usuario){
        Optional<Produto> possivelProduto = repository.findById(idProduto);
        if(possivelProduto.isEmpty()){
            throw new ResourceNotFoundException("Não encontrado produto com o id informado.");
        }
        Produto produto = possivelProduto.get();
        if(!produto.mesmoId(idProduto)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Os ids do produto não conferem");
        }
        produto.adicionaOpiniao(opiniao.toModel(produto, usuario));

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Iterable<Produto> getAll(){
        Iterable<Produto> all = repository.findAll();
        return all;
    }
}
