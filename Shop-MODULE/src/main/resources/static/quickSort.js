var stomp = null;

window.onload = function (){
    connect();
};

function connect(){
    var socket = new SockJS('/socket');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        console.log("Connected: " + frame);
        stomp.subscribe('/topic/sort/quick/'+ $("#sessionId").val(), function (array){
            renderItem(array);
        });
    });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function () { sendContent(); });
});

function  sendContent() {
    stomp.send("/app/sort/quick", {},JSON.stringify({
        'sessionId': $("#sessionId").val(),
        'message': $("#array").val()
    }));
}


function  renderItem(array) {
    var msg =  array.body;
    $("#table").append("<tr>" +
        "<td>" + msg + "</td>" +
        "</tr>" );

}