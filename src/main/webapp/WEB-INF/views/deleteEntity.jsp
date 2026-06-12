<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form class="form form--dialog">

    <h2 class="form__title" id="deleteText">Löschen</h2>

    <c:if test="${not empty error}">
        <div class="alert alert--danger">
                ${error}
        </div>
    </c:if>

    <input id="deleteType" type="hidden" name="type">
    <input id="deleteId" type="hidden" name="id">

    <div class="form__text">
        <p>Sind Sie sicher, dass Sie wirklich löschen wollen?</p>
        <p>Diese Aktion kann nicht rückgängig gemacht werden.</p>
    </div>

    <div class="form__actions">
        <button class="btn btn-danger" type="button" onclick="confirmDelete()">
            Löschen
        </button>

        <button class="btn btn-secondary" type="button"
                onclick="closeDialog('deleteDialog')">
            Abbruch
        </button>
    </div>

</form>