package incidentes.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class TratadorDeErros {
    public record DadosErroValidacao(String campo, String mensagem){
        public DadosErroValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }

    public record ErroResposta(int status, String erro, String mensagem, LocalDateTime timestamp){
        public ErroResposta(int status, String erro, String mensagem){
            this(status, erro, mensagem, LocalDateTime.now());

        }
    }

    //404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    //400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex){
        List<DadosErroValidacao> erros = ex.getFieldErrors().stream()
                .map(DadosErroValidacao::new).toList();
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResposta> tratarErroConflito(){
        ErroResposta erro= new ErroResposta(
                HttpStatus.CONFLICT.value(),
                "Conflito de dados",
                "Dado já cadastrado ou vínculo inválido"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }


}
