const productId = window.location.pathname.split('/').pop();
fetchDataFromEndpoint(productId);

function fetchDataFromEndpoint(productId) {
    console.log(productId)
    fetch('/api/products/' + productId)
        .then(response => response.json())
        .then(product => {
            const container = document.getElementById('view');
            let idProduct = product.idProducts.toString();
            let imagesHTML = [];
            for (let i = 0; i < 15; i++) {
                imagesHTML[i] = "/product/" + idProduct + "/images/" + (i + 1);
            }

            container.innerHTML = `
            <div class="wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="section">
                    <div class="section-content">
                        <div class="col-lg-6 col-md-12">
                            <div class="gallery">
                                ${generateGalleryImages(imagesHTML, 0, 4)}
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-12">
                            <div class="store">
                                <div class="product-info">
                                    <div class="product-title">${product.name}</div>
                                    <div class="product-description">
                                        ${product.detailedDescription}
                                    </div>
                                </div>
                                <div class="cart-info">
                                    <div class="sizes">
                                        <div>Rozmiar:</div>
                                        <div>
                                            ${generateSizes(product.sizes)}
                                        </div>
                                    </div>
                                    <div class="product-price">
                                        <span>Cena:</span><span>${product.price} ZŁ</span>
                                    </div>
                                    <div class="cart-controls">
                                        <div class="add-to-cart-btn" onclick='addItemToCart(${product.idProducts})'
                                             style="margin-bottom: 25px">Dodaj do koszyka
                                        </div>
                                        <div class="add-to-cart-btn" onclick="goToOrder(${product.idProducts})">Kup teraz!</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="section">
        <div class="section-content">
            <div class="product-title">Product title</div>
            <p>
                Lorem ipsum dolor, sit amet consectetur adipisicing elit. Quam
                deserunt ullam aperiam placeat quibusdam accusamus aliquam quo
                soluta rem qui
            </p>
            <p>
                Pariatur adipisci asperiores repellendus veniam aspernatur corrupti
                voluptatem perspiciatis impedit assumenda consectetur. Nihil, atque
                quod?
            </p>
            <p>
                Praesentium doloremque assumenda reprehenderit saepe ea accusantium
                doloribus ducimus dolores quo, tempore excepturi, aliquid
            </p>
        </div>
    </div>
    <div class="section">
        <div class="section-content">
        ${generateGalleryImages(imagesHTML, 5,8)}           
        </div>
    </div>
</div>
<div class="modal" id="modal">
    <div class="close-modal" id="close">X</div>
    <img src="" alt="" id="bigImage"/>
</div>        
<footer class="bg-dark py-4 mt-4 text-white text-center">
    <div class="container">
        <div class="row">
            <div class="col-12 col-md-4">
                <h3>Skontaktuj się z nami</h3>
                <p>
                    Zawoja 2044<br>
                    Malopolska, Polska 12345<br>
                    (+48) 456-7890-12
                </p>
            </div>
            <div class="col-12 col-md-4">
                <h3>Obserwuj nas!</h3>
                <ul class="list-unstyled d-flex justify-content-between">
                    <li><a href="#"><i class="fab fa-facebook-square fa-2x"></i></a></li>
                    <li><a href="#"><i class="fab fa-twitter-square fa-2x"></i></a></li>
                    <li><a href="#"><i class="fab fa-instagram-square fa-2x"></i></a></li>
                </ul>
            </div>
            <div class="col-12 col-md-4">
                <h3>Dane</h3>
                <ul class="list-unstyled">
                    <li><a href="#" class="text-white">Polityka prywatnosci</a></li>
                    <li><a href="#" class="text-white">Regulamin uslug</a></li>
                </ul>
            </div>
        </div>
    </div>
</footer>     
            `
            document.body.appendChild(container);

            const images = document.getElementsByTagName("img");
            for (let index = 0; index < images.length; index++) {
                const element = images[index];
                element.addEventListener("click", (e) => {
                    showImage(e);
                });
            }

            const modal = document.getElementById("modal");
            const closeModal = document.getElementById("close");
            const modalImage = document.getElementById("bigImage");

            closeModal.addEventListener("click", () => {
                modal.style.visibility = "hidden";
            });

            const showImage = function (event) {
                modalImage.src = event.target.src;
                modal.style.visibility = "visible";
            };

        });
}

function generateGalleryImages(imagesHTML, startingPhoto, howManyPhotos) {
    const gallery = document.createElement("div");
    gallery.className = "gallery"
    for (let i = startingPhoto; i < howManyPhotos+startingPhoto; i++) {
        const image = document.createElement("img");
        image.src = imagesHTML[i];
        gallery.appendChild(image);
    }
    return gallery.innerHTML;
}

function generateSizes(sizes) {
    const div = document.createElement("div");
    const allSizes = sizes.split(",");
    for (let i = 0; i < allSizes.length; i++) {
        const div2 = document.createElement("div");
        div2.innerText = allSizes[i];
        div.appendChild(div2);
    }
    return div.innerHTML;
}

window.onload = function () {

}