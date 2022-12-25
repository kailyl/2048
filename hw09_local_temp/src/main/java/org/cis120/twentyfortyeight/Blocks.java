package org.cis120.twentyfortyeight;

import javax.swing.*;
import java.awt.*;

public class Blocks extends JPanel {

    private int number;
    private Font font;
    private Color color;

    // creates a block using the int value
    public Blocks(int value) {
        this.number = value;
        setFont(number);
        setColor(number);
    }

    // returns the color of the block
    public Color getColor() {
        return this.color;
    }

    // returns the font of the block
    public Font getFont() {
        return this.font;
    }


    // sets font based on the value of the block
    private void setFont(final int value) {
        if (value >= 1024) {
            font = new Font("Courier New", 1, 20);
        } else if (value >= 128) {
            font = new Font("Courier New", 1, 25);
        } else if (value >= 16) {
            font = new Font("Courier New", 1, 30);
        } else if (value >= 2) {
            font = new Font("Courier New", 1, 40);
        }
    }

    // sets color based on value of the block
    private void setColor(final int value) {
        switch (value) {
            case 2:
                color = new Color(141, 192, 199);
                break;
            case 4:
                color = new Color(202, 150, 146);
                break;
            case 8:
                color = new Color(240, 205, 151);
                break;
            case 16:
                color = new Color(180, 238, 182);
                break;
            case 32:
                color = new Color(195, 180, 238);
                break;
            case 64:
                color = new Color(225, 238, 180);
                break;
            case 128:
                color = new Color(198, 173, 145);
                break;
            case 256:
                color = new Color(135, 210, 184);
                break;
            case 512:
                color = new Color(234, 185, 155);
                break;
            case 1024:
                color = new Color(215, 127, 137);
                break;
            case 2048:
                color = new Color(156, 183, 232);
                break;
            default:
                break;
        }
    }
}
