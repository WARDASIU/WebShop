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
        </tr>
      </thead>
      <tbody>
      </tbody>
    `;
            const tbody = table.querySelector('tbody');
            products.forEach(item => {
                const tr = document.createElement('tr');
                const tdName = document.createElement('td');
                tdName.textContent = item.name;
                const tdDesc = document.createElement('td');
                tdDesc.textContent = item.description;
                const tdPrice = document.createElement('td');
                tdPrice.textContent = item.price;
                const tdStock = document.createElement('td');
                tdStock.textContent = item.in_stock;
                tr.appendChild(tdName);
                tr.appendChild(tdDesc);
                tr.appendChild(tdPrice);
                tr.appendChild(tdStock);
                tbody.appendChild(tr);
            });
            formContainer.appendChild(table);
        });

});

deleteProductLink.addEventListener("click", () => {
    formContainer.innerHTML = `
      <h2>Delete product</h2>
      <form>
        <!-- form fields for deleting a product -->
      </form>
    `;
});