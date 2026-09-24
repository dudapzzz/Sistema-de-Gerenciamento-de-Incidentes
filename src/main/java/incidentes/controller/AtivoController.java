package incidentes.controller;

import incidentes.model.Ativo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import incidentes.service.AtivoService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/ativo")
@Tag(name="Ativos", description = "Rota para gerenciamento de ativos")

public class AtivoController {

    private final AtivoService service;

    public AtivoController(AtivoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os ativos", description = "Retorna uma lista com todos os ativos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso")

    public ResponseEntity<List<Ativo>> getAtivos() {
        return ResponseEntity.ok(service.getAtivos());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar ativos por usuario", description = "Retorna todos os ativos associados ao usuário")
    @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso")
    public ResponseEntity<List<Ativo>> listarPorUsuario(@PathVariable int usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar ativo por UUID", description = "Retorna os detalhes do ativo com base no UUID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ativo encontrado"),
            @ApiResponse(responseCode = "404", description = "Ativo não encontrado")
    })

    public ResponseEntity<?> getAtivoByUuid(@PathVariable String uuid){
       try{
           Ativo ativo = service.getAtivoByUuid(uuid);
           return ResponseEntity.ok(ativo);
       }catch(NoSuchElementException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
    }

    @PostMapping
    @Operation(summary = "Cadastrar ativo", description = "Cria um novo ativo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ativo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos")
    })
    public ResponseEntity<?> saveAtivo(@RequestBody Ativo ativo) {
        try {
            Ativo salvo = service.saveAtivo(ativo);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    @Operation(summary = "Atualizar ativo", description = "Atualiza as informações de um ativo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ativo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos campos"),
            @ApiResponse(responseCode = "404", description = "Ativo não encontrado")
    })
    public ResponseEntity<?> updateAtivo(@RequestBody Ativo ativo) {
        try {
            Ativo atualizado = service.updateAtivoByUuid(ativo);
            return ResponseEntity.ok(atualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Excluir ativo por UUID", description = "Remove o ativo identificado pelo UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ativo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ativo não encontrado")
    })
    public ResponseEntity<?> deleteAtivo(@PathVariable String uuid) {
        try {
            service.excluirPorUuid(uuid);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
