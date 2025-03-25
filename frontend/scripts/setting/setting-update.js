import { getById, getParent } from "../module/function.js";

const buttonUser = getById('update-user');
const buttonUserCancel = getById('cancel-update');

const email = getById('email-user');
const name = getById('name-user');
const login = getById('login-user');

buttonUserCancel.addEventListener('click', (event) => {
    event.preventDefault();

    location.reload();
});

buttonUser.addEventListener('click', (event) => {
    event.preventDefault();

    const errorLine = getById('error-info');

    let isValid = true;

    [email, name, login].forEach(i => {
        let parent = getParent(i, 1);
        if(i.value.length === 0){
            if(isValid) isValid = false;
            parent.classList.add('error');
        }
        else{
            parent.classList.remove('error');
        }
    });

    if(!isValid){
        errorLine.textContent = 'Поля не могу быть пустыми';
        return;
    }

    errorLine.textContent = '';

    let host = window.localStorage.getItem('host');
    host += '/account/setting/info';
    const jwt = window.localStorage.getItem('jwt');
    
    fetch(host, {
        method: 'PUT',
        headers:{
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwt}`
        },
        body: JSON.stringify(
        {
            "email" : email.value,
            "name" : name.value,
            "login" : login.value
        })
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        if(!data.success) throw new Error(data.message);

        window.localStorage.setItem('jwt', data.token);
    })
    .catch(error => console.log(error.message))
});