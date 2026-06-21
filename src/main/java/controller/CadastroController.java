package controller;

import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.UsuarioService;

@Controller
@RequestMapping("/cadastro_usuario")

public class CadastroController {
    private final UsuarioService usuarioService;

    public CadastroController(usuarioService usuarioService){
        this.usuarioService= usuarioService;
    }

    @GetMapping
    public String exibirFormulario(){
        return "cadastro_usuario";
    }

    @PostMapping
    public String cadastrarUsuario(Usuario novoUsuario, Model model){
        novoUsuario.setAtivo(true);
        System.out.println("chegou no controller:" +novoUsuario.getNome()+ "-" +novoUsuario.getEmail());
        boolean sucesso = usuarioService.inserir(novoUsuario);

        if(sucesso){
            return "redirect:/login?msg=sucesso";
        } else{
            model.addAttribute("erro", "dados inválidos");

            return "cadastro_usuario";
        }
    }

}
