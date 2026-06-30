<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Início - Sistema de Incidentes</title>
    <link rel="stylesheet" type="text/css" href="/style.css">
    <style>
        .rel-alta { color: var(--danger) !important; font-weight: bold; }
        .rel-media { color: var(--warning) !important; font-weight: bold; }
        .rel-baixa { color: #6b7280 !important; font-weight: bold; } /* Cinza */

        .reports-centered {
            margin-top: 20px;
            display: flex;
            justify-content: center;
        }
        .reports-centered .card.report {
            max-width: 100%;
            width: 100%;
            padding: 40px;
            text-align: center;
        }
        .reports-centered .btn-primary {
            max-width: 300px;
            margin: 0 auto;
        }
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
                    <a href="/logout">Sair da conta</a>
                </div>
            </details>
        </div>
    </header>

    <div class="body">
        <aside class="sidebar">
            <nav class="nav">
                <a href="/inicio"class="active">Início</a>
                <a href="/incidente/form">Registrar Incidente</a>
                <a href="/incidente/listar">Visualizar Incidentes</a>
                <a href="/relatorios">Relatórios</a>
                <a href="/ativo/listar">Dispositivos Monitorados</a>
                <a href="/ativo/form">Registrar Dispositivos</a>
            </nav>
        </aside>

        <main class="main">
            <h1>Início</h1>

            <section class="stats">
                <div class="card stat" style="border-top: 5px solid #dc2626;">
                    <div class="label" style="color: #dc2626; font-weight: bold;">Incidentes Não Resolvidos</div>
                    <div class="value" style="color: #dc2626;">${naoResolvidos}</div>
                </div>

                <div class="card stat" style="border-top: 5px solid #f59e0b;">
                    <div class="label" style="color: #f59e0b; font-weight: bold;">Em andamento</div>
                    <div class="value" style="color: #f59e0b;">${emAndamento}</div>
                </div>

                <div class="card stat" style="border-top: 5px solid #16a34a;">
                    <div class="label" style="color: #16a34a; font-weight: bold;">Incidentes Resolvidos</div>
                    <div class="value" style="color: #16a34a;">${resolvidos}</div>
                </div>
            </section>

            <div class="section-head">
                <h2>Incidentes Recentes</h2>
                <a href="/incidente/listar" class="link">Ver todos</a>
            </div>

            <div class="table-wrap">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Relevância</th>
                        <th>Responsável</th>
                        <th>Status</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listaRecentes}" var="inc">
                        <tr>
                            <td>#${inc.codigo}</td>
                            <td><strong>${inc.titulo}</strong></td>
                            <td>
                                <span class="relevancia-txt 
                                    ${inc.relevancia.equalsIgnoreCase('Baixa') ? 'rel-baixa' : ''}
                                    ${inc.relevancia.equalsIgnoreCase('Média') ? 'rel-media' : ''}
                                    ${inc.relevancia.equalsIgnoreCase('Alta') ? 'rel-alta' : ''}">
                                    ${inc.relevancia}
                                </span>
                            </td>
                            <td>${inc.responsavel}</td>
                            <td>
                                <span class="status-badge ${inc.status.equalsIgnoreCase('Resolvido') ? 'status-resolvido' : 
                                    (inc.status.equalsIgnoreCase('Em andamento') ? 'status-andamento' : 'status-pendente')}">
                                    ${inc.status}
                                </span>
                            </td>
                            <td>
                                <a href="/incidente/detalhes?id=${inc.codigo}" class="btn-action btn-details">
                                    Ver detalhes
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty listaRecentes}">
                        <tr>
                            <td colspan="6" style="text-align: center; color: var(--muted); padding: 24px;">
                                Nenhum incidente recente encontrado.
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>

            <div class="section-head">
                <h2>Relatórios</h2>
            </div>

            <section class="reports-centered">
                <div class="card report">
                    <h3>Relatórios por Relevância e Status dos Incidentes</h3>
                    <p style="margin-bottom: 25px;">Visualize dois relatórios que mostram a situação atual dos incidentes cadastrados.</p>
                    <a href="/relatorios" class="btn btn-primary" style="text-decoration: none;">Ver Relatórios</a>
                </div>
            </section>

            <div class="actions">
                <a href="/incidente/form" class="btn btn-success" style="text-decoration: none;">+ Novo incidente</a>
            </div>
        </main>
    </div>
</div>
</body>
</html>
