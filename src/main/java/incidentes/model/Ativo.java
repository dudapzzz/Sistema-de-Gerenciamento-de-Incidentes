package incidentes.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name= "ativo")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @UuidGenerator
    @Column(unique = true, updatable = false)
    @Schema(description = "UUID público do ativo")
    private UUID uuid;

    @Column(nullable = false)
    @Schema(description = "Nome do ativo", example = "Servidor Principal")
    private String nome;

    @Column(nullable = false)
    @Schema(description = "Tipo do ativo", example = "Servidor")
    private String tipo;

    @Column(name = "ip_ou_url")
    @Schema(description = "IP ou URL do ativo", example = "192.168.1.10")
    private String ipOuUrl;

    @Column(nullable = false)
    @Schema(description = "Nível de criticidade", example = "Alta")
    private String criticidade;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuário responsável pelo ativo")
    private Usuario usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUuid() {return uuid;}

    public void setUuid(UUID uuid) {this.uuid = uuid;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIpOuUrl() {
        return ipOuUrl;
    }

    public void setIpOuUrl(String ipOuUrl) {
        this.ipOuUrl = ipOuUrl;
    }

    public String getCriticidade() {
        return criticidade;
    }

    public void setCriticidade(String criticidade) {
        this.criticidade = criticidade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
