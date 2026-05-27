<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="de">
<head>
<meta charset="UTF-8">
<title>Übersicht</title>
</head>
<body>
	<h1>Übersicht</h1>
	<p>
		Hallo,
		<c:out value="${username}" />
		!
	</p>
	<p>
		<a href="${pageContext.request.contextPath}/logout">Abmelden</a>
	</p>

	<a href="${pageContext.request.contextPath}/create">
		<button	id="createBtn">Anlegen<img src="assets/create-dashboard.svg"/></button>
	</a>
	<a href="${pageContext.request.contextPath}/stock">
		<button	id="stockBtn">Bestände<img src="assets/magnifier.svg"/></button>
	</a>
	<a href="${pageContext.request.contextPath}/sold">
		<button	id="soldBtn">Verkäufe<img src="assets/money-stack.svg"/></button>
	</a>
</body>
</html>
