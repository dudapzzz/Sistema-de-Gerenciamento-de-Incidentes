package incidentes.service;

import dao.UsuarioDAO;
import model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioDAO dao;

    public UsuarioService(UsuarioDAO dao) {
        this.dao = dao;
    }

    public boolean inserir(Usuario u) {
        if (u.getNome() == null || u.getNome().trim().isEmpty()) {
            return false;
        }
        if (u.getEmail() == null || !u.getEmail().contains("@")) {
            return false;
        }
        if (u.getSenha() == null || u.getSenha().length() < 6) {
            return false;
        }

        u.setNome(u.getNome().trim());
        u.setEmail(u.getEmail().trim().toLowerCase());
        u.setAtivo(true);

        dao.save(u);
        return true;
    }
}