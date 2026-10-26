package incidentes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import incidentes.model.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import incidentes.service.LoginService;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Rota para autenticação de usuários")
public class LoginController{
    private final LoginService service;

    public LoginController(LoginService service){
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Autenticar usuario", description = "realiza a verficiacao de email e senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "E-mail ou senha incorretos")
    })
    public ResponseEntity<Usuario> autenticar(@RequestBody @Valid LoginRequest request){
      Usuario usuario = service.autenticar(request.email(), request.senha());

      usuario.setSenha(null);
      return ResponseEntity.ok(usuario);
    }

    public record LoginRequest(
            @NotBlank(message = "o email é obrigatorio")
            @Email(message = "o formato do email é inválido")
            String email,

            @NotBlank(message = "a senha é obrigatória")
            String senha
    ){   }


}