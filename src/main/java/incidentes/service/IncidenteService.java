package incidentes.service;

import incidentes.dao.IncidenteRepository;
import incidentes.dao.UsuarioRepository;
import incidentes.model.Incidente;
import incidentes.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IncidenteService {
    private final IncidenteRepository dao;
    private final UsuarioRepository usuarioRepository;

    public IncidenteService(IncidenteRepository dao, UsuarioRepository usuarioRepository) {
        this.dao = dao;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Incidente> getIncidentes(){
        return dao.findAll();
    }

    public Incidente getIncidenteByUuid(String uuidStr){
        UUID uuid = UUID.fromString(uuidStr);
        return dao.findByUuid(uuid).orElseThrow(() -> new NoSuchElementException("Incidente não encontrado para o UUID: " + uuidStr));
    }

    public Incidente salvar(Incidente inc) {
        if (inc.getTitulo() == null || inc.getTitulo().trim().length() < 5) {
            throw new IllegalArgumentException("O titulo deve ter pelo menos 5 caracteres");
        }
        if (inc.getDescricao() == null || inc.getDescricao().trim().length() < 10) {
            throw new IllegalArgumentException("A descricao deve ter pelo menos 10 caracteres");
        }
        if (inc.getResponsavel() == null || inc.getResponsavel().trim().isEmpty()) {
            throw new IllegalArgumentException("O responsavel e obrigatorio");
        }
        if (inc.getCodigo() != 0) {
            Optional<Incidente> incidenteOriginal = dao.findById(inc.getCodigo());

            if (incidenteOriginal.isPresent()) {
                inc.setUsuario(incidenteOriginal.get().getUsuario());
            }
        } else {
            if (inc.getUsuario() == null || inc.getUsuario().getCodigo() <= 0) {
                throw new IllegalArgumentException("O usuario do incidente e obrigatorio");
            }
            Usuario usuario = usuarioRepository.findById(inc.getUsuario().getCodigo())
                    .orElseThrow(() -> new NoSuchElementException("Usuário do incidente não encontrado"));
            inc.setUsuario(usuario);
        }
        inc.setTitulo(inc.getTitulo().trim());
        inc.setResponsavel(inc.getResponsavel().trim());
        inc.setDescricao(inc.getDescricao().trim());

        return dao.save(inc);
    }

    public Incidente updateIncidenteByUuid(Incidente inc){
        if(inc.getUuid() == null){
            throw new IllegalArgumentException("UUID é obrigatorio");
        }

        Incidente existente= getIncidenteByUuid(inc.getUuid().toString());
        inc.setCodigo(existente.getCodigo());
        inc.setUuid(existente.getUuid());
        if (inc.getUsuario() == null) {
            inc.setUsuario(existente.getUsuario());
        } else {
            Usuario usuario = usuarioRepository.findById(inc.getUsuario().getCodigo())
                    .orElseThrow(() -> new NoSuchElementException("Usuário do incidente não encontrado"));
            inc.setUsuario(usuario);
        }

        return dao.save(inc);
    }

    public boolean excluirPorUuid(String uuidStr) {
        Incidente inc = getIncidenteByUuid(uuidStr);
        dao.delete(inc);
        return true;
    }



    public long contarNaoResolvidos(int usuarioId) {
        return dao.countByStatusIgnoreCaseAndUsuario_Codigo("Não resolvido", usuarioId);

    }

    public long contarAltaRelevancia(int usuarioId) {
        return dao.countByRelevanciaIgnoreCaseAndUsuario_Codigo("Alta", usuarioId);
    }

    public long contarEmAndamento(int usuarioId) {
        return dao.countByStatusIgnoreCaseAndUsuario_Codigo("Em andamento", usuarioId);
    }

    public long contarResolvidos(int usuarioId) {
        return dao.countByStatusIgnoreCaseAndUsuario_Codigo("Resolvido", usuarioId);
    }

    public Map<String, Integer> contarIncidentesPorRelevancia(int usuarioId) {
        return converterResultadoParaMapa(dao.getEstatisticasRelevanciaRaw(usuarioId));
    }

    public List<Incidente> listarRecentes(int usuarioId) {
        return dao.findTop3ByUsuario_CodigoOrderByCodigoDesc(usuarioId);
    }

    public List<Incidente> listarTodos(int usuarioId) {
        return dao.findByUsuario_CodigoOrderByCodigoDesc(usuarioId);
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
