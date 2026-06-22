<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>
<auth:requireLogin/>

<c:set var="createView" value="${empty param.createView ? 'products' : param.createView}"/>
<c:set var="categories" value="${creationService.getMatchingCategories(null)}" scope="request"/>

<html>
<head>
    <title>Create</title>
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

<dialog id="createCategoryDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/createcategory.jsp"/>
</dialog>

<dialog id="editCategoryDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/editcategory.jsp"/>
</dialog>

<dialog id="createLocationDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/createlocation.jsp"/>
</dialog>

<dialog id="editLocationDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/editlocation.jsp"/>
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
        <c:if test="${createView == 'products'}">
            <div class="topbar">
                <form method="get" action="create.jsp" class="search-form">
                    <input type="hidden" name="createView" value="${createView}" />
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
        </c:if>

        <c:if test="${createView == 'categories'}">
            <div class="topbar">
                <form method="get" action="create.jsp" class="search-form">
                    <input type="hidden" name="createView" value="${createView}" />

                    <input type="text"
                           class="input"
                           name="search"
                           placeholder="Produkte suchen..."
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
        </c:if>

        <c:if test="${createView == 'locations'}">
            <div class="topbar">
                <form method="get" action="create.jsp" class="search-form">
                    <input type="hidden" name="createView" value="${createView}" />

                    <input type="text"
                           class="input"
                           name="search"
                           placeholder="Produkte suchen..."
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
        </c:if>

        <c:if test="${createView == 'customers'}">
            <div class="topbar">
                <form method="get" action="create.jsp" class="search-form">
                    <input type="hidden" name="createView" value="${createView}" />

                    <input type="text"
                           class="input"
                           name="search"
                           placeholder="Produkte suchen..."
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
        </c:if>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/create.js"></script>

</body>
</html>
