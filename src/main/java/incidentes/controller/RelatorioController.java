package incidentes.controller;

import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import service.IncidenteService;

import java.util.Map;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController{

    private final IncidenteService service;

    public RelatorioController(IncidenteService service){
        this.service = service;
    }

    @GetMapping
    public String gerarRelatorio(@RequestParam(value = "tipo", required = false) String tipo,
                                 @SessionAttribute(value = "usuarioLogado", required = false) Usuario usuarioLogado,
                                 Model model){
        if(usuarioLogado == null){
            return "redirect:/login";
        }
        int idLogado= usuarioLogado.getCodigo();

        long naoResolvidos = service.contarNaoResolvidos(idLogado);
        long resolvidos = service.contarResolvidos(idLogado);
        long altaRelevancia = service.contarAltaRelevancia(idLogado);

        Map<String, Integer> estatisticasStatus = service.contarIncidentesPorStatus(idLogado);
        Map<String, Integer> estatisticasRelevancia = service.contarIncidentesPorRelevancia(idLogado);
        int totalIncidentes= service.listarTodos(idLogado).size();

        //passa dados do controller para as interfaces
        model.addAttribute("tipoRelatorio", tipo);
        model.addAttribute("qtdNaoResolvidos", naoResolvidos);
        model.addAttribute("qtdResolvidos", resolvidos);
        model.addAttribute("qtdAltaRelevancia", altaRelevancia);
        model.addAttribute("mapStatus", estatisticasStatus);
        model.addAttribute("mapRelevancia", estatisticasRelevancia);
        model.addAttribute("totalIncidentes", totalIncidentes);

        return "relatorios";
    }
}