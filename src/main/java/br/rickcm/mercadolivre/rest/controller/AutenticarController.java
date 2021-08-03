package br.rickcm.mercadolivre.rest.controller;

import br.rickcm.mercadolivre.UsuarioDetalhesService;
import br.rickcm.mercadolivre.config.JwtService;
import br.rickcm.mercadolivre.model.Usuario;
import br.rickcm.mercadolivre.repository.UsuarioRepository;
import br.rickcm.mercadolivre.rest.dto.AuthDto;
import br.rickcm.mercadolivre.rest.dto.TokenDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AutenticarController {

    private UsuarioRepository repository;
    private final UsuarioDetalhesService service;
    private final JwtService jwtService;

    public AutenticarController(UsuarioRepository repository, UsuarioDetalhesService service, JwtService jwtService) {
        this.repository = repository;
        this.service = service;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody @Valid AuthDto request){
//        try {
            Usuario usuario = request.toModel();
            UserDetails usuarioAutenticado = service.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(token);
//        }catch (UsernameNotFoundException | SenhaInvalidaException e){
//            System.out.println(e.getMessage());
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//        }
    }
}
