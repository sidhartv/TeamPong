# TeamPong

An Audience version of Pong!

Team Pong is a program anyone in an audience can connect to from their phone, and be randomly assigned to one of 2 teams. (The number of teams is adjustable, but currently the game structure only makes sense for 2 teams).  Each audience member has an up and down arrow for their team on their phone screen, and by clicking the up and down arrows, they can move the pong paddle up and down.

The collective actions of the audience to move the paddles can be projected from the game host computer so the audience can play live (with no delay).


The audience goes on a website hosted by a Node server (code in socketstuff/index.js), and each member is randomly assigned to either team red or team blue.  If they press the red or blue arrow, the node server transmits their click information to a java server which transfers the input to the pong game.

In the pong game, the user input is used to raise and lower the pong paddles and play the game!
