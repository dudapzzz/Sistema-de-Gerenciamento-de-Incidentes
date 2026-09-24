package incidentes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "usuario")
@Schema(description = "Entidade que representa um usuáio do sistema")
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Código identificador do usuário", example = "01")
    private int codigo;

    @UuidGenerator
    @Column(unique = true, updatable = false)
    @Schema(description = "UUID público do usuário",example = "caracteres variados")
    private UUID uuid;

    @Column(nullable = false)
    @Schema(description = "Nome do usuário", example = "Gabriel Silva")
    private String nome;

    @Column(nullable = false, unique = true)
    @Schema(description = "E-mail do usuário", example = "gabriel@gmail.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Senha do usuário", example = "123456789#")
    private String senha;

    @Column(nullable = false)
    @Schema(description = "Indica se o usuário está ativo no sistema", example = "true")
    private boolean ativo;

    public Usuario(){
    }

    public Usuario(int codigo, String nome, String email, String senha, boolean ativo){
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public UUID getUuid() {return uuid;}

    public void setUuid(UUID uuid) {this.uuid = uuid;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
