
var args = process.argv
var port = args[2]

var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

var s = require('net').Socket();
s.connect(1302, '128.237.138.106');

// Keep count of the number of people on each team
var red = 0;
var orange = 0;
var green = 0;
var blue = 0;

app.get('/', function(req, res){
    res.sendFile(__dirname + '/index.html');
});


// a person connects
io.on('connection', function(socket){
    var team = Math.floor(Math.random() * 5);
    console.log('a user connected');
    if(team == 0) {
        red++;
    }
    socket.on('chat message', function(msg){
        console.log('chat message ' + msg + 'Team: ' + team);
        io.emit('chat message', msg);
        // send 'L' or 'R' for left or right and '-1' or '1' depending on
        // which button is pressed
        s.write('L' + parseInt(msg)+ '\n');
    });
    socket.on('disconnect', function(){
        console.log('user disconnected');
        if(team == 0) {
            red--;
        }
    });
});

http.listen(port, function(){
      console.log('listening on *:' + port);
});






