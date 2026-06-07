<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Visualizar Incidentes</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <style>
        /* Ajuste de cores de relevância padronizadas */
        .rel-alta, .rel-critica { color: var(--danger) !important; font-weight: bold; }
        .rel-media { color: var(--warning) !important; font-weight: bold; }
        .rel-baixa { color: #6b7280 !important; font-weight: bold; }
    </style>
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
                    <a href="logout">Sair da conta</a>
                </div>
            </details>
        </div>
    </header>

    <div class="body">
        <aside class="sidebar">
            <nav class="nav">
                <a href="inicio">Início</a>
                <a href="incidente?acao=form">Registrar Incidente</a>
                <a href="incidente?acao=listar" class="active">Visualizar Incidentes</a>
                <a href="relatorios">Relatórios</a>
                <a href="ativo?acao=listar">Dispositivos Monitorados</a>
                <a href="ativo?acao=form">Registrar Dispositivos</a>
            </nav>
        </aside>

        <main class="main">
            <div style="display: flex; justify-content: space-between; align-items: center; max-width: 1200px;">
                <h1>Todos os Incidentes Registrados</h1>
                <a href="incidente?acao=form" class="btn btn-primary" style="text-decoration: none;">+ Novo Incidente</a>
            </div>

            <div class="table-container">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th style="width: 80px;">ID</th>
                            <th>Título</th>
                            <th>Relevância</th>
                            <th>Departamento Responsável</th>
                            <th>Status</th>
                            <th style="width: 300px; text-align: center;">Ações</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:if test="${not empty listaIncidentes}">
                            <c:forEach var="inc" items="${listaIncidentes}">
                                <tr>
                                    <td><strong>#${inc.codigo}</strong></td>
                                    <td><strong>${inc.titulo}</strong></td>
                                    <td>
                                        <span class="relevancia-txt
                                            ${inc.relevancia.equalsIgnoreCase('Baixa') ? 'rel-baixa' : ''}
                                            ${inc.relevancia.equalsIgnoreCase('Média') ? 'rel-media' : ''}
                                            ${inc.relevancia.equalsIgnoreCase('Alta') ? 'rel-alta' : ''}
                                            ${inc.relevancia.equalsIgnoreCase('Crítica') ? 'rel-critica' : ''}">
                                            ${inc.relevancia}
                                        </span>
                                    </td>
                                    <td>${inc.responsavel}</td>
                                    <td>
                                        <span class="status-badge
                                            ${inc.status.equalsIgnoreCase('Resolvido') ? 'status-resolvido' :
                                            (inc.status.equalsIgnoreCase('Em andamento') ? 'status-andamento' : 'status-pendente')}">
                                            ${inc.status}
                                        </span>
                                    </td>
                                    <td style="text-align: center;">
                                        <a href="incidente?acao=detalhes&id=${inc.codigo}" class="btn-action btn-details">
                                            Ver detalhes
                                        </a>
                                        <a href="incidente?acao=form&id=${inc.codigo}" class="btn-action btn-edit">Editar</a>
                                        <a href="incidente?acao=excluir&id=${inc.codigo}"
                                           class="btn-action btn-delete"
                                           onclick="return confirm('Tem certeza que deseja excluir este incidente?');">
                                            Excluir
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>

                        <c:if test="${empty listaIncidentes}">
                            <tr>
                                <td colspan="6" class="no-data">
                                    Nenhum incidente encontrado no banco de dados.
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</div>
</body>
</html>
