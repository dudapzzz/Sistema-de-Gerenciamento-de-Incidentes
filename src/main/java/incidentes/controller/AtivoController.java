package incidentes.controller;

import incidentes.model.Ativo;
import incidentes.service.AtivoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ativo") // ou /ativos
public class AtivoController {
    private final AtivoService ativoService;

    public AtivoController(AtivoService ativoService) {
        this.ativoService = ativoService;
    }

    @GetMapping
    public List<Ativo> listar() {
        return this.ativoService.getAtivos();
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<Ativo> getAtivoByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(this.ativoService.getAtivoByUuid(uuid));
    }

    @GetMapping("/usuario/{uuid}")
    @Operation(summary = "Listar ativos por UUID do usuário")
    public ResponseEntity<List<Ativo>> listarPorUsuario(@PathVariable String uuid) {
        List<Ativo> ativos = this.ativoService.listarPorUsuarioUuid(uuid);
        return ResponseEntity.ok(ativos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Ativo> salvar(@RequestBody @Valid Ativo ativo, UriComponentsBuilder uriBuilder) {
        Ativo ativoSalvo = this.ativoService.saveAtivo(ativo);
        URI uri = uriBuilder.path("/ativo/uuid/{uuid}").buildAndExpand(ativoSalvo.getUuid()).toUri();
        return ResponseEntity.created(uri).body(ativoSalvo);
    }

    @PutMapping("/uuid")
    @Transactional
    public ResponseEntity<Ativo> atualizarUUID(@RequestBody @Valid Ativo ativo) {
        Ativo ativoAtualizado = this.ativoService.updateAtivoByUuid(ativo);
        return ResponseEntity.ok(ativoAtualizado);
    }

    @DeleteMapping("/uuid/{uuid}")
    @Transactional
    public ResponseEntity<Void> deletarUUID(@PathVariable String uuid) {
        this.ativoService.excluirPorUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}