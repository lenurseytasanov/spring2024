let stompClient = null;

function connect() {
    let socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic', function (response) {
            showMessage(JSON.parse(response.body).content);
        });
    });
}

function sendMessage() {
    let message = document.getElementById('message').value;
    stompClient.send("/app/message", {}, JSON.stringify({ 'content': message }));
}

function showMessage(message) {
    let chatArea = document.getElementById('chatArea');
    let p = document.createElement('p');
    p.appendChild(document.createTextNode(message));
    chatArea.appendChild(p);
}

function registerUser(username) {
    let username = document.getElementById('username').value;
    stompClient.send("/app/register", {}, JSON.stringify({ 'username': username }))
}

document.getElementById('sendBtn').addEventListener('click', function () {
    sendMessage();
});
document.getElementById('regBtn').addEventListener('click', function () {
    registerUser();
});

connect();
