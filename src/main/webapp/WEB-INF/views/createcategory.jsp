<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form class="form" onsubmit="createCategory(); return false;">
    <div class="dialog-input">
        <label for="categoryName">Name</label>
        <input class="input" id="categoryName" type="text" required>
    </div>

    <div class="dialog-input">
        <label for="categoryDescription">Beschreibung</label>
        <textarea class="textarea" id="categoryDescription"></textarea>
    </div>

    <div>
        <button class="btn btn-primary" type="submit">Erstellen</button>
        <button class="btn" type="button"
                onclick="closeDialog('createCategoryDialog')">
            Abbruch
        </button>
    </div>
</form>
