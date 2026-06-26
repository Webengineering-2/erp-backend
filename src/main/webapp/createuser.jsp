<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>
<auth:requireLogin/>

<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <title>Benutzer erstellen</title>
</head>
<body>
<div>
    <%-- Returns to wherever the user navigated here from. --%>
    <button type="button" onclick="history.back()">Zurück</button>
</div>

<h1>Benutzer erstellen</h1>
<p>Lege einen weiteren Account an.</p>

<form method="post" action="${pageContext.request.contextPath}/create-user" accept-charset="UTF-8">
    <div>
        <label for="username">Username</label><br>
        <input type="text" id="username" name="username" required autofocus>
    </div>
    <div>
        <label for="password">Passwort</label><br>
        <input type="password" id="password" name="password" required>
    </div>
    <div>
        <label for="passwordRepeat">Passwort wiederholen</label><br>
        <input type="password" id="passwordRepeat" name="passwordRepeat" required>
    </div>
    <div>
        <button type="submit">Erstellen</button>
    </div>
</form>
</body>
</html>
