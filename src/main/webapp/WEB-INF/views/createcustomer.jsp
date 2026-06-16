<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form class="form" onsubmit="createCustomer(); return false;">

    <div class="dialog-input">
        <label for="customerName">Name</label>
        <input class="input" id="customerName" type="text" required>
    </div>

    <div>
        <button class="btn btn-primary" type="submit">Erstellen</button>
        <button class="btn" type="button" onclick="closeDialog('createCustomerDialog')">
            Abbruch
        </button>
    </div>
</form>