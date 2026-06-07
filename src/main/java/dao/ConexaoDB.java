package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    private static final String URL= "jdbc:postgresql://localhost:5432/trabalhoincidentes";
    private static final String USER= "postgres";
    private static final String PASSWORD= "1234";

    public static Connection getConexao(){

        try{
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e){
            System.out.println("Driver não encontrado");
            e.printStackTrace();
        }catch (SQLException e){
            System.out.println("Erro ao conectar ao banco");
            e.printStackTrace();
        }

        return null;
    }

}
