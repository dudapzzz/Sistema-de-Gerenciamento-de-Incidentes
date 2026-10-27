package incidentes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "incidente")
public class Incidente{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @UuidGenerator
    @Column(name = "uuid", unique = true, updatable = false, nullable = false)
    private UUID uuid;

    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "Severidade é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name = "severidade", nullable = false)
    private NivelSeveridade severidade;

    @Column(name = "data_incidente")
    private LocalDateTime dataIncidente;


    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusIncidente status;

    @Column(length = 100)
    private String responsavel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Incidente(){
        this.dataIncidente = LocalDateTime.now();
        this.status = StatusIncidente.NAO_RESOLVIDO;
    }


    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public NivelSeveridade getSeveridade() {
        return severidade;
    }

    public void setSeveridade(NivelSeveridade severidade) {
        this.severidade = severidade;
    }

    public LocalDateTime getDataIncidente() {
        return dataIncidente;
    }

    public void setDataIncidente(LocalDateTime dataIncidente) {
        this.dataIncidente = dataIncidente;
    }

    public StatusIncidente getStatus() {
        return status;
    }

    public void setStatus(StatusIncidente status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UUID getUuid() {return uuid;}

    public void setUuid(UUID uuid) {this.uuid = uuid;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Incidente incidente = (Incidente) o;
        return Objects.equals(uuid, incidente.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}

