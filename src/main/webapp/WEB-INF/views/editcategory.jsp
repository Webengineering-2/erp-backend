<%@ page contentType="text/html;charset=UTF-8" %>

<form class="form" onsubmit="saveCategory(); return false;">
    <h2>Kategorie bearbeiten</h2>

    <input type="hidden" name="id" id="categoryId" />

    <div class="dialog-input">
        <label for="editCategoryName">Name</label>
        <input class="input" id="editCategoryName" type="text" required>
    </div>

    <div class="dialog-input">
        <label for="editCategoryDescription">Description</label>
        <textarea class="textarea" id="editCategoryDescription" name="description"></textarea>
    </div>

    <div>
        <button class="btn btn-primary" type="submit">
            Speichern
        </button>

        <button class="btn btn-secondary" type="button"
                onclick="closeDialog('editCategoryDialog')">
            Abbruch
        </button>
    </div>
</form>