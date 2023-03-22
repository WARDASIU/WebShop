async function getProductById(productId) {
    const response = await fetch(`/api/products/${productId}`);
    const product = await response.json();
    return product;
}

function buyProductsInCart() {
    window.location.href = "/prepareOrder";
}

async function displayCart() {
    const modalBackground = document.createElement('div');
    modalBackground.classList.add('modal-background');
    document.body.appendChild(modalBackground);

    const cartData = sessionStorage.getItem('cart');
    let cart = {};
    if (cartData) {
        cart = JSON.parse(cartData);
    }

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
    closeButton.innerHTML = '&#9664;';
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
            addButton.addEventListener('click', () => addItemToCart(productId));

            const removeButton = document.createElement('button');
            removeButton.innerText = '-';
            removeButton.addEventListener('click', () => removeItemFromCart(productId));

            productElement.appendChild(addButton);
            productElement.appendChild(removeButton);

            cartContent.appendChild(productElement);
        }
    }
    const buyButton = document.createElement('button');
    buyButton.innerText = 'Złóż zamówienie';
    buyButton.className = 'buy-products';
    buyButton.addEventListener('click', () => buyProductsInCart());

    const totalElement = document.createElement('p');
    totalElement.innerText = `Całość zamówienia: ${totalPrice.toFixed(2)} zł`;
    cartContent.appendChild(totalElement);
    cartContent.appendChild(buyButton);

    newCartWindow.appendChild(cartContent);
    document.body.appendChild(newCartWindow);
}

function closeCart() {
    const cartWindow = document.getElementById('cart-window');
    document.body.removeChild(cartWindow);

    const modalBackground = document.querySelector('.modal-background');
    document.body.removeChild(modalBackground);
}

async function updateCartData() {
    const response = await fetch('/checkLogin');
    const isLoggedIn = await response.json();

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
                    if (quantity === 0) {
                        product.remove();
                        delete cart[productId];
                    }
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

function removeFromCart(productId) {
    let cart = {};
    const cartData = sessionStorage.getItem('cart');
    if (cartData) {
        cart = JSON.parse(cartData);
    }
    if (cart[productId]) {
        if (cart[productId] > 1) {
            cart[productId]--;
        } else {
            delete cart[productId];
        }
    }
    sessionStorage.setItem('cart', JSON.stringify(cart));
    updateCartData();
}

async function removeItemFromCart(productId) {
    const response = await fetch('/checkLogin');
    const isLoggedIn = await response.json();

    if (isLoggedIn) {
        fetch(`/cart/removeItem`, {
            method: 'POST',
            body: JSON.stringify({ productId }),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (response.ok) {
                console.log('Product removed from cart!');
                if (!document.getElementById('cart-window').hidden){
                    updateCartData();
                }
            } else {
                console.log('Error removing product from cart');
            }
        })
            .catch(error => console.error(error));
    } else {
        const cartData = sessionStorage.getItem('cart');
        let cart = {};
        if (cartData) {
            cart = JSON.parse(cartData);
        }
        if (cart[productId]) {
            cart[productId] -= 1;
            if (cart[productId] <= 0) {
                delete cart[productId];
            }
        }
        sessionStorage.setItem('cart', JSON.stringify(cart));
        console.log('Product removed from cart (session storage)!');
        if (!document.getElementById('cart-window').hidden){
            updateCartData();
        }
    }
}

async function addItemToCart(productId) {
    fetch('/cart/addItem', {
        method: 'POST',
        body: JSON.stringify({ productId }),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
            if (response.ok) {
                const cartData = sessionStorage.getItem('cart');
                let cart = {};
                if (cartData) {
                    cart = JSON.parse(cartData);
                }
                if (!cart[productId]) {
                    cart[productId] = 0;
                }
                cart[productId] += 1;
                sessionStorage.setItem('cart', JSON.stringify(cart));
                console.log('Product added to cart!');
                if (!document.getElementById('cart-window').hidden){
                    updateCartData();
                }
            } else {
                console.log('Error adding product to cart');
            }
        }).catch(error => console.error(error));
}