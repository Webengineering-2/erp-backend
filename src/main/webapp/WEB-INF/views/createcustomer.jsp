<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>



<form class="form form--dialog" onsubmit="createCustomer(); return false;">

    <div class="form__group">
        <label class="form__label" for="customerName">Name</label>
        <input class="input" id="customerName" type="text">
    </div>

    <div class="form__actions">
        <button class="btn btn-primary" type="submit">Erstellen</button>
        <button class="btn btn-secondary" type="button"
                onclick="closeDialog('createCustomerDialog')">
            Abbruch
        </button>
    </div>

</form>

