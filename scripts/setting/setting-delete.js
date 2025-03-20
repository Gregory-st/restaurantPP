import { getById } from "../module/function.js";

const buttonUser = getById('delete-user');
const modalConfirm = getById('confirm-modal');
const modalCacel = getById('cancel-modal');
const modal = getById('modal');

buttonUser.addEventListener('click', (event) => {
    event.preventDefault();

    modal.style.setProperty('display', 'block');
    getById('setting').classList.add('blur');
});

modalCacel.addEventListener('click', (event) => {
    event.preventDefault();

    modal.style.setProperty('display', 'none');
    getById('setting').classList.remove('blur');
});

modalConfirm.addEventListener('click', (event) => {
    event.preventDefault();

    let host = window.localStorage.getItem('host');
    host += '/account/setting';
    const jwt = window.localStorage.getItem('jwt');
    fetch(host, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwt}`
        }
    })
    .then(response => {
        if(response.ok){ 
            window.localStorage.removeItem('jwt');
            window.location.href = './index.html'
            return response.json();
        }
    })
    .catch(error => console.log(error))
});