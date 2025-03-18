import func from "../module/function.js";
const registerButton = document.getElementById('registerButton');
function getParent(element, deep){
    if(deep === 0)
        return element;
    
    return getParent(element.parentElement, deep - 1);
}


registerButton.addEventListener('click', (event) => {
    event.preventDefault();

    const name = document.getElementById('nameUser');
    const email = document.getElementById('emailUser');
    const login = document.getElementById('loginUser');
    const password = document.getElementById('passwordUser');
    const repeatPassword = document.getElementById('repeatPassword');
    const errorLine = document.getElementById('error');

    const parentPassword = getParent(password, 2);
    const parentPasswordRepeate = getParent(repeatPassword, 2);
    let controlls = [name, email, login, password];
    let controllsParent = [];
    let isValid = true;
    controlls.forEach(i => {
        let parent = getParent(i, 2);
        if(i.value.length === 0){
            if(isValid) isValid = false;
            parent.classList.add('error');
        }
        else{
            controllsParent.push(parent);
        }
    });

    if(!isValid) {
        errorLine.textContent = "Не все поля были заполнены";
        return
    }

    controllsParent.forEach(i => {
        i.classList.remove('error');
    });

    if(password.value != repeatPassword.value) {
        parentPassword.classList.add('error');
        parentPasswordRepeate.classList.add('error');
        errorLine.textContent = "Пароли не совпадают";
        return;
    }

    parentPassword.classList.remove('error');
    parentPasswordRepeate.classList.remove('error');
    errorLine.textContent = " ";

    fetch("http://localhost:8080/restaurant/auth/reg", {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(
            {
                "name" : name.value,
                "email" : email.value,
                "login" : login.value, 
                "password" : password.value
            })
    })
    .then(response => {
        if(response.ok) return response.json();
        alert(response.body);
    })
    .then(data => {
        localStorage.setItem('jwt', data.token);
    })
    .catch(error => console.log(error))
});