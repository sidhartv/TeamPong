
// Take in command line arguments to determine port No.
var args = process.argv
var port = args[2]

// Configure websocket, used for running the webserver
var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

// Configure another socket and connect to the game server
var s = require('net').Socket();
//s.connect(1302, '128.237.138.106');

// Keep count of the number of people on each team
var left = 0;
var right = 0;

// Keep track of their clicks too!
var leftclicks = 0;
var rightclicks = 0;

// respond to requests with index.html file
app.get('/', function(req, res){
    res.sendFile(__dirname + '/index.html');
});


// a person connects
io.on('connection', function(socket){
    var team = Math.floor(Math.random() * 2);
    if(team == 0) {
        name = 'L';
        io.emit('team', 'red');
        left++;
    } else if(team == 1) {
        name = 'R';
        io.emit('team', 'blue');
        right++;
    }
    console.log('a user connected\nTeam Breakdown--  Left: ' + left + ' Right: ' + right);
    socket.on('chat message', function(msg){
        console.log('Choice: ' + msg + ' Team: ' + name);
        io.emit('chat message', '1');
        
        // Update clicks for each team
        if(team == 0) {
            leftclicks++;
        } else if(team == 1) {
            rightclicks++;
        }
        // send 'L' or 'R' for left or right and '-1' or '1' depending on
        // which button is pressed
        //s.write(name + parseInt(msg)+ '\n');
    });
    socket.on('yes', function(msg){
        console.log(msg);
    });
    socket.on('disconnect', function(){
        console.log('disconnected');
        // subtract members when team players disconnect
        if(team == 0) {
            left--;
        } else if(team == 1) {
            right--;
        }
        console.log('user disconnected\nTeam Breakdown--  Left: ' + left + ' Right: ' + right);
    });
});

http.listen(port, function(){
      console.log('listening on *:' + port);
});






