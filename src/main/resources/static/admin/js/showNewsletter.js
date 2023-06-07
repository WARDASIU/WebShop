window.onload = function () {
    const form = document.querySelector('.newsletter-form');
    const spinner = document.querySelector('.spinner-border');
    const alert = document.querySelector('.alert');
    form.addEventListener('submit', (event) => {
        event.preventDefault();

        spinner.classList.remove('d-none');
        const formData = new FormData(form);

        fetch('/admin/sendMail', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // window.location.href = '/admin/adminNewsletter'; // Redirect to the adminNewsletter page
                    alert.classList.remove('d-none');
                    alert.querySelector('strong').textContent = data.message;
                    form.reset();
                } else {
                    alert.classList.add('d-none');
                    alert.querySelector('strong').textContent = 'An error occurred while sending the email';
                }
            })
            .catch(error => console.error(error))
            .finally(() => {
                spinner.classList.add('d-none');
            });
    });
}