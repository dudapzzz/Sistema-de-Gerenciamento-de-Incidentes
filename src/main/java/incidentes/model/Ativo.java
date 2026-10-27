package incidentes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name= "ativo")
public class Ativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    @Column(name = "uuid", unique = true, updatable = false)
    private UUID uuid;

    @NotBlank(message= "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAtivo tipo;

    @Column(name = "ip_ou_url")
    private String ipOuUrl;

    @NotBlank(message= "Criticidade é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Criticidade criticidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Ativo(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public TipoAtivo getTipo() {
        return tipo;
    }

    public void setTipo(TipoAtivo tipo) {
        this.tipo = tipo;
    }

    public String getIpOuUrl() {
        return ipOuUrl;
    }

    public void setIpOuUrl(String ipOuUrl) {
        this.ipOuUrl = ipOuUrl;
    }

    public Criticidade getCriticidade() {
        return criticidade;
    }

    public void setCriticidade(Criticidade criticidade) {
        this.criticidade = criticidade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //ensina o java a comparar se dois ativos sao iguais
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ativo ativo = (Ativo) o;
        return Objects.equals(uuid, ativo.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid); //gerou hash
    }
}
