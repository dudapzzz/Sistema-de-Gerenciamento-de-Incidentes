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
import java.util.UUID;

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
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtida com sucesso")
    public ResponseEntity<List<Usuario>> listar() {

        List<Usuario> usuarios = this.usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar usuário por UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> buscarPorUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(usuarioService.buscarPorUuid(uuid));
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Usuario> salvar(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder) {
        Usuario usuarioSalvo = this.usuarioService.salvar(usuario);
        URI uri = uriBuilder.path("/usuarios/{uuid}").buildAndExpand(usuarioSalvo.getUuid()).toUri();
        return ResponseEntity.created(uri).body(usuarioSalvo);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Atualizar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> atualizar(
            @PathVariable UUID uuid,
            @RequestBody @Valid Usuario usuario
    ) {
        return ResponseEntity.ok(usuarioService.atualizarPorUuid(uuid, usuario));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Deletar usuário por UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID uuid) {
        this.usuarioService.excluirPorUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
