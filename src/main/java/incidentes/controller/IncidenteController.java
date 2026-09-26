package incidentes.controller;

import incidentes.model.Incidente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import incidentes.service.IncidenteService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/incidente")
@Tag(name= "Incidentes", description = "Rotas para gerenciamento de incidentes")

public class IncidenteController{
    private final IncidenteService service;

    public IncidenteController(IncidenteService service){
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os incidentes", description = "Retorna uma lista contendo os incidentes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso")
    public ResponseEntity<List<Incidente>> getIncidentes(){
        return ResponseEntity.ok(service.getIncidentes());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar incidentes por usuário", description = "Retorna os incidentes acssociados a um usuário")
    @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso")
    public ResponseEntity<List<Incidente>> listarPorUsuario(@PathVariable int usuarioId){
       return ResponseEntity.ok(service.listarTodos(usuarioId));
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar incidente por UUID", description = "Retorna as informacoes de um incidente com base no UUID")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Incidente encontrado"),
            @ApiResponse(responseCode = "404", description = "Incidente não encontrado")
    })
    public ResponseEntity<?> getIncidenteByUuid(@PathVariable String uuid){
        try{
            Incidente incidente = service.getIncidenteByUuid(uuid);
            return ResponseEntity.ok(incidente);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    @PostMapping
    @Operation(summary = "Criar incidente", description = "Cadastra um novo incidente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Incidente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<?> criar(@RequestBody Incidente incidente) {
        try {
            if (incidente.getCodigo() == 0 && (incidente.getStatus() == null || incidente.getStatus().trim().isEmpty())) {
                incidente.setStatus("Não Resolvido");
            }
            Incidente salvo = service.salvar(incidente);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    @Operation(summary = "Atualizar Incidente", description = "Atualiza os dados de um incidente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incidente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Inciente não encontrado")
    })
    public ResponseEntity<?> atualizar(@RequestBody Incidente incidente){
        try{
            Incidente atualizado = service.updateIncidenteByUuid(incidente);
            return ResponseEntity.ok(atualizado);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Excluir incidente por UUID", description = "Remove o incidente identificado pelo UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Incidente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Incidente não encontrado")
    })
    public ResponseEntity<?> excluir(@PathVariable String uuid) {
        try {
            service.excluirPorUuid(uuid);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //endpoint de estatistica

    @GetMapping("/estatisticas/status/{usuarioId}")
    @Operation(summary = "Obter estatísticas por status", description = "Retorna a contagem de incidentes agrupados por status para o usuário informado")
    @ApiResponse(responseCode = "200", description = "Estatísticas obtidas com sucesso")
    public ResponseEntity<Map<String, Integer>> estatisticasStatus(@PathVariable int usuarioId) {
        return ResponseEntity.ok(service.contarIncidentesPorStatus(usuarioId));
    }

    @GetMapping("/estatisticas/relevancia/{usuarioId}")
    @Operation(summary = "Obter estatísticas por relevância", description = "Retorna a contagem de incidentes agrupados por nível de relevância para o usuário informado")
    @ApiResponse(responseCode = "200", description = "Estatísticas obtidas com sucesso")
    public ResponseEntity<Map<String, Integer>> estatisticasRelevancia(@PathVariable int usuarioId) {
        return ResponseEntity.ok(service.contarIncidentesPorRelevancia(usuarioId));
    }
}
