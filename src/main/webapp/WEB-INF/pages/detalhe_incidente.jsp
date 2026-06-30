<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Incidente</title>
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
                <a href="/inicio">Início</a>
                <a href="/incidente/form">Registrar Incidente</a>
                <a href="/incidente/listar" class="active">Visualizar Incidentes</a>
                <a href="/relatorios">Relatórios</a>
                <a href="/ativo/listar">Dispositivos Monitorados</a>
                <a href="/ativo/form">Registrar Dispositivos</a>
            </nav>
        </aside>

        <main class="main" style="display: flex; flex-direction: column; align-items: center;">
            <h1 style="width: 100%; max-width: 760px; text-align: left;">Detalhes do Incidente #${incidente.codigo}</h1>

            <div class="card detail-card">
                <div class="detail-row">
                    <strong>Título</strong>
                    <span>${incidente.titulo}</span>
                </div>

                <div class="detail-row">
                    <strong>Relevância</strong>
                    <span class="relevancia-txt
                        ${incidente.relevancia == 'Baixa' ? 'rel-baixa' : ''}
                        ${incidente.relevancia == 'Média' ? 'rel-media' : ''}
                        ${incidente.relevancia == 'Alta' ? 'rel-alta' : ''}
                        ${incidente.relevancia == 'Crítica' ? 'rel-critica' : ''}">
                        ${incidente.relevancia}
                    </span>
                </div>

                <div class="detail-row">
                    <strong>Status</strong>
                    <span class="status-badge ${incidente.status.equalsIgnoreCase('Resolvido') ? 'status-resolvido' :
                            (incidente.status.equalsIgnoreCase('Em andamento') || incidente.status.equalsIgnoreCase('Em Andamento') ? 'status-andamento' : 'status-pendente')}">
                            ${incidente.status}
                    </span>
                </div>

                <div class="detail-row">
                    <strong>Responsável</strong>
                    <span>${incidente.responsavel}</span>
                </div>

                <div class="detail-row detail-description">
                    <strong>Descrição</strong>
                    <p>${incidente.descricao}</p>
                </div>
            </div>

            <div class="actions" style="width: 100%; max-width: 760px; justify-content: flex-start;">
                <a href="/incidente/listar" class="btn-action btn-edit" style="text-decoration: none;">Voltar</a>
                <a href="/incidente/form?id=${incidente.codigo}" class="btn-action btn-details" style="text-decoration: none;">Editar</a>
                <a href="/incidente/excluir?id=${incidente.codigo}"
                   class="btn-action btn-delete"
                   onclick="return confirm('Tem certeza que deseja excluir este incidente?');">
                    Excluir
                </a>
            </div>
        </main>
    </div>
</div>
</body>
</html>
