<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>Create Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <form action="create-product" method="post">

        <label>Name</label>
        <input required type="text" name="name">

        <label>Kategorie</label>
        <select required name="categoryId">
            <c:forEach var="c" items="${categories}">
                <option value="${c.id}">
                        ${c.name}
                </option>
            </c:forEach>
        </select>
        <label>Preis</label>
        <input type="number" name="price" step="0.01" min="0" required>

        <button class="primary" type="submit">Erstellen</button>
        <button type="button" onclick="window.close()">Abbruch</button>
    </form>
</body>
</html>


