<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <title>Fehler</title>
</head>
<body>
<h1>Es ist ein Fehler aufgetreten</h1>
<p>
    <c:choose>
        <c:when test="${not empty errorMessage}">
            <c:out value="${errorMessage}"/>
        </c:when>
        <c:otherwise>
            Ein unerwarteter Fehler ist aufgetreten.
        </c:otherwise>
    </c:choose>
</p>
<p><a href="${pageContext.request.contextPath}/">OK</a></p>
</body>
</html>
