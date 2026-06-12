<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form class="form form--dialog" onsubmit="createLocation(); return false;">

    <div class="form__group">
        <label class="form__label" for="locationNameCreate">Name</label>
        <input class="input" id="locationNameCreate" type="text">
    </div>

    <div class="form__group">
        <label class="form__label" for="locationDescriptionCreate">Beschreibung</label>
        <textarea class="textarea" id="locationDescriptionCreate"></textarea>
    </div>

    <div class="form__actions">
        <button class="btn btn-primary" type="submit">Erstellen</button>
        <button class="btn btn-secondary" type="button"
                onclick="closeDialog('createLocationDialog')">
            Abbruch
        </button>
    </div>

</form>