$(document).ready(function(){
    var parts = document.location.pathname.split("/");
    $("#" + parts[1]).addClass("active");
    
    $(".delete-link").click(function(e){
        e.preventDefault();
        $("#delete").attr("href", $(this).attr("path"));
    });
    
    copyRouteForm();
   
    fillRouteSegmentFormOnChange();
   
});

function fillRouteSegmentFormOnChange() {
    $("#airpt_id_from").change(function(){
        $("#segment\\.airpt_id_from").val($(this).val());
    });
    $("#airpt_id_to").change(function(){
        $("#segment\\.airpt_id_to").val($(this).val());
    });
    $("#dep_time").change(function(){
        $("#segment\\.dep_time").val($(this).val());
    });
    $("#arr_time").change(function(){
        $("#segment\\.arr_time").val($(this).val());
    });    
}

function copyRouteForm() {
    $("#segment\\.airpt_id_from").val($("#airpt_id_from").val());
    $("#segment\\.airpt_id_to").val($("#airpt_id_to").val());
    $("#segment\\.dep_time").val($("#dep_time").val());

    $("#segment\\.arr_time").val($("#arr_time").val());
}