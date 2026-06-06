
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>Create Customer</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<form action="create-customer" method="post">

    <label>Name</label>
    <input required type="text" name="name">

    <button class="primary" type="submit">Erstellen</button>
    <button type="button" onclick="window.close()">Abbruch</button>
</form>
</body>
</html>

