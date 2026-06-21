package controller;

import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import service.IncidenteService;

//indica que é uma classe controller que processa requisições
@Controller
public class PaginaInicialController {

    private final IncidenteService service;

    public PaginaInicialController(IncidenteService service){
        this.service= service;
    }

    //mepeia as url's para disparar esse metodo quando acessados
    @GetMapping({"/pagina_inicial", "/inicio", "/"})
    public String exibirPaginaInicial(
            @SessionAttribute(value = "usuarioLogado", required = false) Usuario usuarioLogado, Model model) {

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        int idLogado = usuarioLogado.getCodigo();

        model.addAttribute("nao resolvidos", service.contarNaoResolvidos(idLogado));
        model.addAttribute("emAndamento", service.contarEmAndamento(idLogado));
        model.addAttribute("resolvidos", service.contarResolvidos(idLogado));
        model.addAttribute("listaRecentes", service.listarRecentes(idLogado));

        return "pagina_inicial";
    }
}