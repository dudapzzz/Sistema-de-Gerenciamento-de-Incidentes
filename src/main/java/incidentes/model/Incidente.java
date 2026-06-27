package incidentes.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "incidente")

public class Incidente{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    private String titulo;
    private String descricao;
    private String relevancia;

    @Column(name = "data_incidente")
    private Date dataIncidente;
    private String status;
    @Column(name = "usuario_id")
    private int usuarioId;

    private String responsavel;

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
}
