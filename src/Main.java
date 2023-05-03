import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static Random rnd;

    /**
     * Gets the board sizes as STR, split using X to know the num or rows and cols
     *
     * @return an int array - first place rows and the second one is col
     */

    public static int[] getBoardSizes() {
        System.out.println("Enter the board size");
        scanner = new Scanner(System.in);  //Delete before submitting
        String board_sizes = scanner.nextLine();
        String[] dimensions = board_sizes.split("X");
        int rows = Integer.parseInt(dimensions[0]);
        int columns = Integer.parseInt(dimensions[1]);
        return new int[]{rows, columns};
    }


    /**
     * The function gets the number and the sizes of the ships as an STR input, split using space
     * and afterward split using X to 2 arrays one os the amount of the ships and the second
     * the sizes of the ship
     *
     * @return a 2 dim array with int
     */
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
        return new int[][]{num, sizes};
    }


    /**
     * function that calculates the number of spaces needed
     *
     * @param spaces and int var - will be the largest number of rows.
     *               we will take this number and convert to a str and then will check the len of the str
     * @return number of how much spaces we need
     */
    public static int getSpaces(int spaces) {
        String numSpacesStr = Integer.toString(spaces);
        return numSpacesStr.length();
    }

    /**
     * function that only creates the initial game board
     * we split the function into 2 : the first row is only the number of cols. in the place [0][0]
     * it will print the amount of spaces as needed with the help of get spaces function.
     * the second part is all the other rows. in the place [i][0] will be printed the according
     * spaces using a loop that checks the len of the number (converting to str) and print
     * accordingly the num of spaces and the num of rows using counter.In all the other places
     * will be printed the sign "-".
     *
     * @param boardSizes an int array that tells the functions how much rows and cols will be
     * @return the updated game board as an 2 dim array
     */
    public static String[][] createGameBoard(int[] boardSizes) {
        int numRows = boardSizes[0], numCols = boardSizes[1];
        int countCols = 0, countRows = 0;
        int numSpaceForTable = getSpaces(numRows) + 1;
        String space = " ";
        String resultSpace = "";
        String[][] gameBoard = new String[numRows + 1][numCols + 1];
        int countToRemove = 1;
        if (numSpaceForTable - 1 != getSpaces(numRows - 1))
            countToRemove = 2;

        // fill the first row with spaces and numbers
        for (int index_row = 0; index_row < numRows + 1; ++index_row) {
            if (index_row == 0) {
                //loop that`s creating the spaces in the first row
                for (int i = 0; i < numSpaceForTable - countToRemove; ++i) {
                    resultSpace += space;
                }
                gameBoard[0][0] = resultSpace;
                resultSpace = "";

                //printing the cols number
                for (int index_cols = 1; index_cols < numCols + 1; ++index_cols) {
                    String CountColsSTR = Integer.toString(countCols);
                    gameBoard[0][index_cols] = (CountColsSTR);
                    ++countCols;
                }
            }
            // filling the rest of the board
            else if (index_row < numRows + 1) {
                String CountRowsSTR = Integer.toString(countRows);
                String spaceForRowSTR = Integer.toString(countRows);
                int numSpaceForRow = numSpaceForTable - spaceForRowSTR.length();
                for (int i = 0; i < numSpaceForRow - countToRemove; ++i)
                    resultSpace += space;
                gameBoard[index_row][0] = resultSpace + CountRowsSTR;
                resultSpace = "";
                for (int index_col = 1; index_col < numCols + 1; index_col++)
                    gameBoard[index_row][index_col] = "–";
                countRows++;
            }
        }
        return gameBoard;
    }

    /**
     * function that prints the current board game of the player
     * the function gets the updated board and prints it on the scree.
     *
     * @param boardGame a STR 2 - dim array that gets the updated board
     */
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

    /**
     * The function takes a sty array and convert it to tn int array
     *
     * @param infoStr gets a str array that comes from the function battleshipPlace
     * @return an int array with the 3 cordinates for placement.
     */
    public static int[] stringToIntArr(String[] infoStr) {
        int[] infoInt = new int[infoStr.length];
        for (int i = 0; i < infoStr.length; ++i)
            infoInt[i] = Integer.parseInt(infoStr[i]);
        return infoInt;
    }

    /**
     * side function to help check the orientation if its ligal - 0 or 1
     *
     * @param placeInfo - the int str with the info of placing the battelship
     * @param id        - who called the function: the player or the computer
     * @return bol statment - true or false
     */
    public static boolean checkOrientation(int[] placeInfo, int id) {
        if ((placeInfo[2] != 0) && (placeInfo[2] != 1)) {
            if (id == 0)
                System.out.println("Illegal orientation, try again!");
            return true;
        }
        return false;
    }

    /**
     * side function to help check the placement of the tile is ligal (inside the board)
     *
     * @param placingBoard - a side board helps us to understand where to place the ship
     * @param placeInfo    - the int str with the info of placing the battelship
     * @param id           - who called the function: the player or the computer
     * @return bol statment - true or false
     */
    public static boolean checkTile(String[][] placingBoard, int[] placeInfo, int id) {
        if ((placeInfo[0] < 0) || (placeInfo[0] >= placingBoard.length - 1) ||
                (placeInfo[1] < 0) || (placeInfo[1] >= placingBoard[0].length - 1)) {
            if (id == 0)
                System.out.println("Illegal tile, try again!");
            return true;
        }
        return false;
    }

    /**
     * side function to help check the if the tile is legal but the placement of the ship according
     * to the orientation and the size of the ship will be legal or exceeding the board border.
     *
     * @param placingBoard - a side board helps us to understand where to place the ship
     * @param placeInfo    - the int str with the info of placing the battelship
     * @param sizeOfShip   - the size of the current ship we are checking
     * @param id           - who called the function: the player or the computer
     * @return bol statment - true or false
     */
    public static boolean checkExcession(String[][] placingBoard, int[] placeInfo, int sizeOfShip, int id) {
        if (placeInfo[2] == 0) {
            if (placeInfo[1] + sizeOfShip > placingBoard[0].length - 1) {
                if (id == 0)
                    System.out.println("Battleship exceeds the boundaries of the board, try again!");
                return true;
            }
        } else if (placeInfo[2] == 1) {
            if (placeInfo[0] + sizeOfShip > placingBoard.length - 1) {
                if (id == 0)
                    System.out.println("Battleship exceeds the boundaries of the board, try again!");
                return true;
            }
        }
        return false;
    }

    /**
     * side function to help check the if the current position the size of ship will overlap another ship
     * that is already placed.
     *
     * @param placingBoard - a side board helps us to understand where to place the ship
     * @param placeInfo    - the int str with the info of placing the battelship
     * @param sizeOfShip   - the size of the current ship we are checking
     * @param id           - who called the function: the player or the computer
     * @return bol statment - true or false
     */
    public static boolean checkOverlap(String[][] placingBoard, int[] placeInfo, int sizeOfShip, int id) {
        if (placeInfo[2] == 0) {
            for (int i = 0; i < sizeOfShip; ++i) {
                if ((placingBoard[placeInfo[0] + 1][placeInfo[1] + 1 + i]).equals("#")) {
                    if (id == 0)
                        System.out.println("Battleship overlaps another battleship, try again!");
                    return true;
                }
            }
        } else if (placeInfo[2] == 1) {
            for (int i = 0; i < sizeOfShip; ++i) {
                if ((placingBoard[placeInfo[0] + 1 + i][placeInfo[1] + 1]).equals("#")) {
                    if (id == 0)
                        System.out.println("Battleship overlaps another battleship, try again!");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * function that checks if from the current placement given the
     * ship will be adjacent to another battleship
     * @param placingBoard - the board that is updated only with the real ships that are already placed
     * @param placeInfo    an int array with the info of placement and cordinates
     * @param sizeOfShip   - the size of current ship
     * @return bol statement - true or false
     */

    /**
     * side function to help check the placement of the ship will be adacent to another ship that is already been
     * placed on the board. the adjacent rules were described in the working manual.
     *
     * @param placingBoard - a side board helps us to understand where to place the ship
     * @param placeInfo    - the int str with the info of placing the battelship
     * @param sizeOfShip   - the size of the current ship we are checking
     * @param id           - who called the function: the player or the computer
     * @return bol statment - true or false
     */
    public static boolean checkAdjacent(String[][] placingBoard, int[] placeInfo, int sizeOfShip, int id) {
        if (placeInfo[2] == 0) {
            for (int i = 0; i < sizeOfShip; ++i) {
                if ((placingBoard[placeInfo[0] + 1][placeInfo[1] + 1 + i]).equals("A")) {
                    if (id == 0)
                        System.out.println("Adjacent battleship detected, try again!");
                    return true;
                }
            }
        } else if (placeInfo[2] == 1) {
            for (int i = 0; i < sizeOfShip; ++i) {
                if ((placingBoard[placeInfo[0] + 1 + i][placeInfo[1] + 1]).equals("A")) {
                    if (id == 0)
                        System.out.println("Adjacent battleship detected, try again!");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * size function that place in side board - every time the placement is correct it will add all
     * around the sign 'A' to help us with the adjacent function.
     *
     * @param placingBoard - a side board helps us to understand where to place the ship
     * @param placeInfo    - the int str with the info of placing the battelship
     * @param sizeOfShip   - the size of the current ship we are checking
     */
    public static void placeA(String[][] placingBoard, int[] placeInfo, int sizeOfShip) {
        int k = 1, p = sizeOfShip;
        if (placeInfo[2] == 1) {
            k = sizeOfShip;
            p = 1;
        }
        for (int i = -1; i <= k; ++i) {
            for (int j = -1; j <= p; ++j) {
                if ((i + placeInfo[0] >= 0) && (i + placeInfo[0] <= placingBoard.length - 2) &&
                        (j + placeInfo[1] >= 0) && (j + placeInfo[1] <= placingBoard[0].length - 2) &&
                        (placingBoard[placeInfo[0] + 1 + i][placeInfo[1] + 1 + j]).equals("–")) {
                    placingBoard[placeInfo[0] + 1 + i][placeInfo[1] + 1 + j] = "A";
                }
            }
        }
    }

    /**
     * function that in the according place from the given coordinates, position and the size of ship,
     * will put in the correct place the '#' sign as representation of ship.
     * the function will be called only if the input was checked before and was find as correct.
     *
     * @param placingBoard - a side board helps us to understand where to place the ship
     * @param placeInfo    - the int str with the info of placing the battelship
     * @param sizeOfShip   - the size of the current ship we are checking
     */
    public static void placeInBoard(String[][] placingBoard, int[] placeInfo, int sizeOfShip) {
        if (placeInfo[2] == 0) {
            for (int i = 0; i < sizeOfShip; ++i) {
                placingBoard[placeInfo[0] + 1][placeInfo[1] + 1 + i] = "#";
            }
        } else if (placeInfo[2] == 1) {
            for (int i = 0; i < sizeOfShip; ++i) {
                placingBoard[placeInfo[0] + 1 + i][placeInfo[1] + 1] = "#";
            }
        }
    }


    /**
     * creates a random vector according to who called the function. the function generates 3 numbers.
     *
     * @param boardSizes int array for size of the board
     * @param id         int number with indicates who called the function. 0 will be the computer needs X,Y cordinate to
     *                   hit its rival. 1 - the computer needs a cordinates to place ship.
     * @return an array with the rand numbers according to who called the function
     */
    public static int[] randomVector(int[] boardSizes, int id) {
        rnd = new Random();// Delete before submitting
        int numRows = boardSizes[0];
        int numCols = boardSizes[1];
        int Xrandom = rnd.nextInt(numRows);
        int Yrandom = rnd.nextInt(numCols);
        int orientationRandom = rnd.nextInt(2);
        //id==0 meaning the computer called the function for 2 coordinates to hit the opponent
        if (id == 0)
            return new int[]{Xrandom, Yrandom};

            // else - meaning the computer called the function for placing the ship in the board
        else
            return new int[]{Xrandom, Yrandom, orientationRandom};
    }

    /**
     * A function that checks all the options from the helper functions we defined.
     * Only if the input is correct, then the function will ask to insert the sign of the battleship '#'
     * in the guessing board and accordingly will also mark around the sign 'A' to know that no other
     * ships are allowed  to be placed there,
     *
     * @param gameBoard   the gameboard of the player
     * @param boardSizes  int array with the board size
     * @param numOfShips  int array with the amount of ships that needs to be placed
     * @param sizeOfShips the sizes of the current ships
     */
    public static void playerBattleShipsPlace(String[][] gameBoard, int[] boardSizes, int[] numOfShips,
                                              int[] sizeOfShips) {
        int id = 0;
        System.out.println("Your current game board:");
        printBoardGame(gameBoard);
        String[][] placingBoard = createGameBoard(boardSizes);

        for (int i = 0; i < numOfShips.length; ++i) {
            for (int j = 0; j < numOfShips[i]; ++j) {
                System.out.println("Enter location and orientation for battleship of size " + sizeOfShips[i]);
                String[] placeInfoStr = (scanner.nextLine()).split(", ");
                int[] placeInfoInt = stringToIntArr(placeInfoStr);


                while ((checkOrientation(placeInfoInt, id)) || (checkTile(placingBoard, placeInfoInt, id)) ||
                        (checkExcession(placingBoard, placeInfoInt, sizeOfShips[i], id)) ||
                        (checkOverlap(placingBoard, placeInfoInt, sizeOfShips[i], id)) ||
                        (checkAdjacent(placingBoard, placeInfoInt, sizeOfShips[i], id))) {
                    placeInfoStr = (scanner.nextLine()).split(", ");
                    placeInfoInt = stringToIntArr(placeInfoStr);
                }
                placeInBoard(placingBoard, placeInfoInt, sizeOfShips[i]);
                for (int row = 0; row < gameBoard.length; ++row) {
                    for (int col = 0; col < gameBoard[0].length; ++col) {
                        gameBoard[row][col] = placingBoard[row][col];
                        if (gameBoard[row][col].equals("A")) {
                            gameBoard[row][col] = "–";
                        }
                    }
                }
                placeA(placingBoard, placeInfoInt, sizeOfShips[i]);
                System.out.println("Your current game board:");
                printBoardGame(gameBoard);


                //System.out.println("Pupip");
            }
        }
    }

    /**
     * A function that checks all the options from the helper functions we defined.
     * Only if the input is correct, then the function will ask to insert the sign of the battleship '#'
     * in the guessing board and accordingly will also mark around the sign 'A' to know that no other
     * ships are allowed  to be placed there,
     *
     * @param computerGameBoard the gameboard of the computer
     * @param boardSizes        int array with the board size
     * @param numOfShips        int array with the amount of ships that needs to be placed
     * @param sizeOfShips       the sizes of the current ships
     */
    public static void computerBattleshipsPlace(String[][] computerGameBoard, int[] boardSizes, int[] numOfShips,
                                                int[] sizeOfShips) {
        int id = 1;
        String[][] placingBoard = createGameBoard(boardSizes);
        for (int i = 0; i < numOfShips.length; ++i) {
            for (int j = 0; j < numOfShips[i]; ++j) {
                int[] placeInfoInt = randomVector(boardSizes, 1);
                while ((checkOrientation(placeInfoInt, id)) || (checkTile(placingBoard, placeInfoInt, id)) ||
                        (checkExcession(placingBoard, placeInfoInt, sizeOfShips[i], id)) ||
                        (checkOverlap(placingBoard, placeInfoInt, sizeOfShips[i], id)) ||
                        (checkAdjacent(placingBoard, placeInfoInt, sizeOfShips[i], id))) {
                    placeInfoInt = randomVector(boardSizes, 1);
                }
                placeInBoard(placingBoard, placeInfoInt, sizeOfShips[i]);
                for (int row = 0; row < computerGameBoard.length; ++row) {
                    for (int col = 0; col < computerGameBoard[0].length; ++col) {
                        computerGameBoard[row][col] = placingBoard[row][col];
                        if (computerGameBoard[row][col].equals("A")) {
                            computerGameBoard[row][col] = "–";
                        }
                    }
                }
                placeA(placingBoard, placeInfoInt, sizeOfShips[i]);
            }
        }
    }

    public static void hitOrMiss(String[][] gameboardOpponent, String[][] gameBoardGguessing, int[] hitCoordinates) {
        if (gameboardOpponent[hitCoordinates[0] + 1][hitCoordinates[1] + 1].equals("#")) {
            gameBoardGguessing[hitCoordinates[0] + 1][hitCoordinates[1] + 1] = "V";
            // to check if its only hit or drown
        } else{
            gameBoardGguessing[hitCoordinates[0] + 1][hitCoordinates[1] + 1] = "X";
            System.out.println("That is a miss!");}

    }

    /**
     * checks if the tile was already been hit
     *
     * @param gameBoardGuessing - the current updated guessing board according to the player
     * @param hitCoordinates    what are the hit hitCoordinates in an inr array.
     * @param ID                - if the player now is the computer or the person. the ID for the person is 0 and
     *                          for the computer is 1.
     * @return bol statement - if the tile was already hit - true, else - false.
     */
    public static boolean checkTileAlreadyHit(String[][] gameBoardGuessing, int[] hitCoordinates, int ID) {
        if (gameBoardGuessing[hitCoordinates[0] + 1][hitCoordinates[1] + 1].equals("X") ||
                gameBoardGuessing[hitCoordinates[0] + 1][hitCoordinates[1] + 1].equals("v")) {
            //ID==0 meaning that the player is trying to hit
            if (ID == 0) {
                System.out.println("Tile already attacked,try again!");
                return true;
            }
        }
        return false;
    }

    public static void attackTurn(String[][] gameboardOpponent, String[][] gameBoardGguessing, int ID, int[] boardSizes) {
        //person turn
        if (ID == 0) {
            System.out.println("Your current guessing board:");
            printBoardGame(gameBoardGguessing);
            System.out.println("Enter a tile to attack");
            String XY_Cordinans = scanner.nextLine();
            String[] XY_CordinansArray = XY_Cordinans.split(",");
            int[] XY = stringToIntArr(XY_CordinansArray);
            System.out.println(XY[0]);
            // checks if the attack valid
            while ((checkTile(gameBoardGguessing, XY, ID))||(checkTileAlreadyHit(gameBoardGguessing, XY, ID)))
            {   XY_Cordinans = scanner.nextLine();
                XY_CordinansArray = XY_Cordinans.split(",");
                XY = stringToIntArr(XY_CordinansArray);
            }
            hitOrMiss (gameboardOpponent,gameBoardGguessing,XY);
            printBoardGame(gameBoardGguessing);

            // computer turn
            // else {
            //int[] XY = randomVector(boardSizes, 0);
            //}
        }
    }








    public static void battleshipGame() {
        //get data
        int[] boardSizes = getBoardSizes();
        int[][] battleShipSizes = getBattleshipSizes();
        int[] numOfShips = battleShipSizes[0];
        int[] sizeOfShips = battleShipSizes[1];
        //create boards
        String[][] gameBoardPlayer = createGameBoard(boardSizes);
        String[][] computerGameBoard = createGameBoard(boardSizes);
        String[][] playerGuessing = createGameBoard(boardSizes);
        String[][] computerGuessing = createGameBoard(boardSizes);

        playerBattleShipsPlace(gameBoardPlayer, boardSizes, numOfShips, sizeOfShips);
        computerBattleshipsPlace(computerGameBoard, boardSizes, numOfShips, sizeOfShips);
        printBoardGame(gameBoardPlayer);
        printBoardGame(computerGameBoard);

        attackTurn(computerGameBoard,playerGuessing,0,boardSizes);







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



