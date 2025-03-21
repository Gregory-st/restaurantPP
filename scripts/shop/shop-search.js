import { getById, setDataChilderByElement } from '../module/function.js';

const button = getById('search-button');
const line = getById('search-line');
const container = getById('product-container');
let carts = document.querySelectorAll('.cart');

button.addEventListener('click', (event) => {
    event.preventDefault();

    console.log(carts);

    if(line.value.length === 0) return;

    container.innerHTML = '';

    carts.forEach(cart => {
        let title = cart.querySelector('.title-cart').textContent;

        if(title.includes(line.value)){
            container.appendChild(cart);
        }

        console.log(cart);
    });
});