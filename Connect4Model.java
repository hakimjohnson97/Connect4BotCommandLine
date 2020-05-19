/**
 * This is the model of the game, which basically contains all the data and logistics of the game. Place balls onto the board
 * with addBall and check for winners with checkForWinner
 */
public class Connect4Model {
    public enum Color {EMPTY, RED, YELLOW};

    public final static boolean PLAYER_1 = false;
    public final static boolean PLAYER_2 = true;

    public static final int MOVES_TO_WIN = 4;

    public static final int BOARD_WIDTH = 7;
    public static final int BOARD_HEIGHT = 6;

    private static final String DEFAULT_PLAYER_1_NAME = "Player 1";
    private static final String DEFAULT_PLAYER_2_NAME = "Player 2";

    private Color mBoard[][];
    private WinningMove mWinningMove = null;
    private boolean mIsGameOver = false;

    private int mPlayer1Score;
    private int mPlayer2Score;
    private String mPlayer1Name;
    private String mPlayer2Name;

    private Point mLastPlacedBall = null;

    private boolean mPlayerTurn;

    /**
     * Simple Point class for convenience
     */
    public class Point {
        public int x;
        public int y;

        public void set(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(int x, int y) {
            if (this.x == x && this.y == y) {
                return true;
            }
            return false;
        }
    }


    /**
     * This class is used to store the winning move so the view can draw it.
     */
    public class WinningMove {
        public Color winner;
        public Point startPos = new Point();
        public Point endPos = new Point();
    }

    public Connect4Model() {

        mBoard = new Color[BOARD_WIDTH][BOARD_HEIGHT];
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                mBoard[i][j] = Color.EMPTY;
            }
        }

        mPlayerTurn = PLAYER_1;
        mPlayer1Name = DEFAULT_PLAYER_1_NAME;
        mPlayer2Name = DEFAULT_PLAYER_2_NAME;

    }

    public void displayBoard()
    {
        System.out.print("\nBoard\n"); 
        for (int i = 1; i <= BOARD_WIDTH; i++) {
            System.out.print(i);
            System.out.print(" ");  
        }  
        System.out.print("\n");
        for (int i = BOARD_HEIGHT-1; i >= 0; i--)    {
            for (int j = 0; j < BOARD_WIDTH; j++)    {
                if (mBoard[j][i] == Color.EMPTY)
                    System.out.print("- ");
                else if (mBoard[j][i] == Color.RED)
                    System.out.print("X ");
                else if (mBoard[j][i] == Color.YELLOW)
                    System.out.print("O ");
            }
            System.out.print("\n");
        }

        System.out.print("\n");
    }

    public Point getLastPlacedBall() {
        return mLastPlacedBall;
    }

    public boolean getPlayerTurn() {
        return mPlayerTurn;
    }

    public void nextPlayerTurn() {
        mPlayerTurn = !mPlayerTurn;
    }

    public Color[][] getBoard() {
        return mBoard;
    }

    public WinningMove getWinningMove() {
        return mWinningMove;
    }

    public boolean hasWinner() {
        return mIsGameOver;
    }

    public int getPlayer1Score() {
        return mPlayer1Score;
    }

    public int getPlayer2Score() {
        return mPlayer2Score;
    }

    public String getPlayer1Name() {
        return mPlayer1Name;
    }

    public String getPlayer2Name() {
        return mPlayer2Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.mPlayer1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.mPlayer2Name = player2Name;
    }

    /**
     * Adds the ball to next available space in the column
     * @param col column numb r, goes from 0 - BOARD_WIDTH-1
     * @param color color of the ball
     * @return the row it was placed in
     */
    public int addBall(int col, Color color)//first column is 0
    {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            if (mBoard[col][i] == Color.EMPTY) {
                mBoard[col][i] = color;
                mLastPlacedBall = new Point();
                mLastPlacedBall.set(col, i);
                return i;
            }
        }
        return -1; //Error
    }

    /**
     * Checks the board to see if there's a winner. i.e 4 balls placed in a row
     * It then stores the boolean which can be accessed with hasWinner()
     * and the winning move with getWinningMove()
     * @return whether there is a winner
     */
    public boolean checkForWinner()
    {
        int count;
        Color precolor;

        mWinningMove = new WinningMove();

        //Checks in all possible 4 directions, horizontally, vertically, diagonally right, diagonally left

        //Check horizontally
        precolor = Color.EMPTY; count = 0;
        for (int i = 0; i < BOARD_HEIGHT; i++)    {
            for (int j = 0; j < BOARD_WIDTH; j++)    {
                if (mBoard[j][i] == precolor && mBoard[j][i] != Color.EMPTY) {
                    count++;
                    if (count >= MOVES_TO_WIN-1) {
                        setupWinningMove(j, i, precolor);
                        return true;
                    }
                }
                else {
                    count = 0;
                    mWinningMove.startPos.set(j, i);
                }
                precolor = mBoard[j][i];
            }
            precolor = Color.EMPTY;
            count = 0;
        }

        //Check vertically
        precolor = Color.EMPTY; count = 0;
        for (int j = 0; j < BOARD_WIDTH; j++)    {
            for (int i = 0; i < BOARD_HEIGHT; i++)    {
                if (mBoard[j][i] == precolor && mBoard[j][i] != Color.EMPTY) {
                    count++;
                    if (count >= MOVES_TO_WIN-1) {
                        setupWinningMove(j, i, precolor);
                        return true;
                    }
                }
                else {
                    count = 0;
                    mWinningMove.startPos.set(j, i);
                }
                precolor = mBoard[j][i];
            }
            precolor = Color.EMPTY;
            count = 0;
        }



        //Check diagonally to the right
        precolor = Color.EMPTY; count = 0;
        for (int i = BOARD_HEIGHT-1; i >= 0; i--)    {
            for (int j = 0; (j < BOARD_WIDTH && (i+j) < BOARD_HEIGHT); j++)    {
                if (mBoard[j][i+j] == precolor && mBoard[j][i+j] != Color.EMPTY) {
                    count++;
                    if (count >= MOVES_TO_WIN-1) {
                        setupWinningMove(j, i+j, precolor);
                        return true;
                    }
                }
                else {
                    count = 0;
                    mWinningMove.startPos.set(j, i+j);
                }
                precolor = mBoard[j][i+j];
            }
            precolor = Color.EMPTY;
            count = 0;
        }
        precolor = Color.EMPTY; count = 0;
        for (int j = 1; j < BOARD_WIDTH; j++)    {
            for (int i = 0; (i < BOARD_HEIGHT && (i+j) < BOARD_WIDTH); i++)    {
                if (mBoard[j+i][i] == precolor && mBoard[j+i][i] != Color.EMPTY) {
                    count++;
                    if (count >= MOVES_TO_WIN-1) {
                        setupWinningMove(j+i, i, precolor);
                        return true;
                    }
                }
                else {
                    count = 0;
                    mWinningMove.startPos.set(j+i, i);
                }
                precolor = mBoard[j+i][i];
            }
            precolor = Color.EMPTY;
            count = 0;
        }

        //Check diagonally to the left
        precolor = Color.EMPTY; count = 0;
        for (int i = BOARD_HEIGHT-1; i >= 0; i--)    {
            for (int j = 0; (j < BOARD_WIDTH && (i+j) < BOARD_HEIGHT); j++)    {
                if (mBoard[BOARD_WIDTH-1-j][i+j] == precolor && mBoard[BOARD_WIDTH-1-j][i+j] != Color.EMPTY) {
                    count++;
                    if (count >= MOVES_TO_WIN-1) {
                        setupWinningMove(BOARD_WIDTH-1-j, i+j, precolor);
                        return true;
                    }
                }
                else {
                    count = 0;
                    mWinningMove.startPos.set(BOARD_WIDTH-1-j, i+j);
                }
                precolor = mBoard[BOARD_WIDTH-1-j][i+j];
            }
            count = 0;
            precolor = Color.EMPTY;
        }
        precolor = Color.EMPTY; count = 0;
        for (int j = BOARD_WIDTH-2; j >= 0; j--)    {
            for (int i = 0; (i < BOARD_HEIGHT && (j-i) >= 0); i++)    {
                if (mBoard[j-i][i] == precolor && mBoard[j-i][i] != Color.EMPTY) {
                    count++;
                    if (count >= MOVES_TO_WIN-1) {
                        setupWinningMove(j-i, i, precolor);
                        return true;
                    }
                }
                else {
                    count = 0;
                    mWinningMove.startPos.set(j-i, i);
                }
                precolor = mBoard[j-i][i];
            }
            precolor = Color.EMPTY;
            count = 0;
        }

        mWinningMove = null;

        if (isBoardFull()) {
            mIsGameOver = true;
            return true;
        }

        return false;


    }

    /**
     * Completes the winning move object mWinningMove
     * @param x x coordinate of the endPos
     * @param y y coordinate of the endPos
     * @param winner Color of the winner
     */
    private void setupWinningMove(int x, int y, Color winner) {

        mWinningMove.endPos.set(x, y);
        mIsGameOver = true;
        if (winner == Color.RED) {
            mPlayer1Score++;
        } else {
            mPlayer2Score++;
        }
    }

    /**
     * Checks if the board is full of balls
     * @return true if it is full
     */
    public boolean isBoardFull() {

        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                if (mBoard[i][j] == Color.EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Resets the board to start a new game
     */
    public void reset() {

        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                mBoard[i][j] = Color.EMPTY;
            }
        }

        mWinningMove = null;
        mIsGameOver = false;

        //Swaps player turns every time it is reset
        if ((mPlayer1Score + mPlayer2Score) % 2 == 0) {
            mPlayerTurn = PLAYER_1;
        }
        else {
            mPlayerTurn = PLAYER_2;
        }

        mLastPlacedBall = null;

    }

}
