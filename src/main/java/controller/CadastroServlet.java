package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import service.UsuarioService;

import java.io.IOException;

@WebServlet("/cadastro_usuario")
public class CadastroServlet extends HttpServlet {
    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nome= req.getParameter("nome");
        String email= req.getParameter("email");
        String senha = req.getParameter("senha");

        System.out.println("Chegou no Servlet: " + nome + " - " + email);

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senha);

        boolean sucesso = usuarioService.inserir(novoUsuario);
        if(sucesso){
            resp.sendRedirect("login.jsp?msg=sucesso");
        }else{
            req.setAttribute("erro", "dados inválidos");
            req.getRequestDispatcher("WEB-INF/pages/cadastro_usuario.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/pages/cadastro_usuario.jsp").forward(req, resp);
    }
}
