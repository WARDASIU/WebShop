const addProductLink = document.querySelector(".add-product");
const editProductLink = document.querySelector(".edit-product");
const deleteProductLink = document.querySelector(".delete-product");
const formContainer = document.querySelector(".form-container");

addProductLink.addEventListener("click", () => {
    formContainer.innerHTML = `
      <div class="container text-center" style="margin-top: 50px; width: 100%">
    <form class="form-signin text-center" th:action="@{/product/add}" method="POST">
        <h1 class="h3 mb-3 font-weight-normal">Dodaj produkt</h1>
        
        <input type="text" name="name" id="name" class="form-control" placeholder="Nazwa produktu" required autofocus style="margin: 15px 0;">
        <input type="text" name="description" id="description" class="form-control" placeholder="Opis produktu" required style="margin: 15px 0;">
        <input type="number" name="price" id="price" class="form-control" placeholder="Cena produktu" required style="margin: 15px 0;">
        <input type="number" name="in_stock" id="in_stock" class="form-control" placeholder="Podaj dostępną ilość" required style="margin: 15px 0;">

        <button class="btn btn-lg btn-primary btn-block" type="submit" style="width: 40%; margin: 0 auto;">Dodaj produkt</button>   
    </form>
</div>
    `;
});

editProductLink.addEventListener("click", () => {
    formContainer.innerHTML = `
      
`;
    fetch('http://localhost:8080/api/products')
        .then(response => response.json())
        .then(products => {
            const table = document.createElement('table');
            table.classList.add('table');
            table.innerHTML = `
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>In Stock</th>
                    <th>Detailed Description</th>
                    <th>Sizes</th>
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
                deleteButton.textContent = 'Delete';
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

deleteProductLink.addEventListener("click", () => {
    formContainer.innerHTML = `
      <h2>Delete product</h2>
      <form>
        <!-- form fields for deleting a product -->
      </form>
    `;
});