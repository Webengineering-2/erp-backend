<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<form class="form form--dialog" onsubmit="createCategory(); return false;">

    <div class="form__group">
        <label class="form__label" for="categoryName">Name</label>
        <input class="input" id="categoryName" type="text">
    </div>

    <div class="form__group">
        <label class="form__label" for="categoryDescription">Beschreibung</label>
        <textarea class="textarea" id="categoryDescription"></textarea>
    </div>

    <div class="form__actions">
        <button class="btn btn-primary" type="submit">Erstellen</button>
        <button class="btn btn-secondary" type="button"
                onclick="closeDialog('createCategoryDialog')">
            Abbruch
        </button>
    </div>

</form>
