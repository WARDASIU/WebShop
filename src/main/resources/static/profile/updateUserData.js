const profileForm = document.getElementById('profile-form');
let updatedData = {};

const inputFields = profileForm.querySelectorAll('input');
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

profileForm.addEventListener('submit', (event) => {
    event.preventDefault();
    updateUserProfile(updatedData);
});

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



$(function() {
    $('#change-password-form').submit(function(event) {
        var newPassword = $('#new-password').val();
        var confirmNewPassword = $('#confirm-new-password').val();
        if (newPassword !== confirmNewPassword) {
            const messageBox = document.createElement('div');
            messageBox.classList.add('message-box');
            messageBox.textContent = 'Hasła muszą sie zgadzac!';

            document.body.appendChild(messageBox);
            setTimeout(() => {
                messageBox.remove();
            }, 3000);
            event.preventDefault();
        }
    });
});


const changePassForm = document.getElementById('change-password-form');
changePassForm.addEventListener('submit',  (event) => {
    event.preventDefault();
    const formData = new FormData(changePassForm);
    const newPassword = formData.get("newPassword")
    const confirmNewPassword = formData.get("confirmNewPassword")

    changePassword(confirmNewPassword);
});

async function changePassword(confirmNewPassword){
    const response = await fetch('/change-password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(confirmNewPassword)
    });

    if (!response.ok) {
        throw new Error('Failed to change the user password!');
    }else{
        const messageBox = document.createElement('div');
        messageBox.classList.add('message-box');
        messageBox.textContent = 'Hasło zmienione!';

        document.body.appendChild(messageBox);
        setTimeout(() => {
            messageBox.remove();
        }, 3000);
    }
}