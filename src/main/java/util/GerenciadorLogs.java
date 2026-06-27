package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class GerenciadorLogs {
    private static final String caminho_log = "/logs/incidentes/incidentes.log";
    public static void registrar(String mensagem){

        try (FileWriter fw = new FileWriter(caminho_log, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(LocalDateTime.now() + "[LOG] => " + mensagem);
        } catch (IOException e){
            System.err.println("Erro ao salvar log no: "+e.getMessage());
        }

    }
}
