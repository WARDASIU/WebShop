window.onload = function () {
    const form = document.getElementById('service-form');
    const alert = document.querySelector('.alert');
    const spinner = document.querySelector('.spinner-border');

    form.addEventListener('submit', (event) => {
        event.preventDefault();
        spinner.classList.remove('d-none');

        const formData = new FormData();
        const selectFields = document.querySelectorAll('select');
        const inputFields = document.querySelectorAll('input');

        selectFields.forEach(field => {
            if(field.value !== "") {
                formData.append(field.name, field.value);
            }
        });

        inputFields.forEach(field => {
            if(field.value !== "") {
                formData.append(field.name, field.value);
            }
        });

        fetch('/serviceOrder', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if(response.ok) {
                    alert.classList.remove('d-none');
                    alert.querySelector('strong').textContent = "Zapytanie o usługę zostało wysłane!";
                    form.reset();
                } else {
                    alert.classList.remove('d-none');
                    alert.querySelector('strong').textContent = 'An error occurred while sending the email';
                }
            })
            .catch(error => {
                console.error('Error sending data:', error);
            }).finally(() => {
            spinner.classList.add('d-none');
            showAdditionalFields();
        });
    });
}