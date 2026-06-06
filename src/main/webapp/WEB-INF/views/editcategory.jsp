<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Edit Category</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>

<h2>Kategorie bearbeiten</h2>

<form action="edit-category" method="post">

    <input type="hidden" name="id" value="${category.id}" />

    <label>Name</label>
    <input type="text" name="name" value="${category.name}" />

    <label>Description</label>
    <textarea name="description">${category.description}</textarea>

    <button class="primary" type="submit">Save</button>
    <button type="button" onclick="window.close()">Cancel</button>

</form>

</body>
</html>


