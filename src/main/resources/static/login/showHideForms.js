const loginForm = document.getElementById('login-form');
const registerForm = document.getElementById('register-form');
const loginLink = document.getElementById('login-link');
const registerLink = document.getElementById('register-link');

loginLink.addEventListener('click', () => {
    loginForm.style.display = 'block';
    registerForm.style.display = 'none';
    loginLink.style.display = 'none';
    registerLink.style.display = 'block';
});

registerLink.addEventListener('click', () => {
    loginForm.style.display = 'none';
    registerForm.style.display = 'block';
    loginLink.style.display = 'block';
    registerLink.style.display = 'none';
});

registerForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(registerForm);
    fetch('/register', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            if (data.errors) {
                const emailTakenError = registerForm.querySelector('.email-taken-error');
                const loginTakenError = registerForm.querySelector('.login-taken-error');
                const emailTaken = data.errors.emailTaken;
                const loginTaken = data.errors.loginTaken;
                if (emailTaken) {
                    emailTakenError.style.display = 'block';
                } else {
                    emailTakenError.style.display = 'none';
                }
                if (loginTaken) {
                    loginTakenError.style.display = 'block';
                } else {
                    loginTakenError.style.display = 'none';
                }
            } else {
                registerForm.style.display = 'none';
                loginForm.style.display = 'block';
                loginLink.style.display = 'none';
                registerLink.style.display = 'block';
            }
        });
});
const usernameInput = document.getElementById('username');
const forgotPasswordForm = document.getElementById('forgot-password-form');
const forgotPasswordLink = document.getElementById('forgot-password-link');
const backToLoginLink = document.getElementById('back-to-login-link');
const emailInput = document.getElementById('emailToRemind');

forgotPasswordLink.addEventListener('click', (event) => {
    event.preventDefault();
    loginForm.style.display = 'none';
    forgotPasswordForm.style.display = 'block';
    emailInput.focus();
});

backToLoginLink.addEventListener('click', (event) => {
    event.preventDefault();
    forgotPasswordForm.style.display = 'none';
    loginForm.style.display = 'block';
    usernameInput.focus();
});

forgotPasswordForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const email = emailInput.value;
    fetch('/resetPassword', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({email})
    })
        .then(response => response.json())
        .then(data => {
            if (data.exists) {
                forgotPasswordForm.querySelector('.email-send').style.display = 'block';
            } else {
                forgotPasswordForm.querySelector('.email-noexists').style.display = 'block';
            }
        }).catch(error => {
        alert('Wystąpił błąd podczas przetwarzania żądania.');
    });
});
