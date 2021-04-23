package Model;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Infotext extends JFrame {

    public Infotext() {
        initComponents();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Infotext();
            }
        });
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    Font font = new Font("Dialog", Font.ITALIC, 20);
    String s = "LIMBO";

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(font);

        FontMetrics fm = g.getFontMetrics(font);

        int width = fm.stringWidth(s);

        Dimension d = getSize();

        //center String/text
        int cx = (d.width - width) / 2;
        int cy = (d.height - fm.getHeight()) / 2 + fm.getAscent();

        g2.drawString(s, cx, cy);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 100);
    }
}
