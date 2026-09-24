package incidentes.service;

import incidentes.dao.UsuarioRepository;
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

    public Usuario autenticar(String email, String senha){
        if(email == null || senha == null){
            return null;
        }
        String emailIgual= email.trim().toLowerCase();

        Optional<Usuario> usuarioOptional = dao.findByEmailAndSenha(emailIgual, senha);

        if(usuarioOptional.isEmpty()){
            return null;
        }
        Usuario usuario = usuarioOptional.get();

        if(!usuario.isAtivo()){
            System.out.println("Usuario inativo tentando entrar");
            return null;
        }
        return usuario;
    }

    public Usuario getUsuarioByUuid(String uuidStr){
        UUID uuid = UUID.fromString(uuidStr);
        return dao.findByUuid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado para o UUID: " + uuidStr));
    }
}