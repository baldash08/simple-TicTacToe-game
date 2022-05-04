package tictactoe;

import java.util.Objects;
import java.util.Scanner;

class CellIsOccupied extends Exception {
    public String toString() {
         return "This cell is occupied! Choose another one!";
    }
}

class CoordinatesOutOfBounds extends Exception {
    public String toString() {
        return "Coordinates should be from 1 to 3!";
    }
}

public class TicTacToe {
    private static final Scanner scanner = new Scanner(System.in);
    private static final char[][] gameField = {
            {'_', '_', '_'},
            {'_', '_', '_'},
            {'_', '_', '_'},
    };
    private static int countX = 0;
    private static int countO = 0;
    private static int xCounts = 0;
    private static int oCounts = 0;
    private static boolean isXWin = false;
    private static boolean isOWin = false;
    private static char move = 'O';


    public static void playGame() {
        CellIsOccupied cellIsOccupied = new CellIsOccupied();
        CoordinatesOutOfBounds coordinatesOutOfBounds = new CoordinatesOutOfBounds();
        boolean b = true;
        int n;
        int m;

        while (b) {
            System.out.print("Enter the coordinates: ");
            String[] x = scanner.nextLine().split(" ");
            try {
                n = Integer.parseInt(x[0]);
                m = Integer.parseInt(x[1]);

                if (n > 3 || m > 3 || n < 1 || m < 1)
                    throw new CoordinatesOutOfBounds();
                if (gameField[n-1][m-1] != '_')
                    throw new CellIsOccupied();
                TicTacToe.printGameField(n, m);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("You should enter numbers");
            } catch (CellIsOccupied e) {
                System.out.println(cellIsOccupied.toString());
            } catch (CoordinatesOutOfBounds e) {
                System.out.println(coordinatesOutOfBounds.toString());
            }

            if (!Objects.equals(gameStatus(), "Game not finished")) {
                System.out.println(gameStatus());
                b = false;
            }
        }
    }

    public static void whoWins(int count1, int count2) {
        if (count1 == 3)
            isXWin = true;
        else if (count2 == 3)
            isOWin = true;

        countX = 0;
        countO = 0;
    }

    public static void printGameField(int n, int m) {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                if (i == n - 1 && j == m - 1 && gameField[i][j] == '_' && move != 'X') {
                    gameField[i][j] = 'X';
                    move = 'X';
                } else if (i == n - 1 && j == m - 1 && gameField[i][j] == '_' && move == 'X') {
                    gameField[i][j] = 'O';
                    move = 'O';
                }
            }
        }

        System.out.println("---------");
        for (char[] chars : gameField) {
            System.out.print("| ");
            for (int j = 0; j < gameField.length; ++j) {
               System.out.print(chars[j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static String gameStatus() {
        xCounts = 0;
        oCounts = 0;
        for (char[] item : gameField) {
            for (int j = 0; j < gameField.length; j++) {
                if (item[j] == 'X')
                    xCounts++;
                else if (item[j] == 'O')
                    oCounts++;
            }
        }

        // horizontal
        for (char[] value : gameField) {
            for (int j = 0; j < gameField.length; j++) {
                if (value[j] == 'X')
                    countX++;
                else if (value[j] == 'O')
                    countO++;

            }
            whoWins(countX, countO);
        }

        // vertical
        for (int j = 0; j < gameField.length; j++) {
            for (char[] chars : gameField) {
                if (chars[j] == 'X')
                    countX++;
                else if (chars[j] == 'O')
                    countO++;
            }
            whoWins(countX,countO);
        }

        // diagonal
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                if (i == j && gameField[i][j] == 'X')
                    countX++;
                else if (i == j && gameField[i][j]== 'O')
                    countO++;
            }
        }
        whoWins(countX,countO);

        // diagonal 2
        int[] temp = new int[3];
        int counter = gameField.length - 1;
        for (int i = 0; i < gameField.length; i++) {
                temp[i] = gameField[i][counter];
                counter--;
        }

        for (int j : temp) {
            if (j == 'X')
                countX++;
            else if (j == 'O')
                countO++;
        }
        whoWins(countX,countO);


        return isXWin ? "X wins" : isOWin ? "O wins" :
                xCounts + oCounts != 9 ? "Game not finished" : "Draw";
    }
}
