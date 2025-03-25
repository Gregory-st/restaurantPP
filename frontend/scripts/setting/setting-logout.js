import { getById } from "../module/function.js";

const buttonLogout = getById('logout-button');

buttonLogout.addEventListener('click', (event) => {
    event.preventDefault();
    window.localStorage.removeItem('jwt');
    window.location.href = './index.html';
});