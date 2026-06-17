<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form class="form">
    <h2 id="deleteText">Löschen</h2>

    <c:if test="${not empty error}">
        <div>
            ${error}
        </div>
    </c:if>

    <input id="deleteType" type="hidden" name="type">
    <input id="deleteId" type="hidden" name="id">

    <div>
        <p>Sind Sie sicher, dass Sie wirklich löschen wollen?</p>
        <p>Diese Aktion kann nicht rückgängig gemacht werden.</p>
    </div>

    <div>
        <button class="btn btn-danger" type="button" onclick="confirmDelete()">
            Löschen
        </button>

        <button class="btn" type="button"
                onclick="closeDialog('deleteDialog')">
            Abbruch
        </button>
    </div>
</form>