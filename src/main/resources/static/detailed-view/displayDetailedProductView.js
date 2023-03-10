function displayProduct(productId) {
    console.log(productId);
    window.location.href = '/product/' + productId;
    displayDetailedProductView(productId);
}