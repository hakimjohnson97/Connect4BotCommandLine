import java.util.Scanner;

/**
*  A basic command line implementation of ConnectFour. The game will alternate between the user and the bot until the game
*  ends
*/

public class Connect4Main {


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Connect4Model model = new Connect4Model();
        Connect4Bot bot = new Connect4Bot();

        System.out.println("Welcome to the Connect 4 game. Here you will play against a bot.");

        while (true) {
         //   System.out.flush();
           // System.out.println(display);
            model.displayBoard();
            if (model.getPlayerTurn() == Connect4Model.PLAYER_1) {
                System.out.println("It is your move. Enter the column number you will place your next move:");
                int colNum = input.nextInt();
                if (model.addBall(colNum-1, Connect4Model.Color.RED) == -1) {
                    System.out.println("That column is full!");
                }
                else {
                    model.nextPlayerTurn(); 
                }
            }
            else {
                int colNum = bot.getNextMove(model.getBoard());
                model.addBall(colNum, Connect4Model.Color.YELLOW);
                model.nextPlayerTurn(); 
                
            }
            if (model.checkForWinner()) {
                Connect4Model.WinningMove winningMove = model.getWinningMove();
                if (winningMove.winner == Connect4Model.Color.RED) {
                    model.displayBoard();
                    System.out.println("Congratulations! You Win!");
                }
                else {
                    model.displayBoard();
                    System.out.println("Looks like the bot won! Better luck next time.");
                }
                break;
            }
            if (model.isBoardFull()) {
                model.displayBoard();
                System.out.println("It's a tie!");
            }
        }
    }
    
}
