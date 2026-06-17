<%@ page contentType="text/html;charset=UTF-8" %>

<form class="form" onsubmit="saveLocation(); return false;">
    <h2>Lagerort bearbeiten</h2>

    <input type="hidden" name="id" id="locationId"/>

    <div class="dialog-input">
        <label for="locationName">Name</label>
        <input class="input" type="text" name="name" id="locationName" required />
    </div>

    <div class="dialog-input">
        <label for="locationDescription">Description</label>
        <textarea class="textarea" name="description" id="locationDescription"></textarea>
    </div>

    <div>
        <button class="btn btn-primary" type="submit">Speichern</button>

        <button class="btn" type="button" onclick="closeDialog('editLocationDialog')">
            Abbrechen
        </button>
    </div>
</form>