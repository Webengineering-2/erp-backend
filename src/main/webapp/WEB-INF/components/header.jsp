<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div id="header">
    <h1>Warenwirtschaftsprogramm</h1>
    <div id="header-button-group">
        <a href="${pageContext.request.contextPath}/create/products.jsp">
            <button>+ Anlegen</button>
        </a>
        <a href="${pageContext.request.contextPath}/stock.jsp">
            <button>Bestände</button>
        </a>
        <a href="${pageContext.request.contextPath}/sold.jsp">
            <button>Verkäufe</button>
        </a>
        <button id="profile-button" popovertarget="profile-popover">P</button>
        <div id="profile-popover" popover>
            <ul>
                <li><a href="${pageContext.request.contextPath}/createuser.jsp">Benutzer erstellen</a></li>
                <li><a href="${pageContext.request.contextPath}/logs.jsp">Logs einsehen</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Abmelden</a></li>
            </ul>
        </div>
    </div>
</div>
