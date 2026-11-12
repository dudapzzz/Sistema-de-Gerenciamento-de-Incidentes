package incidentes.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import incidentes.infra.CredenciaisInvalidasException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


@RestControllerAdvice
public class TratadorDeErros {

    public record ErroResposta(int status, String erro, String mensagem, LocalDateTime timestamp){
        public ErroResposta(int status, String erro, String mensagem){
            this(status, erro, mensagem, LocalDateTime.now());

        }
    }

    public record ErroValidacao(String campo, String mensagem){
        public ErroValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }

    //404
    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<ErroResposta> tratarNaoEncontrado(Exception ex){
        return responder(HttpStatus.NOT_FOUND, "Recurso não encontrado", ex.getMessage());
    }

    //400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroValidacao>> tratarValidacao(MethodArgumentNotValidException ex){
        List<ErroValidacao> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErroValidacao::new)
                .toList();
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ErroResposta> tratarRequisicaoInvalida(Exception ex) {
        return responder(
                HttpStatus.BAD_REQUEST,
                "Requisição inválida",
                "Verifique os dados enviados."
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResposta> tratarConflito(
            DataIntegrityViolationException ex
    ) {
        return responder(
                HttpStatus.CONFLICT,
                "Conflito de dados",
                "Os dados informados conflitam com um registro ou vínculo existente."
        );
    }

    private ResponseEntity<ErroResposta> responder(
            HttpStatus status,
            String erro,
            String mensagem
    ) {
        ErroResposta corpo = new ErroResposta(
                status.value(),
                erro,
                mensagem == null ? erro : mensagem
        );

        return ResponseEntity.status(status).body(corpo);


    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErroResposta> tratarCredenciaisInvalidas(
            CredenciaisInvalidasException ex
    ) {
        return responder(
                HttpStatus.UNAUTHORIZED,
                "Não autorizado",
                ex.getMessage()
        );
    }
}

