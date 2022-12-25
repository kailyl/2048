package org.cis120.twentyfortyeight;

import java.io.*;
import java.util.LinkedList;

public class TwentyFortyEight {

    // LinkedList for pushing and popping elements from the list.
    // First element of LinkedList represents most recent board.
    private LinkedList<Board> moves;

    public static void main(String[] args) {
        TwentyFortyEight b = new TwentyFortyEight();
        b.shiftBoard(Direction.UP);
        b.shiftBoard(Direction.LEFT);
        b.undo();
    }

    // main constructor that initializes a board with 2 elements (2 or 4)
    // and initializes this.moves
    public TwentyFortyEight() {
        moves = new LinkedList<>();
        moves.add(new Board());
    }

    // following 2 constructors used mainly for testing implementation
    // for boards with more than 2 elements
    public TwentyFortyEight(final int[][] newBoard) {
        this.moves = new LinkedList<>();
        moves.add(new Board(newBoard));
    }

    public TwentyFortyEight(final int[][] newBoard, final LinkedList<Board> boards) {
        this.moves = boards;
        moves.add(new Board(newBoard));
    }

    // if moves is not empty, pops off the first element and sets board to the 2d array
    // that was removed. If there are no previous moves, no change.
    public void undo() {
        if (moves.size() > 1) {
            moves.pop();
        }
    }

    // creates a new board by creating an empty 2d array and placing 2 elements (either 2 or 4)
    // initializes a LinkedList for moves
    public void reset() {
        moves = new LinkedList<>();
        moves.add(new Board());
    }

    // creates a copy of the current board
    // uses shifts elements of the copy to create the next board
    public void shiftBoard(final Direction dir) {
        int[][] currentBoard = moves.peek().getBoard();
        int[][] copy = new int[currentBoard.length][currentBoard.length];
        for (int i = 0; i < currentBoard.length; i++) {
            copy[i] = currentBoard[i].clone();
        }
        Board copiedBoard = new Board(copy);
        if (!copiedBoard.checkShiftSame(dir)) {
            moves.push(copiedBoard.generateNextBoard(dir));
        }
    }

    // creates a PrintStream that prints out a text file
    // separates elements in txt file by ,
    public void generateOutput(final String pathName) throws FileNotFoundException {
        try {
            PrintStream output = new PrintStream(pathName);
            for (Board state : moves) {
                for (int i = 0; i < state.getBoard().length; i++) {
                    String out = "";
                    for (int j = 0; j < state.getBoard().length; j++) {
                        out += state.getCell(i, j) + ",";
                    }
                    output.println(out);
                }
            }
            output.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            throw new FileNotFoundException();
        }
    }

    // pushes a copy of the current board to moves collection
    // reads board using a BufferedReader
    // traverses through each row of text in output.txt
    // traverses through each element in the board to add element
    // traverses through moves collection to find previous board and pops off boards that have
    // indices before the previous board in the LinkedList
    public void readInput(final String fileName) throws IOException {
        String line;
        int row = 0;
        int length = 4;
        int[][] currentBoard = new int[length][length];
        moves.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null && row < length) {
                String[] val = line.trim().split(",");
                for (int col = 0; col < length; col++) {
                    currentBoard[row][col] = Integer.parseInt(val[col]);
                }
                row++;
                if (row == length) {
                    row = 0;
                    int[][] lastBoard = new int[length][length];
                    for (int i = 0; i < currentBoard.length; i++) {
                        lastBoard[i] = currentBoard[i].clone();
                    }
                    moves.add(new Board(lastBoard));
                    currentBoard = new int[length][length];
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new FileNotFoundException();
        } catch (IOException e) {
            System.out.println("IO error from closing reader");
            throw new IOException();
        } catch (NullPointerException e) {
            System.out.println("File is empty");
//            throw new NullPointerException();
        }
    }

    // return the list of moves/boards
    public LinkedList<Board> getMoves() {
        return moves;
    }
}