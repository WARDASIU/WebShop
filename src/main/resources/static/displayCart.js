async function getProductById(productId) {
    const response = await fetch(`/api/products/${productId}`);
    const product = await response.json();
    return product;
}

async function displayCart() {
    const cartData = sessionStorage.getItem('cart');
    let cart = {};
    if (cartData) {
        cart = JSON.parse(cartData);
    }

    // Remove cart window if it already exists
    const cartWindow = document.getElementById('cart-window');
    if (cartWindow) {
        document.body.removeChild(cartWindow);
    }

    const newCartWindow = document.createElement('div');
    newCartWindow.id = 'cart-window';

    const cartHeader = document.createElement('div');
    cartHeader.id = 'cart-header';

    const cartTitle = document.createElement('h2');
    cartTitle.innerText = 'Cart';

    const closeButton = document.createElement('button');
    closeButton.innerHTML = '&#9664;'; // Use left arrow symbol as button text
    closeButton.id = 'cart-close-button';
    closeButton.addEventListener('click', closeCart);

    cartHeader.appendChild(cartTitle);
    cartHeader.appendChild(closeButton);
    newCartWindow.appendChild(cartHeader);

    const cartContent = document.createElement('div');
    cartContent.id = 'cart-content';

    let totalPrice = 0;
    for (const productId in cart) {
        const quantity = cart[productId];
        const product = await getProductById(productId);
        if (product) {
            const { name, price } = product;
            const productElement = document.createElement('div');
            productElement.classList.add('product');
            productElement.id = "product-" + productId;
            const productTotal = quantity * price;
            totalPrice += productTotal;

            const productName = document.createElement('div');
            productName.classList.add('product-name');
            productName.innerText = name;

            const productQuantity = document.createElement('div');
            productQuantity.classList.add('product-quantity');
            productQuantity.innerText = `${quantity} sztuki`;

            const productPrice = document.createElement('div');
            productPrice.classList.add('product-price');
            productPrice.innerText = `${productTotal.toFixed(2)} zł`;

            productElement.appendChild(productName);
            productElement.appendChild(productQuantity);
            productElement.appendChild(productPrice);

            const addButton = document.createElement('button');
            addButton.innerText = '+';
            addButton.addEventListener('click', () => addToCart(productId));

            const removeButton = document.createElement('button');
            removeButton.innerText = '-';
            removeButton.addEventListener('click', () => removeFromCart(productId));

            productElement.appendChild(addButton);
            productElement.appendChild(removeButton);

            cartContent.appendChild(productElement);
        }
    }

    const totalElement = document.createElement('p');
    totalElement.innerText = `Całość zamówienia: ${totalPrice.toFixed(2)} zł`;
    cartContent.appendChild(totalElement);
    newCartWindow.appendChild(cartContent);

    document.body.appendChild(newCartWindow);
}


async function updateCartData() {
    const cartWindow = document.getElementById('cart-window');
    if (cartWindow) {
        const cartData = sessionStorage.getItem('cart');
        let cart = {};
        if (cartData) {
            cart = JSON.parse(cartData);
        }
        let totalPrice = 0;
        for (const productId in cart) {
            const quantity = cart[productId];
            const productIdInString = productId.toString();
            const product = document.getElementById('product-' + productIdInString);
            if (product) {
                const {name, price} = await getProductById(productId);
                if (name) {
                    const productTotal = quantity * price;
                    totalPrice += productTotal;
                    product.querySelector('.product-quantity').innerText = `${quantity} sztuki`;
                    product.querySelector('.product-price').innerText = `${productTotal.toFixed(2)} zł`;
                } else {
                    product.remove();
                    delete cart[productId];
                }
            }
        }
        const totalElement = cartWindow.querySelector('p');
        if (totalElement) {
            totalElement.innerText = `Całość zamówienia: ${totalPrice.toFixed(2)} zł`;
        }
        sessionStorage.setItem('cart', JSON.stringify(cart));
    }
}


function closeCart() {
    const cartWindow = document.getElementById('cart-window');
    document.body.removeChild(cartWindow);
}

function addToCart(productId) {
    let cart = {};
    const cartData = sessionStorage.getItem('cart');
    if (cartData) {
        cart = JSON.parse(cartData);
    }
    if (cart[productId]) {
        cart[productId]++;
    } else {
        cart[productId] = 1;
    }
    sessionStorage.setItem('cart', JSON.stringify(cart));
    updateCartData();
}

function removeFromCart(productId) {
    let cart = {};
    const cartData = sessionStorage.getItem('cart');
    if (cartData) {
        cart = JSON.parse(cartData);
    }
    if (cart[productId]) {
        cart[productId]--;
        if (cart[productId] === 0) {
            delete cart[productId];
        }
    }
    sessionStorage.setItem('cart', JSON.stringify(cart));
    updateCartData();
}

