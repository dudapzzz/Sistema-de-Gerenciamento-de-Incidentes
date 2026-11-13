package incidentes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
public class RelatorioService {

    private final IncidenteService incidenteService;

    public RelatorioService(IncidenteService incidenteService) {
        this.incidenteService = incidenteService;
    }

    @Transactional(readOnly = true)
    public RelatorioResposta gerarPorUsuario(UUID usuarioUuid) {
        Map<String, Long> porStatus = incidenteService.contarPorStatus(usuarioUuid);
        Map<String, Long> porCriticidade =
                incidenteService.contarPorSeveridade(usuarioUuid);

        long naoResolvidos = porStatus.getOrDefault("NAO_RESOLVIDO", 0L);
        long emAndamento = porStatus.getOrDefault("EM_ANDAMENTO", 0L);
        long resolvidos = porStatus.getOrDefault("RESOLVIDO", 0L);
        long total = naoResolvidos + emAndamento + resolvidos;
        long altaCriticidade = porCriticidade.getOrDefault("ALTA", 0L);

        return new RelatorioResposta(
                usuarioUuid,
                naoResolvidos,
                emAndamento,
                resolvidos,
                altaCriticidade,
                total,
                porStatus,
                porCriticidade
        );
    }

    public record RelatorioResposta(
            UUID usuarioUuid,
            long qtdNaoResolvidos,
            long qtdEmAndamento,
            long qtdResolvidos,
            long qtdAltaCriticidade,
            long totalIncidentes,
            Map<String, Long> incidentesPorStatus,
            Map<String, Long> incidentesPorCriticidade
    ) {}
}
