import { getById } from "../module/function.js";

const email = getById('email-user');
const name = getById('name-user');
const login = getById('login-user');

document.addEventListener('DOMContentLoaded', () => {

    let host = window.localStorage.getItem('host');
    host += '/account/setting';
    const jwt = window.localStorage.getItem('jwt');

    fetch(host, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwt}`
        }
    })
    .then(response => {
        if(response.ok) return response.json();
        throw new Error('Требуется авторизация');
    })
    .then(data => {
        email.value = data.email;
        name.value = data.name;
        login.value = data.login;
    })
    .catch(error => {
        alert(error.message);
        window.localStorage.removeItem('jwt');
        window.location.href='./index.html';
    })

});