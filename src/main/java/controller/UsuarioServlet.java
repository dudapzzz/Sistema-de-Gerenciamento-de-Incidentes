package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import service.UsuarioService;

import java.io.IOException;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {
    private UsuarioService service= new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException{
        String nome= req.getParameter("nome");
        String email= req.getParameter("email");
        String senha= req.getParameter("senha");

        Usuario u= new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        u.setSenha(senha);
        u.setAtivo(true);

        boolean sucesso= service.inserir(u);

        if(sucesso){
            resp.sendRedirect("login.jsp");
        }else{
            resp.sendRedirect("cadastro_usuario.jsp?erro=1");
        }
    }

}
