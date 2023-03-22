async function checkCart() {
    let cartData;
    const loggedIn = await isLoggedIn();
    if (loggedIn === true) {
        cartData = await fetchUserCart();
    } else {
        cartData = getSessionCart();
    }
    let cart = {};
    if (cartData) {
        cart = JSON.parse(cartData);
    }

    const cartBody = document.getElementById('cart-items-body');
    const totalPrice = document.getElementById('total-price');

    let cartTotalPrice = 0;

    for (const productId in cart) {
        const quantity = cart[productId];

        fetch(`/api/products/${productId}`)
            .then(response => response.json())
            .then(product => {
                const row = document.createElement('tr');

                let idProduct = product.idProducts.toString();
                let imagesHTML = "/product/" + idProduct + "/images/1";

                const imageCell = document.createElement('td');
                const image = document.createElement('img');
                image.src = imagesHTML;
                image.alt = product.name;
                image.width = 200;
                image.height = 200;
                imageCell.appendChild(image);
                row.appendChild(imageCell);

                const nameCell = document.createElement('td');
                nameCell.innerText = product.name;
                row.appendChild(nameCell);

                const quantityCell = document.createElement('td');
                quantityCell.innerText = quantity;
                row.appendChild(quantityCell);

                const priceCell = document.createElement('td');
                const productTotal = quantity * product.price;
                priceCell.innerText = `${productTotal.toFixed(2)} zł`;
                row.appendChild(priceCell);

                cartBody.appendChild(row);

                cartTotalPrice += productTotal;
                totalPrice.innerText = `${cartTotalPrice.toFixed(2)} zł`;
            })
            .catch(error => console.error(`Failed to load product ${productId}: ${error}`));
    }
}

checkCart();





// let cartData;

// async function checkCart() {
//     const loggedIn = await isLoggedIn();
//     if (loggedIn === true) {
//         cartData = await fetchUserCart();
//     } else {
//         cartData = getSessionCart();
//     }
// }

// let bool = isLoggedIn();
// if (bool === true) {
//     cartData = fetchUserCart();
// } else {
//     cartData = getSessionCart();
// }
//
// let cart = {};
// if (cartData) {
//     cart = JSON.parse(cartData);
// }
//
// const cartBody = document.getElementById('cart-items-body');
// const totalPrice = document.getElementById('total-price');
//
// let cartTotalPrice = 0;
//
// for (const productId in cart) {
//     const quantity = cart[productId];
//
//     fetch(`/api/products/${productId}`)
//         .then(response => response.json())
//         .then(product => {
//             const row = document.createElement('tr');
//
//             let idProduct = product.idProducts.toString();
//             let imagesHTML = "/product/" + idProduct + "/images/1";
//
//             const imageCell = document.createElement('td');
//             const image = document.createElement('img');
//             image.src = imagesHTML;
//             image.alt = product.name;
//             image.width = 200;
//             image.height = 200;
//             imageCell.appendChild(image);
//             row.appendChild(imageCell);
//
//             const nameCell = document.createElement('td');
//             nameCell.innerText = product.name;
//             row.appendChild(nameCell);
//
//             const quantityCell = document.createElement('td');
//             quantityCell.innerText = quantity;
//             row.appendChild(quantityCell);
//
//             const priceCell = document.createElement('td');
//             const productTotal = quantity * product.price;
//             priceCell.innerText = `${productTotal.toFixed(2)} zł`;
//             row.appendChild(priceCell);
//
//             cartBody.appendChild(row);
//
//             cartTotalPrice += productTotal;
//             totalPrice.innerText = `${cartTotalPrice.toFixed(2)} zł`;
//         })
//         .catch(error => console.error(`Failed to load product ${productId}: ${error}`));
// }

function getSessionCart() {
    return cartData = sessionStorage.getItem('cart');
}

async function fetchUserCart() {
    return await fetch('/cart/getCart')
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to fetch user cart (${response.status} ${response.statusText})`);
            }
            return response.json();
        }).catch(error => {
            console.error(`Failed to fetch user cart: ${error}`);
            throw error;
        });
}

async function isLoggedIn() {
    return await fetch('/checkLogin')
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to check login`);
            }
            return response.json();
        })
        .then(isLoggedIn => {
            console.log(isLoggedIn);
            return isLoggedIn;
        });
}