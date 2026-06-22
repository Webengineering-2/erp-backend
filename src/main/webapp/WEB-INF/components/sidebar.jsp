<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="sidebar">
    <nav>
        <ul>
            <li>
                <a class="sidebar-link"
                   href="${pageContext.request.contextPath}/create.jsp?createView=categories">
                    Produktkategorien
                </a>
            </li>

            <li>
                <a class="sidebar-link"
                   href="${pageContext.request.contextPath}/create.jsp?createView=products">
                    Produkte
                </a>
            </li>

            <li>
                <a class="sidebar-link"
                   href="${pageContext.request.contextPath}/create.jsp?createView=locations">
                    Lagerlocations
                </a>
            </li>

            <li>
                <a class="sidebar-link"
                   href="${pageContext.request.contextPath}/create.jsp?createView=customers">
                    Kunden
                </a>
            </li>

        </ul>
    </nav>
</div>
