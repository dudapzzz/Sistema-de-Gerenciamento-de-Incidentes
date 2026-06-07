package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import service.IncidenteService;

import java.io.IOException;

@WebServlet({"/pagina_inicial", "/inicio"})
public class PaginaInicialServlet extends HttpServlet {
    private IncidenteService service = new IncidenteService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // pega o codigo do usuario
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        int idLogado = usuario.getCodigo();

        req.setAttribute("naoResolvidos", service.contarNaoResolvidos(idLogado));
        req.setAttribute("emAndamento", service.contarEmAndamento(idLogado));
        req.setAttribute("resolvidos", service.contarResolvidos(idLogado));
        req.setAttribute("listaRecentes", service.listarRecentes(idLogado));

        req.getRequestDispatcher("WEB-INF/pages/pagina_inicial.jsp").forward(req, resp);
    }
}