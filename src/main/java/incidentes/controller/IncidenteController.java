package incidentes.controller;

import incidentes.model.Incidente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import incidentes.service.IncidenteService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/incidente")

public class IncidenteController{
    private final IncidenteService service;

    public IncidenteController(IncidenteService service){
        this.service = service;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Incidente>> listarPorUsuario(@PathVariable int usuarioId){
        List<Incidente>incidentes = service.listarTodos(usuarioId);
        return ResponseEntity.ok(incidentes);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Incidente> buscarPorCodigo(@PathVariable int codigo){
        Incidente incidente = service.buscarPorCodigo(codigo);

        if(incidente == null){
            return ResponseEntity.notFound().build(); //404
        }
        return ResponseEntity.ok(incidente);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Incidente incidente){
        try{
            if (incidente.getCodigo() == 0){
                incidente.setStatus("Não Resolvido");
            }
            Incidente salvo = service.salvar(incidente);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo); //201
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody Incidente incidente){
        try{
            Incidente salvo = service.salvar(incidente);
            return ResponseEntity.ok(salvo); //200
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage()); // 400
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluir(@PathVariable int codigo){
        boolean excluido = service.excluir(codigo);
        if(excluido){
            return ResponseEntity.noContent().build(); //204
        }
        return ResponseEntity.notFound().build(); // 404
    }

    //endpoint de estatistica
    @GetMapping("/estatisticas/status/{usuarioId}")
    public ResponseEntity<Map<String,Integer>> estatisticasStatus(@PathVariable int usuarioId){
        return ResponseEntity.ok(service.contarIncidentesPorStatus(usuarioId));
    }

    @GetMapping("/estatisticas/relevancia/{usuarioId}")
    public ResponseEntity<Map<String, Integer>> estatisticasRelevancia(@PathVariable int usuarioId){
        return ResponseEntity.ok(service.contarIncidentesPorRelevancia(usuarioId));
    }

}
