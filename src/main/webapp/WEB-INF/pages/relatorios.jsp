<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Relatórios e Estatísticas</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <style>
        .chart-wrapper {
            display: flex;
            align-items: flex-end;
            justify-content: space-around;
            height: 250px;
            padding: 20px;
            background: #fff;
            border-bottom: 2px solid var(--border);
            margin: 20px 0;
            gap: 20px;
        }

        .bar-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 80px;
            height: 100%;
            justify-content: flex-end;
        }

        .bar-v-fill {
            width: 100%;
            border-radius: 4px 4px 0 0;
            display: flex;
            align-items: flex-start;
            justify-content: center;
            color: #fff;
            font-size: 11px;
            font-weight: bold;
            padding-top: 5px;
            min-height: 2px;
            transition: height 0.6s ease;
            position: relative;
        }

        .bar-v-fill span {
            position: absolute;
            top: -20px;
            color: var(--text);
            font-size: 12px;
        }

        .label-v {
            margin-top: 10px;
            font-size: 13px;
            font-weight: 600;
            color: var(--text);
        }

        .bar-alta { background-color: var(--danger) !important; }
        .bar-media { background-color: var(--warning) !important; }
        .bar-baixa { background-color: #6b7280 !important; } /* Cinza */

        .status-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        .status-table th, .status-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid var(--border);
        }
        .status-table th {
            color: var(--muted);
            font-size: 13px;
            text-transform: uppercase;
        }
        .status-table td {
            font-size: 14px;
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
                <a href="incidente?acao=listar" >Visualizar Incidentes</a>
                <a href="relatorios"class="active">Relatórios</a>
                <a href="ativo?acao=listar">Dispositivos Monitorados</a>
                <a href="ativo?acao=form">Registrar Dispositivos</a>
            </nav>
        </aside>

        <main class="main">
            <h1>Painel de Relatórios</h1>
            <p class="page-subtitle">Análise em porcentagem dos incidentes registrados.</p>

            <div class="section-container">
                <h2>Análise por Relevância (%)</h2>
                <div class="chart-wrapper">
                    <c:set var="total" value="${totalIncidentes > 0 ? totalIncidentes : 1}" />

                    <c:set var="pAlta" value="${(mapRelevancia['Alta'] != null ? mapRelevancia['Alta'] : 0) * 100.0 / total}" />
                    <c:set var="pMedia" value="${(mapRelevancia['Média'] != null ? mapRelevancia['Média'] : 0) * 100.0 / total}" />
                    <c:set var="pBaixa" value="${(mapRelevancia['Baixa'] != null ? mapRelevancia['Baixa'] : 0) * 100.0 / total}" />

                    <div class="bar-container">
                        <div class="bar-v-fill bar-alta" style="height: ${pAlta}%;">
                            <span><fmt:formatNumber value="${pAlta}" maxFractionDigits="1"/>%</span>
                        </div>
                        <span class="label-v">Alta</span>
                    </div>

                    <div class="bar-container">
                        <div class="bar-v-fill bar-media" style="height: ${pMedia}%;">
                            <span><fmt:formatNumber value="${pMedia}" maxFractionDigits="1"/>%</span>
                        </div>
                        <span class="label-v">Média</span>
                    </div>

                    <div class="bar-container">
                        <div class="bar-v-fill bar-baixa" style="height: ${pBaixa}%;">
                            <span><fmt:formatNumber value="${pBaixa}" maxFractionDigits="1"/>%</span>
                        </div>
                        <span class="label-v">Baixa</span>
                    </div>
                </div>
            </div>

            <div class="section-container">
                <h2>Análise por Status</h2>
                <table class="status-table">
                    <thead>
                    <tr>
                        <th>Status</th>
                        <th style="text-align: right;">Quantidade</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Não resolvido</td>
                        <td style="text-align: right; font-weight: bold;">
                            ${mapStatus['Não resolvido'] != null ? mapStatus['Não resolvido'] : 0}
                        </td>
                    </tr>
                    <tr>
                        <td>Em andamento</td>
                        <td style="text-align: right; font-weight: bold;">
                            ${mapStatus['Em andamento'] != null ? mapStatus['Em andamento'] : 0}
                        </td>
                    </tr>
                    <tr>
                        <td>Resolvido</td>
                        <td style="text-align: right; font-weight: bold;">
                            ${mapStatus['Resolvido'] != null ? mapStatus['Resolvido'] : 0}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div style="margin-top: 20px; text-align: center; color: var(--muted); font-size: 13px;">
                Total de <strong>${totalIncidentes}</strong> incidentes processados.
            </div>
        </main>
    </div>
</div>
</body>
</html>
