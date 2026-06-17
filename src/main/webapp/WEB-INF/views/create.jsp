<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<html>
<head>
    <title>Create</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>

<body>
<jsp:include page="../components/header.jsp"/>

<dialog id="deleteDialog" class="dialog">
    <jsp:include page="deleteEntity.jsp"/>
</dialog>

<dialog id="createProductDialog" class="dialog">
    <jsp:include page="createproduct.jsp"/>
</dialog>

<dialog id="editProductDialog" class="dialog">
    <jsp:include page="editproduct.jsp"/>
</dialog>

<dialog id="createCategoryDialog" class="dialog">
    <jsp:include page="createcategory.jsp"/>
</dialog>

<dialog id="editCategoryDialog" class="dialog">
    <jsp:include page="editcategory.jsp"/>
</dialog>

<dialog id="createLocationDialog" class="dialog">
    <jsp:include page="createlocation.jsp"/>
</dialog>

<dialog id="editLocationDialog" class="dialog">
    <jsp:include page="editlocation.jsp"/>
</dialog>

<dialog id="createCustomerDialog" class="dialog">
    <jsp:include page="createcustomer.jsp"/>
</dialog>

<dialog id="editCustomerDialog" class="dialog">
    <jsp:include page="editcustomer.jsp"/>
</dialog>

<div class="layout">
    <jsp:include page="sidebar.jsp"/>

    <div class="content">
        <c:if test="${createView == 'products'}">
            <div class="topbar">
                <form method="get" action="create" class="search-form">
                    <input type="hidden" name="createView" value="${createView}" />
                    <input type="text"
                           class="input"
                           name="search"
                           placeholder="Produkte suchen..."
                           value="${param.search}" />

                    <button type="submit" class="btn">
                        Search
                    </button>
                </form>

                <button type="button" class="btn btn-primary" onclick="openCreateProduct()">
                    + Produkt
                </button>
            </div>

            <div class="table-wrapper">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th></th>
                        </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="p" items="${products}">
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.name}</td>
                            <td>
                                <button class="btn" onclick="openEditProduct(${p.id})">Edit</button>
                                <button class="btn btn-danger"
                                        onclick="openDeleteDialog('${p.id}','product','${p.name}')">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${createView == 'categories'}">
            <div class="topbar">
                <form method="get" action="create" class="search-form">
                    <input type="hidden" name="createView" value="${createView}" />

                    <input type="text"
                           class="input"
                           name="search"
                           placeholder="Produkte suchen..."
                           value="${param.search}" />

                    <button type="submit" class="btn">
                        Search
                    </button>
                </form>

                <button class="btn btn-primary" type="button" onclick="openCreateCategory()">
                    + Kategorie
                </button>
            </div>

            <div class="table-wrapper">
                <table class="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="c" items="${categories}">
                        <tr>
                            <td>${c.id}</td>
                            <td>${c.name}</td>
                            <td>
                                <button class="btn" onclick="openEditCategory(${c.id})">Edit</button>
                                <button class="btn btn-danger"
                                        onclick="openDeleteDialog('${c.id}','category','${c.name}')">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${createView == 'locations'}">
            <div class="topbar">
                <form method="get" action="create" class="search-form">
                    <input type="hidden" name="createView" value="${createView}" />

                    <input type="text"
                           class="input"
                           name="search"
                           placeholder="Produkte suchen..."
                           value="${param.search}" />

                    <button type="submit" class="btn">
                        Search
                    </button>
                </form>

                <button class="btn btn-primary" type="button" onclick="openCreateLocation()">
                    + Location
                </button>

            </div>

            <div class="table-wrapper">
                <table class="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="l" items="${locations}">
                        <tr>
                            <td>${l.id}</td>
                            <td>${l.name}</td>
                            <td>
                                <button class="btn" onclick="openEditLocation(${l.id})">Edit</button>
                                <button class="btn btn-danger"
                                        onclick="openDeleteDialog('${l.id}','location','${l.name}')">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${createView == 'customers'}">
            <div class="topbar">
                <form method="get" action="create" class="search-form">
                    <input type="hidden" name="createView" value="${createView}" />

                    <input type="text"
                           class="input"
                           name="search"
                           placeholder="Produkte suchen..."
                           value="${param.search}" />

                    <button type="submit" class="btn">
                        Search
                    </button>
                </form>

                <button class="btn btn-primary" type="button" onclick="openCreateCustomer()">
                    + Kunde
                </button>
            </div>

            <div class="table-wrapper">
                <table class="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="cu" items="${customers}">
                        <tr>
                            <td>${cu.id}</td>
                            <td>${cu.name}</td>
                            <td>
                                <button class="btn" onclick="openEditCustomer(${cu.id})">Edit</button>
                                <button class="btn btn-danger"
                                        onclick="openDeleteDialog('${cu.id}','customer','${cu.name}')">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>

<script>
    function openDialog(id){ document.getElementById(id).showModal(); }
    function closeDialog(id){ document.getElementById(id).close(); }

    function openCreateProduct(){ openDialog("createProductDialog"); }
    function openCreateCategory(){ openDialog("createCategoryDialog"); }
    function openCreateLocation(){ openDialog("createLocationDialog"); }
    function openCreateCustomer(){ openDialog("createCustomerDialog"); }

    function openEditProduct(id){
        fetch("/api/product/id/"+id)
            .then(r => r.json())
            .then(p => {
                document.getElementById("productId").value=p.id;
                document.getElementById("editName").value=p.name;
                document.getElementById("editPrice").value=p.unitPrice;
                document.getElementById("editCategory").value=p.category.id;
                openDialog("editProductDialog");
            });
    }

    function saveProduct(){
        const id=document.getElementById("productId").value;
        fetch("/api/product/id/"+id,{
            method:"PUT",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({
                name:document.getElementById("editName").value,
                unitPrice:document.getElementById("editPrice").value,
                category:{id:document.getElementById("editCategory").value}
            })
        }).then(() => {
            closeDialog("editProductDialog");
            location.reload();
        });
    }

    function createProduct(){
        fetch("/api/product",{
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({
                name:document.getElementById("productName").value,
                unitPrice:document.getElementById("productPrice").value,
                category:{id:document.getElementById("categorySelect").value}
            })
        }).then(() => {
            closeDialog("createProductDialog");
            location.reload();
        });
    }

    function openEditCategory(id){
        fetch("/api/category/id/"+id)
            .then(r=>r.json())
            .then(c=>{
                document.getElementById("categoryId").value=c.id;
                document.getElementById("editCategoryName").value=c.name;
                document.getElementById("editCategoryDescription").value=c.description;
                openDialog("editCategoryDialog");
            });
    }

    function saveCategory(){
        const id=document.getElementById("categoryId").value;
        fetch("/api/category/id/"+id,{
            method:"PUT",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({
                name:document.getElementById("editCategoryName").value,
                description:document.getElementById("editCategoryDescription").value
            })
        }).then(() => {
            closeDialog("editCategoryDialog");
            location.reload();
        });
    }

    function createCategory(){
        fetch("/api/category",{
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({
                name:document.getElementById("categoryName").value,
                description:document.getElementById("categoryDescription").value
            })
        }).then(() => {
            closeDialog("createCategoryDialog");
            location.reload();
        });
    }

    function openEditLocation(id){
        fetch("/api/location/id/"+id)
            .then(r=>r.json())
            .then(l=>{
                document.getElementById("locationId").value=l.id;
                document.getElementById("locationName").value=l.name;
                document.getElementById("locationDescription").value=l.description;
                openDialog("editLocationDialog");
            });
    }

    function saveLocation(){
        const id=document.getElementById("locationId").value;
        fetch("/api/location/id/"+id,{
            method:"PUT",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({
                name:document.getElementById("locationName").value,
                description:document.getElementById("locationDescription").value
            })
        }).then(() => {
            closeDialog("editLocationDialog");
            location.reload();
        });
    }

    function createLocation(){
        fetch("/api/location",{
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({
                name:document.getElementById("locationNameCreate").value,
                description:document.getElementById("locationDescriptionCreate").value
            })
        }).then(() => {
            closeDialog("createLocationDialog");
            location.reload();
        });
    }

    function openEditCustomer(id){
        fetch("/api/customer/id/"+id)
            .then(r => r.json())
            .then(c => {
                document.getElementById("customerId").value=c.id;
                document.getElementById("editCustomerName").value=c.name;
                openDialog("editCustomerDialog");
            });
    }

    function saveCustomer(){
        const id=document.getElementById("customerId").value;
        fetch("/api/customer/id/"+id,{
            method:"PUT",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({
                name:document.getElementById("editCustomerName").value
            })
        }).then(() => {
            closeDialog("editCustomerDialog");
            location.reload();
        });
    }

    function createCustomer(){
        fetch("/api/customer",{
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({
                name:document.getElementById("customerName").value
            })
        }).then(() => {
            closeDialog("createCustomerDialog");
            location.reload();
        });
    }

    function openDeleteDialog(id,type,name){
        document.getElementById("deleteId").value=id;
        document.getElementById("deleteType").value=type;
        document.getElementById("deleteText").innerText=name+" wirklich löschen?";
        openDialog("deleteDialog");
    }

    function confirmDelete(){
        const id=document.getElementById("deleteId").value;
        const type=document.getElementById("deleteType").value;

        fetch("/api/"+type+"/id/"+id, { method: "DELETE" })
            .then(() => {
                closeDialog("deleteDialog");
                location.reload();
            });
    }
</script>

</body>
</html>