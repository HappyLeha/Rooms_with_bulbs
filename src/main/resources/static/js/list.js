var href=$(location).attr('href');
var array=href.toString().split("/");
var path="http:";
for (var i=1;i<array.length-1;i++) {
    path+="/"+array[i];
}
$( document ).ready(function(){
    getAllRooms();
    $.ajax({
        url: "room/country",
        async: true,
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var countries=JSON.parse(JSON.stringify(data));
            $.each(countries, function( index, country ) {
                $("select").append("<option>"+country+"</option>");
            });
        }
    });
    $("#submit").click(function () {
        var formData = {
            title: $("#title").val(),
            country: $("#select").val(),
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "room",
            data: JSON.stringify(formData),
            dataType: 'text',
            success: function(result) {
                getAllRooms();
            }
        });
        return false;
    });
});
function getAllRooms() {
    $("tbody").empty();
    $.ajax({
        url: "room",
        async: true,
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var rooms=JSON.parse(JSON.stringify(data));
            $.each(rooms, function( index, room ) {
                var id=room.id;
                var title=room.title;
                var country=room.country;
                var light=room.light?"On":"Off";
                $("tbody").append("<tr>\n" +
                    "            <td><a href=\'"+path+"/roompage/"+id+"\'>"+title+"</a></td>\n"+
                    "            <td>"+country+"</td>\n" +
                    "            <td>"+light+"</td>\n" +
                    "        </tr>");
            });
        }
    });
}



