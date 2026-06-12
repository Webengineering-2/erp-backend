<%@ page contentType="text/html;charset=UTF-8" %>

<form class="form form--dialog">

    <h2 class="form__title">Kunde bearbeiten</h2>

    <input type="hidden" id="customerId">

    <div class="form__group">
        <label class="form__label" for="editCustomerName">Name</label>
        <input class="input" id="editCustomerName" type="text">
    </div>

    <div class="form__actions">
        <button class="btn btn-primary" type="button" onclick="saveCustomer()">
            Save
        </button>

        <button class="btn btn-secondary" type="button"
                onclick="closeDialog('editCustomerDialog')">
            Cancel
        </button>
    </div>

</form>