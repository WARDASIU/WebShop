const form = document.getElementById('profile-form');
const form2 = document.getElementById('order-form');
const alert = document.querySelector('.alert');
const spinner = document.querySelector('.spinner-border');

displayUserData();

async function displayUserData(){
    const response = await fetch('/checkLogin');
    const isLoggedIn = await response.json();

    if (isLoggedIn) {
        let updatedData = {};

        const inputFields = form2.querySelectorAll('input');
        inputFields.forEach(input => {
            input.addEventListener('input', (event) => {
                const fieldName = event.target.name;
                const fieldValue = event.target.value;

                updatedData = {
                    ...updatedData,
                    [fieldName]: fieldValue
                };
            });

            input.addEventListener('blur', (event) => {
                const fieldName = event.target.name;
                const fieldValue = event.target.value;

                updatedData = {
                    ...updatedData,
                    [fieldName]: fieldValue
                };

                updateUserProfile(updatedData);
            });

            input.addEventListener('keydown', (event) => {
                const fieldName = event.target.name;
                const fieldValue = event.target.value;

                if (event.key === 'Escape') {
                    event.target.value = event.target.dataset.oldValue;
                    event.target.blur();
                } else if (event.key === 'Enter') {
                    updatedData = {
                        ...updatedData,
                        [fieldName]: fieldValue
                    };

                    updateUserProfile(updatedData);
                    event.preventDefault();
                }
            });
        });
    }
}

async function updateUserProfile(updatedData) {
    const response = await fetch('/update-profile', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedData)
    });

    if (!response.ok) {
        throw new Error('Failed to update user profile.');
    }

    const data = await response;
    if (response.ok) {
        Object.keys(updatedData).forEach(key => delete updatedData[key]);

        const messageBox = document.createElement('div');
        messageBox.classList.add('message-box');
        messageBox.textContent = 'Dane zmienione!';

        document.body.appendChild(messageBox);
        setTimeout(() => {
            messageBox.remove();
        }, 3000);
    } else {
        alert(data.message);
    }
}

form2.addEventListener('submit', (event) => {
    event.preventDefault();
    spinner.classList.remove('d-none');

    let formData = new FormData;
    const inputFields = document.querySelectorAll('input');
    inputFields.forEach(field => {
        if (field.value !== "") {
            formData.append(field.name, field.value);
        }
    });

    const cartData = JSON.parse(sessionStorage.getItem("cart"));

    formData.append('cartData', JSON.stringify(cartData));

    console.log(formData);

    fetch('/prepareOrder/checkout', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                alert.classList.remove('d-none');
                alert.querySelector('strong').textContent = "Zamówienie zrealizowane! \nSprawdz swojego emaila!";
                form.reset();
                sessionStorage.clear();
            } else {
                alert.classList.remove('d-none');
                alert.querySelector('strong').textContent = 'An error occurred while sending the email';
            }
        }).catch(error => {
            console.error('Error sending data:', error);
        }).finally(() => {
        spinner.classList.add('d-none');
    });
});
