<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form onsubmit="saveProduct(); return false;" class="form">
    <input id="productId" type="hidden" name="id" value="${product.id}" />

    <div class="dialog-input">
        <label>Name</label>
        <input id="editName" type="text" name="name" value="${product.name}" required />
    </div>

    <div class="dialog-input">
        <label>Price</label>
        <input id="editPrice" type="number" step="0.01" name="price" value="${product.unitPrice}" required />
    </div>

    <div class="dialog-input">
        <label>Category</label>
        <select id="editCategory" name="categoryId" required>
            <c:forEach var="c" items="${categories}">
                <option value="${c.id}"
                        <c:if test="${c.id == product.category.id}">
                            selected
                        </c:if>>
                        ${c.name}
                </option>
            </c:forEach>
        </select>
    </div>

    <div>
        <button type="submit" class="btn btn-primary">Speichern</button>
        <button type="button" class="btn" onclick="closeDialog('editProductDialog')">Abbrechen</button>
    </div>
</form>


