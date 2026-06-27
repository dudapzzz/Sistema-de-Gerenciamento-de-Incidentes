package service;

import dao.AtivoDAO;
import model.Ativo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtivoService {

    private final AtivoDAO dao;

    public AtivoService(AtivoDAO dao) {
        this.dao = dao;
    }

    public List<Ativo> listarPorUsuario(int usuarioId) {
        return dao.findByUsuarioIdOrderByIdDesc(usuarioId);
    }

    public Ativo buscarPorId(int id) {
        return dao.findById(id).orElse(null);
    }

    public boolean salvar(Ativo ativo) {
        if (ativo.getNome() == null || ativo.getNome().trim().isEmpty()) {
            return false;
        }
        if (ativo.getTipo() == null || ativo.getTipo().trim().isEmpty()) {
            return false;
        }
        if (ativo.getUsuarioId() <= 0) {
            return false;
        }

        ativo.setNome(ativo.getNome().trim());
        ativo.setTipo(ativo.getTipo().trim());

        if (ativo.getIpOuUrl() != null) {
            ativo.setIpOuUrl(ativo.getIpOuUrl().trim());
        }

        dao.save(ativo);
        return true;
    }

    public boolean excluir(int id) {
        if (!dao.existsById(id)) {
            return false;
        }

        dao.deleteById(id);
        return true;
    }
}