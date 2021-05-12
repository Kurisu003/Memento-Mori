package Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Dialog2 extends JPanel {
    ArrayList<String> text = new ArrayList<>();
    JLabel label;
    int charIndex = 0;

    public Dialog2() {
        text.add("Hello");
        System.out.println(text.get(0).length());
        text.add("Welcome");
        System.out.println(text.get(1).length());
        setLayout(new GridBagLayout());
        label = new JLabel();
        add(label);
        for(int i = 0; i < text.size(); i++){
            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String labelText = label.getText();
                    labelText += text.get(i).charAt(charIndex);
                    label.setText(labelText);
                    charIndex++;
                    if (charIndex >= t.length()) {
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            timer.start();
        }
    }
}
