package org.cis120.twentyfortyeight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// class instantiates a Game/2048 object, which is the model for the game.
// As the user clicks the game board, the model is updated. Whenever the model
// is updated, the game board repaints itself and updates its status JLabel to
// reflect the current state of the model.

@SuppressWarnings("serial")
public class BoardUI extends JPanel {

    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 360;
    public static final int BOARD_HEIGHT = 360;

    final TwentyFortyEight game;

    // initializes game board
    public BoardUI(JLabel statusInit) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);

        game = new TwentyFortyEight();
        status = statusInit;

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    game.shiftBoard(Direction.LEFT);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    game.shiftBoard(Direction.RIGHT);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    game.shiftBoard(Direction.DOWN);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    game.shiftBoard(Direction.UP);
                }
                updateStatus();
                repaint();
            }
        });
    }

    // resets game to initial game
    public void reset() {
        game.reset();
        repaint();
        updateStatus();
        requestFocusInWindow();
    }

    // undo the most recent move
    public void undo() {
        game.undo();
        repaint();
        updateStatus();
        requestFocusInWindow();
    }

    // saves the state of the board
    public void save() throws FileNotFoundException {
        game.generateOutput("output.txt");
        repaint();
        requestFocusInWindow();
    }

    // retrieves the saved state of the board
    public void load() throws IOException {
        game.readInput("output.txt");
        repaint();
        updateStatus();
        requestFocusInWindow();
    }

    // Updates the JLabel to represent if the user has won/lost/neither
    private void updateStatus() {
        if (!game.getMoves().isEmpty()) {
            boolean winner = game.getMoves().peek().checkWinner();
            boolean loser = game.getMoves().peek().checkLoser();
            if (!winner && !loser) {
                status.setText("Play 2048!");
            } else if (winner) {
                status.setText("Player wins!!!");
            } else if (loser) {
                status.setText("Player loses :(");
            }
        } else {
            status.setText("Play 2048!");
        }

    }

    // draws the game board
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        g.drawLine(0, 0, 0, 360);
        g.drawLine(90, 0, 90, 360);
        g.drawLine(180, 0, 180, 360);
        g.drawLine(270, 0, 270, 360);
        g.drawLine(360, 0, 360, 360);

        g.drawLine(0, 0, 360, 0);
        g.drawLine(0, 90, 360, 90);
        g.drawLine(0, 180, 360, 180);
        g.drawLine(0, 270, 360, 270);
        g.drawLine(0, 360, 360, 360);


        // Draws values in 2048
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!game.getMoves().isEmpty()) {
                    int state = game.getMoves().peek().getCell(i, j);
                    if (state != 0) {
                        Blocks block = new Blocks(state);
                        g.setColor(block.getColor());
                        g.fillRect(90 * j, 90 * i, 90, 90);
                        g.setColor(Color.black);
                        g.drawRect(90 * j, 90 * i, 90, 90);
                        g.setFont(block.getFont());
                        g.setColor(Color.white);
                        if (state >= 1024) {
                            g.drawString(Integer.toString(state), 90 * j + 30, 90 * i + 56);
                        } else if (state >= 128) {
                            g.drawString(Integer.toString(state), 90 * j + 26, 90 * i + 56);
                        } else if (state >= 16) {
                            g.drawString(Integer.toString(state), 90 * j + 28, 90 * i + 56);
                        } else {
                            g.drawString(Integer.toString(state), 90 * j + 35, 90 * i + 56);
                        }
                    }
                }
            }
        }
    }

    // returns the size of the board
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
