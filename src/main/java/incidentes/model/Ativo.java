package incidentes.model;

import jakarta.persistence.*;

@Entity
@Table(name= "ativo")
public class Ativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String tipo;

    @Column(name = "ip_ou_url")
    private String ipOuUrl;
    private String criticidade;

    @Column(name = "usuario_id")
    private int usuarioId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}