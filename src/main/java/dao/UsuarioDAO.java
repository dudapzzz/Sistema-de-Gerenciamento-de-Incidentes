package dao;

import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    public Usuario autenticar(String email, String senha){
        String sql= "SELECT * FROM usuario WHERE email = ? AND senha = ?";

        try(Connection conn = ConexaoDB.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, email);
            stmt.setString(2,senha);

            try(ResultSet rs= stmt.executeQuery()){
                if(rs.next()){
                    Usuario u= new Usuario();

                    u.setCodigo(rs.getInt("codigo"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    u.setAtivo(rs.getBoolean("ativo"));
                    return u;
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean inserir(Usuario u){
        String sql= "INSERT INTO usuario(nome, email, senha, ativo) VALUES (?, ?, ?, ?)";

        try(Connection conn= ConexaoDB.getConexao(); PreparedStatement stmt=conn.prepareStatement(sql)){
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setBoolean(4,u.isAtivo());

            int registrosInseridos = stmt.executeUpdate();
            return registrosInseridos >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
