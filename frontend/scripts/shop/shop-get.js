import { getById, cloneTemplate, getParent } from "../module/function.js";

const template = getById('product-template');
const container = getById('product-container');
const mess = getById('banner');
let examples = window.localStorage.getItem('examples');
examples += '/';

function renderProduct(temp, product, container){

    const clone = cloneTemplate(temp);

    clone.querySelector('.cart').dataset.productId = product.id;
    clone.querySelector('.image-cart').src = examples + product.image;
    clone.querySelector('.title-cart').textContent = product.title;
    clone.querySelector('.description-cart').textContent = product.description;
    clone.querySelector('.price-cart').textContent = product.price + ' р.';

    clone.querySelector('#add-basket').addEventListener('click', click_button);

    container.appendChild(clone);
}

function click_button(event) {
    event.preventDefault();
    
    const id = getParent(this, 2).dataset.productId;
    let host = window.localStorage.getItem('host');
    const jwt = window.localStorage.getItem('jwt');

    host += '/shop';

    fetch(host, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwt}`
        },
        body: JSON.stringify({
            'id': id
        })
    })
    .then(response => {
        if(response.status === 401) throw new Error('Требуется авторизация');
        return response.json();
    })
    .then(data => {
        if(!data.success) {
            if(data.code === 0)
                throw new Error(data.message);
            if(data.code === -1){  
                let cart = container
                .querySelector(`[data-product-id="${data.id}"]`);
                
                cart.remove();
                alert('Ошибка!\nНеверный товар');
            }
            return;
        }

        mess.classList.remove('slide-in-reverse');
        mess.classList.add('slide-in-reverse');
    })
    .catch(error => {
        alert(error.message);
        window.location.href = './account.html';
    })
}

document.addEventListener('DOMContentLoaded', () =>{

    let host = window.localStorage.getItem('host');
    host += '/shop';

    fetch(host)
        .then(response => response.json())
        .then(data => {
            data.eats.forEach(eat => {
                renderProduct(template, eat, container);
            });
        })
        .catch(error => console.error(error));
});