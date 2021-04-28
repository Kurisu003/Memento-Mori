package View;

import Model.Game;
import Model.GameState;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;

public class Window {

    public Window(int width, int height, String title, Game game){
        JFrame frame = new JFrame(title);
        // Setting the size of the window
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        // we add the game class to the frame

        frame.add(game);

        // you cannot resize the window
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // frame is in middle of the window
        frame.setLocationRelativeTo(null);

        // lets us see the window
        frame.setVisible(true);
        frame.toFront();
        frame.requestFocus();
    }
}
