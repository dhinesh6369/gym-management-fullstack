if(sessionStorage.getItem("isLoggedIn") !== 'true' ){
    window.location.href="../login.html"
}
function setItem(key, value) {
    localStorage.setItem(key, value);
}
function getItem(key) {
    return localStorage.getItem(key);
}
function clearItem(key) {
    localStorage.removeItem(key);
}