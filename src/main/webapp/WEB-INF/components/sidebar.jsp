<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="sidebar">
    <nav>
        <ul>
            <li>
                <a class="sidebar-link"
                   href="${pageContext.request.contextPath}/create/products.jsp">
                    Produkte
                </a>
            </li>

            <li>
                <a class="sidebar-link"
                   href="${pageContext.request.contextPath}/create/categories.jsp">
                    Produktkategorien
                </a>
            </li>

            <li>
                <a class="sidebar-link"
                   href="${pageContext.request.contextPath}/create/locations.jsp">
                    Lagerlocations
                </a>
            </li>

            <li>
                <a class="sidebar-link"
                   href="${pageContext.request.contextPath}/create/customers.jsp">
                    Kunden
                </a>
            </li>

        </ul>
    </nav>
</div>
