<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="sidebar">

    <nav class="sidebar__nav">
        <ul class="sidebar__list">

            <li class="sidebar__item">
                <a class="sidebar__link"
                   href="${pageContext.request.contextPath}/create?createView=categories">
                    Produktkategorien
                </a>
            </li>

            <li class="sidebar__item">
                <a class="sidebar__link"
                   href="${pageContext.request.contextPath}/create?createView=products">
                    Produkte
                </a>
            </li>

            <li class="sidebar__item">
                <a class="sidebar__link"
                   href="${pageContext.request.contextPath}/create?createView=locations">
                    Lagerlocations
                </a>
            </li>

            <li class="sidebar__item">
                <a class="sidebar__link"
                   href="${pageContext.request.contextPath}/create?createView=customers">
                    Kunden
                </a>
            </li>

        </ul>
    </nav>

</div>