package service;

import dao.UsuarioDAO;
import model.Usuario;

public class LoginService {
    private UsuarioDAO dao= new UsuarioDAO();

    public Usuario autenticar(String email, String senha){
        if(email == null || senha == null){
            return null;
        }
        String emailigual = email.trim().toLowerCase();

        Usuario usuario = dao.autenticar(emailigual, senha);

        if(usuario !=null && !usuario.isAtivo()){
            System.out.println("Usuario inativo tentando logar.");
            return null;
        }
        return usuario;
    }
}


