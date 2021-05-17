package View;

import Model.Game;
import Model.LevelDialog;

import javax.swing.*;
import java.awt.*;

public class Window {
    private static JFrame frame;

    public Window(int width, int height, String title, Game game){
        frame = new JFrame(title);
        // Setting the size of the window
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setLayout(new CardLayout());

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


        /*
        Implementation with all dialogues!! 23 auskommentieren
         */

        //LevelDialog levelDialog = new LevelDialog();
        //StoryDialog dialog = new StoryDialog();
        //frame.getContentPane().add(dialog);
        //frame.getContentPane().add(levelDialog);
        //frame.getContentPane().add(game);


        //frame.remove(dialog);
        //frame.revalidate();
        //frame.repaint();
    }

    public static JFrame getFrame() {
        return frame;
    }
}
