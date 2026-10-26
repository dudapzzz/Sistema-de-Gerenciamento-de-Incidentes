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

        List<Usuario> usuarios = this.usuarioService.getUsuarios();

        usuarios.forEach(u -> u.setSenha(null));
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar usuário por UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> getUsuarioByUuid(@PathVariable UUID uuid) {
        Usuario usuario= this.usuarioService.getUsuarioUUID(uuid.toString());
        usuario.setSenha(null);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Usuario> salvar(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder) {
        Usuario usuarioSalvo = this.usuarioService.inserir(usuario);
        URI uri = uriBuilder.path("/usuarios/{uuid}").buildAndExpand(usuarioSalvo.getUuid()).toUri();

        usuarioSalvo.setSenha(null);
        return ResponseEntity.created(uri).body(usuarioSalvo);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Atualizar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> atualizar(@PathVariable UUID uuid, @RequestBody @Valid Usuario usuario) {
        Usuario usuarioAtualizado = this.usuarioService.atualizarUUID(uuid.toString(), usuario);
        usuarioAtualizado.setSenha(null);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Deletar usuário por UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID uuid) {
        this.usuarioService.deletarUUID(uuid.toString());
        return ResponseEntity.noContent().build();
    }
}
