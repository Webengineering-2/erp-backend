<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>
<auth:redirectIfLoggedIn/>

<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<h1>Login</h1>

<form method="post" action="${pageContext.request.contextPath}/login" accept-charset="UTF-8">
    <div>
        <label for="username">Username</label><br>
        <input type="text" id="username" name="username" required autofocus>
    </div>
    <div>
        <label for="password">Passwort</label><br>
        <input type="password" id="password" name="password" required>
    </div>
    <div>
        <button type="submit">Anmelden</button>
    </div>
</form>
</body>
</html>
