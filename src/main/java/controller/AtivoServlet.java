package controller;

import dao.AtivoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Ativo;
import model.Usuario;

import java.io.IOException;

@WebServlet("/ativo")
public class AtivoServlet extends HttpServlet {
    private AtivoDAO dao = new AtivoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("usuarioLogado") == null){
            resp.sendRedirect("login.jsp");
            return;
        }
        Usuario usuarioLogado =(Usuario) session.getAttribute("usuarioLogado");
        int idLogado = usuarioLogado.getCodigo();
        String acao= req.getParameter("acao");

        if("form".equals(acao)){
            String idStr = req.getParameter("id");
            if(idStr !=null && !idStr.isEmpty()){
                Ativo ativoExistente = dao.buscarPorId(Integer.parseInt(idStr));
                req.setAttribute("ativo", ativoExistente);
            }
            req.getRequestDispatcher("WEB-INF/pages/form_ativo.jsp").forward(req,resp);
        }else if("excluir".equals(acao)){
            String idStr = req.getParameter("id");
            if(idStr != null && !idStr.isEmpty()){
                dao.excluir(Integer.parseInt(idStr));
            }
            resp.sendRedirect("ativo?acao=listar");
        }else{
            req.setAttribute("listaAtivos", dao.listar(idLogado));
            req.getRequestDispatcher("WEB-INF/pages/lista_ativos.jsp").forward(req, resp);

        }
}


@Override

protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    HttpSession session = req.getSession(false);
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

    String idStr = req.getParameter("id");
    String nome = req.getParameter("nome");
    String tipo = req.getParameter("tipo");
    String ipOuUrl = req.getParameter("ipOuUrl");
    String criticidade = req.getParameter("criticidade");

    int id = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : 0;

    if (nome == null || nome.trim().length() < 3) {
        req.setAttribute("erro", "O nome do ativo deve ter pelo menos 3 caracteres.");
        req.getRequestDispatcher("WEB-INF/pages/form_ativo.jsp").forward(req, resp);
        return;
    }

    Ativo a = new Ativo();
    a.setId(id);
    a.setNome(nome.trim());
    a.setTipo(tipo);
    a.setIpOuUrl(ipOuUrl.trim());
    a.setCriticidade(criticidade);
    a.setUsuarioId(usuarioLogado.getCodigo());

    dao.salvar(a);
    resp.sendRedirect("ativo?acao=listar");
}
}