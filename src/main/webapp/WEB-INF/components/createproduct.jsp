<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form class="form" onsubmit="createProduct(); return false;">
    <div class="dialog-input">
        <label for="productName">Name</label>
        <input class="input" id="productName" type="text" required>
    </div>

    <div class="dialog-input">
        <label for="categorySelect">Kategorie</label>
        <select class="input" id="categorySelect" required>
            <c:forEach var="c" items="${categories}">
                <option value="${c.id}">${c.name}</option>
            </c:forEach>
        </select>
    </div>

    <div class="dialog-input">
        <label for="productPrice">Preis</label>
        <input class="input" id="productPrice" type="number" step="0.01" min="0" required>
    </div>

    <div>
        <button class="btn btn-primary" type="submit">Erstellen</button>
        <button class="btn" type="button"
                onclick="closeDialog('createProductDialog')">
            Abbruch
        </button>
    </div>
</form>