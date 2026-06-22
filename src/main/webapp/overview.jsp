<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>
<auth:requireLogin/>

<!DOCTYPE html>
<html lang="de">
<head>
	<meta charset="UTF-8">
	<title>Übersicht</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
	<jsp:include page="/WEB-INF/components/header.jsp"/>
	<h1>Übersicht</h1>
	<p>
		Hallo,
		<c:out value="${sessionScope.username}" />
		!
	</p>
	<p>
		<a href="${pageContext.request.contextPath}/logout">Abmelden</a>
	</p>

	<a href="${pageContext.request.contextPath}/create.jsp">
		<button	id="createBtn">Anlegen<img src="assets/create-dashboard.svg" alt="Create"/></button>
	</a>
	<a href="${pageContext.request.contextPath}/stock">
		<button	id="stockBtn">Bestände<img src="assets/magnifier.svg" alt="Stock"/></button>
	</a>
	<a href="${pageContext.request.contextPath}/sold">
		<button	id="soldBtn">Verkäufe<img src="assets/money-stack.svg" alt="Sold"/></button>
	</a>
</body>
</html>
