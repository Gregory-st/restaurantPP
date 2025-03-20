import { getById, cloneTemplate } from "../module/function.js";

const template = getById('product-template');
const container = getById('product-container');

function renderProduct(temp, product, container){

    const clone = cloneTemplate(temp);

    clone.id = product.id;
    clone.querySelector('.image-cart').src = product.image;
    clone.querySelector('.title-cart').textContent = product.title;
    clone.querySelector('.description-cart').textContent = product.description;
    clone.querySelector('.price-cart').textContent = product.price + ' р.';

    container.appendChild(clone);
}

document.addEventListener('DOMContentLoaded', () =>{


});