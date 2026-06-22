<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- Guard for public pages (login/onboarding): skip them when already logged in. --%>
<c:if test="${not empty sessionScope.username}">
    <c:redirect url="/overview.jsp"/>
</c:if>
