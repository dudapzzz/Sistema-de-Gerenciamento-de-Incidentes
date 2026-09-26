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
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<Map<String, Object>> gerarRelatorio(
            @PathVariable int usuarioId,
            @RequestParam(value = "tipo", required = false) String tipo) {

        long naoResolvidos = service.contarNaoResolvidos(usuarioId);
        long resolvidos = service.contarResolvidos(usuarioId);
        long altaRelevancia = service.contarAltaRelevancia(usuarioId);

        Map<String, Integer> estatisticasStatus = service.contarIncidentesPorStatus(usuarioId);
        Map<String, Integer> estatisticasRelevancia = service.contarIncidentesPorRelevancia(usuarioId);
        int totalIncidentes = service.listarTodos(usuarioId).size();

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("tipoRelatorio", tipo);
        relatorio.put("qtdNaoResolvidos", naoResolvidos);
        relatorio.put("qtdResolvidos", resolvidos);
        relatorio.put("qtdAltaRelevancia", altaRelevancia);
        relatorio.put("mapStatus", estatisticasStatus);
        relatorio.put("mapRelevancia", estatisticasRelevancia);
        relatorio.put("totalIncidentes", totalIncidentes);

        return ResponseEntity.ok(relatorio);
    }
}