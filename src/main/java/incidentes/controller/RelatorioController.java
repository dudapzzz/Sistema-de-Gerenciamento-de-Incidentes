package incidentes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import incidentes.service.IncidenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/relatorios")
@Tag(name = "Relatórios", description = "Endpoints para geração de dados estatísticos e relatórios")
public class RelatorioController {

    private final IncidenteService service;

    public RelatorioController(IncidenteService service) {
        this.service = service;
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Gerar dados do relatório por usuário", description = "Retorna o consolidado estatístico de incidentes para exibição de relatórios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do relatório gerados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Map<String, Object>> gerarRelatorio(@PathVariable UUID usuarioUuid,
            @RequestParam(value = "tipo", required = false) String tipo) {

        RelatorioResposta relatorio = service.gerarRelatorio(usuarioUuid, tipo);

        return ResponseEntity.ok(relatorio);
    }

    public record RelatorioResposta(String tipoRelatorio,
                                    long qtdNaoResolvidos, long qtdResolvidos,
                                    long qtdAltaRelevancia, long totalIncidentes,
                                    Map<String, Integer> mapStatus,
                                    Map<String, Integer> mapRelevancia
                                    ){ }
}