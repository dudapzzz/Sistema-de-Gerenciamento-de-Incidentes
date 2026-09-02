package incidentes.controller;

import incidentes.model.Ativo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import incidentes.service.AtivoService;

import java.util.List;

@Controller
@RequestMapping("/ativo")
public class AtivoController {

    private final AtivoService service;

    public AtivoController(AtivoService service) {
        this.service = service;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Ativo>> listarPorUsuario(@PathVariable int usuarioId) {
        List<Ativo> ativos = service.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(ativos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ativo> buscarPorId(@PathVariable int id){
        Ativo ativo = service.buscarPorId(id);
        if(ativo==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ativo);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Ativo ativo){
        try{
            Ativo salvo = service.salvar(ativo);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody Ativo ativo){
        try{
            Ativo salvo = service.salvar(ativo);
            return ResponseEntity.ok(salvo);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id){
        boolean excluido = service.excluir(id);

        if(excluido){
            return ResponseEntity.noContent().build(); //status 204
        }
        return ResponseEntity.notFound().build(); // status 404
    }

}
