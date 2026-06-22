<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>
<auth:requireLogin/>

<c:set var="categories" value="${creationService.getMatchingCategories(null)}" scope="request"/>

<html>
<head>
    <title>Produkte</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>

<body>
<jsp:include page="/WEB-INF/components/header.jsp"/>

<dialog id="deleteDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/deleteEntity.jsp"/>
</dialog>

<dialog id="createProductDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/createproduct.jsp"/>
</dialog>

<dialog id="editProductDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/editproduct.jsp"/>
</dialog>

<div class="layout">
    <jsp:include page="/WEB-INF/components/sidebar.jsp"/>

    <div class="content">
        <div class="topbar">
            <form method="get" action="products.jsp" class="search-form">
                <input type="text"
                       class="input"
                       name="search"
                       placeholder="Produkte suchen..."
                       value="${param.search}" />

                <button type="submit" class="btn">
                    Search
                </button>
            </form>

            <button type="button" class="btn btn-primary" onclick="openCreateProduct()">
                + Produkt
            </button>
        </div>

        <div class="table-wrapper">
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>
                <c:forEach var="p" items="${creationService.getMatchingProducts(param.search)}">
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.name}</td>
                        <td>
                            <button class="btn" onclick="openEditProduct(${p.id})">Edit</button>
                            <button class="btn btn-danger"
                                    onclick="openDeleteDialog('${p.id}','product','${p.name}')">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/create.js"></script>

</body>
</html>
