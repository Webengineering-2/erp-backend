<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<html>
<head>
    <title>Create</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <style>
        .layout { display: flex; }
        .sidebar {
            width: 220px;
            background: #f3f3f3;
            padding: 10px;
        }

        .content {
            flex: 1;
            padding: 20px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 8px;
        }

        .top-bar {
            margin-bottom: 10px;
        }
    </style>
</head>

<body>

<div class="layout">

    <jsp:include page="sidebar.jsp"/>

    <div class="content">

        <c:if test="${createView == 'products'}">

            <div class="top-bar">
                <form method="get" action="create" style="display:flex; gap:10px; align-items:center;">
                    <input type="hidden" name="createView" value="${createView}" />

                    <input type="text"
                           name="search"
                           placeholder="Produkte suchen..."
                           value="${param.search}" />

                    <button type="submit">Search</button>
                </form>

                <button onclick="openCreateProductPopup()">+ Produkt erstellen</button>
            </div>
            <div class="table-container">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Aktionen</th>
                </tr>

                <c:forEach var="p" items="${products}">
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.name}</td>
                        <td>
                            <button  onclick="window.open('edit-product?id=${p.id}', 'edit', 'width=500,height=600')">
                                Edit
                            </button>

                            <button  onclick="openDeletePopup('${p.id}', 'product', '${p.name}')">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>

            </table>

        </c:if>
        <c:if test="${createView == 'categories'}">
            <div class="top-bar">
                <form method="get" action="create" style="display:flex; gap:10px; align-items:center;">
                    <input type="hidden" name="createView" value="${createView}" />

                    <input type="text"
                           name="search"
                           placeholder="Kategorien suchen..."
                           value="${param.search}" />

                    <button type="submit">Search</button>
                </form>

                <button onclick="openCreateCategoryPopup()">+ Kategorie erstellen</button>
            </div>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                </tr>

                <c:forEach var="c" items="${categories}">
                    <tr>
                        <td>${c.id}</td>
                        <td>${c.name}</td>
                        <td>
                        <button onclick="window.open('edit-category?id=${c.id}', 'edit', 'width=500,height=600')">
                            Edit
                        </button>
                            <button onclick="openDeletePopup('${c.id}', 'category', '${c.name}')">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>

            </table>

        </c:if>
        <c:if test="${createView == 'locations'}">

        <div class="top-bar">
            <form method="get" action="create" style="display:flex; gap:10px; align-items:center;">
                <input type="hidden" name="createView" value="${createView}" />

                <input type="text"
                       name="search"
                       placeholder="Locations suchen..."
                       value="${param.search}" />

                <button type="submit">Search</button>
            </form>

            <button onclick="openCreateLocationPopup()">+ Lagerort erstellen</button>
        </div>


            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                </tr>

                <c:forEach var="l" items="${locations}">
                    <tr>
                        <td>${l.id}</td>
                        <td>${l.name}</td>
                        <td>
                            <button onclick="window.open('edit-location?id=${l.id}', 'edit', 'width=500,height=600')">
                                Edit
                            </button>
                            <button onclick="openDeletePopup('${l.id}', 'location', '${l.name}')">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>

            </table>

        </c:if>
        <c:if test="${createView == 'customers'}">

            <div class="top-bar">
                <form method="get" action="create" style="display:flex; gap:10px; align-items:center;">
                    <input type="hidden" name="createView" value="${createView}" />

                    <input type="text"
                           name="search"
                           placeholder="Kunden suchen..."
                           value="${param.search}" />

                    <button type="submit">Search</button>
                </form>

                <button onclick="openCreateCustomerPopup()">+ Kunden erstellen</button>
            </div>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                </tr>

                <c:forEach var="cu" items="${customers}">
                    <tr>
                        <td>${cu.id}</td>
                        <td>${cu.name}</td>
                        <td>
                            <button onclick="window.open('edit-customer?id=${cu.id}', 'edit', 'width=500,height=600')">
                                Edit
                            </button>
                            <button onclick="openDeletePopup('${cu.id}', 'customer', '${cu.name}')">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>

            </table>

        </c:if>
            </div>
        <c:if test="${createView == null}">
            <h3>Bitte Auswahl im Menü treffen</h3>
        </c:if>

    </div>


</div>

<script>
    function openCreateProductPopup() {
        window.open(
            'create-product',
            'createProduct',
            'width=500,height=600'
        );
    }
    function openCreateCategoryPopup() {
        window.open(
            'create-category',
            'createCategory',
            'width=500,height=600'
        );
    }
    function openCreateLocationPopup() {
        window.open(
            'create-location',
            'createLocation',
            'width=500,height=600'
        );
    }
    function openCreateCustomerPopup() {
        window.open(
            'create-customer',
            'createCustomer',
            'width=500,height=600'
        );
    }
    function openDeletePopup(id, type, name) {
        const url =
            'delete-entity?id=' + id +
            '&type=' + type +
            '&name=' + encodeURIComponent(name);

        window.open(url, 'delete', 'width=400,height=300');
    }

</script>

</body>
</html>