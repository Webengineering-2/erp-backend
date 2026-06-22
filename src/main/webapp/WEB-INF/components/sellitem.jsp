<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form class="form" onsubmit="confirmSell(); return false;">
    <input id="sellItemId" type="hidden">
    <h2>Ware verkaufen</h2>
    <p id="sellProductName"></p>

    <div class="dialog-input">
        <label for="sellQuantity">Anzahl</label>
        <input class="input" id="sellQuantity" type="number" min="1" required>
    </div>

    <div class="dialog-input">
        <label for="sellPrice">Verkaufspreis (€/Stk.)</label>
        <input class="input" id="sellPrice" type="number" step="0.01" min="0" required>
    </div>

    <div class="dialog-input">
        <label for="sellCustomer">Käufer</label>
        <select class="input" id="sellCustomer">
            <option value="">keine ausgewählt</option>
            <c:forEach var="cu" items="${customers}">
                <option value="${cu.id}">${cu.name}</option>
            </c:forEach>
        </select>
    </div>

    <p>Gesamtpreis: <span id="sellTotal">0.00 €</span></p>

    <div>
        <button class="btn btn-primary" type="submit">Verkaufen</button>
        <button class="btn" type="button" onclick="closeDialog('sellDialog')">Abbruch</button>
    </div>
</form>
