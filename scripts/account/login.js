const loginButton = document.getElementById('loginButton');

loginButton.addEventListener('click', () => {
    const login = document.getElementById('loginText');
    const password = document.getElementById('passwordText');

    fetch("http://localhost:8080/restaurant/auth", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            "login":login.value, 
            "password":password.value
        })
    })
    .then(response => {
        if(response.ok) return response.json();
        alert(response.body);
    })
    .then(data => {
        localStorage.setItem('jwt', data.token);
    })
});