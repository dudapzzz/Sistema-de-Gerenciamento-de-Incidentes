package service;

import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioService {
    private UsuarioDAO dao = new UsuarioDAO();
    public boolean inserir(Usuario u){
        if(u.getNome() == null || u.getNome().trim().isEmpty()){
            return false;
        }
        if(u.getEmail() == null || !u.getEmail().contains("@")){
            return false;
        }
        if(u.getSenha() == null || u.getSenha().length() < 6){
            return false;
        }

        u.setNome(u.getNome().trim());
        u.setEmail(u.getEmail().trim().toLowerCase());

        u.setAtivo(true);

        return dao.inserir(u);
    }

}


