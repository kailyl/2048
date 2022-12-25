package org.cis120.twentyfortyeight;

import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class TwentyFortyEightTest {

    // tests that user does not lose when board is full but reducible (edge case)
    @Test
    public void testCheckLoseFalse() {
        int[][] board = {{16, 8, 4, 2}, {8, 4, 2, 4}, {2, 2, 8, 16}, {4, 16, 32, 2}};
        TwentyFortyEight game = new TwentyFortyEight(board);
        assertFalse(game.getMoves().peek().checkLoser());
    }

    // tests that users loses when board is full and irreducible
    @Test
    public void testCheckLoseTrue() {
        int[][] board = {{16, 8, 4, 2}, {8, 4, 2, 4}, {64, 2, 8, 16}, {4, 16, 32, 2}};
        TwentyFortyEight game = new TwentyFortyEight(board);
        assertTrue(game.getMoves().peek().checkLoser());
    }

    // tests that user wins if board is full, but has 2048 in it (edge case)
    @Test
    public void testCheckWinnerFull() {
        int[][] board = {{2048, 8, 4, 2}, {8, 4, 2, 4}, {64, 2, 8, 16}, {4, 16, 32, 2}};
        TwentyFortyEight game = new TwentyFortyEight(board);
        assertTrue(game.getMoves().peek().checkWinner());
    }

    // tests that user wins if 2048 is in board
    @Test
    public void testCheckWinner() {
        int[][] board = {{2048, 8, 4, 2}, {8, 4, 0, 4}, {0, 2, 8, 16}, {4, 16, 32, 2}};
        TwentyFortyEight game = new TwentyFortyEight(board);
        assertTrue(game.getMoves().peek().checkWinner());
    }

    // tests that items are shifted and combined when shifted up
    @Test
    public void testShiftUp() {
        int[][] board = {{4, 0, 2, 0}, {0, 0, 2, 4}, {0, 2, 8, 16}, {4, 16, 32, 2}};
        int[][] boardTransform = {{8, 2, 4, 4}, {0, 16, 8, 16}, {0, 0, 32, 2}, {0, 0, 0, 0}};
        TwentyFortyEight game = new TwentyFortyEight(board);
        game.shiftBoard(Direction.UP);
        int countEquals = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (game.getMoves().peek().getCell(i, j) == boardTransform[i][j]) {
                    countEquals++;
                }
            }
        }
        assertEquals(15, countEquals);
    }

    // tests that items are shifted and combined when shifted down
    @Test
    public void testShiftDown() {
        int[][] board = {{4, 0, 2, 0}, {0, 0, 2, 4}, {0, 2, 8, 16}, {4, 16, 32, 2}};
        int[][] boardTransform = {{0, 0, 0, 0}, {0, 0, 4, 4}, {0, 2, 8, 16}, {8, 16, 32, 2}};
        TwentyFortyEight game = new TwentyFortyEight(board);
        game.shiftBoard(Direction.DOWN);
        int countEquals = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (game.getMoves().peek().getCell(i, j) == boardTransform[i][j]) {
                    countEquals++;
                }
            }
        }
        assertEquals(15, countEquals);
    }

    // tests that items are shifted and combined when shifted left
    @Test
    public void testShiftLeft() {
        int[][] board = {{4, 0, 2, 0}, {0, 0, 2, 4}, {0, 2, 8, 16}, {4, 16, 32, 2}};
        int[][] boardTransform = {{4, 2, 0, 0}, {2, 4, 0, 0}, {2, 8, 16, 0}, {4, 16, 32, 2}};
        TwentyFortyEight game = new TwentyFortyEight(board);
        game.shiftBoard(Direction.LEFT);
        int countEquals = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (game.getMoves().peek().getCell(i, j) == boardTransform[i][j]) {
                    countEquals++;
                }
            }
        }
        assertEquals(15, countEquals);
    }

    // tests that items are shifted and combined when shifted right
    @Test
    public void testShiftRight() {
        int[][] board = {{2, 0, 2, 2}, {0, 0, 2, 4}, {2, 2, 2, 2}, {4, 16, 32, 2}};
        int[][] boardTransform = {{0, 0, 2, 4}, {0, 0, 2, 4}, {0, 0, 4, 4}, {4, 16, 32, 2}};
        TwentyFortyEight game = new TwentyFortyEight(board);
        game.shiftBoard(Direction.RIGHT);
        int countEquals = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (game.getMoves().peek().getCell(i, j) == boardTransform[i][j]) {
                    countEquals++;
                }
            }
        }
        assertEquals(15, countEquals);
    }

    // tests that items are not shifted when the board does not change when items are shifted
    // (edge case)
    @Test
    public void testShiftNoChange() {
        int[][] board = {{0, 0, 64, 2}, {0, 0, 2, 4}, {0, 0, 16, 2}, {4, 16, 32, 2}};
        int[][] boardTransform = {{0, 0, 64, 2}, {0, 0, 2, 4}, {0, 0, 16, 2}, {4, 16, 32, 2}};
        TwentyFortyEight game = new TwentyFortyEight(board);
        game.shiftBoard(Direction.RIGHT);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(boardTransform[i][j], game.getMoves().peek().getCell(i, j));
            }
        }
    }

    // tests that no element is generated when the board is full (edge case)
    @Test
    public void testGenerateNoElement() {
        int[][] board = {{64, 2, 64, 2}, {8, 16, 2, 4}, {2, 8, 16, 2}, {0, 0, 0, 0}};
        int[][] boardTransform = {{64, 2, 64, 2}, {8, 16, 2, 4}, {2, 8, 16, 2}, {0, 0, 0, 0}};
        TwentyFortyEight game = new TwentyFortyEight(board);
        game.shiftBoard(Direction.UP);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(boardTransform[i][j], game.getMoves().peek().getCell(i, j));
            }
        }
    }

    // tests undo when there are no previous elements (edge case)
    @Test
    public void testUndoNoElements() {
        int[][] board = {{4, 2, 2, 2}, {2, 2, 2, 4}, {2, 2, 8, 16}, {4, 16, 32, 2}};
        int[][] board2 = {{4, 2, 2, 2}, {2, 2, 2, 4}, {2, 2, 8, 16}, {4, 16, 32, 2}};
        LinkedList<Board> emptyList = new LinkedList<>();
        TwentyFortyEight game = new TwentyFortyEight(board, emptyList);
        game.undo();
        Board newBoard = game.getMoves().peek();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(board2[i][j], newBoard.getCell(i, j));
            }
        }
    }

    // tests undo when there is one previous board
    @Test
    public void testUndoOneElement() {
        int[][] board = {{4, 2, 2, 2}, {2, 2, 2, 4}, {2, 2, 8, 16}, {4, 16, 32, 2}};
        int[][] board2 = {{4, 8, 2, 2}, {0, 2, 16, 4}, {4, 4, 8, 16}, {4, 16, 32, 2}};
        LinkedList<Board> list = new LinkedList<>();
        list.push(new Board(board2));
        list.push(new Board(board));
        TwentyFortyEight game = new TwentyFortyEight(board, list);
        game.undo();
        Board newBoard = game.getMoves().peek();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(board2[i][j], newBoard.getCell(i, j));
            }
        }
    }

    // tests undo when there are multiple previous boards
    @Test
    public void testUndoMultipleTimes() {
        int[][] board = {{4, 2, 2, 2}, {2, 2, 2, 4}, {2, 2, 8, 16}, {4, 16, 32, 2}};
        int[][] board2 = {{4, 8, 2, 2}, {2, 2, 16, 4}, {2, 4, 8, 16}, {4, 16, 32, 2}};
        int[][] board3 = {{64, 8, 2, 2}, {2, 2, 16, 4}, {2, 4, 16, 16}, {4, 16, 32, 2}};
        LinkedList<Board> list = new LinkedList<>();
        list.push(new Board(board2));
        list.push(new Board(board3));
        list.push(new Board(board));
        TwentyFortyEight game = new TwentyFortyEight(board, list);
        game.undo();
        game.undo();
        Board newBoard = game.getMoves().peek();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(board2[i][j], newBoard.getCell(i, j));
            }
        }
    }

    // tests reset
    @Test
    public void testReset() {
        int[][] board = {{4, 2, 2, 2}, {2, 2, 2, 4}, {2, 2, 8, 16}, {4, 16, 32, 2}};
        TwentyFortyEight game = new TwentyFortyEight(board);
        game.reset();
        Board newBoard = game.getMoves().peek();
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (newBoard.getCell(i, j) != 0) {
                    count++;
                }
            }
        }
        assertEquals(2, count);
    }

    // tests io
    @Test
    public void testIO() throws IOException {
        int[][] board = {{4, 2, 2, 2}, {2, 2, 2, 4}, {2, 2, 8, 16}, {4, 16, 32, 2}};
        int[][] board2 = {{4, 8, 2, 2}, {2, 2, 16, 4}, {2, 4, 8, 16}, {4, 16, 32, 2}};
        int[][] board3 = {{64, 8, 2, 2}, {2, 2, 16, 4}, {2, 4, 16, 16}, {4, 16, 32, 2}};
        LinkedList<Board> list = new LinkedList<>();
        list.push(new Board(board2));
        list.push(new Board(board3));
        list.push(new Board(board));
        TwentyFortyEight game = new TwentyFortyEight(board, list);
        game.generateOutput("otherOutput.txt");
        game.readInput("otherOutput.txt");
        Board currentBoard = game.getMoves().peek();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                assertEquals(board[i][j], currentBoard.getCell(i, j));
            }
        }
    }

    // tests io with invalid fileName (edge case)
    @Test
    public void testIOInvalid() throws FileNotFoundException {
        int[][] board = {{4, 2, 2, 2}, {2, 2, 2, 4}, {2, 2, 8, 16}, {4, 16, 32, 2}};
        int[][] board2 = {{4, 8, 2, 2}, {2, 2, 16, 4}, {2, 4, 8, 16}, {4, 16, 32, 2}};
        int[][] board3 = {{64, 8, 2, 2}, {2, 2, 16, 4}, {2, 4, 16, 16}, {4, 16, 32, 2}};
        LinkedList<Board> list = new LinkedList<>();
        list.add(new Board(board2));
        list.addFirst(new Board(board3));
        TwentyFortyEight game = new TwentyFortyEight(board, list);
        game.generateOutput("otherOutput.txt");
        assertThrows(FileNotFoundException.class, () -> game.readInput("otherOther.txt"));
    }
}
