package tictactoe;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // set up the empty field as 2d array
        String[][] field = setGameField();

        // game state
        String activePlayer = "X";

        // print starting grid
        printField(field);

        while (true) {
            // get user move
            int[] userMove = getUserMoveCoordinates(field);

            // update field with user move and print it
            updateField(field, userMove, activePlayer);
            printField(field);

            // initialize state variable
            int state = assessTheState(field);

            // check if game ended and break out of game loop
            if (state == 1) {
                break;
            }

            if ("X".equals(activePlayer)) {
                activePlayer = "O";
            } else {
                activePlayer = "X";
            }
        }
    }

    static String[][] setGameField() {
        /* sets empty game field */
        return new String[][]{{"_", "_", "_"}, {"_", "_", "_"}, {"_", "_", "_"}};
    }

    static void printField(String[][] field) {
        /* this method takes in field array, formats it as 3/3 playing area,
        and outputs it with visual enhancements.
         */
        System.out.println("---------");

        for (String[] subArr: field) {
            System.out.print("| ");
            for (String data: subArr) {
                System.out.print(data + " ");
            }
            System.out.println("|");
        }

        System.out.println("---------");
    }

    static int assessTheState(String[][] field) {
        /* checks the state of the game: Impossible, Game not finished, Draw and Win */
        // IMPOSSIBLE (someone is 2 moves ahead, there are 2 winners etc.)
        if ((hasThreeInARow(field, "X") && (hasThreeInARow(field, "O")) ||
                Math.abs(countElement(field, "X") - countElement(field, "O")) >= 2)) {
            System.out.println("Impossible");
            return 1;
        }

        // GAME NOT FINISHED (if field has empty cells and no-one has a winning combination
        if (hasEmptyCells(field) && !hasThreeInARow(field, "X") && !hasThreeInARow(field,"O")) {
            return 0;
        }

        // DRAW (no empty cells and no winning combination)
        if (!hasEmptyCells(field) && !hasThreeInARow(field, "X") && !hasThreeInARow(field,"O")) {
            System.out.println("Draw");
            return 1;
        }

        // WIN
        // check if X is a winner
        if (hasThreeInARow(field, "X")) {
            System.out.println("X wins");
            return 1;
        }

        // check if O is a winner
        if (hasThreeInARow(field, "O")) {
            System.out.println("O wins");
            return 1;
        }
        return 0;
    }

    static boolean hasEmptyCells(String[][] field) {
        /* checks if there are empty cells in field */
        for (String[] row: field) {
            for (String item: row) {
                if ("_".equals(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean hasThreeInARow(String[][] field, String element) {
        /* checks if given player has a winning combination
         */
        // HORIZONTAL
        int counter = 0;

        for (int row = 0; row < 3; row++) {
            for (int el = 0; el < 3; el++) {
                if (field[row][el].equals(element)) {
                    counter++;
                }
            }
            if (counter == 3) {
                return true;
            } else {
                counter = 0;
            }
        }

        // VERTICAL
        for (int el = 0; el < 3; el++) {
            for (int row = 0; row < 3; row++) {
                if (field[row][el].equals(element)) {
                    counter++;
                }
            }
            if (counter == 3) {
                return true;
            } else {
                counter = 0;
            }
        }

        // DIAGONALLY
        // from left to right bottom
        for (int i = 0, j = 0; i < 3; i++, j++) {
            if (field[i][j].equals(element)) {
                counter++;
            }

            if (counter == 3) {
                return true;
            }
        }

        // from top right to bottom left
        counter = 0;

        for (int i = 0, j = 2; i < 3; i++, j--) {
            if (field[i][j].equals(element)) {
                counter++;
            }

            if (counter == 3) {
                return true;
            }
        }
        return false;
    }

    static int countElement(String[][] field, String element) {
        /* Helper function to calculate how many elements of certain
        type are currently on the field. Returns integer count.
         */
        int count = 0;
        for (String[] row: field) {
            for (String el: row) {
                if (el.equals(element)) {
                    count++;
                }
            }
        }
        return count;
    }

    static boolean cellOccupied(String[][] field, int point1, int point2) {
        /* takes in current field state and chosen user
        coordinates and returns true if cell is already occupied
         */
        return !Objects.equals(field[point1 - 1][point2 - 1], "_");
    }

    static int[] getUserMoveCoordinates(String[][] field) {
        /* get move from user and return it as array with
        * coordinates */
        Scanner scanner = new Scanner(System.in);

        int c1;
        int c2;


        while (true) {
            // ensure we really get integers
            System.out.print("Enter the coordinates: ");
            if (scanner.hasNextInt()) {
                c1 = scanner.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                scanner.nextLine();
                continue;
            }

            if (scanner.hasNextInt()) {
                c2 = scanner.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                scanner.nextLine();
                continue;
            }

            // check if point is out of bounds or cell is occupied
            if (c1 < 1 || c1 > 3 || c2 < 1 || c2 > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            } else if (cellOccupied(field, c1, c2)) {
                System.out.println("This cell is occupied! Choose another one!");
                scanner.nextLine();
                continue;
            }
            break;
        }

        return new int[]{c1, c2};
    }

    static void updateField(String[][] field, int[] points, String playerSymbol) {
        /* take in field, user move coordinates and user symbol
        and update field according to his move
         */
        field[points[0] - 1][points[1] - 1] = playerSymbol.toUpperCase();
    }
}

