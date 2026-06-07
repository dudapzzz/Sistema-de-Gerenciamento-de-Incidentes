<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastro - Sistema de Incidentes</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body class="auth-page">

<div class="auth-container">
    <div class="auth-box">
        <div class="auth-header">
            <h2>Cadastre-se</h2>
            <p>Insira seus dados</p>
        </div>

        <c:if test="${not empty erro}">
            <div class="alert alert-danger">
                    ${erro}
            </div>
        </c:if>

        <form action="cadastro_usuario" method="post">

            <div class="input-group">
                <label for="nome">Nome Completo</label>
                <input type="text" id="nome" name="nome" required placeholder="Ex: João da Silva">
            </div>

            <div class="input-group">
                <label for="email">E-mail Corporativo</label>
                <input type="email" id="email" name="email" required placeholder="joao@empresa.com">
            </div>

            <div class="input-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" required placeholder="Mínimo 6 caracteres">
            </div>

            <button type="submit" class="btn-primary">Cadastrar</button>
        </form>

        <div class="auth-footer">
            <p>Já tem uma conta? <a href="login.jsp">Faça login aqui</a></p>
            <a href="index.jsp" class="back-link">← Voltar ao início</a>
        </div>
    </div>
</div>

</body>
</html>