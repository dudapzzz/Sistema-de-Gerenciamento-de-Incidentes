package incidentes.controller;

import incidentes.model.Ativo;
import incidentes.service.AtivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ativos")
@Tag(name = "Ativos", description = "rotas para gerenciamento de ativos de ti")
public class AtivoController {
    private final AtivoService ativoService;

    public AtivoController(AtivoService ativoService) {
        this.ativoService = ativoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os ativos ou filtrar pelo uuid do usuario")
    public ResponseEntity<List<Ativo>> listar(
            @RequestParam(required = false) UUID usuarioUuid
    ) {
        List<Ativo> ativos = usuarioUuid == null
                ? ativoService.listarTodos()
                : ativoService.listarPorUsuarioUuid(usuarioUuid);

        return ResponseEntity.ok(ativos);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar um ativo por seu UUID")
    public ResponseEntity<Ativo> buscarPorUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(ativoService.buscarPorUuid(uuid));
    }

    @PostMapping
    @Operation(summary = "cadastrar um novo ativo")
    public ResponseEntity<Ativo> salvar(@RequestBody @Valid Ativo ativo, UriComponentsBuilder uriBuilder) {
        Ativo ativoSalvo = this.ativoService.salvar(ativo);

        URI uri = uriBuilder.path("/ativos/{uuid}").buildAndExpand(ativoSalvo.getUuid()).toUri();
        return ResponseEntity.created(uri).body(ativoSalvo);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Atualizar um ativo por uuid")
    public ResponseEntity<Ativo> atualizar(
            @PathVariable UUID uuid,
            @RequestBody @Valid Ativo ativo
    ) {
        return ResponseEntity.ok(ativoService.atualizarPorUuid(uuid, ativo));
    }

    @DeleteMapping("/uuid/{uuid}")
    @Operation(summary = "Excluir um ativo por uuid")
    public ResponseEntity<Void> excluir(@PathVariable UUID uuid) {
        this.ativoService.excluirPorUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}