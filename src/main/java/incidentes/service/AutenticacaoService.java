package incidentes.service;

import incidentes.dao.UsuarioRepository;
import incidentes.model.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

//ele vai localizar o usuario e devolver os dados
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository){
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException{
        Usuario usuario = repository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha incorretos"));

        return User.withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(usuario.getPermissao())
                .disabled(!usuario.isAtivo())
                .build();

    }
}
