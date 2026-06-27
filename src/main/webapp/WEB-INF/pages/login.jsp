<%--
  Created by IntelliJ IDEA.
  User: Eduarda
  Date: 30/04/2026
  Time: 20:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Login - Sistema de Incidentes</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>

<body class="auth-page">
    <div class="auth-container">
        <div class="auth-box">
            <div class="auth-header">
                <h2>Sistema de <span> Incidentes</span></h2>
                <p>Entre com os seus dados:</p>
            </div>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger">
                    ${erro}
                </div>
            </c:if>

            <c:if test="${param.msg == 'sucesso'}">
                <div class="alert alert-success">
                    Cadastro realizado! Faça Login.
                </div>
            </c:if>

            <form action="login" method="post">
                <div class="input-group">
                    <label for="email">E-mail</label>
                    <input type="email" id="email" name="email" required placeholder="seu@email.com">
                </div>

                <div class="input-group">
                    <label for="senha">Senha</label>
                    <input type="password" id="senha" name="senha" required placeholder="********">

                </div>

                <button type="submit" class="btn-primary">Entrar</button>
            </form>

            <div class="auth-footer">
                <p>Não tem uma conta? <a href="cadastro_usuario">Cadastre-se</a> </p>
                <a href="index.jsp" class="back-link"> Voltar</a>
            </div>
        </div>
    </div>

</body>
</html>
