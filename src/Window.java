import javax.swing.*;
import java.awt.*;

public class Window {

    public Window(int width, int height, String title, Game game){
        JFrame frame = new JFrame(title);
        //Setting the size of the window
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.add(game);//we add the game class to the frame
        frame.setResizable(false);//you cannot resize the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);//frame is in middle of the window
        frame.setVisible(true);//let us see the window
    }
}
