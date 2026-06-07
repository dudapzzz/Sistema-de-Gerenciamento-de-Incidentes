package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import service.LoginService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private LoginService service= new LoginService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email= req.getParameter("email");
        String senha= req.getParameter("senha");

        Usuario usuario= service.autenticar(email, senha);

        if(usuario != null){
            HttpSession session = req.getSession();
            session.setAttribute("usuarioLogado", usuario);

            resp.sendRedirect(req.getContextPath() + "/pagina_inicial");
        }else{
            req.setAttribute("erro", "Email ou senha incorretos.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
