package incidentes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "incidente")
@Schema(description = "Entidade que representa um incidente identificado")

public class Incidente{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Código identificador do incidente", example = "01")
    private int codigo;

    @UuidGenerator
    @Column(unique = true, updatable = false)
    @Schema(description = "UUID público do incidente",example = "conjunto aletório de números e letras")
    private UUID uuid;

    @Column(nullable = false)
    @Schema(description = "Título do incidente", example = "Vazamento de senha")
    private String titulo;

    @Column(nullable = false)
    @Schema(description = "Descrição do incidente", example = "Fui informado que houve um acesso ao sistema da empresa em um local desconhecido")
    private String descricao;

    @Column(nullable = false)
    @Schema(description = "Grau de relevância do incidente", example = "Alto")
    private String relevancia;

    @Column(name = "data_incidente")
    @Schema(description = "Data do registro do incidente")
    private Date dataIncidente;

    @Column(nullable = false)
    @Schema(description = "Status da resolução do incidente", example = "Em andamento")
    private String status;

    @Schema(description = "Responsável pela resolução do incidente", example = "Carlos")
    private String responsavel;

    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "ID do usuário que registrou o incidente", example = "01")
    private int usuarioId;

    public Incidente(){
        this.dataIncidente = new Date();
    }


    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
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

    public String getRelevancia() {
        return relevancia;
    }

    public void setRelevancia(String relevancia) {
        this.relevancia = relevancia;
    }

    public Date getDataIncidente() {
        return dataIncidente;
    }

    public void setDataIncidente(Date dataIncidente) {
        this.dataIncidente = dataIncidente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UUID getUuid() {return uuid;}

    public void setUuid(UUID uuid) {this.uuid = uuid;}
}

