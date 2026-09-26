package incidentes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codigo")
@Schema(description = "Entidade que representa um usuáio do sistema")
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Código identificador do usuário")
    private Integer codigo;

    @UuidGenerator
    @Column(unique = true, updatable = false)
    @Schema(description = "UUID público do usuário",example = "edddd513-9ecd-45ac-8387-16256eae723a")
    private UUID uuid;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    @Schema(description = "Nome do usuário", example = "Gabriel Silva")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Column(nullable = false, unique = true)
    @Schema(description = "E-mail do usuário", example = "gabriel@gmail.com")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Senha do usuário")
    private String senha;

    @Schema(description = "Indica se o usuário está ativo no sistema", example = "true")
    private Boolean ativo= true;

    @OneToMany(mappedBy = "usuario")
    private List<Ativo> ativos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Incidente> incidentes = new ArrayList<>();

    public Usuario(){
    }

    public Usuario(Integer codigo, String nome, String email, String senha, boolean ativo){
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
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

    public boolean getAtivo() {
        return ativo;
    }

    public boolean isAtivo() { return ativo != null && ativo;}

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
