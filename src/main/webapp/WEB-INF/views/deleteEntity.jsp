<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<html>
<head>
    <title>Delete</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>

<form action="delete-entity" method="post">

    <h2>${name} löschen</h2>
    <c:if test="${not empty error}">
        <div style="color:red; font-weight:bold;">
                ${error}
        </div>
    </c:if>

    <input type="hidden" name="type" value="${type}">
    <input type="hidden" name="id" value="${id}">

    <p>Sind Sie sicher, dass Sie wirklich löschen wollen?</p>
    <p>Diese Aktion kann nicht rückgängig gemacht werden.</p>

    <button class="danger" type="submit"
            onclick="return confirm('Wirklich löschen?')">
        Löschen
    </button>

    <button type="button" onclick="window.close()">
        Abbruch
    </button>

</form>

</body>
</html>


