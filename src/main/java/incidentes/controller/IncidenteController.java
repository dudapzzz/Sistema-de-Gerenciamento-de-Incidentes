package incidentes.controller;

import incidentes.model.Incidente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import incidentes.service.IncidenteService;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
    public ResponseEntity<List<Incidente>> listar(@RequestParam(required = false)UUID usuarioUuid) {
        List<Incidente> incidentes = (usuarioUuid != null)
                ? service.listarTodosPorUsuarioUuid(usuarioUuid.toString())
                : service.getIncidentes();
        return ResponseEntity.ok(incidentes);
    }


    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar incidente por UUID", description = "Retorna as informacoes de um incidente com base no UUID")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Incidente encontrado"),
            @ApiResponse(responseCode = "404", description = "Incidente não encontrado")
    })
    public ResponseEntity<?> getIncidenteByUuid(@PathVariable UUID uuid){

        Incidente incidente = service.getIncidenteByUuid(uuid.toString());
        return ResponseEntity.ok(incidente);
    }

    @PostMapping
    @Operation(summary = "Criar incidente", description = "Cadastra um novo incidente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Incidente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<?> criar(@RequestBody @Valid Incidente incidente, UriComponentsBuilder uriBuilder) {

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
    public ResponseEntity <Incidente> atualizar (@PathVariable UUID uuid,
                                       @RequestBody @Valid Incidente incidente){
       Incidente atualizado = service.updateIncidenteByUuid(uuid.toString(), incidente);
       return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Excluir incidente por UUID", description = "Remove o incidente identificado pelo UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Incidente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Incidente não encontrado")
    })
    public ResponseEntity<?> excluir(@PathVariable UUID uuid) {
        service.excluirPorUuid(uuid.toString());
        return ResponseEntity.noContent().build();
    }

    //endpoints de estatistica

    @GetMapping("/estatisticas/status")
    @Operation(summary = "Obter estatísticas por status", description = "Retorna a contagem de incidentes agrupados por status")
    @ApiResponse(responseCode = "200", description = "Estatísticas obtidas com sucesso")
    public ResponseEntity<Map<String, Integer>> estatisticasStatus(@RequestParam(required = false) UUID usuarioUuid) {
        return ResponseEntity.ok(service.contarIncidentesPorStatus(usuarioUuid));
    }

    @GetMapping("/estatisticas/relevancia")
    @Operation(summary = "Obter estatísticas por relevância", description = "Retorna a contagem de incidentes agrupados por nível de relevância")
    @ApiResponse(responseCode = "200", description = "Estatísticas obtidas com sucesso")
    public ResponseEntity<Map<String, Integer>> estatisticasRelevancia(@RequestParam(required = false) UUID usuarioUuid) {
        return ResponseEntity.ok(service.contarIncidentesPorRelevancia(usuarioUuid));
    }
}
