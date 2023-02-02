var stomp = null;

window.onload = function (){
    connect();
};

function connect(){
    var socket = new SockJS('/socket');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        console.log("Connected: " + frame);
        stomp.subscribe('/topic/products', function (product){
            renderItem(product);
        });
    });
}

//$(function () {
  //  $("form").on('submit', function (e) {
    //    e.preventDefault();
    //});
    //$( "#send" ).click(function () { sendContent(); });
//});

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send2" ).click(function () { sendContent2(); });
});

function  sendContent() {
    stomp.send("/app/products", {}, JSON.stringify({
        'title': $("#title").val(),
        'price': $("#price").val()
    }));
}
function  sendContent2() {
    stomp.send("/app/products2", {}, JSON.stringify({
        'title': $("#title").val(),
        'price': $("#price").val()
    }));
}


function  renderItem(productJson) {
    var product = JSON.parse(productJson.body);
    $("#table").append("<tr>" +
        "<td>" + product.title + "</td>" +
        "<td>" + product.price + "</td>" +
        "<td><a href='/product'" + product.id + "/bucket'>Add to backet</a></td>" +
        "</tr>" );

}