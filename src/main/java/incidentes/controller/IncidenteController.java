package incidentes.controller;

import incidentes.model.Incidente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import incidentes.service.IncidenteService;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/incidentes")
@Tag(name= "Incidentes", description = "Rotas para gerenciamento de incidentes")

public class IncidenteController{
    private final IncidenteService service;

    public IncidenteController(IncidenteService service){
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os incidentes", description = "Retorna uma lista contendo os incidentes cadastrados ou filtra por uuid")
    @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso")
    public ResponseEntity<List<Incidente>> listar(
            @RequestParam(required = false) UUID usuarioUuid
    ) {
        List<Incidente> incidentes = usuarioUuid == null
                ? service.listarTodos()
                : service.listarPorUsuarioUuid(usuarioUuid);

        return ResponseEntity.ok(incidentes);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar incidente por UUID", description = "Retorna as informacoes de um incidente com base no UUID")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Incidente encontrado"),
            @ApiResponse(responseCode = "404", description = "Incidente não encontrado")
    })
    public ResponseEntity<Incidente> buscarPorUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.buscarPorUuid(uuid));
    }

    @PostMapping
    @Operation(summary = "Criar incidente", description = "Cadastra um novo incidente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Incidente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Incidente> criar(@RequestBody @Valid Incidente incidente, UriComponentsBuilder uriBuilder) {

        Incidente salvo = service.salvar(incidente);
        URI uri = uriBuilder.path("/incidentes/{uuid}").buildAndExpand(salvo.getUuid()).toUri();
        return ResponseEntity.created(uri).body(salvo);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Atualizar Incidente", description = "Atualiza os dados de um incidente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incidente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Inciente não encontrado")
    })
    public ResponseEntity<Incidente> atualizar(
            @PathVariable UUID uuid,
            @RequestBody @Valid Incidente incidente
    ) {
        return ResponseEntity.ok(service.atualizarPorUuid(uuid, incidente));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Excluir incidente por UUID", description = "Remove o incidente identificado pelo UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Incidente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Incidente não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable UUID uuid) {
        service.excluirPorUuid(uuid);
        return ResponseEntity.noContent().build();
    }

    //endpoints de estatistica

    @GetMapping("/estatisticas/status")
    @Operation(summary = "Obter estatísticas por status", description = "Retorna a contagem de incidentes agrupados por status")
    @ApiResponse(responseCode = "200", description = "Estatísticas obtidas com sucesso")
    public ResponseEntity<Map<String, Long>> estatisticasStatus(
            @RequestParam UUID usuarioUuid
    ) {
        return ResponseEntity.ok(service.contarPorStatus(usuarioUuid));
    }

    @GetMapping("/estatisticas/severidade")
    @Operation(summary = "Obter estatísticas por severidade", description = "Retorna a contagem de incidentes agrupados por nível de severidade")
    @ApiResponse(responseCode = "200", description = "Estatísticas obtidas com sucesso")
    public ResponseEntity<Map<String, Long>> estatisticasSeveridade(
            @RequestParam UUID usuarioUuid
    ) {
        return ResponseEntity.ok(service.contarPorSeveridade(usuarioUuid));
    }
}
