package Model;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * This class shows the story on a label within a JPanel. After the story is finished
 * and the player is informed about the game it will right after display the main menu.
 */

public class StoryDialog extends JPanel {
    private String text;
    private JLabel label;
    int charIndex = 0;

    public StoryDialog() {

        text = "Hello\nYou\n";
        setLayout(new GridBagLayout());
        label = new JLabel();
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        add(label);

        startWriting(text);
    }

    //Method to start the writing
    public void startWriting(String t) {
        Timer timer = new Timer(400, e -> {
            String labelText = label.getText();
            labelText += t.charAt(charIndex);
            label.setText(labelText);
            if (t.charAt(charIndex) == '\n') {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                label.setText("");
            }
            charIndex++;
            if (charIndex >= t.length()) {
                ((Timer) e.getSource()).stop();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                //Panel being removed afterwards so the main menu is displayed
                View.Window.getFrame().getContentPane().remove(StoryDialog.this);
                View.Window.getFrame().revalidate();
                View.Window.getFrame().repaint();
            }

        });
        timer.start();
    }
}
