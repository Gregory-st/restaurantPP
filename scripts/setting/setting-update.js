import { getById, getParent } from "../module/function.js";

const buttonUser = getById('update-user');

buttonUser.addEventListener('click', (event) => {
    event.preventDefault();

    const email = getById('email-user');
    const name = getById('name-user');
    const login = getById('login-user');
    const errorLine = getById('error');

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

})