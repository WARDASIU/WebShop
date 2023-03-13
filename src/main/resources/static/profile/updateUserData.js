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
    console.log(updatedData);

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
    const data = await response.json();
    if (data.success) {
        // Reset all form fields to their original values
        const formFields = document.querySelectorAll('#profile-form input, #profile-form select');
        formFields.forEach(field => {
            field.value = field.dataset.oldValue;
        });
        // Clear the updatedData object
        Object.keys(updatedData).forEach(key => delete updatedData[key]);
    } else {
        alert(data.message);
    }
}
