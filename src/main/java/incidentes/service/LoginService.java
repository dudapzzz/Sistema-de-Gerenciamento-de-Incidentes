package incidentes.service;

import dao.UsuarioDAO;
import model.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private final UsuarioDAO dao;
    public LoginService(UsuarioDAO dao){
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
}