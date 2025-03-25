export function getParent(element, deep){
    if(deep === 0)
        return element;
    
    return getParent(element.parentElement, deep - 1);
}

export function getById(id){
    return document.getElementById(id);
}

export function setDataChilderByElement(parent, predicate){
    parent.childNodes.forEach(element => {
        if(element.nodeName != '#text')
            predicate(element);
    });
}

export function cloneTemplate(template){
    return template.content.cloneNode(true);
}