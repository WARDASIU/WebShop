const editProductLink = document.querySelector(".edit-product");
const formContainer = document.querySelector(".form-container");

editProductLink.addEventListener("click", () => {
    formContainer.innerHTML = `
      
`;
    fetch('/api/products')
        .then(response => response.json())
        .then(products => {
            const table = document.createElement('table');
            table.classList.add('table');
            table.innerHTML = `
            <thead>
                <tr>
                    <th>Nazwa</th>
                    <th>Opis</th>
                    <th>Cena</th>
                    <th>Ilość</th>
                    <th>Szczegółowy opis</th>
                    <th>Rozmiary</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        `;
            const tbody = table.querySelector('tbody');
            products.forEach(item => {
                const tr = document.createElement('tr');
                const tdName = createEditableCell(item.name, 'name', item.idProducts);
                const tdDesc = createEditableCell(item.description, 'description', item.idProducts);
                const tdPrice = createEditableCell(item.price, 'price', item.idProducts);
                const tdStock = createEditableCell(item.in_stock, 'in_stock', item.idProducts);
                const tdDetailedDescription = createEditableCell(item.detailedDescription, 'detailedDescription', item.idProducts);
                const tdSizes = createEditableCell(item.sizes, 'sizes', item.idProducts);
                const tdDelete = document.createElement('td');
                const deleteButton = document.createElement('button');
                deleteButton.className = "deleteButton";
                deleteButton.textContent = 'Usuń produkt';
                deleteButton.addEventListener('click', function() {
                    deleteProduct(item.idProducts);
                    tr.remove();
                });
                tdDelete.appendChild(deleteButton);
                tr.appendChild(tdName);
                tr.appendChild(tdDesc);
                tr.appendChild(tdPrice);
                tr.appendChild(tdStock);
                tr.appendChild(tdDetailedDescription);
                tr.appendChild(tdSizes);
                tr.appendChild(tdDelete)
                tbody.appendChild(tr);
            });
            table.addEventListener('dblclick', function (e) {
                const target = e.target;
                if (target.tagName === 'TD' && target.dataset.editable === 'true') {
                    const input = document.createElement('input');
                    input.value = target.textContent;
                    target.innerHTML = '';
                    target.appendChild(input);
                    input.focus();
                    input.addEventListener('blur', function () {
                        const value = input.value.trim();
                        const idProducts = target.dataset.idProducts;
                        const field = target.dataset.field;
                        updateProductData(idProducts, field, value)
                            .then(() => {
                                target.textContent = value;
                            })
                            .catch(error => {
                                console.log(error);
                                target.textContent = target.dataset.oldValue;
                            });
                    });
                    input.addEventListener('keydown', function (e) {
                        if (e.key === 'Escape') {
                            input.value = target.dataset.oldValue;
                            input.blur();
                        }
                    });
                    input.addEventListener('keydown', function (e) {
                        if (e.key === 'Escape') {
                            input.value = target.dataset.oldValue;
                            input.blur();
                        } else if (e.key === 'Enter') {
                            input.blur();
                        }
                    });
                }
            });
            formContainer.appendChild(table);
        });

    function createEditableCell(value, field, idProducts) {
        const td = document.createElement('td');
        td.textContent = value;
        td.dataset.field = field;
        td.dataset.idProducts = idProducts;
        td.dataset.editable = true;
        return td;
    }

    async function updateProductData(idProducts, field, value) {
        const body = {};
        body[field] = value;
        const response = await fetch('/api/products/' + idProducts, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });
        if (!response.ok) {
            throw new Error('Failed to update product data.');
        }
        const data = await response.json();
        return data;
    }

    async function deleteProduct(idProducts) {
        const response = await fetch('/api/products/' + idProducts, {
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error('Failed to delete product.');
        }
        const data = await response.json();
        return data;
    }
});