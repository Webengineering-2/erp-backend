<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<form>

    <input id="productId" type="hidden" name="id" value="${product.id}" />

    <label>Name</label>
    <input id="editName" type="text" name="name" value="${product.name}" />

    <label>Price</label>
    <input  id="editPrice" type="number" step="0.01" name="price" value="${product.unitPrice}" />

    <label>Category</label>
    <select id="editCategory" name="categoryId">
        <c:forEach var="c" items="${categories}">
            <option value="${c.id}"
                    <c:if test="${c.id == product.category.id}">
                        selected
                    </c:if>>
                    ${c.name}
            </option>
        </c:forEach>
    </select>

    <button type="button" onclick="saveProduct()">Speichern</button>
    <button type="button" onclick="closeDialog('editProductDialog')">Abbrechen</button>

</form>


