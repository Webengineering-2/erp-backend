<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>
<auth:requireLogin/>

<html>
<head>
    <title>Verkäufe</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/2.3.8/css/dataTables.dataTables.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/columncontrol/1.2.1/css/columnControl.dataTables.min.css">
</head>

<body>
<jsp:include page="/WEB-INF/components/header.jsp"/>

<div class="layout">
    <div class="content">
        <div class="topbar">
            <h2>Verkäufe</h2>
        </div>

        <div class="table-wrapper">
            <table id="soldTable" class="display" style="width:100%">
                <thead>
                    <tr>
                        <th>Datum</th>
                        <th>Artikel</th>
                        <th>Menge</th>
                        <th>Preis</th>
                        <th>Empfänger</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="i" items="${itemService.soldItems}">
                    <tr>
                        <td data-order="${i.updated}" data-search="${i.updated}">${itemService.formatDate(i.updated)}</td>
                        <td>${i.product.name}</td>
                        <td>${i.quantity}</td>
                        <td data-order="${i.sellUnitPrice}" data-search="${i.sellUnitPrice}">${i.sellUnitPrice} €</td>
                        <td>${i.soldTo.name}</td>
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
<script src="${pageContext.request.contextPath}/js/sold.js"></script>

</body>
</html>
