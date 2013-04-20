$(document).ready(function(){
    var parts = document.location.pathname.split("/");
    $("#" + parts[1]).addClass("active");
    
    $(".delete-link").click(function(e){
        e.preventDefault();
        $("#delete").attr("href", $(this).attr("path"));
    });
    
    bindShowSegment();
    
//    fillRouteSegmentFormOnChange();

});

function bindShowSegment() {
    $('a[id$="show-segment"]').click(function(){
        var element = "#" + $(this).attr("id").split("-")[0]+"-segment";
        if ($(element).hasClass("hide")) {
            $(element).removeClass("hide");
            $(this).html("<i class=\"icon-arrow-up\"></i>Hide");
        } else {
            $(element).addClass("hide");
            $(this).html("<i class=\"icon-arrow-down\"></i>Segments");
        }
    });
}

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