<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>
<auth:requireLogin/>

<html>
<head>
    <title>Lagerlocations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>

<body>
<jsp:include page="/WEB-INF/components/header.jsp"/>

<dialog id="deleteDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/deleteEntity.jsp"/>
</dialog>

<dialog id="createLocationDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/createlocation.jsp"/>
</dialog>

<dialog id="editLocationDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/editlocation.jsp"/>
</dialog>

<div class="layout">
    <jsp:include page="/WEB-INF/components/sidebar.jsp"/>

    <div class="content">
        <div class="topbar">
            <form method="get" action="locations.jsp" class="search-form">
                <input type="text"
                       class="input"
                       name="search"
                       placeholder="Lagerorte suchen..."
                       value="${param.search}" />

                <button type="submit" class="btn">
                    Search
                </button>
            </form>

            <button class="btn btn-primary" type="button" onclick="openCreateLocation()">
                + Location
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
                <c:forEach var="l" items="${creationService.getMatchingLocations(param.search)}">
                    <tr>
                        <td>${l.id}</td>
                        <td>${l.name}</td>
                        <td>
                            <button class="btn" onclick="openEditLocation(${l.id})">Edit</button>
                            <button class="btn btn-danger"
                                    onclick="openDeleteDialog('${l.id}','location','${l.name}')">
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
