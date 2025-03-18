function getParent(element, deep){
    if(deep === 0)
        return element;
    
    return getParent(element.parentElement, deep - 1);
}