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