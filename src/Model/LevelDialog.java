package Model;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * With this class the player gets introduced to the new level with
 * a growing label which indicates the name of the level.
 */
public class LevelDialog extends JPanel {
    private final JLabel label;
    private int size = 0;

    public LevelDialog() {
        String text = "HELLO";
        setLayout(new GridBagLayout());
        label = new JLabel();
        label.setText(text);
        add(label);

        resizeString();
    }

    public void resizeString(){
        Timer timer = new Timer(0, e ->{
            label.setFont(new Font("Serif", Font.PLAIN, size));
            size++;

            //If maximum size of 300 is reached the timer gets stopped
            if(size >=300){
                ((Timer)e.getSource()).stop();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                /*Removal of the current JPane from the frame so the
                 *MainMenu gets displayed
                 */
                View.Window.getFrame().getContentPane().remove(LevelDialog.this);
                View.Window.getFrame().revalidate();
                View.Window.getFrame().repaint();
            }
        });
        timer.start();
    }
}
