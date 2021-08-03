package br.rickcm.mercadolivre;


import br.rickcm.mercadolivre.error.SenhaInvalidaException;
import br.rickcm.mercadolivre.model.Usuario;
import br.rickcm.mercadolivre.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UsuarioDetalhesService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public Usuario salvar(Usuario usuario){
        return repository.save(usuario);
    }

    public UserDetails autenticar (Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = usuario.getSenha().equals(user.getPassword());
        if(senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException("Senha invalida");
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Login nao encontrado na base de dados."));
    }
}
