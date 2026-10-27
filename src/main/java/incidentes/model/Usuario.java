package incidentes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @UuidGenerator
    @Column(unique = true, updatable = false)
    private UUID uuid;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @JsonIgnore //garante que a senha nao seja retornada no json
    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private Boolean ativo= true;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Ativo> ativos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Incidente> incidentes = new ArrayList<>();

    public Usuario(){
    }

    public Usuario(Long codigo, String nome, String email, String senha, boolean ativo){
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
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

    public List<Incidente> getIncidentes(){
        return incidentes;
    }

    public void setIncidentes(List<Incidente> incidentes){
        this.incidentes = incidentes;
    }

    public void addAtivo(Ativo ativo){
        ativos.add(ativo);
        ativo.setUsuario(this);
    }

    public void addIncidente(Incidente incidente){
        incidentes.add(incidente);
        incidente.setUsuario(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(uuid, usuario.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}
