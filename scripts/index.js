import { getById } from './module/function.js';

document.addEventListener('DOMContentLoaded', () => {
    let links = [
        getById('account-link-1'),
        getById('account-link-2')
    ];

    links.forEach(i => i.setAttribute('href', './account.html'));

    if(window.localStorage.getItem('host') === null) 
        window.localStorage.setItem('host', 'http://localhost:8080/restaurant');

    if(window.localStorage.getItem('examples') === null)
        window.localStorage.setItem('examples', './examples');

    if(window.localStorage.getItem('jwt') === null) return;


    links.forEach(i => i.setAttribute('href', './setting.html'));
});