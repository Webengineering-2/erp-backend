<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<style>
    #header {
        display: flex;
        width: 100%;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        gap: 4rem;
        height: 4rem;
        border-bottom: 1px solid black;
        background-color: lightgray;
    }
    #header h1 {
        margin-left: 1rem;
    }

    #header-button-group {
        display: flex;
        flex-direction: row;
        gap: .5rem;
        align-items: center;
        margin-right: 1rem;
    }
    #profile-button {
        anchor-name: --profile-button;
        border-radius: 50%;
        width: 2rem;
        height: 2rem;
    }
    #profile-popover {
        position-anchor: --profile-button;
        position-area: bottom span-left;
        margin: 0;
        padding: .5rem;
        border: 1px solid black;
        background-color: lightgray;
    }
    #profile-popover ul {
        list-style: none;
        margin: 0;
        padding: 0;
        display: flex;
        flex-direction: column;
        gap: .5rem;
    }
</style>

<div id="header">
    <h1>Warenwirtschaftsprogramm</h1>
    <div id="header-button-group">
        <a href="${pageContext.request.contextPath}/create">
            <button>+ Anlegen</button>
        </a>
        <a href="${pageContext.request.contextPath}/stock">
            <button>Bestände</button>
        </a>
        <a href="${pageContext.request.contextPath}/sold">
            <button>Verkäufe</button>
        </a>
        <button id="profile-button" popovertarget="profile-popover">P</button>
        <div id="profile-popover" popover>
            <ul>
                <li><a href="#">Benutzer erstellen</a></li>
                <li><a href="${pageContext.request.contextPath}/logs">Logs einsehen</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Abmelden</a></li>
            </ul>
        </div>
    </div>
</div>
