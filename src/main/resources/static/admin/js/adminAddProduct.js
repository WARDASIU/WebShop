const form = document.getElementById('add-product-form');

window.onload = function () {
    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const formData = new FormData(form);

        fetch('/admin/product/add', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    form.reset();
                }
            })
            .catch(error => console.error(error))

    });
}