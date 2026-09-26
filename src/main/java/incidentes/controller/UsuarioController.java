package incidentes.controller;

import incidentes.model.Usuario;
import incidentes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Rotas para gerenciamento dos usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public List<Usuario> listar() {
        return this.usuarioService.getUsuarios();
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar usuário por UUID")
    public ResponseEntity<Usuario> getUsuarioByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(this.usuarioService.getUsuarioUUID(uuid));
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Usuario> salvar(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder) {
        Usuario usuarioSalvo = this.usuarioService.inserir(usuario);
        URI uri = uriBuilder.path("/usuarios/uuid/{uuid}").buildAndExpand(usuarioSalvo.getUuid()).toUri();
        return ResponseEntity.created(uri).body(usuarioSalvo);
    }

    @PutMapping("/uuid")
    @Operation(summary = "Atualizar um usuário")
    public ResponseEntity<Usuario> atualizarUUID(@RequestBody @Valid Usuario usuario) {
        return ResponseEntity.ok(this.usuarioService.atualizarUUID(usuario));
    }

    @DeleteMapping("/uuid/{uuid}")
    @Operation(summary = "Deletar usuário por UUID")
    public ResponseEntity<?> deletarUUID(@PathVariable String uuid) {
        this.usuarioService.deletarUUID(uuid);
        return ResponseEntity.noContent().build();
    }
}
