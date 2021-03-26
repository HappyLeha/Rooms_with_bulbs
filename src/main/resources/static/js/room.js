var href=$(location).attr('href');
var array=href.toString().split("/");
var id=array[array.length-1];
var ip;
var path="http:";
for (var i=1;i<array.length-2;i++) {
    path+="/"+array[i];
}
function changeLight() {
    $.ajax({
        url: path + "/room/light/" + id + "?ip="+ip,
        method: "PATCH",
        contentType: "application/json",
        dataType: "json",
    });
}
function getRoom() {
    $.ajax({
        url: path + "/room/" + id + "?ip=" + ip,
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var room = JSON.parse(JSON.stringify(data));
            if (room.light) {
                $("#body").html("<div id='on'>light is on</div>\n" +
                    "<button id='button' onclick='changeLight()'>Off</button>");
            } else {
                $("#body").html("<div id='off'>light is off</div>\n" +
                    "<button id='button' onclick='changeLight()'>On</button>");
            }
            setTimeout(getRoom, 1000);
        },
        statusCode: {
            403: function (response) {
                $("#body").html("<div id='error'>" + response.responseText + "</div>");
            },
            404: function (response) {
                $("#body").html("<div id='error'>" + response.responseText + "</div>");
            }
        }
    });
}
$( document ).ready(function(){
    $.ajax({
        url: "https://ipapi.co/json/",
        async: true,
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var result=JSON.parse(JSON.stringify(data));
            ip=result.ip;
        }
    }).then(getRoom);
});