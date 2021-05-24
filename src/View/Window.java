package View;

import Model.Game;
import Model.LevelDialog;

import javax.swing.*;
import java.awt.*;

/**
 * This class creates the frame where everything gets displayed on.
 */
public class Window {
    private static JFrame frame;

    /**
     * This is the constructor of the class Window to create an object of this class
     * @param width the width of the frame
     * @param height the height of the frame
     * @param title the title of the frame
     * @param game the instance of the game which should be displayed
     */
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

        //To set program icon
        BufferedImageLoader loader = new BufferedImageLoader();
        frame.setIconImage(loader.loadImage("../Character/IdleWithWeapon.png"));
    }

    /**
     * Getter method of the current frame
     * @return the frame
     */
    public static JFrame getFrame() {
        return frame;
    }
}
