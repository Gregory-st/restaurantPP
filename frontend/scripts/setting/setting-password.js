import { getById, getParent } from "../module/function.js";

const buttonUser = getById('update-password');
const buttonCancel = getById('cancel-password');

const oldPassword = getById('password-user');
const password = getById('repeat-password-user');
const errorLine = getById('error-security');

buttonCancel.addEventListener('click', (event) => {
    event.preventDefault();

    oldPassword.value = '';
    password.value = '';
    errorLine.textContent = '';
});

buttonUser.addEventListener('click', (event) => {
    event.preventDefault();

    let isValid = true;

    [oldPassword, password].forEach(i => {
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
    host += '/account/setting/pass';
    const jwt = window.localStorage.getItem('jwt');

    fetch(host, {
        method: 'PUT',
        headers:{
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwt}`
        },
        body: JSON.stringify(
        {
            "oldPassword" : oldPassword.value,
            "password" : password.value
        })
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        if(!data.success) throw new Error(data.message);

        errorLine.textContent = 'Пароль успешно изменён!';
        errorLine.classList.add('success');
        oldPassword.classList.remove('error');
        password.value = '';
        oldPassword.value = '';
    })
    .catch(error => {
        errorLine.textContent = error.message;
        errorLine.classList.remove('success');
        getParent(oldPassword, 1).classList.add('error');
    })
});