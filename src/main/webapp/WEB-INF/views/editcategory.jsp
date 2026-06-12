<%@ page contentType="text/html;charset=UTF-8" %>

<form class="form form--dialog">

    <h2 class="form__title">Kategorie bearbeiten</h2>

    <input type="hidden" name="id" id="categoryId"/>

    <div class="form__group">
        <label class="form__label" for="editCategoryName">Name</label>
        <input class="input" id="editCategoryName" type="text">
    </div>

    <div class="form__group">
        <label class="form__label" for="editCategoryDescription">Description</label>
        <textarea class="textarea" id="editCategoryDescription" name="description"></textarea>
    </div>

    <div class="form__actions">
        <button class="btn btn-primary" type="button" onclick="saveCategory()">
            Speichern
        </button>

        <button class="btn btn-secondary" type="button"
                onclick="closeDialog('editCategoryDialog')">
            Abbruch
        </button>
    </div>

</form>