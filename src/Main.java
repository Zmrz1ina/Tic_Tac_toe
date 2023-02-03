import java.util.Scanner;

public class Main {

    // Keep track if the game has ending
    public static boolean gameEnded = false;
    // new Char array for found game result
    public static char[][] board_copy = new char[3][3];

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // Get player names
        System.out.println("Player 1, what's your name? ");
        String p1 = in.nextLine();
        System.out.println("Player 2, what's your name? ");
        String p2 = in.nextLine();

        // Tic_Tac_Toe board 3x3
        // - empty space
        // x player 1
        // o player 2
        char[][] board = new char[3][3];

        // Step counter
        int counter = 1;

        // Fill the board with dashes = заполнение '-'
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                board[i][j] = '-';
            }
        }

        // Keep track of whose torn it is
        boolean isPlayer1 = true;

        while (!gameEnded) {

            // Draw the board
            drawBoard(board);

            // Keep track of what symbol we are using to player
            char symbol = ' ';
            if (isPlayer1) {
                symbol = 'x';
            } else {
                symbol = 'o';
            }

            // Row and col variables
            int row = 0;
            int col = 0;

            // Print out the player's turn
            if (isPlayer1) {
                System.out.println("\n" + p1 + "'s turn (x): ");
            } else {
                System.out.println("\n" + p2 + "'s turn (o): ");
            }

            while (true) {
                // Get row and col from user
                System.out.println("Enter a row (1, 2 or 3): ");
                row = in.nextInt();
                System.out.println("Enter a col (1, 2 or 3): ");
                col = in.nextInt();

                System.out.println("\n" + "       COUNTER - " + counter);
                counter++;

                // Check if row and col are valid
                if (row < 1 || col < 1 || row > 3 || col > 3) {
                    // Row and col are out of bounds = знач. выходит за поле
                    System.out.println("\nYour row and col are out of bounds!\n---try again---\n");
                    counter--;

                } else if (board[row - 1][col - 1] != '-') {
                    // Board position already has an x or o = позиция уже занята
                    System.out.println("\nSomeone has already made a move there!\n---try again---\n");
                    counter--;

                } else {
                    // Row and col are valid! = все ОК
                    break;
                }
            }

            // Setting the position on board to the player's symbol
            board[row - 1][col - 1] = symbol; // -1  for this (1, 2 or 3)

            // Check for future won or not = проверка на возможеость победить
            if (counter == 8) {

                System.out.println("\nCheck for tied");
                System.out.println("Loading...\n");

                is_won_possible(freeCells(copyBoard(board)), copyBoard(board));

                if (gameEnded) {
                    System.out.println("The end");
                }

                System.out.println("\n" + "       COUNTER - " + counter);
            }

            // Check if a player has won
            if (hasWon(board) == 'x') {
                // Player 1 has won
                System.out.println("\n" + p1 + " has won!\n");
                gameEnded = true;

            } else if (hasWon(board) == 'o') {
                // Player 2 has won
                System.out.println("\n" + p2 + " has won!\n");
                gameEnded = true;

            } else {
                // Nobody has won
                if (hasTied(board)) {
                    // Tied
                    System.out.println("\nIt's a tie!");
                    System.out.println("Nobody won (((\n");
                    gameEnded = true;

                } else {
                    // Continue the game and toggles the turn
                    isPlayer1 = !isPlayer1; // Player swicher
                    System.out.println("\n/............................/\n");
                }
            }
        }

        // Print out final state of the board
        drawBoard(board);
    }//////////////////////////////////////////////////////////////////////////

    // Check for possible won
    private static void is_won_possible(int coordinates, char[][] board) {

        gameEnded = false;

        int x1, y1, x2, y2;

        x1 = coordinates / 1000;
        y1 = coordinates / 100 - x1 * 10;

        x2 = coordinates % 100 / 10;
        y2 = coordinates % 10;

        board[x1][y1] = 'x';
        board[x2][y2] = 'o';

        if (hasWon(board) == '-') {

            System.out.println("But u can try won!)");

            board[x1][y1] = 'o';
            board[x2][y2] = 'x';

            if (hasWon(board) == '-') {

                System.out.println("Tied. No reason to continue.");
                gameEnded = true;
            }

        } else {
            System.out.println("U need continue the game");
            gameEnded = false;
        }
    }

    // Printing out the board = вывод игрового поля
    public static void drawBoard(char[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Check for the winner
    public static char hasWon(char[][] board) {

        // Row
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-') {

                return board[i][0];
            }
        }

        // Col
        for (int j = 0; j < board.length; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[0][j] != '-') {

                return board[0][j];
            }
        }

        // Diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {

            return board[0][0];
        }

        if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != '-') {

            return board[2][0];
        }

        // Nobody has won
        return '-';
    }

    // Check if the board is full
    public static boolean hasTied(char[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] == '-') {
                    return false;
                }

            }
        }

        return true;
    }

    // find - on the board = поиск пустых позиций
    public static int freeCells(char[][] board) {
        int coordinates = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] == '-') {
                    coordinates = coordinates * 100 + i * 10 + j;
                }

            }
        }

        return coordinates;
    }

    // Full copy the board
    public static char[][] copyBoard(char[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                board_copy[i][j] = board[i][j];
            }
        }

        return board_copy;
    }

}
