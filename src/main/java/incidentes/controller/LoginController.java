package incidentes.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import incidentes.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import incidentes.service.LoginService;

@Controller
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Rota para autenticação de usuários")
public class LoginController{
    private final LoginService service;

    public LoginController(LoginService service){
        this.service = service;
    }

    @PostMapping

    public ResponseEntity<?> autenticar(@RequestParam("email") String email, @RequestParam("senha") String senha){
        if(email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()){
            return ResponseEntity.badRequest().body("E-mail e senha são obrigatórios");
        }

        Usuario usuario = service.autenticar(email,senha);

        if(usuario != null){
            return ResponseEntity.ok(usuario);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha incorretos");
        }
    }


}