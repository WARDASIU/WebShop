window.onload = function () {
    fetch('http://localhost:8080/api/products')
        .then(response => response.json())
        .then(products => {
            const container = document.getElementById('products');
            products.forEach(item => {
                const element = document.createElement('div');
                element.classList.add('product-tile', "col-lg-3", "product-tile-addition", "mx-2");
                let idProduct = item.idProducts.toString();
                let imagesHTML = [];
                for (let i = 0; i < 4; i++) {
                    imagesHTML[i] = "/product/" + idProduct + "/images/" + (i+1);
                }

                element.innerHTML = `
                                  <div class="main-image" onclick="window.location.href='/products/${item.idProducts}'">
                                    <img src="${imagesHTML[0]}" alt="" id='produkt_${idProduct}'>
                                  </div>
                                  <div class="col-text" style="padding: 20px">
                                    <div onclick="window.location.href='/products/${item.idProducts}'">                             
                                      <h4>${item.name}</h4>
                                      <h6>${item.description}</h6>
                                      <h6>${item.price}</h6>                                 
                                    </div>         
                                    <div><button onclick="addToCart(${item.idProducts})" class="add-to-cart">Add to cart</button></div>                          
                                  </div>                               
                                  <div class="nav-arrows" style="position: absolute; top: 45%; width: 100%; display: none;">
                                    <div style="float: left; margin-left: 20px;">
                                      <i class="fa fa-arrow-left" aria-hidden="true"></i>
                                    </div>
                                    <div style="float: right; margin-right: 20px;">
                                      <i class="fa fa-arrow-right" aria-hidden="true"></i>
                                    </div>
                                  </div>`;

                container.appendChild(element);

                let tileCarouselElement = document.getElementById("produkt_" + idProduct);

                element.addEventListener('mouseenter', function () {
                    this.querySelector('.nav-arrows').style.display = "block";
                });

                element.addEventListener('mouseleave', function () {
                    this.querySelector('.nav-arrows').style.display = "none";
                });

                element.querySelector('.fa-arrow-left').addEventListener('click', function () {
                    for (let i = 0; i < imagesHTML.length; i++) {
                        if (imagesHTML[i] === tileCarouselElement.getAttribute("src")){
                            if (i === 0){
                                tileCarouselElement.setAttribute("src", imagesHTML[imagesHTML.length-1]);
                                break;
                            } else{
                                tileCarouselElement.setAttribute("src", imagesHTML[i - 1]);
                                break;
                            }
                        }
                    }
                });

                element.querySelector('.fa-arrow-right').addEventListener('click', function () {
                    for (let i = 0; i < imagesHTML.length; i++) {
                        if (imagesHTML[i] === tileCarouselElement.getAttribute("src")){
                            if (i === imagesHTML.length - 1){
                                tileCarouselElement.setAttribute("src", imagesHTML[0]);
                                break;
                            } else{
                                tileCarouselElement.setAttribute("src", imagesHTML[i + 1]);
                                break;
                            }
                        }
                    }
                });
            })
        });
}

function addToCart(productId) {
    console.log("Produkt: " + productId)
    fetch('/add', {
        method: 'POST',
        body: JSON.stringify({ productId }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                const badge = document.querySelector('.cart-icon .badge');
                const currentCount = parseInt(badge.innerText);
                badge.innerText = currentCount + 1;
                console.log('Product added to cart!');
            } else {
                console.log('Error adding product to cart');
            }
        })
        .catch(error => console.error(error));
}
