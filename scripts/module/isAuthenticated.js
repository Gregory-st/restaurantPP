document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('#account-link')
    .forEach(a => {
        a.setAttribute('href', './account.html')
    });

    if(window.localStorage.getItem('jwt') === null) return;

    document.querySelectorAll('#account-link')
    .forEach(a => {
        a.setAttribute('href', './setting.html')
    });
});