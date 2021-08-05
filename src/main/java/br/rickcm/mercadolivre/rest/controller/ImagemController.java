package br.rickcm.mercadolivre.rest.controller;

import br.rickcm.mercadolivre.error.ResourceNotFoundException;
import br.rickcm.mercadolivre.model.ImagemProduto;
import br.rickcm.mercadolivre.model.Produto;
import br.rickcm.mercadolivre.model.Usuario;
import br.rickcm.mercadolivre.processor.UploadImagem;
import br.rickcm.mercadolivre.repository.ProdutoRepository;
import br.rickcm.mercadolivre.rest.dto.ImagensRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ImagemController {

    private ProdutoRepository produtoRepository;
    private UploadImagem servicoUpload;

    public ImagemController(ProdutoRepository produtoRepository, UploadImagem servicoUpload) {
        this.produtoRepository = produtoRepository;
        this.servicoUpload = servicoUpload;
    }

    @PostMapping("/produtos/{id}/imagens")
    public ResponseEntity<?> uploadImage(@PathVariable("id") long idProduto, @Valid ImagensRequest imagens, @AuthenticationPrincipal Usuario usuario){
        Optional<Produto> possivelProduto = produtoRepository.findById(idProduto);
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
}
