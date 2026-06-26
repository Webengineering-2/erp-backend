document.addEventListener("DOMContentLoaded", () => {
    new DataTable("#logsTable", {
        serverSide: true,
        processing: true,
        ordering: false,
        searching: true,
        pageLength: 25,
        ajax: {
            url: "/api/logs"
        },
        columns: [
            { data: "created" },
            { data: "user" },
            { data: "description" }
        ]
    });
});
