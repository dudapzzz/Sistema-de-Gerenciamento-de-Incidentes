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
import java.util.Map;

@WebServlet("/relatorios")
public class RelatorioServlet extends HttpServlet {

    private IncidenteService service = new IncidenteService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("usuarioLogado") == null){
            resp.sendRedirect("login.jsp");
            return;
        }
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        int idLogado = usuario.getCodigo();

        long naoResolvidos = service.contarNaoResolvidos(idLogado);
        long resolvidos = service.contarResolvidos(idLogado);
        long altaRelevancia = service.contarAltaRelevancia(idLogado);

        Map<String, Integer> estatisticasStatus = service.contarIncidentesPorStatus(idLogado);
        Map<String, Integer> estatisticasRelevancia = service.contarIncidentesPorRelevancia(idLogado);
        int totalIncidentes = service.listarTodos(idLogado).size();

        String tipo = req.getParameter("tipo");

        req.setAttribute("tipoRelatorio", tipo);

        req.setAttribute("qtdNaoResolvidos", naoResolvidos);
        req.setAttribute("qtdResolvidos", resolvidos);
        req.setAttribute("qtdAltaRelevancia", altaRelevancia);

        req.setAttribute("mapStatus", estatisticasStatus);
        req.setAttribute("mapRelevancia", estatisticasRelevancia);
        req.setAttribute("totalIncidentes", totalIncidentes);

        req.getRequestDispatcher("WEB-INF/pages/relatorios.jsp").forward(req, resp);
    }
}