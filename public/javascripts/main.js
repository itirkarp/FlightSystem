$(document).ready(function(){
    var parts = document.location.pathname.split("/");
    $("#" + parts[1]).addClass("active");
    
    $(".delete-link").click(function(e){
        e.preventDefault();
        $("#delete").attr("href", $(this).attr("path"));
    });
    
    bindShowSegment();
    
    $("#add-segment").click(function() {
        var n = $('table tr').length - 1;
        $('table > tbody:last').append('<tr><td><input type="text" class="input-small" name="segments[' + n + '].arr_time" id="segments[' + n + '].arr_time"></td><td><input type="text" class="input-small" name="segments[' + n + '].dep_time" id="segments[' + n + '].dep_time"></td><td><input type="text" class="input-small" name="segments[' + n + '].airpt_id_from" id="segments[' + n + '].airpt_id_from"></td><td><input type="text" class="input-small" name="segments[' + n + '].airpt_id_to" id="segments[' + n + '].airpt_id_to"></td><td><a class="btn" id="delete-segment"><i class="icon-remove"></i> Delete</a></td></tr>');
    });

});

function bindShowSegment() {
    $('a[id$="show-segment"]').click(function(){
        var element = "." + $(this).attr("id").split("-")[0]+"-segment";
        if ($(element).hasClass("hide")) {
            $(element).removeClass("hide");
            $(this).html("<i class=\"icon-arrow-up\"></i>Hide");
        } else {
            $(element).addClass("hide");
            $(this).html("<i class=\"icon-arrow-down\"></i>Segments");
        }
    });
}
