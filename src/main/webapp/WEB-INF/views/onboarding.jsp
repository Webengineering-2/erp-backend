<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <title>Willkommen!</title>
</head>
<body>
<h1>Willkommen!</h1>
<p>Erstelle den ersten Account, um loszulegen.</p>

<form method="post" action="${pageContext.request.contextPath}/onboarding" accept-charset="UTF-8">
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
        <button type="submit">Loslegen</button>
    </div>
</form>
</body>
</html>
