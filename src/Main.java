import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static Random rnd;



    //Gets the board sizes and returns the number of rows and columns as an array
    public static int[] getBoardSizes() {
        System.out.println("Enter the board size");
        scanner = new Scanner(System.in);  //Delete before submitting
        String board_sizes = scanner.nextLine();
        String[] dimensions = board_sizes.split("X");
        int rows = Integer.parseInt(dimensions[0]);
        int columns = Integer.parseInt(dimensions[1]);
        return new int[] {rows, columns};
    }


    //The function gets the number and the sizes of the ships and creates two arrays accordingly
    public static int[][] getBattleshipSizes() {
        System.out.println("Enter the battleship sizes");
        String ships_info = scanner.nextLine();
        String[] ships_num_size = ships_info.split(" ");
        int len = ships_num_size.length;
        int[] sizes = new int[len];
        int[] num = new int[len];
        for (int i = 0; i < len; ++i) {
            String[] ship_parts = ships_num_size[i].split("X");
            sizes[i] = Integer.parseInt(ship_parts[1]);
            num[i] = Integer.parseInt(ship_parts[0]);
        }
        return new int[][] {num, sizes};
    }


    //Returns the number of digits
    public static int getSpaces(int spaces) {
        String numSpacesStr = Integer.toString(spaces);
        return numSpacesStr.length();
    }


    // function that creates the initial game board
    public static String[][] createGameBoard(int[] boardSizes) {
        int numRows = boardSizes[0], numCols = boardSizes[1];
        int countCols = 0, countRows = 0;
        int numSpaceForTable = getSpaces(numRows) + 1;
        String space = " ";
        String resultSpace = "";
        String[][] gameBoard = new String[numRows+1][numCols+1];
        int countToRemove = 1;
        if (numSpaceForTable - 1 != getSpaces(numRows - 1))
            countToRemove = 2;

        // fill the first row with spaces and numbers
        for (int index_row = 0; index_row < numRows+1; ++index_row) {
            if (index_row==0) {
                //loop that`s creating the spaces in the first row
                for (int i=0; i < numSpaceForTable - countToRemove;++i){
                    resultSpace += space;
                }
                gameBoard[0][0] = resultSpace;
                resultSpace = "";

                //printing the cols number
                for (int index_cols = 1; index_cols < numCols+1; ++index_cols) {
                    String CountColsSTR = Integer.toString(countCols);
                    gameBoard[0][index_cols] = (CountColsSTR);
                    ++countCols;
                }
            }
            // filling the rest of the board
            else if (index_row < numRows+1) {
                String CountRowsSTR = Integer.toString(countRows);
                String spaceForRowSTR = Integer.toString(countRows);
                int numSpaceForRow = numSpaceForTable - spaceForRowSTR.length();
                for (int i = 0; i < numSpaceForRow - countToRemove;++i)
                    resultSpace += space;
                gameBoard[index_row][0] = resultSpace + CountRowsSTR;
                resultSpace = "";
                for (int index_col = 1;index_col < numCols+1; index_col++)
                    gameBoard[index_row][index_col] = "–";
                countRows++;
            }
        }
        return gameBoard;
    }

    public static void printBoardGame(String[][] boardGame) {
        for (int i = 0; i < boardGame.length; i++) {
            for (int j = 0; j < boardGame[i].length; j++) {
                if (j == (boardGame[i].length - 1))
                    System.out.println(boardGame[i][j]);
                else
                    System.out.print(boardGame[i][j] + " ");
            }
        }
        System.out.println(); // space row after every boardGame
    }

    public static int[] stringToIntArr(String[] infoStr) {
        int[] infoInt = new int[infoStr.length];
        for (int i = 0; i < infoStr.length; ++i)
            infoInt[i] = Integer.parseInt(infoStr[i]);
        return infoInt;
    }

    public static boolean checkOrientation(int[] placeInfo) {
        if ((placeInfo[2] != 0) && (placeInfo[2] != 1)) {
            System.out.println("Illegal orientation, try again!");
            return true;
        }
        return false;
    }

    public static boolean checkTile(String[][] placingBoard, int[] placeInfo) {
        if ((placeInfo[0] < 0) || (placeInfo[0] >= placingBoard.length - 1) ||
                (placeInfo[1] < 0) || (placeInfo[1] >= placingBoard[0].length - 1)) {
            System.out.println("Illegal tile, try again!");
            return true;
        }
        return false;
    }

    public static boolean checkExcession(String[][] placingBoard, int[] placeInfo, int sizeOfShip) {
        if (placeInfo[2] == 0) {
            if (placeInfo[1] + sizeOfShip > placingBoard[0].length - 1) {
                System.out.println("Battleship exceeds the boundaries of the board, try again!");
                return true;
            }
        }
        else if (placeInfo[2] == 1) {
            if (placeInfo[0] + sizeOfShip > placingBoard.length - 1) {
                System.out.println("Battleship exceeds the boundaries of the board, try again!");
                return true;
            }
        }
        return false;
    }

    public static boolean checkOverlap(String[][] placingBoard, int[] placeInfo, int sizeOfShip) {
        if (placeInfo[2] == 0) {
            for (int i = 0; i < sizeOfShip; ++i) {
                if ((placingBoard[placeInfo[0] + 1][placeInfo[1] + 1 + i]).equals("#")) {
                    System.out.println("Battleship overlaps another battleship, try again!");
                    return true;
                }
            }
        }
        else if (placeInfo[2] == 1) {
            for (int i = 0; i < sizeOfShip; ++i) {
                if ((placingBoard[placeInfo[0] + 1 + i][placeInfo[1] + 1]).equals("#")) {
                    System.out.println("Battleship overlaps another battleship, try again!");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkAdjacent(String[][] placingBoard, int[] placeInfo, int sizeOfShip) {
        if (placeInfo[2] == 0) {
            for (int i = 0; i < sizeOfShip; ++i) {
                if ((placingBoard[placeInfo[0] + 1][placeInfo[1] + 1 + i]).equals("A")) {
                    System.out.println("Adjacent battleship detected, try again!");
                    return true;
                }
            }
        }
        else if (placeInfo[2] == 1) {
            for (int i = 0; i < sizeOfShip; ++i) {
                if ((placingBoard[placeInfo[0] + 1 + i][placeInfo[1] + 1]).equals("A")) {
                    System.out.println("Adjacent battleship detected, try again!");
                    return true;
                }
            }
        }
        return false;
    }


    public static void placeInBoard(String[][] placingBoard, int[] placeInfo, int sizeOfShip) {
        if (placeInfo[2] == 0) {
            for (int i = 0; i < sizeOfShip; ++i) {
                placingBoard[placeInfo[0] + 1][placeInfo[1] + 1 + i] = "#";
            }
        }

        else if (placeInfo[2] == 1) {
            for (int i = 0; i < sizeOfShip; ++i) {
                placingBoard[placeInfo[0] + 1 + i][placeInfo[1] + 1] = "#";
            }
        }
    }


    public static void battleShipsPlace(String[][] gameBoard, int[] boardSizes, int[] numOfShips, int[] sizeOfShips) {
        System.out.println("Your current game board:");
        printBoardGame(gameBoard);
        String[][] placingBoard = createGameBoard(boardSizes);

        for(int i = 0; i < numOfShips.length; ++i) {
            for(int j = 0; j < numOfShips[i]; ++j) {
                System.out.println("Enter location and orientation for battleship of size " + sizeOfShips[i]);
                String[] placeInfoStr = (scanner.nextLine()).split(", ");
                int[] placeInfoInt = stringToIntArr(placeInfoStr);


                while ((checkOrientation(placeInfoInt)) || (checkTile(placingBoard, placeInfoInt)) ||
                        (checkExcession(placingBoard, placeInfoInt, sizeOfShips[i])) ||
                        (checkOverlap(placingBoard, placeInfoInt, sizeOfShips[i])) ||
                        (checkAdjacent(placingBoard, placeInfoInt, sizeOfShips[i]))) {
                    placeInfoStr = (scanner.nextLine()).split(", ");
                    placeInfoInt = stringToIntArr(placeInfoStr);
                }
                placeInBoard(placingBoard, placeInfoInt, sizeOfShips[i]);
                System.out.println("Your current game board:");
                printBoardGame(placingBoard);

                    /*for (int index = 0; index < sizeOfShips[i]; ++index) {
                        gameBoard[placeInfoInt[0] + 1][placeInfoInt[1] + 1 + index] = "#";
                        System.out.println("Your current game board:");
                    }
                    printBoardGame(gameBoard);*/

                //System.out.println("Pupip");
            }
        }
    }

    public static void battleshipGame() {
        // TODO: Add your code here (and add more methods).
        int[] boardSizes = getBoardSizes();
        String[][] gameBoard = createGameBoard(boardSizes);
        //printBoardGame(gameBoard);
        int[][] battleShipSizes = getBattleshipSizes();
        int[] numOfShips = battleShipSizes[0];
        int[] sizeOfShips = battleShipSizes[1];
        battleShipsPlace(gameBoard, boardSizes, numOfShips, sizeOfShips);



    }


    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Total of " + numberOfGames + " games.");

        for (int i = 1; i <= numberOfGames; i++) {
            scanner.nextLine();
            int seed = scanner.nextInt();
            rnd = new Random(seed);
            scanner.nextLine();
            System.out.println("Game number " + i + " starts.");
            battleshipGame();
            System.out.println("Game number " + i + " is over.");
            System.out.println("------------------------------------------------------------");
        }
        System.out.println("All games are over.");
    }
}



