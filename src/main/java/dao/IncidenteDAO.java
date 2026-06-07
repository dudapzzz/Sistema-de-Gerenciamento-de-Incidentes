package dao;

import model.Incidente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidenteDAO {

    public boolean inserir(Incidente inc) {
        String sql;

        if(inc.getCodigo() > 0){
            sql= "UPDATE incidente SET titulo= ?, descricao= ?, relevancia = ?, status= ?, responsavel = ?, usuario_id = ? WHERE codigo= ?";
        } else{
            sql = "INSERT INTO incidente(titulo,descricao,relevancia,status,responsavel, usuario_id) VALUES (?, ?,?, ?, ?, ?)";
        }

        try (Connection conn = ConexaoDB.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, inc.getTitulo());
            stmt.setString(2, inc.getDescricao());
            stmt.setString(3, inc.getRelevancia());
            stmt.setString(4, inc.getStatus());
            stmt.setString(5, inc.getResponsavel());
            stmt.setInt(6, inc.getUsuarioId()); //pk

            if(inc.getCodigo()> 0){
                stmt.setInt(7, inc.getCodigo());
            }
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas >0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Incidente> listar(int usuarioId) {
        List<Incidente> lista = new ArrayList<>();
        String sql = "SELECT * FROM incidente WHERE usuario_id = ? ORDER BY codigo DESC";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Incidente inc = new Incidente();
                    inc.setCodigo(rs.getInt("codigo"));
                    inc.setTitulo(rs.getString("titulo"));
                    inc.setDescricao(rs.getString("descricao"));
                    inc.setRelevancia(rs.getString("relevancia"));
                    inc.setStatus(rs.getString("status"));
                    inc.setResponsavel(rs.getString("responsavel"));
                    inc.setUsuarioId(rs.getInt("usuario_id"));
                    lista.add(inc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Map<String, Integer> getEstatisticasStatus(int usuarioId) {
        Map<String, Integer> estatisticas = new HashMap<>();
        String sql = "SELECT status, COUNT(*) as total FROM incidente WHERE usuario_id = ? GROUP BY status";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    estatisticas.put(rs.getString("status"), rs.getInt("total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estatisticas;
    }

    public Map<String, Integer> getEstatisticasRelevancia(int usuarioId) {
        Map<String, Integer> mapa = new java.util.HashMap<>();
        String sql = "SELECT relevancia, COUNT(*) AS total FROM incidente WHERE usuario_id = ? GROUP BY relevancia";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    mapa.put(rs.getString("relevancia"), rs.getInt("total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapa;
    }

    public long contarPorStatus(String status, int usuarioId) {
        String sql = "SELECT COUNT(*) FROM incidente WHERE LOWER(status) = LOWER(?) AND usuario_id = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long contarPorRelevancia(String relevancia, int usuarioId) {
        String sql = "SELECT COUNT(*) FROM incidente WHERE LOWER(relevancia) = LOWER(?) AND usuario_id = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, relevancia);
            stmt.setInt(2, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Incidente> listarRecentesGeral(int usuarioId) {
        List<Incidente> lista = new ArrayList<>();
        String sql = "SELECT * FROM incidente WHERE usuario_id = ? ORDER BY codigo DESC LIMIT 3";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Incidente inc = new Incidente();
                    inc.setCodigo(rs.getInt("codigo"));
                    inc.setTitulo(rs.getString("titulo"));
                    inc.setDescricao(rs.getString("descricao"));
                    inc.setResponsavel(rs.getString("responsavel"));
                    inc.setRelevancia(rs.getString("relevancia"));
                    inc.setStatus(rs.getString("status"));
                    inc.setUsuarioId(rs.getInt("usuario_id"));
                    lista.add(inc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Incidente buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM incidente WHERE codigo = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Incidente inc = new Incidente();
                    inc.setCodigo(rs.getInt("codigo"));
                    inc.setTitulo(rs.getString("titulo"));
                    inc.setDescricao(rs.getString("descricao"));
                    inc.setResponsavel(rs.getString("responsavel"));
                    inc.setRelevancia(rs.getString("relevancia"));
                    inc.setStatus(rs.getString("status"));
                    inc.setUsuarioId(rs.getInt("usuario_id"));
                    return inc;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean excluir(int codigo) {
        String sql = "DELETE FROM incidente WHERE codigo = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}