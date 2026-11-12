package incidentes.service;

import incidentes.dao.IncidenteRepository;
import incidentes.dao.UsuarioRepository;
import incidentes.model.Criticidade;
import incidentes.model.Incidente;
import incidentes.model.StatusIncidente;
import incidentes.model.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class IncidenteService {

    private final IncidenteRepository incidenteRepository;
    private final UsuarioRepository usuarioRepository;

    public IncidenteService(
            IncidenteRepository incidenteRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.incidenteRepository = incidenteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Incidente> listarTodos() {
        return incidenteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Incidente> listarPorUsuarioUuid(UUID usuarioUuid) {
        buscarUsuario(usuarioUuid);
        return incidenteRepository.findByUsuario_UuidOrderByCodigoDesc(usuarioUuid);
    }

    @Transactional(readOnly = true)
    public Incidente buscarPorUuid(UUID uuid) {
        return incidenteRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Incidente não encontrado: " + uuid
                ));
    }

    @Transactional
    public Incidente salvar(Incidente incidente) {
        if (incidente.getUsuario() == null
                || incidente.getUsuario().getUuid() == null) {
            throw new IllegalArgumentException(
                    "O UUID do usuário do incidente é obrigatório."
            );
        }

        incidente.setUsuario(buscarUsuario(incidente.getUsuario().getUuid()));
        normalizarCampos(incidente);
        return incidenteRepository.save(incidente);
    }

    @Transactional
    public Incidente atualizarPorUuid(UUID uuid, Incidente dados) {
        Incidente existente = buscarPorUuid(uuid);

        existente.setTitulo(dados.getTitulo());
        existente.setDescricao(dados.getDescricao());
        existente.setSeveridade(dados.getSeveridade());
        existente.setDataIncidente(dados.getDataIncidente());
        existente.setStatus(dados.getStatus());
        existente.setResponsavel(dados.getResponsavel());

        if (dados.getUsuario() != null && dados.getUsuario().getUuid() != null) {
            existente.setUsuario(buscarUsuario(dados.getUsuario().getUuid()));
        }

        normalizarCampos(existente);
        return incidenteRepository.save(existente);
    }

    @Transactional
    public void excluirPorUuid(UUID uuid) {
        incidenteRepository.delete(buscarPorUuid(uuid));
    }

    @Transactional(readOnly = true)
    public Map<String, Long> contarPorStatus(UUID usuarioUuid) {
        buscarUsuario(usuarioUuid);
        return converterResultado(
                incidenteRepository.getEstatisticasStatusRaw(usuarioUuid)
        );
    }

    @Transactional(readOnly = true)
    public Map<String, Long> contarPorSeveridade(UUID usuarioUuid) {
        buscarUsuario(usuarioUuid);
        return converterResultado(
                incidenteRepository.getEstatisticasSeveridadeRaw(usuarioUuid)
        );
    }

    @Transactional(readOnly = true)
    public long contarPorStatus(UUID usuarioUuid, StatusIncidente status) {
        buscarUsuario(usuarioUuid);
        return incidenteRepository.countByStatusAndUsuario_Uuid(status, usuarioUuid);
    }

    @Transactional(readOnly = true)
    public long contarPorSeveridade(UUID usuarioUuid, Criticidade severidade) {
        buscarUsuario(usuarioUuid);
        return incidenteRepository.countBySeveridadeAndUsuario_Uuid(
                severidade,
                usuarioUuid
        );
    }

    private Usuario buscarUsuario(UUID uuid) {
        return usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário não encontrado: " + uuid
                ));
    }

    private Map<String, Long> converterResultado(List<Object[]> resultado) {
        Map<String, Long> mapa = new LinkedHashMap<>();

        for (Object[] linha : resultado) {
            mapa.put(String.valueOf(linha[0]), ((Number) linha[1]).longValue());
        }

        return mapa;
    }

    private void normalizarCampos(Incidente incidente) {
        if (incidente.getTitulo() != null) {
            incidente.setTitulo(incidente.getTitulo().trim());
        }
        if (incidente.getDescricao() != null) {
            incidente.setDescricao(incidente.getDescricao().trim());
        }
        if (incidente.getResponsavel() != null) {
            incidente.setResponsavel(incidente.getResponsavel().trim());
        }
    }
}
