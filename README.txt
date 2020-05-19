This is an implementation of a Connect Four AI that uses a minmax heuristic to calculate its next move. The game is played on the command terminal and the board is printed out using charaters. Every turn you enter the column you want to play your next move and the bot will respond.

This is what the board looks like:
1 2 3 4 5 6 7 
- - - - - - - 
- - X - - - - 
- O O O - - - 
- X O X - - - 
- X X O O - - 
O X X X O - - 

To play, install java and then simply compile Connect4Main.java with "javac Connect4Main.java" and run it with "java Connect4Main"

The bot has several tweaking factors that can be changed in the source code to adjust its strength. In the file Connect4Bot.java, BOT_THINKING_TIME at the very top of the file is the amount of time in milliseconds that the bot will think per move. Change this to whatever you want; The higher it is the stronger the bot will be and the longer it will take to make its move.

Enjoy the game! Don't use too powerful a computer if you want to win!
