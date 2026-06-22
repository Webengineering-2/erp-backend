<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>
<auth:requireLogin/>

<html>
<head>
    <title>Produktkategorien</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>

<body>
<jsp:include page="/WEB-INF/components/header.jsp"/>

<dialog id="deleteDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/deleteEntity.jsp"/>
</dialog>

<dialog id="createCategoryDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/createcategory.jsp"/>
</dialog>

<dialog id="editCategoryDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/editcategory.jsp"/>
</dialog>

<div class="layout">
    <jsp:include page="/WEB-INF/components/sidebar.jsp"/>

    <div class="content">
        <div class="topbar">
            <form method="get" action="categories.jsp" class="search-form">
                <input type="text"
                       class="input"
                       name="search"
                       placeholder="Kategorien suchen..."
                       value="${param.search}" />

                <button type="submit" class="btn">
                    Search
                </button>
            </form>

            <button class="btn btn-primary" type="button" onclick="openCreateCategory()">
                + Kategorie
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
                <c:forEach var="c" items="${creationService.getMatchingCategories(param.search)}">
                    <tr>
                        <td>${c.id}</td>
                        <td>${c.name}</td>
                        <td>
                            <button class="btn" onclick="openEditCategory(${c.id})">Edit</button>
                            <button class="btn btn-danger"
                                    onclick="openDeleteDialog('${c.id}','category','${c.name}')">
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
