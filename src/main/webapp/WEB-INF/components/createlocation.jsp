<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form class="form" onsubmit="createLocation(); return false;">
    <div class="dialog-input">
        <label for="locationNameCreate">Name</label>
        <input class="input" id="locationNameCreate" type="text" required>
    </div>

    <div class="dialog-input">
        <label for="locationDescriptionCreate">Beschreibung</label>
        <textarea class="textarea" id="locationDescriptionCreate"></textarea>
    </div>

    <div>
        <button class="btn btn-primary" type="submit">Erstellen</button>
        <button class="btn" type="button"
                onclick="closeDialog('createLocationDialog')">
            Abbruch
        </button>
    </div>
</form>