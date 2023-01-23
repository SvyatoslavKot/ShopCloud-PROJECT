var stomp = null;

window.onload = function (){
    connect();
};

function connect(){
    var socket = new SockJS('/addToBucket');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        console.log("Connected: " + frame);
       stomp.subscribe('/topic/addToBucket', function (product){
            renderItem(product);
        });
    });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
        var id = document.form.id;
    });
    $( "#add" ).click(function () { sendContent(); });
});

function  sendContent() {
    stomp.send("/app/addToBucket", {}, JSON.stringify({
        'id': $("#id").val()
    }));
}




