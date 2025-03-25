const button = document.getElementById('submit');

button.addEventListener('click', (event) => {
    event.preventDefault();

    let inputs = document.querySelectorAll('input');
    let json = {};
    inputs.forEach(i => {
        if(i.name){
            json[i.name] = i.value;
        }
    });

    let host = window.localStorage.getItem('host');
    host += '/shop/add-eat';

    fetch(host, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(json)
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
    })
    .catch(error => console.log(error))
});