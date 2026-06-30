<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dispositivos - Sistema de Incidentes</title>
    <link rel="stylesheet" type="text/css" href="/style.css?v=4">
</head>
<body>
<div class="app">
    <header class="topbar">
        <div class="brand">Sistema de Incidentes</div>
        <div class="user">
            <details class="user-menu">
                <summary class="user-trigger">
                    <span>Olá, ${sessionScope.usuarioLogado.nome}</span>
                    <div class="avatar"></div>
                </summary>
                <div class="user-dropdown">
                    <a href="/logout">Sair da conta</a>
                </div>
            </details>
        </div>
    </header>

    <div class="body">
        <aside class="sidebar">
            <nav class="nav">
                <a href="/inicio">Início</a>
                <a href="/incidente/form">Registrar Incidente</a>
                <a href="/incidente/listar">Visualizar Incidentes</a>
                <a href="/relatorios">Relatórios</a>
                <a href="/ativo/listar"class="active">Dispositivos Monitorados</a>
                <a href="/ativo/form">Registrar Dispositivos</a>
            </nav>
        </aside>

        <main class="main">
            <div class="section-head">
                <h1>Dispositivos</h1>
                <a href="/ativo/form" class="btn btn-success" style="text-decoration: none;">+ Novo Ativo</a>
            </div>

            <div class="table-wrap">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Tipo</th>
                        <th>IP / URL</th>
                        <th>Criticidade</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="ativo" items="${listaAtivos}">
                        <tr>
                            <td>#${ativo.id}</td>
                            <td><strong>${ativo.nome}</strong></td>
                            <td>${ativo.tipo}</td>
                            <td>${ativo.ipOuUrl}</td>
                            <td>
                                <span class="status-badge ${ativo.criticidade == 'Alta' ? 'status-pendente' : (ativo.criticidade == 'Média' ? 'status-andamento' : 'status-resolvido')}">
                                        ${ativo.criticidade}
                                </span>
                            </td>
                            <td>
                                <a href="/ativo/editar?id=${ativo.id}" class="btn-action btn-details">Editar</a>
                                <a href="/ativo/excluir?id=${ativo.id}" class="btn-action btn-details" style="color: var(--danger);" onclick="return confirm('Excluir este ativo?');">Excluir</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty listaAtivos}">
                        <tr>
                            <td colspan="6" style="text-align: center; padding: 20px;">Nenhum ativo cadastrado.</td>
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
