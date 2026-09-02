package incidentes.service;

import incidentes.dao.AtivoRepository;
import incidentes.model.Ativo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtivoService {

    private final AtivoRepository dao;

    public AtivoService(AtivoRepository dao) {
        this.dao = dao;
    }

    public List<Ativo> listarPorUsuario(int usuarioId) {
        return dao.findByUsuarioIdOrderByIdDesc(usuarioId);
    }

    public Ativo buscarPorId(int id) {
        return dao.findById(id).orElse(null);
    }

    public Ativo salvar(Ativo ativo) {
        if (ativo.getNome() == null || ativo.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do ativo é obrigatorio");
        }
        if (ativo.getTipo() == null || ativo.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo do ativo é obrigatorio");
        }
        if (ativo.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("O usuario do ativo é obrigatorio");
        }

        ativo.setNome(ativo.getNome().trim());
        ativo.setTipo(ativo.getTipo().trim());

        if (ativo.getIpOuUrl() != null) {
            ativo.setIpOuUrl(ativo.getIpOuUrl().trim());
        }
        return dao.save(ativo);
    }

    public boolean excluir(int id) {
        if (!dao.existsById(id)) {
            return false;
        }

        dao.deleteById(id);
        return true;
    }
}
