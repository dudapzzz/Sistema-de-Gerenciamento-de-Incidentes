package incidentes.controller;

import incidentes.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import incidentes.service.UsuarioService;

@Controller
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService= usuarioService;
    }

    @GetMapping("/cadastro_usuario")
    public String exibirFormulario(){
        return "cadastro_usuario";
    }

    @PostMapping("/cadastro_usuario")
    public String cadastrarUsuario(Usuario novoUsuario, Model model){
        try {
            usuarioService.inserir(novoUsuario);
            return "redirect:/login?msg=sucesso";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastro_usuario";
        }
    }

    @PostMapping("/usuarios")
    @ResponseBody
    public ResponseEntity<?> cadastrar(@RequestBody Usuario novoUsuario){
        try{
            Usuario usuarioSalvo= usuarioService.inserir(novoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo); //201
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage()); //400
        }
    }

    @PostMapping("/usuarios/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha){
        try{
            Usuario usuario = usuarioService.autenticar(email,senha);
            if(usuario== null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos"); //401
            }
            return ResponseEntity.ok(usuario); //200
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage()); //400
        }
    }

    @GetMapping("/usuarios/{id}")
    @ResponseBody
    public ResponseEntity<Usuario> buscarPorId(@PathVariable int id){
        Usuario usuario= usuarioService.buscarPorId(id);
        if(usuario == null){
            return ResponseEntity.notFound().build();//404
        }
        return ResponseEntity.ok(usuario); //200
    }
}
