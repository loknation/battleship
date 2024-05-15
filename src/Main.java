import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static final int BOARD_SIZE = 10; //board size
    private static final char[][] board = new char[BOARD_SIZE][BOARD_SIZE]; //board that will be printed
    private static final boolean[][] ships = new boolean[BOARD_SIZE][BOARD_SIZE]; //ships locations

    private static final String[] SHIP_NAMES = {
            "Aircraft Carrier", "Battleship", "Battleship", "Cruiser",
            "Submarine", "Submarine", "Destroyer"
    }; //I think i got these right
    private static final int[] SHIP_SIZES = {5, 4, 4, 3, 3, 3, 2}; //mapped to ship names

    public static void main(String[] args) { //main loop
        Scanner scanner = new Scanner(System.in);
        SafeInput.prettyHeader("Welcome to Battleship!");

        initializeBoard();
        placeShips();

        //debugPrintShipLocations();

        int strikes = 0; //strikes (3 to loose)
        int consecutiveMisses = 0; //consecutive misses(5 to get strike)

        while (strikes < 3) {
            displayBoard();
            String rowInput = SafeInput.getRegEx(scanner, "Enter row (A-J): ", "[A-Ja-j]"); //get row
            int row = Character.toUpperCase(rowInput.charAt(0)) - 'A'; //set row to standard orm
            int col = SafeInput.getRangedInt(scanner, "Enter column (0-9): ", 0, BOARD_SIZE - 1); //get col

            if (ships[row][col]) { //if ship at location guessed
                System.out.println("Hit!");
                board[row][col] = 'X'; //update to hit
                ships[row][col] = false; //need to go back and fix this so they cant hit here again in a better way
                consecutiveMisses = 0;
            } else {
                System.out.println("Miss!");
                board[row][col] = 'O'; //set board to miss
                consecutiveMisses++;
                if (consecutiveMisses == 5) {
                    strikes++;
                    System.out.println("Strike! You have " + (3 - strikes) + " strikes left.");
                    consecutiveMisses = 0;
                }
            }

            if (isAllShipsSunk()) {//win
                System.out.println("Congratulations! You sunk all the ships!");
                break;
            }
        }

        if (strikes == 3) {//loss
            System.out.println("Game Over! You've struck out.");
        }

        displayBoard();
        scanner.close();
    }

    private static void initializeBoard() { //fill empty boards
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = '-';
                ships[i][j] = false;
            }
        }
    }

    private static void placeShips() {
        Random rand = new Random();

        for (int shipSize : SHIP_SIZES) {//damitri would like this i got all fancy
            boolean placed = false;
            while (!placed) {
                int row = rand.nextInt(BOARD_SIZE);//pick random location
                int col = rand.nextInt(BOARD_SIZE);//pick random location
                boolean horizontal = rand.nextBoolean();//pick random orientation
                placed = placeShip(row, col, shipSize, horizontal);//returns true if placed succsessfully
            }
        }
    }

    private static boolean placeShip(int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > BOARD_SIZE) return false;//check for overlap
            for (int i = 0; i < size; i++) {//check for overlap with ships
                if (ships[row][col + i]) return false;
            }
            for (int i = 0; i < size; i++) {//check no overlap
                ships[row][col + i] = true;//modify ships to add this ship
            }
        } else {//same thing for other orientation
            if (row + size > BOARD_SIZE) return false;
            for (int i = 0; i < size; i++) {
                if (ships[row + i][col]) return false;
            }
            for (int i = 0; i < size; i++) {
                ships[row + i][col] = true;
            }
        }
        return true;
    }

    private static void displayBoard() {//displays the board
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");//map to leters
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void debugPrintShipLocations() {//print a map of ships for debuging purpoces
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (ships[i][j]) {
                    System.out.print("S ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    private static boolean isAllShipsSunk() {//check if all ships sunk by iterating array until it finds true
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (ships[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
