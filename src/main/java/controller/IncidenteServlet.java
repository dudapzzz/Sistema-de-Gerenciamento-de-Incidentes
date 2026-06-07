package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Incidente;
import model.Usuario;
import service.IncidenteService;

import java.io.IOException;

@WebServlet("/incidente")
public class IncidenteServlet extends HttpServlet {
    private IncidenteService service = new IncidenteService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("usuarioLogado") == null){
            resp.sendRedirect("login.jsp");
            return;
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        int idLogado = usuarioLogado.getCodigo();

        String acao = req.getParameter("acao");

        if("form".equals(acao)){
            String idStr = req.getParameter("id");
            if(idStr != null && !idStr.isEmpty()){
                int id = Integer.parseInt(idStr);

                Incidente incExistente = service.buscarPorCodigo(id);
                req.setAttribute("incidente", incExistente);
            }
            req.getRequestDispatcher("WEB-INF/pages/form_incidente.jsp").forward(req, resp);
        } else if("listar".equals(acao)){
            req.setAttribute("listaIncidentes", service.listarTodos(idLogado));
            req.getRequestDispatcher("WEB-INF/pages/lista_incidentes.jsp").forward(req, resp);
        } else if("excluir".equals(acao)){
            String idStr = req.getParameter("id");
            if(idStr != null && !idStr.isEmpty()){
                try{
                    int id = Integer.parseInt(idStr);
                    service.excluir(id);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
            resp.sendRedirect("incidente?acao=listar");
        } else if("detalhes".equals(acao) || "detalhe_incidentes".equals(acao)){
            String idStr = req.getParameter("id");
            if(idStr != null && !idStr.isEmpty()){
                try{
                    int id = Integer.parseInt(idStr);
                    Incidente incidente = service.buscarPorCodigo(id);
                    if(incidente != null){
                        req.setAttribute("incidente", incidente);
                        req.getRequestDispatcher("WEB-INF/pages/detalhe_incidente.jsp").forward(req, resp);
                        return;
                    }
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
            resp.sendRedirect("incidente?acao=listar");
        }else{
            resp.sendRedirect("inicio");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("usuarioLogado") == null){
            resp.sendRedirect("login.jsp");
            return;
        }

        Usuario usuariologado = (Usuario) session.getAttribute("usuarioLogado");

        String titulo = req.getParameter("titulo");
        String descricao = req.getParameter("descricao");
        String relevancia = req.getParameter("relevancia");
        String status = req.getParameter("status");
        String responsavel = req.getParameter("responsavel");
        String codigoStr = req.getParameter("codigo");

        int codigo = (codigoStr != null && !codigoStr.isEmpty()) ? Integer.parseInt(codigoStr) : 0;

        Incidente inc = new Incidente();
        inc.setCodigo(codigo);
        inc.setTitulo(titulo);
        inc.setDescricao(descricao);
        inc.setRelevancia(relevancia);

        inc.setStatus(codigo == 0 ? "Não resolvido" : status);
        inc.setResponsavel(responsavel);

        inc.setUsuarioId(usuariologado.getCodigo());

        boolean sucesso = service.salvar(inc);

        if(sucesso){
            resp.sendRedirect("incidente?acao=listar");
        }
        else{
            req.setAttribute("incidente", inc);
            req.setAttribute("erro", "Erro ao registrar incidente, o título deve ter mais de 5 caracteres e a descrição, mais de 10");
            req.getRequestDispatcher("WEB-INF/pages/form_incidente.jsp").forward(req, resp);
        }
    }
}