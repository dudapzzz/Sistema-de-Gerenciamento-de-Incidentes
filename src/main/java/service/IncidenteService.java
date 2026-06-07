package service;

import dao.IncidenteDAO;
import model.Incidente;

import java.util.List;
import java.util.Map;

public class IncidenteService {
    private IncidenteDAO dao = new IncidenteDAO();

    public boolean salvar(Incidente inc) {
        if (inc.getTitulo() == null || inc.getTitulo().trim().length() < 5) {
            return false;
        }
        if (inc.getDescricao() == null || inc.getDescricao().trim().length() < 10) {
            return false;
        }
        if (inc.getResponsavel() == null || inc.getResponsavel().trim().isEmpty()) {
            return false;
        }

        if (inc.getCodigo() != 0) {
            Incidente incidenteOriginal = dao.buscarPorCodigo(inc.getCodigo());

            if (incidenteOriginal != null) {
                inc.setUsuarioId(incidenteOriginal.getUsuarioId());
            }
        } else {
            if(inc.getUsuarioId() <= 0){
                return false;
            }
        }

        inc.setTitulo(inc.getTitulo().trim());
        inc.setResponsavel(inc.getResponsavel().trim());
        inc.setDescricao(inc.getDescricao().trim());

        return dao.inserir(inc);
    }

    public long contarNaoResolvidos(int usuarioId) {
        return dao.contarPorStatus("Não resolvido", usuarioId);
    }

    public long contarAltaRelevancia(int usuarioId) {
        return dao.contarPorRelevancia("Alta", usuarioId);
    }

    public long contarEmAndamento(int usuarioId) {
        return dao.contarPorStatus("Em andamento", usuarioId);
    }

    public long contarResolvidos(int usuarioId) {
        return dao.contarPorStatus("Resolvido", usuarioId);
    }

    public Map<String, Integer> contarIncidentesPorRelevancia(int usuarioId){
        return dao.getEstatisticasRelevancia(usuarioId);
    }

    public List<Incidente> listarRecentes(int usuarioId) {
        return dao.listarRecentesGeral(usuarioId);
    }

    public List<Incidente> listarTodos(int usuarioId) {
        return dao.listar(usuarioId);
    }

    public Incidente buscarPorCodigo(int codigo) {
        return dao.buscarPorCodigo(codigo);
    }

    public boolean excluir(int codigo) {
        return dao.excluir(codigo);
    }

    public Map<String, Integer> contarIncidentesPorStatus(int usuarioId) {
        return dao.getEstatisticasStatus(usuarioId);
    }
}