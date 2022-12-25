package org.cis120.twentyfortyeight;

import java.util.Arrays;
import java.util.Random;

public class Board {

    // uses a 2d array to represent the state of the board because a 2d array has a grid structure,
    // like the structure of 2048
    private int[][] board;

    // initializes empty 2d array to represent board
    // adds two numbers (2 or 4) to board randomly
    public Board() {
        board = new int[4][4];
        addNextNumberToBoard(generateNextNumber());
        addNextNumberToBoard(generateNextNumber());
    }

    // initializes 2d array to represent board --> used for testing
    public Board(final int[][] board) {
        this.board = board;
    }

    // takes in an enum Direction that represents the direction of the user input
    // (i.e. UP, DOWN, LEFT, RIGHT).
    // if game has not ended (i.e. winner/loser declared), create a copy of the old board
    // (lastBoard), pushes lastBoard to the list, and shifts the board in the direction
    // specified. a new element is only added if shifting the elements in the direction will
    // change the display of the board and if the board has enough space.
    public Board generateNextBoard(final Direction direction) {
        if (!checkLoser() && !checkWinner()) {
            boolean addElement = true;
            switch (direction) {
                case UP:
                    if (checkShiftSame(Direction.UP)) {
                        addElement = false;
                    }
                    shiftUp(board);
                    break;
                case DOWN:
                    if (checkShiftSame(Direction.DOWN)) {
                        addElement = false;
                    }
                    shiftDown(board);
                    break;
                case LEFT:
                    if (checkShiftSame(Direction.LEFT)) {
                        addElement = false;
                    }
                    shiftLeft(board);
                    break;
                case RIGHT:
                    if (checkShiftSame(Direction.RIGHT)) {
                        addElement = false;
                    }
                    shiftRight(board);
                    break;
                default: break;
            }
            if (!allFull() && addElement) {
                addNextNumberToBoard(generateNextNumber());
            }
        }
        return this;
    }

    // returns the value at the coordinates
    public int getCell(final int i, final int j) {
        return board[i][j];
    }

    // traverses through the 2d array to see if an element is equal to 2048. returns true if so.
    public boolean checkWinner() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    // returns false if loser wins (i.e. in the case that the board is all full but 2048 is present)
    // returns true if all the coordinates are filled, and the size of the board cannot be reduced.
    // false otherwise.
    public boolean checkLoser() {
        if (checkWinner()) {
            return false;
        }
        return (allFull() && checkShiftSame(Direction.UP) && checkShiftSame(Direction.DOWN)
                && checkShiftSame(Direction.LEFT) && checkShiftSame(Direction.RIGHT));
    }

    // returns board
    public int[][] getBoard() {
        return board;
    }

    // creates a new board checkBoard that is a copy of board. using checkBoard, the method shifts
    // checkBoard in the direction specified, and then determines checkBoard and board's equality.
    // returns true if same, false otherwise.
    public boolean checkShiftSame(final Direction direction) {
        int length = board.length;
        int[][] checkBoard = new int[length][length];
        for (int i = 0; i < board.length; i++) {
            checkBoard[i] = board[i].clone();
        }
        switch (direction) {
            case UP:
                shiftUp(checkBoard);
                break;
            case DOWN:
                shiftDown(checkBoard);
                break;
            case LEFT:
                shiftLeft(checkBoard);
                break;
            case RIGHT:
                shiftRight(checkBoard);
                break;
            default:
                break;
        }
        for (int i = 0; i < board.length; i++) {
            if (!Arrays.equals(board[i], checkBoard[i])) {
                return false;
            }
        }
        return true;
    }

    // takes in a 2d array (board) and uses a nested for loop to separate the board into
    // 1d arrays for each of the columns.
    // calls shift on each of the columns, and then replaces the column with the shifted array
    private void shiftUp(int[][] board) {
        for (int j = 0; j < board[0].length; j++) {
            int[] array = new int[board.length];
            for (int i = 0; i < board.length; i++) {
                array[i] = board[i][j];
            }
            shift(array);
            for (int i = 0; i < board.length; i++) {
                board[i][j] = array[i];
            }
        }
    }

    // same as above, except each column is reversed, then shifted, then reversed.
    // ex: array = (top) [2, 4, 0, 16] (bottom)
    //     reversed = (top) [16, 0, 4, 2] (bottom)
    //     shifted = (top) [16, 4, 2, 0] (bottom)
    //     reversed & shifted = (top) [0, 2, 4, 16] (bottom)
    private void shiftDown(int[][] board) {
        for (int j = 0; j < board[0].length; j++) {
            int[] array = new int[board.length];
            for (int i = 0; i < board.length; i++) {
                array[i] = board[i][j];
            }
            array = reverseArray(shift(reverseArray(array)));
            for (int i = 0; i < board.length; i++) {
                board[i][j] = array[i];
            }
        }
    }

    // takes in a 2d array and uses a for loop to separate each row of the board
    // calls shift on each of the row, and then replaces the row with the shifted row.
    private void shiftLeft(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            int[] array = board[i];
            shift(array);
            board[i] = array;
        }
    }

    // same as above, except each row is reversed, then shifted, then reversed.
    private void shiftRight(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            int[] array = board[i];
            array = reverseArray(shift(reverseArray(array)));
            board[i] = array;
        }
    }

    // while the game has not stopped, generates random variables from 0 to 3 for x and y.
    // exits while loop when the position at board[x][y] is not filled and board[x][y]
    // is set with nextNumber
    private void addNextNumberToBoard(final int nextNumber) {
        final Random random = new Random();
        while (!checkWinner() && !checkLoser()) {
            final int x = random.nextInt(4);
            final int y = random.nextInt(4);
            if (board[x][y] == 0) {
                board[x][y] = nextNumber;
                return;
            }
        }
    }

    // traverses through array, shifting values to the front (towards index = 0).
    // top represents index closest to the front that has not been finalized with a value
    // prev represents the value of the closest non-zero element to the left (if the element has
    // not been finalized/paired (if possible)/position determined), 0 if current element begins
    // the pair.
    // for loop uses prev and top to pair elements (if possible) and shift elements to the front.
    private int[] shift(int[] array) {
        int top = 0;
        int prev = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                if (prev == 0) {
                    prev = array[i];
                } else {
                    if (array[i] == prev) {
                        array[top] = array[i] * 2;
                        prev = 0;
                    } else {
                        array[top] = prev;
                        prev = array[i];
                    }
                    top++;
                }
                array[i] = 0;
            }
        }
        array[top] = prev;
        return array;
    }

    // creates a new array that contains the elements of array, reversed.
    private int[] reverseArray(final int[] array) {
        int[] reversedArray = new int[array.length];
        int index = array.length - 1;
        for (int i = 0; i < array.length; i++) {
            reversedArray[index] = array[i];
            index--;
        }
        return reversedArray;
    }

    // uses a Random object to generate a number from 0 to 99.
    // returns 4 if the number generated is greater than 75, 2 otherwise
    private int generateNextNumber() {
        final Random random = new Random();
        return random.nextInt(99) > 75 ? 4 : 2;
    }

    // traverses through the board and counts up the number of elements that are not 0.
    // returns true if all elements are non-zero.
    private boolean allFull() {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0) {
                    count++;
                }
            }
        }
        return (count == 16);
    }

}