import { getById, setDataChilderByElement } from '../module/function.js';

const button = getById('search-button');
const line = getById('search-line');
const container = getById('product-container');
let carts = [];

button.addEventListener('click', (event) => {
    event.preventDefault();

    if(carts.length === 0){
        container.querySelectorAll('.cart').forEach(i => 
            carts.push(i.cloneNode(true))
        );
    }

    container.innerHTML = '';

    if(line.value.length === 0){
        carts.forEach(cart => {
            container.appendChild(cart);
        });
        return;
    }

    carts.forEach(cart => {
        let title = cart.querySelector('.title-cart').textContent;
        title = title.toLowerCase();

        if(title.includes(line.value.toLowerCase())){
            container.appendChild(cart);
        }
    });
});