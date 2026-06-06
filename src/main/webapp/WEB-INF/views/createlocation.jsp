
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<style>
    textarea {field-sizing: content;}</style>
<html>
<head>
    <title>Create Location</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<form action="create-location" method="post">

    <label>Name</label>
    <input required type="text" name="name">
    <label>Beschreibung</label>
    <textarea required name="description" rows="4" cols="20" contenteditable="true" ></textarea>

    <button class="primary" type="submit">Erstellen</button>
    <button type="button" onclick="window.close()">Abbruch</button>
</form>
</body>
</html>
