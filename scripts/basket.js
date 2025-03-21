import { getById, cloneTemplate, getParent } from "./module/function.js";

const template = getById('template-cart');
const container = getById('product-container');
let examples = window.localStorage.getItem('examples');
examples += '/';

function renderProduct(temp, product, container){

    const clone = cloneTemplate(temp);

    let count = product.count;
    let price = product.price;
    price = price * count;

    clone.querySelector('.cart').dataset.productId = product.id;
    clone.querySelector('.image-cart').src = examples + product.image;
    clone.querySelector('#title').textContent = product.title;
    clone.querySelector('#description').textContent = product.description;

    clone.querySelector('#B').textContent = `Белки: ${product.proteins}`;
    clone.querySelector('#G').textContent = `Жиры: ${product.fats}`;
    clone.querySelector('#U').textContent = `Углеводы: ${product.carbon}`;

    clone.querySelector('#wigth').textContent = `Вес: ${product.weight} г.`;
    clone.querySelector('#count').textContent = `Количество: ${count}`;
    clone.querySelector('#price').textContent = `Сумма: ${price} р.`;

    container.appendChild(clone);
}

document.addEventListener('DOMContentLoaded', () => {

    let host = window.localStorage.getItem('host');
    const jwt = window.localStorage.getItem('jwt');
    host += '/basket';
    console.log(jwt);

    fetch(host, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwt}`
        }
    })
    .then(response => {
        if(response.ok) return response.json();
        throw new Error("Требуется авторизация");
    })
    .then(data => {
        data.eats.forEach(element => {
            renderProduct(template, element, container);
        });
    })
    .catch(error => {
        alert(error.message);
        window.location.href = './account.html';
    })
});