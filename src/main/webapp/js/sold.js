document.addEventListener("DOMContentLoaded", () => {
    new DataTable("#soldTable", {
        // Most recent sale first.
        order: [[0, "desc"]],
        ordering: { indicators: false },
        columnControl: [
            { target: 0, content: ['order', ['searchList', 'spacer', 'orderAsc', 'orderDesc', 'orderClear']] },
            // Second header row: a free-text search input per column.
            { target: 1, content: ['searchText'] }
        ],
        columnDefs: [
            { targets: '_all', className: 'dt-head-left' },
            // Numeric columns: amount + price.
            { targets: [2, 3], columnControl: [
                { target: 0, content: ['order', ['orderAsc', 'orderDesc', 'orderClear']] },
                { target: 1, content: ['searchNumber'] }
            ] }
        ]
    });
});
