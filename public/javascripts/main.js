$(document).ready(function(){
    var parts = document.location.pathname.split("/");
    $("#" + parts[1]).addClass("active");
});