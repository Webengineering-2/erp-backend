<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>
<auth:requireLogin/>

<html>
<head>
    <title>Logs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/2.3.8/css/dataTables.dataTables.min.css">
</head>

<body>
<jsp:include page="/WEB-INF/components/header.jsp"/>

<div class="layout">
    <div class="content">
        <div class="topbar">
            <h2>Logs</h2>
        </div>

        <div class="table-wrapper">
            <table id="logsTable" class="display" style="width:100%">
                <thead>
                    <tr>
                        <th>Zeitpunkt</th>
                        <th>Benutzer</th>
                        <th>Beschreibung</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.datatables.net/2.3.8/js/dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/js/logs.js"></script>

</body>
</html>
