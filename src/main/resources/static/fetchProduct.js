window.onload = function () {
    fetch("http://localhost:8080/api/products")
        .then(response => response.json())
        .then(products => {
            const container = document.getElementById('products');
            var i = 0;

            for (let item of products) {
                const element = document.createElement('div');
                element.className = "col-lg-3 col-md-5 col-sm-12 hvr-shrink mx-2";

                element.innerHTML = `
                            <div class="col-img-with-text">
                                <img src="/api/products/images/1.png" alt="">
                            </div>
                            <div class="col-text">
                                <div>                             
                                    <h4>${item.name}</h4>
                                    <h6>${item.price}</h6>
                                </div>
                            </div>`;
                container.appendChild(element);
                i++;
                if (i === 6) break;
            }
        });
}