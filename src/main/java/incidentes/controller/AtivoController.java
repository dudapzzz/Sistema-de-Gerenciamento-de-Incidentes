package incidentes.controller;

import incidentes.model.Ativo;
import incidentes.service.AtivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity<List<Ativo>> listar(@RequestParam(required = false)UUID usuarioUuid) {

        List<Ativo> ativos = (usuarioUuid != null)
            ? this.ativoService.listarPorUsuarioUuid(usuarioUuid.toString())
            : this.ativoService.getAtivos();

        return ResponseEntity.ok(ativos);

    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar um ativo por seu UUID")
    public ResponseEntity<Ativo> getAtivoByUuid(@PathVariable UUID uuid) {
        Ativo ativo = this.ativoService.getAtivoByUuid(uuid.toString());
        return ResponseEntity.ok(ativo);
    }

    @PostMapping
    @Operation(summary = "cadastrar um novo ativo")
    public ResponseEntity<Ativo> salvar(@RequestBody @Valid Ativo ativo, UriComponentsBuilder uriBuilder) {
        Ativo ativoSalvo = this.ativoService.saveAtivo(ativo);

        URI uri = uriBuilder.path("/ativos/{uuid}").buildAndExpand(ativoSalvo.getUuid()).toUri();
        return ResponseEntity.created(uri).body(ativoSalvo);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Atualizar um ativo por uuid")
    public ResponseEntity<Ativo> atualizar(@PathVariable String uuid,
                                               @RequestBody @Valid Ativo ativo) {
        Ativo ativoAtualizado = this.ativoService.updateAtivoByUuid(uuid, ativo);
        return ResponseEntity.ok(ativoAtualizado);
    }

    @DeleteMapping("/uuid/{uuid}")
    @Operation(summary = "Excluir um ativo por uuid")
    public ResponseEntity<Void> deletar(@PathVariable String uuid) {
        this.ativoService.excluirPorUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}