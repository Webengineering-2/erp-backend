<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form class="form form--dialog" onsubmit="createProduct(); return false;">

    <div class="form__group">
        <label class="form__label" for="productName">Name</label>
        <input class="input" id="productName" type="text" required>
    </div>

    <div class="form__group">
        <label class="form__label" for="categorySelect">Kategorie</label>
        <select class="input" id="categorySelect" required>
            <c:forEach var="c" items="${categories}">
                <option value="${c.id}">${c.name}</option>
            </c:forEach>
        </select>
    </div>

    <div class="form__group">
        <label class="form__label" for="productPrice">Preis</label>
        <input class="input" id="productPrice" type="number" step="0.01" min="0" required>
    </div>

    <div class="form__actions">
        <button class="btn btn-primary" type="submit">Erstellen</button>
        <button class="btn btn-secondary" type="button"
                onclick="closeDialog('createProductDialog')">
            Abbruch
        </button>
    </div>

</form>