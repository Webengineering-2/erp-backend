<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- Guard for protected pages: redirect to root when no user is logged in. --%>
<c:if test="${empty sessionScope.username}">
    <c:redirect url="/"/>
</c:if>
