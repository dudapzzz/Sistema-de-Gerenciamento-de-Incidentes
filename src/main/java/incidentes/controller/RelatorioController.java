package incidentes.controller;

import incidentes.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/relatorios")
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/usuario/{usuarioUuid}")
    @Operation(
            summary = "Gerar relatório de incidentes por usuário",
            description = "Retorna totais por status e criticidade para o usuário informado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relatório gerado"),
            @ApiResponse(responseCode = "400", description = "UUID inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<RelatorioService.RelatorioResposta> gerarRelatorio(
            @PathVariable UUID usuarioUuid
    ) {
        return ResponseEntity.ok(relatorioService.gerarPorUsuario(usuarioUuid));
    }
}
