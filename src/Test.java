public class Test {
    public static void main(String[] args) {

        char[][] board = {{'x', 'o', 'x'}, {'x', 'o', 'x'}, {'o', '-', '-'}};
        drawBoard(board);

        int coordinates = freeCells(board);
        int x1, y1, x2, y2;

        x1 = coordinates / 1000;
        y1 = coordinates / 100 - x1 * 10;

        x2 = coordinates % 100 / 10;
        y2 = coordinates % 10;

        System.out.println("\nx1 = " + x1);
        System.out.println("y1 = " + y1);
        System.out.println("x2 = " + x2);
        System.out.println("y2 = " + y2);

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

}