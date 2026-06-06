<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<html>
<head>
    <title>Edit Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>

<h2>Produkt bearbeiten</h2>

<form action="edit-product" method="post">

    <input type="hidden" name="id" value="${product.id}" />

    <label>Name</label>
    <input type="text" name="name" value="${product.name}" />

    <label>Price</label>
    <input type="number" step="0.01" name="price" value="${product.unitPrice}" />

    <label>Category</label>
    <select name="categoryId">
        <c:forEach var="c" items="${categories}">
            <option value="${c.id}"
                    <c:if test="${c.id == product.category.id}">
                        selected
                    </c:if>
            >
                    ${c.name}
            </option>
        </c:forEach>
    </select>

    <button class="primary" type="submit">Save</button>
    <button type="button" onclick="window.close()">Cancel</button>

</form>

</body>
</html>


