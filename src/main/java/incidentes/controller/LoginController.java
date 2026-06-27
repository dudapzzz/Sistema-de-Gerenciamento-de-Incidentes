package incidentes.controller;

import jakarta.servlet.http.HttpSession;
import incidentes.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import incidentes.service.LoginService;

@Controller
public class LoginController{
    private final LoginService service;

    public LoginController(LoginService service){
        this.service = service;
    }

    @GetMapping("/login")
    public String exibirLogin(@RequestParam(value = "msg", required = false) String msg, Model model){
        if("sucesso".equals(msg)){
            model.addAttribute("mensagemSucesso", "Usuario cadastrado com sucesso, faça o seu login!");
        }
        return "../login";
    }

    @PostMapping("/login")
    public String autenticar(
            //serve para extrair valores passados na url
            @RequestParam("email") String email,
            @RequestParam("senha") String senha,
            HttpSession session, Model model){
        Usuario usuario = service.autenticar(email, senha);

        if(usuario != null){
            session.setAttribute("usuarioLogado", usuario);
            return "redirect:/pagina_inicial";
        }else{
            model.addAttribute("erro", "Email ou senha incorretos");
            return "../login";
        }

    }

}