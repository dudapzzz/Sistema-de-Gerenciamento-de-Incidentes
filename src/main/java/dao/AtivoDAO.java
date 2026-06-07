package dao;

import java.sql.Connection;
import model.Ativo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtivoDAO {

    public boolean salvar(Ativo ativo) {
        String sql;
        //se o id for maior que 0, já existe, só precisa atualizar
        if (ativo.getId() > 0) {
            sql = "UPDATE ativo SET nome = ?, tipo = ?, ip_ou_url = ?, criticidade = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO ativo (nome, tipo, ip_ou_url, criticidade, usuario_id) VALUES (?, ?, ?, ?, ?)";
        } //se nao, é um ativo novo, precisa ser inserido

        try (Connection conn = ConexaoDB.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ativo.getNome());
            stmt.setString(2, ativo.getTipo());
            stmt.setString(3, ativo.getIpOuUrl());
            stmt.setString(4, ativo.getCriticidade());

            //pelo que entendi, se ele nao for novo, pega o id do ativo, e se for novo, pega o IdUsuario relacionado (?)
            if (ativo.getId() > 0) {
                stmt.setInt(5, ativo.getId());
            } else {
                stmt.setInt(5, ativo.getUsuarioId());
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Ativo> listar(int usuarioId) {
        List<Ativo> lista = new ArrayList<>();
        String sql = "SELECT * FROM ativo WHERE usuario_id = ? ORDER BY id DESC";

        try (Connection conn = ConexaoDB.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ativo a = new Ativo();
                    a.setId(rs.getInt("id"));
                    a.setNome(rs.getString("nome"));
                    a.setTipo(rs.getString("tipo"));
                    a.setIpOuUrl(rs.getString("ip_ou_url"));
                    a.setCriticidade(rs.getString("criticidade"));
                    a.setUsuarioId(rs.getInt("usuario_id"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Ativo buscarPorId(int id) {
        String sql = "SELECT * FROM ativo WHERE id = ?";
        try (Connection conn = ConexaoDB.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Ativo a = new Ativo();
                    a.setId(rs.getInt("id"));
                    a.setNome(rs.getString("nome"));
                    a.setTipo(rs.getString("tipo"));
                    a.setIpOuUrl(rs.getString("ip_ou_url"));
                    a.setCriticidade(rs.getString("criticidade"));
                    a.setUsuarioId(rs.getInt("usuario_id"));
                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM ativo WHERE id = ?";
        try (Connection conn = ConexaoDB.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}