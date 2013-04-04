$(document).ready(function(){
    var active = document.location.pathname.replace("/","");
    $("#" + active).addClass("active");
});