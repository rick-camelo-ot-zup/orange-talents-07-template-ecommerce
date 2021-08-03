package br.rickcm.mercadolivre.rest.controller;

import br.rickcm.mercadolivre.model.Usuario;
import br.rickcm.mercadolivre.repository.UsuarioRepository;
import br.rickcm.mercadolivre.rest.dto.UsuarioRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UsuarioRequest request){
        repository.save(request.toModel());
        return ResponseEntity.ok().build();
    }
}