<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Edit Customer</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>

<h2>Kunde bearbeiten</h2>

<form action="edit-customer" method="post">

    <input type="hidden" name="id" value="${customer.id}" />

    <label>Name</label>
    <input type="text" name="name" value="${customer.name}" />

    <button class="primary" type="submit">Save</button>
    <button type="button" onclick="window.close()">Cancel</button>

</form>

</body>
</html>


