function openDialog(id){ document.getElementById(id).showModal(); }
function closeDialog(id){ document.getElementById(id).close(); }

function openCreateDialog(id){
    const form = document.getElementById(id).querySelector("form");
    if (form) form.reset();
    openDialog(id);
}

function openCreateProduct(){ openCreateDialog("createProductDialog"); }
function openCreateCategory(){ openCreateDialog("createCategoryDialog"); }
function openCreateLocation(){ openCreateDialog("createLocationDialog"); }
function openCreateCustomer(){ openCreateDialog("createCustomerDialog"); }

function openEditProduct(id){
    fetch("/api/product/"+id)
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
    fetch("/api/product/"+id,{
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
    fetch("/api/category/"+id)
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
    fetch("/api/category/"+id,{
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
    fetch("/api/location/"+id)
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
    fetch("/api/location/"+id,{
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
    fetch("/api/customer/"+id)
        .then(r => r.json())
        .then(c => {
            document.getElementById("customerId").value=c.id;
            document.getElementById("editCustomerName").value=c.name;
            openDialog("editCustomerDialog");
        });
}

function saveCustomer(){
    const id=document.getElementById("customerId").value;
    fetch("/api/customer/"+id,{
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

    fetch("/api/"+type+"/"+id, { method: "DELETE" })
        .then(() => {
            closeDialog("deleteDialog");
            location.reload();
        });
}