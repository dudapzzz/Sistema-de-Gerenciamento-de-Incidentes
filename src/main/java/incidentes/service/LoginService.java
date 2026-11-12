package incidentes.service;

import incidentes.dao.UsuarioRepository;
import incidentes.infra.CredenciaisInvalidasException;
import incidentes.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginService {
    private final UsuarioRepository dao;
    public LoginService(UsuarioRepository dao){
        this.dao = dao;
    }

    public Usuario autenticar(String email, String senha) {
        String emailNormalizado = email.trim().toLowerCase();

        Usuario usuario = dao.findByEmail(emailNormalizado)
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!usuario.isAtivo() || !usuario.getSenha().equals(senha)) {
            throw new CredenciaisInvalidasException();
        }

        return usuario;
    }

}