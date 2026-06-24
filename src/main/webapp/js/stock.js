function openDialog(id){ document.getElementById(id).showModal(); }
function closeDialog(id){ document.getElementById(id).close(); }

document.addEventListener("DOMContentLoaded", () => {
    new DataTable("#stockTable", {
        ordering: { indicators: false },
        columnControl: [
            { target: 0, content: ['order', ['searchList', 'spacer', 'orderAsc', 'orderDesc', 'orderClear']] },
            // Second header row: a free-text search input per column.
            { target: 1, content: ['searchText'] }
        ],
        columnDefs: [
            { targets: '_all', className: 'dt-head-left' },
            { targets: [1, 2], columnControl: [
                { target: 0, content: ['order', ['orderAsc', 'orderDesc', 'orderClear']] },
                { target: 1, content: ['searchNumber'] }
            ] },
            // Action column
            { targets: [4], orderable: false, columnControl: [{ target: 0, content: [] }, { target: 1, content: [] }] }
        ]
    });

    document.querySelectorAll(".sell-btn").forEach(btn => {
        btn.addEventListener("click", () =>
            openSell(btn.dataset.id, btn.dataset.name, parseInt(btn.dataset.qty, 10)));
    });

    document.getElementById("sellQuantity").addEventListener("input", updateTotal);
    document.getElementById("sellPrice").addEventListener("input", updateTotal);
});

function openCreateItem(){
    const form = document.getElementById("createItemDialog").querySelector("form");
    if (form) form.reset();
    openDialog("createItemDialog");
}

function createItem(){
    fetch("/api/item", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            product:  { id: document.getElementById("itemProduct").value },
            location: { id: document.getElementById("itemLocation").value },
            quantity: parseInt(document.getElementById("itemQuantity").value, 10),
            unitBuyPrice: document.getElementById("itemBuyPrice").value
        })
    }).then(r => {
        if (!r.ok) { alert("Warenbestand konnte nicht angelegt werden."); return; }
        closeDialog("createItemDialog");
        location.reload();
    });
}

function openSell(id, name, qty){
    document.getElementById("sellItemId").value = id;
    document.getElementById("sellProductName").innerText = name;
    const q = document.getElementById("sellQuantity");
    q.max = qty;
    q.value = qty;
    document.getElementById("sellCustomer").value = "";
    document.getElementById("sellPrice").value = "";
    updateTotal();
    openDialog("sellDialog");
}

function updateTotal(){
    const qty = parseFloat(document.getElementById("sellQuantity").value) || 0;
    const price = parseFloat(document.getElementById("sellPrice").value) || 0;
    document.getElementById("sellTotal").innerText = (qty * price).toFixed(2) + " €";
}

function confirmSell(){
    const id = document.getElementById("sellItemId").value;
    const customer = document.getElementById("sellCustomer").value;
    fetch("/api/sell/" + id, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            quantity: parseInt(document.getElementById("sellQuantity").value, 10),
            sellUnitPrice: document.getElementById("sellPrice").value,
            customerId: customer === "" ? null : customer
        })
    }).then(r => {
        if (!r.ok) { alert("Verkauf fehlgeschlagen."); return; }
        closeDialog("sellDialog");
        location.reload();
    });
}
