package br.rickcm.mercadolivre.rest.controller;

import br.rickcm.mercadolivre.repository.CategoriaRepository;
import br.rickcm.mercadolivre.rest.dto.CategoriaRequest;
import br.rickcm.mercadolivre.rest.dto.CategoriaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CategoriaRequest request){
        repository.save(request.toModel(repository));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<CategoriaResponse> getAll(){
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(CategoriaResponse::new)
                .collect(Collectors.toList());
    }
}
