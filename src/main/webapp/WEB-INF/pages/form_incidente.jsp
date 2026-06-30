<%--
  Created by IntelliJ IDEA.
  User: Eduarda
  Date: 30/04/2026
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${not empty incidente.codigo ? 'Editar' : 'Novo'} Incidente</title>
    <link rel="stylesheet" type="text/css" href="/style.css?v=2">

</head>

<body>
<div class="app">
    <header class="topbar">
        <div class="brand">Sistema de Incidentes</div>
        <div class="user">
            <details class="user-menu">
                <summary class="user-trigger">
                    <span>Olá, ${sessionScope.usuarioLogado.nome}</span>
                    <div class="avatar" aria-label="Menu do usuário"></div>
                </summary>
                <div class="user-dropdown">
                    <a href="/logout">Sair da conta</a>
                </div>
            </details>
        </div>
    </header>
    <div class="body form-layout">
        <aside class="sidebar">
            <nav class="nav">
                <a href="/inicio">Início</a>
                <a href="/incidente/form" class="active">Registrar Incidente</a>
                <a href="/incidente/listar">Visualizar Incidentes</a>
                <a href="/relatorios">Relatórios</a>
                <a href="/ativo/listar">Dispositivos Monitorados</a>
                <a href="/ativo/form">Registrar Dispositivos</a>
            </nav>
        </aside>

        <main class="main">
            <h1 class="form-page-title">${not empty incidente.codigo ? 'Editar Incidente #' : 'Registrar Incidente'} ${incidente.codigo}</h1>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger" style="max-width: 600px; margin-bottom: 16px; text-align: left; padding: 12px;">
                    <strong>Erro:</strong> ${erro}
                </div>
            </c:if>

            <div class="form-container">
                <form action="/incidente/salvar" method="POST">

                    <input type="hidden" name="acao" value="salvar" />

                    <input type="hidden" name="codigo" value="${not empty incidente.codigo ? incidente.codigo : '0'}" />

                    <div class="form-group">
                        <label for="titulo">Título do Incidente *Mínimo 5 caracteres</label>
                        <input type="text" id="titulo" name="titulo" value="${incidente.titulo}" required placeholder="Ex: Tentativa de Phishing por E-mail" />
                    </div>

                    <div class="form-group">
                        <label for="relevancia">Nível de Relevância</label>
                        <select id="relevancia" name="relevancia" required>
                            <option value="Baixa" ${incidente.relevancia == 'Baixa' ? 'selected' : ''}>Baixa</option>
                            <option value="Média" ${incidente.relevancia == 'Média' ? 'selected' : ''}>Média</option>
                            <option value="Alta" ${incidente.relevancia == 'Alta' ? 'selected' : ''}>Alta</option>

                        </select>
                    </div>


                    <div class="form-group">
                        <label for="responsavel">Departamento Responsável por Resolver</label>
                        <select id="responsavel" name="responsavel" required>
                            <option value="Segurança da Informação (SOC)" ${incidente.responsavel == 'Segurança da Informação (SOC)' ? 'selected' : ''}>Segurança da Informação (SOC)</option>
                            <option value="Infraestrutura e Redes" ${incidente.responsavel == 'Infraestrutura e Redes' ? 'selected' : ''}>Infraestrutura e Redes</option>
                            <option value="Suporte Técnico / Helpdesk" ${incidente.responsavel == 'Suporte Técnico / Helpdesk' ? 'selected' : ''}>Suporte Técnico / Helpdesk</option>
                            <option value="Engenharia de Software / Sistemas" ${incidente.responsavel == 'Engenharia de Software / Sistemas' ? 'selected' : ''}>Engenharia de Software / Sistemas</option>
                        </select>
                    </div>

                    <c:if test="${not empty incidente.codigo && incidente.codigo != 0}">
                        <div class="form-group">
                            <label for="status">Status Atual</label>
                            <select id="status" name="status" required>
                                <option value="Não resolvido" ${incidente.status == 'Não resolvido' ? 'selected' : ''}>Não resolvido</option>
                                <option value="Em andamento" ${incidente.status == 'Em andamento' ? 'selected' : ''}>Em andamento</option>
                                <option value="Resolvido" ${incidente.status == 'Resolvido' ? 'selected' : ''}>Resolvido</option>
                            </select>
                        </div>
                    </c:if>

                    <div class="form-group">
                        <label for="descricao">Descrição do Ocorrido *Mínimo 10 caracteres</label>
                        <textarea id="descricao" name="descricao" required placeholder="Descreva o que aconteceu">${incidente.descricao}</textarea>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-success">Salvar</button>
                        <a href="/inicio" class="btn btn-cancel">Cancelar</a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</div>
</body>
</html>
