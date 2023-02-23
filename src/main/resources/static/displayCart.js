function displayCart() {
    const cartData = localStorage.getItem('cart');
    let cart = {};
    if (cartData) {
        cart = JSON.parse(cartData);
    }

    const cartContainer = document.querySelector('.cart-container');

    for (const productId in cart) {
        const quantity = cart[productId];
        const product = getProductById(productId);
        if (product) {
            const itemContainer = document.createElement('div');
            itemContainer.classList.add('cart-item');

            const nameElement = document.createElement('span');
            nameElement.textContent = product.name;

            const quantityElement = document.createElement('span');
            quantityElement.textContent = `x${quantity}`;

            const priceElement = document.createElement('span');
            priceElement.textContent = `$${(product.price * quantity).toFixed(2)}`;

            itemContainer.appendChild(nameElement);
            itemContainer.appendChild(quantityElement);
            itemContainer.appendChild(priceElement);

            cartContainer.appendChild(itemContainer);
        }
    }
}
