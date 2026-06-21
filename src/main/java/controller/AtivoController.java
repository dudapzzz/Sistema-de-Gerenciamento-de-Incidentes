package controller;

import model.Ativo;
import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ativo")
public class AtivoController {
    private final AtivoService service = new AtivoService();

    @GetMapping("/listar")
    public String listarAtivos(@SessionAttribute (value="usuarioLogado", required = false)Usuario usuarioLogado, Model model){

        if(usuarioLogado == null){
            return "redirect: /login";
        }
        int idLogado = usuarioLogado.getCodigo();
        model.addAttribute("listaAtivos", service.listarPorUsuario(idLogado));
        return "lista_ativos";
    }

    @RequestMapping
    @GetMapping("/excluir")
    public String excluirAtivo(@RequestParam("id") int id){
        service.excluir(id);
        return "redirect:/ativo/listar";
    }

    @GetMapping("/editar")
    public String carregarEdicao(@RequestParam("id") int id, Model model){
        Ativo ativo = service.buscarPorId(id);
        model.addAttribute("ativo", ativo);

        return "form_ativo";
    }

    @PostMapping("/salvar")
    public String salvarAtivo(Ativo ativo, @SessionAttribute("usuarioLogado") Usuario usuarioLogado){
        ativo.setUsuario(usuarioLogado);
        service.salvar(ativo);

        return "redirect:/ativo/listar";
    }
}