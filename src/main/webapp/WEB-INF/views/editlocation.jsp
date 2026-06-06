<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Edit Location</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>

<h2>Lagerort bearbeiten</h2>

<form action="edit-location" method="post">

    <input type="hidden" name="id" value="${location.id}" />

    <label>Name</label>
    <input type="text" name="name" value="${location.name}" />

    <label>Description</label>
    <textarea name="description">${location.description}</textarea>

    <button class="primary" type="submit">Save</button>
    <button type="button" onclick="window.close()">Cancel</button>

</form>

</body>
</html>
