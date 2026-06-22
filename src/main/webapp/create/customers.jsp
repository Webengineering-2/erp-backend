<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>
<auth:requireLogin/>

<html>
<head>
    <title>Kunden</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>

<body>
<jsp:include page="/WEB-INF/components/header.jsp"/>

<dialog id="deleteDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/deleteEntity.jsp"/>
</dialog>

<dialog id="createCustomerDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/createcustomer.jsp"/>
</dialog>

<dialog id="editCustomerDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/editcustomer.jsp"/>
</dialog>

<div class="layout">
    <jsp:include page="/WEB-INF/components/sidebar.jsp"/>

    <div class="content">
        <div class="topbar">
            <form method="get" action="customers.jsp" class="search-form">
                <input type="text"
                       class="input"
                       name="search"
                       placeholder="Kunden suchen..."
                       value="${param.search}" />

                <button type="submit" class="btn">
                    Search
                </button>
            </form>

            <button class="btn btn-primary" type="button" onclick="openCreateCustomer()">
                + Kunde
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
                <c:forEach var="cu" items="${creationService.getMatchingCustomers(param.search)}">
                    <tr>
                        <td>${cu.id}</td>
                        <td>${cu.name}</td>
                        <td>
                            <button class="btn" onclick="openEditCustomer(${cu.id})">Edit</button>
                            <button class="btn btn-danger"
                                    onclick="openDeleteDialog('${cu.id}','customer','${cu.name}')">
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
