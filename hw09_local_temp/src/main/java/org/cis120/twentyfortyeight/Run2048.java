package org.cis120.twentyfortyeight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// This class sets up the top-level frame and widgets for the GUI.
public class Run2048 implements Runnable {
    public void run() {
        // Instructions window
        JFrame window = new JFrame("2048 Instructions");
        window.setSize(300, 300);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.add(new JTextArea("Welcome to 2048! Here are the rules: \n\n" +
                "1) The controls you’ll be using are up, down, left, and right. \n\n" +
                "2) Each time you move, and the elements shift positions, a \n" +
                "    new tile will be added. \n\n" +
                "3) When two tiles with the same value collide, they will \n" +
                "    merge, and form a tile with a value of their sum. \n\n" +
                "4) A player wins the game when the value of a block reaches \n" +
                "    2048. \n\n" +
                "5) A player loses when the board is full and cannot be reduced. \n\n" +
                "6) The undo button undos your previous move. \n\n" +
                "7) The save button saves the current board state. \n\n" +
                "8) The load button loads the previously saved state. \n\n") {
        }, BorderLayout.CENTER);
        window.pack();
        window.setVisible(true);
        window.setLocation(750, 300);
        window.toFront();

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("2048");
        frame.setLocation(360, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.NORTH);
        final JLabel status = new JLabel("Play 2048!");
        status_panel.add(status);

        // Game board
        final BoardUI board = new BoardUI(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.SOUTH);

        // Reset button with action listener
        // Overrides the actionPerformed() method
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);

        // Undo button with action listener
        // Overrides the actionPerformed() method
        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.undo();
            }
        });
        control_panel.add(undo);

        // Save button with action listener
        // Overrides the actionPerformed() method
        final JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    board.save();
                } catch (FileNotFoundException ex) {
                    System.out.println("File not found");
                }
            }
        });
        control_panel.add(save);

        // Load button with action listener
        // Overrides the actionPerformed() method
        final JButton load = new JButton("Load");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    board.load();
                } catch (IOException ex) {
                    System.out.println("IO Exception");
                }
            }
        });
        control_panel.add(load);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}