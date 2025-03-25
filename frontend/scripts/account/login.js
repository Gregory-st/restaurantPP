import { getParent, getById } from "../module/function.js";
const loginButton = document.getElementById('loginButton');

loginButton.addEventListener('click', () => {
    const login = getById('loginText');
    const password = getById('passwordText');
    const parentLogin = getParent(login, 2);
    const parentPassword = getParent(password, 2);
    const errorLine = getById('error');

    let message = '';

    if(login.value.length === 0) {
        parentLogin.classList.add('error');
        message = 'Не введён логин';
    }

    if(password.value.length === 0){
        parentPassword.classList.add('error');
        if(message.length > 0) message += ' и пароль';
        else { 
            message = 'Не введён пароль'; 
            parentLogin.classList.remove('error');
        }
        errorLine.textContent = message;
        return;
    }

    parentLogin.classList.remove('error');
    parentPassword.classList.remove('error');
    errorLine.textContent = ' ';

    let host = window.localStorage.getItem('host');
    host += '/auth';

    fetch(host, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            "login":login.value, 
            "password":password.value
        })
    })
    .then(response => {
        if(response.ok) return response.json();

        parentLogin.classList.add('error');
        parentPassword.classList.add('error');
        throw new Error('Не верный логин или пароль');
    })
    .then(data => {
        localStorage.setItem('jwt', data.token);
        window.location.href='../index.html';
    })
    .catch(error => errorLine.textContent = error.message)
});