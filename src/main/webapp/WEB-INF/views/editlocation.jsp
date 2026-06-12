<%@ page contentType="text/html;charset=UTF-8" %>

<form class="form form--dialog">

    <h2 class="form__title">Lagerort bearbeiten</h2>

    <input type="hidden" name="id" id="locationId"/>

    <div class="form__group">
        <label class="form__label" for="locationName">Name</label>
        <input class="input" type="text" name="name" id="locationName" />
    </div>

    <div class="form__group">
        <label class="form__label" for="locationDescription">Description</label>
        <textarea class="textarea" name="description" id="locationDescription"></textarea>
    </div>

    <div class="form__actions">
        <button class="btn btn-primary" type="button" onclick="saveLocation()">
            Speichern
        </button>

        <button class="btn btn-secondary" type="button"
                onclick="closeDialog('editLocationDialog')">
            Abbrechen
        </button>
    </div>

</form>