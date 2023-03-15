// Extract the productId from the URL
const productId = window.location.pathname.split('/').pop();
console.log(productId)
// Fetch the product data using the productId
fetchDataFromEndpoint(productId);

function fetchDataFromEndpoint(productId) {
    console.log(productId)
    fetch('/api/products/' + productId)
        .then(response => response.json())
        .then(product => {
            const container = document.createElement('div');
            let idProduct = product.idProducts.toString();
            let imagesHTML = [];
            for (let i = 0; i < 4; i++) {
                imagesHTML[i] = "/product/" + idProduct + "/images/" + (i+1);
            }

            container.innerHTML = `
            <div class="wrapper">
            <div class="section">
        <div class="section-content">
            <div class="gallery">     
            ${generateGalleryImages(imagesHTML)}         
            ${generateGalleryImages(imagesHTML)}         
            </div> 
          <div class="store">
            <div class="product-info">
              <div class="product-title">${product.name}</div>
              <div class="product-description">
               ${product.detailedDescription}
              </div>
            </div>
            <div class="cart-info">
              <div class="sizes">
                <div>Wybierz rozmiar:</div>
                <div>
                  ${generateSizes(product.sizes)}
                </div>
              </div>
              <div class="product-price">
                <span>Cena:</span><span>${product.price} ZŁ</span>
              </div>
              <div class="cart-controls">
                <div class="add-to-cart-btn" onclick='addItemToCart(${product.idProducts})' style="margin-bottom: 25px">Dodaj do koszyka</div>
                <div class="add-to-cart-btn">Kup teraz!</div>
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
          <img src="https://via.placeholder.com/1000x500" />
          <img src="https://via.placeholder.com/1000x1000" />
          <img src="https://via.placeholder.com/1000x500" />
          <img src="https://via.placeholder.com/1000x1000" />
          <img src="https://via.placeholder.com/2000x500" />
          <img src="https://via.placeholder.com/1000x1000" />
          <img src="https://via.placeholder.com/1000x500" />
          <img src="https://via.placeholder.com/1000x500" />
        </div>
      </div>
</div>
<div class="modal" id="modal">
      <div class="close-modal" id="close">X</div>
      <img src="" alt="" id="bigImage" />
    </div>
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

function generateGalleryImages(imagesHTML) {
    const gallery = document.createElement("div");
    gallery.className = "gallery"
    for (let i = 0; i < imagesHTML.length; i++) {
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


window.onload = function (){

}