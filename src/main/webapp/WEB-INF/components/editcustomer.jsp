<%@ page contentType="text/html;charset=UTF-8" %>

<form class="form" onsubmit="saveCustomer(); return false;">
    <h2>Kunde bearbeiten</h2>

    <input type="hidden" id="customerId">

    <div class="dialog-input">
        <label for="editCustomerName">Name</label>
        <input class="input" id="editCustomerName" type="text" required>
    </div>

    <div>
        <button class="btn btn-primary" type="submit" onclick="saveCustomer()">
            Save
        </button>

        <button class="btn" type="button" onclick="closeDialog('editCustomerDialog')">
            Cancel
        </button>
    </div>
</form>