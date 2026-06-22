<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form class="form" onsubmit="createItem(); return false;">
    <h2>Warenbestand erstellen</h2>

    <div class="dialog-input">
        <label for="itemProduct">Produkt</label>
        <select class="input" id="itemProduct" required>
            <c:forEach var="p" items="${products}">
                <option value="${p.id}">${p.name}</option>
            </c:forEach>
        </select>
    </div>

    <div class="dialog-input">
        <label for="itemQuantity">Anzahl</label>
        <input class="input" id="itemQuantity" type="number" min="1" value="1" required>
    </div>

    <div class="dialog-input">
        <label for="itemBuyPrice">Kaufpreis (€/Stk.)</label>
        <input class="input" id="itemBuyPrice" type="number" step="0.01" min="0" required>
    </div>

    <div class="dialog-input">
        <label for="itemLocation">Lagerort</label>
        <select class="input" id="itemLocation" required>
            <c:forEach var="l" items="${locations}">
                <option value="${l.id}">${l.name}</option>
            </c:forEach>
        </select>
    </div>

    <div>
        <button class="btn btn-primary" type="submit">Hinzufügen</button>
        <button class="btn" type="button" onclick="closeDialog('createItemDialog')">Abbruch</button>
    </div>
</form>
