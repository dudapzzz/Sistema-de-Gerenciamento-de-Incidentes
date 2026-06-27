package incidentes.service;

import incidentes.dao.IncidenteDAO;
import incidentes.model.Incidente;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IncidenteService {
    private final IncidenteDAO dao;

    public IncidenteService(IncidenteDAO dao) {
        this.dao = dao;
    }

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
            Optional<Incidente> incidenteOriginal = dao.findById(inc.getCodigo());

            if (incidenteOriginal.isPresent()) {
                inc.setUsuarioId(incidenteOriginal.get().getUsuarioId());
            }
        } else {
            if (inc.getUsuarioId() <= 0) {
                return false;
            }
        }
        inc.setTitulo(inc.getTitulo().trim());
        inc.setResponsavel(inc.getResponsavel().trim());
        inc.setDescricao(inc.getDescricao().trim());

        dao.save(inc);
        return true;
    }



    public long contarNaoResolvidos(int usuarioId) {
        return dao.countByStatusIgnoreCaseAndUsuarioId("Não resolvido", usuarioId);

    }

    public long contarAltaRelevancia(int usuarioId) {
        return dao.countByRelevanciaIgnoreCaseAndUsuarioId("Alta", usuarioId);
    }

    public long contarEmAndamento(int usuarioId) {
        return dao.countByStatusIgnoreCaseAndUsuarioId("Em andamento", usuarioId);
    }

    public long contarResolvidos(int usuarioId) {
        return dao.countByStatusIgnoreCaseAndUsuarioId("Resolvido", usuarioId);
    }

    public Map<String, Integer> contarIncidentesPorRelevancia(int usuarioId) {
        return converterResultadoParaMapa(dao.getEstatisticasRelevanciaRaw(usuarioId));
    }

    public List<Incidente> listarRecentes(int usuarioId) {
        return dao.findTop3ByUsuarioIdOrderByCodigoDesc(usuarioId);
    }

    public List<Incidente> listarTodos(int usuarioId) {
        return dao.findByUsuarioIdOrderByCodigoDesc(usuarioId);
    }

    public Incidente buscarPorCodigo(int codigo) {
        return dao.findById(codigo).orElse(null);
    }

    public boolean excluir(int codigo) {
        if (!dao.existsById(codigo)) {
            return false;
        }
        dao.deleteById(codigo);
        return true;
    }

    public Map<String, Integer> contarIncidentesPorStatus(int usuarioId) {
        return converterResultadoParaMapa(dao.getEstatisticasStatusRaw(usuarioId));
    }

    private Map<String, Integer> converterResultadoParaMapa(List<Object[]> resultado) {
        Map<String, Integer> mapa = new LinkedHashMap<>();
        for (Object[] linha : resultado) {
            String chave = String.valueOf(linha[0]);
            Integer valor = ((Number) linha[1]).intValue();
            mapa.put(chave, valor);
        }
        return mapa;
    }
}