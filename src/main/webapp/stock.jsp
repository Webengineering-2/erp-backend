<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>
<auth:requireLogin/>

<c:set var="products"  value="${creationService.getMatchingProducts(null)}"  scope="request"/>
<c:set var="locations" value="${creationService.getMatchingLocations(null)}" scope="request"/>
<c:set var="customers" value="${creationService.getMatchingCustomers(null)}" scope="request"/>

<html>
<head>
    <title>Bestände</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/2.3.8/css/dataTables.dataTables.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/columncontrol/1.2.1/css/columnControl.dataTables.min.css">
</head>

<body>
<jsp:include page="/WEB-INF/components/header.jsp"/>

<dialog id="createItemDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/createitem.jsp"/>
</dialog>

<dialog id="sellDialog" class="dialog">
    <jsp:include page="/WEB-INF/components/sellitem.jsp"/>
</dialog>

<div class="layout">
    <div class="content">
        <div class="topbar">
            <h2>Bestände</h2>
            <button type="button" class="btn btn-primary" onclick="openCreateItem()">
                + Warenbestand hinzufügen
            </button>
        </div>

        <div class="table-wrapper">
            <table id="stockTable" class="display" style="width:100%">
                <thead>
                    <tr>
                        <th>Produkt</th>
                        <th>Menge</th>
                        <th>Preis</th>
                        <th>Lagerort</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="i" items="${itemService.stockItems}">
                    <tr>
                        <td>${i.product.name}</td>
                        <td>${i.quantity}</td>
                        <td data-order="${i.unitBuyPrice}" data-search="${i.unitBuyPrice}">${i.unitBuyPrice} €</td>
                        <td>${i.location.name}</td>
                        <td>
                            <button class="btn btn-primary sell-btn"
                                    data-id="${i.id}"
                                    data-name="${i.product.name}"
                                    data-qty="${i.quantity}">
                                Verkaufen
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.datatables.net/2.3.8/js/dataTables.min.js"></script>
<script src="https://cdn.datatables.net/columncontrol/1.2.1/js/dataTables.columnControl.min.js"></script>
<script src="${pageContext.request.contextPath}/js/stock.js"></script>

</body>
</html>
