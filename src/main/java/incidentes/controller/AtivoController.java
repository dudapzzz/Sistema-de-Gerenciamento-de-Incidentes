package incidentes.controller;

import incidentes.model.Ativo;
import incidentes.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import incidentes.service.AtivoService;

@Controller
@RequestMapping("/ativo")
public class AtivoController {

    private final AtivoService service;

    public AtivoController(AtivoService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public String listarAtivos(
            @SessionAttribute(value = "usuarioLogado", required = false) Usuario usuarioLogado,
            Model model) {

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        int idLogado = usuarioLogado.getCodigo();
        model.addAttribute("listaAtivos", service.listarPorUsuario(idLogado));

        return "lista_ativos";
    }

    @GetMapping("/excluir")
    public String excluirAtivo(
            @RequestParam("id") int id,
            @SessionAttribute(value = "usuarioLogado", required = false) Usuario usuarioLogado) {

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        service.excluir(id);
        return "redirect:/ativo/listar";
    }

    @GetMapping("/editar")
    public String carregarEdicao(
            @RequestParam("id") int id,
            @SessionAttribute(value = "usuarioLogado", required = false) Usuario usuarioLogado,
            Model model) {

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        Ativo ativo = service.buscarPorId(id);
        model.addAttribute("ativo", ativo);

        return "form_ativo";
    }

    @GetMapping("/form")
    public String exibirFormulario(
            @SessionAttribute(value = "usuarioLogado", required = false) Usuario usuarioLogado,
            Model model) {

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        model.addAttribute("ativo", new Ativo());

        return "form_ativo";
    }

    @PostMapping("/salvar")
    public String salvarAtivo(
            Ativo ativo,
            @SessionAttribute(value = "usuarioLogado", required = false) Usuario usuarioLogado,
            Model model) {

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        ativo.setUsuarioId(usuarioLogado.getCodigo());

        boolean sucesso = service.salvar(ativo);

        if (!sucesso) {
            model.addAttribute("ativo", ativo);
            model.addAttribute("erro", "Dados inválidos");
            return "form_ativo";
        }

        return "redirect:/ativo/listar";
    }
}