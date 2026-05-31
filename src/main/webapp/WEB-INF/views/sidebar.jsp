<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="sidebar">
    <ul>
        <li><a href="${pageContext.request.contextPath}/create?createView=categories">Produktkategorien</a></li>
        <li><a href="${pageContext.request.contextPath}/createView=products">Produkte</a></li>
        <li><a href="${pageContext.request.contextPath}/createView=locations">Lagerlocations</a></li>
        <li><a href="${pageContext.request.contextPath}/createView=customers">Kunden</a></li>
    </ul>
</div>
</body>
</html>
