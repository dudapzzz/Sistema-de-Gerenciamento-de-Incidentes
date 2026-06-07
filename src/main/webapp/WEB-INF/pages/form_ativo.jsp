<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${not empty ativo.id ? 'Editar' : 'Novo'} Ativo</title>
    <link rel="stylesheet" type="text/css" href="style.css?v=4">
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
                    <a href="logout">Sair da conta</a>
                </div>
            </details>
        </div>
    </header>

    <div class="body form-layout">
        <aside class="sidebar">
            <nav class="nav">
                <a href="inicio">Início</a>
                <a href="incidente?acao=form">Registrar Incidente</a>
                <a href="incidente?acao=listar">Visualizar Incidentes</a>
                <a href="relatorios">Relatórios</a>
                <a href="ativo?acao=listar">Dispositivos Monitorados</a>
                <a href="ativo?acao=form"class="active">Registrar Dispositivos</a>
            </nav>
        </aside>

        <main class="main">
            <h1 class="form-page-title">${not empty ativo.id ? 'Editar Ativo' : 'Registrar Dispositivo'}</h1>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger" style="padding: 12px; margin-bottom: 16px;">
                    <strong>Erro:</strong> ${erro}
                </div>
            </c:if>

            <div class="form-container">
                <form action="ativo" method="POST">
                    <input type="hidden" name="id" value="${not empty ativo.id ? ativo.id : '0'}" />

                    <div class="form-group">
                        <label for="nome">Nome do Dispositivo *</label>
                        <input type="text" id="nome" name="nome" value="${ativo.nome}" required placeholder="Ex: Servidor de Banco de Dados" />
                    </div>

                    <div class="form-group">
                        <label for="tipo">Tipo de Dispositivo</label>
                        <select id="tipo" name="tipo" required>
                            <option value="Servidor" ${ativo.tipo == 'Servidor' ? 'selected' : ''}>Servidor</option>
                            <option value="Notebook" ${ativo.tipo == 'Notebook' ? 'selected' : ''}>Notebook</option>
                            <option value="Sistema Web" ${ativo.tipo == 'Sistema Web' ? 'selected' : ''}>Sistema Web</option>
                            <option value="Rede" ${ativo.tipo == 'Rede' ? 'selected' : ''}>Dispositivo de Rede</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="ipOuUrl">Endereço IP ou URL</label>
                        <input type="text" id="ipOuUrl" name="ipOuUrl" value="${ativo.ipOuUrl}" placeholder="192.168.0.1 ou https://..." />
                    </div>

                    <div class="form-group">
                        <label for="criticidade">Nível de Criticidade</label>
                        <select id="criticidade" name="criticidade" required>
                            <option value="Baixa" ${ativo.criticidade == 'Baixa' ? 'selected' : ''}>Baixa</option>
                            <option value="Média" ${ativo.criticidade == 'Média' ? 'selected' : ''}>Média</option>
                            <option value="Alta" ${ativo.criticidade == 'Alta' ? 'selected' : ''}>Alta</option>
                        </select>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-success">Salvar</button>
                        <a href="ativo?acao=listar" class="btn btn-cancel">Cancelar</a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</div>
</body>
</html>