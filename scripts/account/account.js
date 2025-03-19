import { getById, setDataChilderByElement } from "../module/function.js";

const loginTab = document.getElementById('loginTab');
        const registerTab = getById('registerTab');
        const loginForm = getById('loginForm');
        const registerForm = getById('registerForm');
        const errorLine = getById('error');

        // Switch login form
        loginTab.addEventListener('click', () => {
            loginTab.classList.add('active');
            registerTab.classList.remove('active');
            loginForm.classList.add('active');
            registerForm.classList.remove('active');

            let controlls= [
                getById('nameUser'),
                getById('emailUser'),
                getById('loginUser'),
                getById('passwordUser'),
                getById('repeatPassword')
            ];

            setDataChilderByElement(registerForm, element => {
                element.classList.remove('error');
            });
            errorLine.textContent = ' ';

            controlls.forEach(i => i.value = '');
        });

        //Switch register form
        registerTab.addEventListener('click', () => {
            registerTab.classList.add('active');
            loginTab.classList.remove('active');
            registerForm.classList.add('active');
            loginForm.classList.remove('active');

            let controlls = [
                getById('loginText'),
                getById('passwordText'),
            ];

            setDataChilderByElement(loginForm, element => {
                element.classList.remove('error');
            });
            errorLine.textContent = ' ';

            controlls.forEach(i => i.value = '');
        });