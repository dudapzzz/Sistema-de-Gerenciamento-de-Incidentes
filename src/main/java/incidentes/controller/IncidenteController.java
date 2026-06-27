package incidentes.controller;

import model.Incidente;
import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.IncidenteService;
import util.GerenciadorLogs;

@Controller
@RequestMapping("/incidente")

public class IncidenteController{
    private final IncidenteService service;

    public IncidenteController(IncidenteService service){
        this.service = service;
    }

    @GetMapping("/listar")
    public String listar(@SessionAttribute(value="usuarioLogado", required = false) Usuario usuarioLogado,
                         Model model){
        if(usuarioLogado == null){
            return "redirect: /login.jsp";
        }
        // addatribute insere par chave-valor, passa os dados p interface
        model.addAttribute("listaIncidentes", service.listarTodos(usuarioLogado.getCodigo()));
        return "lista_incidentes";
    }
    @GetMapping("/form")
    public String exibirFormulario(
            @RequestParam(value = "id", required = false) Integer id,
            @SessionAttribute(value = "usuarioLogado", required = false) Usuario usuarioLogado,
            Model model){
        if(usuarioLogado == null){
            return "redirect:/login.jsp";
        }

        if(id != null){
            Incidente incExistente = service.buscarPorCodigo(id);
            model.addAttribute("incidente", incExistente);
        } else{
            model.addAttribute("incidente", new Incidente());
        }
        return "form_incidente";
    }

    @GetMapping("/excluir")
    public String excluir(
            @RequestParam("id")int id,
            @SessionAttribute(value = "usuarioLogado", required = false) Usuario usuarioLogado){
        if(usuarioLogado == null){
            return "redirect:/login.jsp";
        }
        service.excluir(id);
        return "redirect:/incidente/listar";
    }

    @GetMapping("/detalhes")
    public String exibirDetalhes(
            @RequestParam("id") int id,
            @SessionAttribute(value = "usuarioLogado", required = false) Usuario usuarioLogado,
            Model model){
        if (usuarioLogado == null){
            return "redirect:/login.jsp";
        }
        Incidente incidente = service.buscarPorCodigo(id);
        if(incidente != null){
            model.addAttribute("incidente", incidente);
            return "detalhe_incidente";
        }
        return "redirect:/incidente/listar";
    }

    @PostMapping("/salvar")
    public String salvar(
            Incidente incidente, @SessionAttribute(value = "usuarioLogado", required = false) Usuario usuarioLogado,
            Model model){
        if(usuarioLogado == null) {
            return "redirect:/login.jsp";
        }
        incidente.setUsuarioId(usuarioLogado.getCodigo());

        if(incidente.getCodigo() == 0){
            incidente.setStatus("Não resolvido");
        }
        boolean sucesso = service.salvar(incidente);

        if(sucesso){

            GerenciadorLogs.registrar("Incidente '" +incidente.getTitulo() + "' foi salvo pelo usuario de ID: " + usuarioLogado.getCodigo());
            return "redirect:/incidente/listar";
        }else{
            model.addAttribute("incidente", incidente);
            model.addAttribute("erro", "Erro ao registrar incidente, o título deve ter mais de 5 caracteres e a descrição, mais de 10");
            return "form_incidente";

        }
    }
}
