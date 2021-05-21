package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class creates the menu which is displayed when pressed esc.
 */
public class EscMenu extends MouseAdapter {
    private final BufferedImage background;
    private final BufferedImage shopBackground;
    private final BufferedImage musicVolume;
    private final BufferedImage gameVolume;
    private final BufferedImage saveAndExit;
    private final BufferedImage resumeGame;
    private final BufferedImage shopButton;
    private final BufferedImage soundBarEmpty;
    private final BufferedImage soundBarFull;
    private final BufferedImage minus;
    private final BufferedImage plus;
    private final BufferedImage backButton;

    private final BufferedImage playerSpeedIcon;
    private final BufferedImage bulletSpeedIcon;
    private final BufferedImage bulletRangeIcon;

    private static double tempCamX;
    private static double tempCamY;

    /**
     * Constructor to create a new instance of the class EscMenu
     */
    public EscMenu(){
        BufferedImageLoader loader = new BufferedImageLoader();

        background = loader.loadImage("../MainMenuAssets/BackgroundFrames/EscMenuBackground.png");
        shopBackground = loader.loadImage("../Assets/Shop.png");
        musicVolume = loader.loadImage("../MainMenuAssets/MusicVolume.png");
        gameVolume = loader.loadImage("../MainMenuAssets/GameVolume.png");
        soundBarEmpty = loader.loadImage("../MainMenuAssets/GameSoundbarEmpty.png");
        soundBarFull = loader.loadImage("../MainMenuAssets/GameSoundbarFiller.png");
        saveAndExit = loader.loadImage("../MainMenuAssets/SaveAndExit.png");
        resumeGame = loader.loadImage("../MainMenuAssets/ResumeGame.png");
        shopButton = loader.loadImage("../MainMenuAssets/Shop.png");

        plus = loader.loadImage("../MainMenuAssets/Plus.png");
        minus = loader.loadImage("../MainMenuAssets/Minus.png");
        backButton = loader.loadImage("../MainMenuAssets/BackspaceLeft.png");

        playerSpeedIcon = loader.loadImage("../Assets/PlayerSpeed.png");
        bulletSpeedIcon = loader.loadImage("../Assets/BulletSpeed.png");
        bulletRangeIcon = loader.loadImage("../Assets/BulletRange.png");
    }

    /**
     * This method is called whenever the mouse is pressed and checks the position of the mouse click
     * @param e MouseEvent to get information about the mouse click
     */
    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + Camera.getInstance().getX());
        int my = (int) (e.getY() + Camera.getInstance().getY());

        if(Game.getState().equals(GameState.EscMenu)) {
            // To check for Music Volume
            if (mx >= 1538 && mx <= 1588 && my >= -526 && my <= -476) {
                Music.setMusicVolume((float) (Music.getMusicVolume() + Math.log(Math.abs(Music.getMusicVolume())) * 2.5));
                Music.setSimpleMusicVolume(Music.getSimpleMusicVolume() + 1);
            }
            if (mx >= 1438 && mx <= 1488 && my >= -526 && my <= -476) {
                Music.setMusicVolume((float) (Music.getMusicVolume() - Math.log(Math.abs(Music.getMusicVolume())) * 2.5));
                Music.setSimpleMusicVolume(Music.getSimpleMusicVolume() - 1);
            }

            // To check for Sound volume
            if (mx >= 1538 && mx <= 1588 && my >= -426 && my <= -376) {
                Music.setSoundVolume((float) (Music.getSoundVolume() + Math.log(Math.abs(Music.getSoundVolume())) * 2.5));
                Music.setSimpleGameVolume(Music.getSimpleGameVolume() + 1);
            }
            if (mx >= 1438 && mx <= 1488 && my >= -426 && my <= -376) {
                Music.setSoundVolume((float) (Music.getSoundVolume() - Math.log(Math.abs(Music.getSoundVolume())) * 2.5));
                Music.setSimpleGameVolume(Music.getSimpleGameVolume() - 1);
            }

            // To save and exit to main menu
            if (mx >= 1118 && mx <= 1618 && my >= -326 && my <= -276) {
                Game.setState(GameState.MainMenu);

                Camera.getInstance().setTempx(tempCamX);
                Camera.getInstance().setTempy(tempCamY);

                try {
                    FileOutputStream out = new FileOutputStream("out" + Game.getInstance().getSelectedSaveState() + ".ser");
                    out.write(("").getBytes());
                    ObjectOutputStream out1 = new ObjectOutputStream(out);
                    for (int i = 0; i< Handler1.getInstance().objects.size(); i++) {
                        out1.writeObject(Handler1.getInstance().objects.get(i));
                    }
                    out1.writeObject(Camera.getInstance());
                    out1.writeObject(GenerateLevel.getInstance());
                    out1.flush();
                    out.close();
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

                MainMenu.setCamera(1088, 0);
            }

            // To Resume game
            if (mx >= 1118 && mx <= 1618 && my >= -226 && my <= -176) {
                Game.setState(GameState.Game);
                Camera.getInstance().setX(tempCamX);
                Camera.getInstance().setY(tempCamY);
            }

            // To get to shopButton
            if (mx >= 1118 && mx <= 1618 && my >= -126 && my <= -76) {
                Camera.getInstance().setX(Camera.getInstance().getX() + 1088);
            }

            // To get back to esc menu from shop
            if (mx >= 2168 && mx <= 2411 && my >= -566 && my <= -516){
                Camera.getInstance().setX(Camera.getInstance().getX() - 1088);
            }

        }
        else if(Game.getState().equals(GameState.GameOver)){
            if(mx >= -2000 && my >= -2000){
                System.out.println(mx + "   " + my);
            }
        }
    }

    /**
     * This method renders all the graphics for the EscMenu
     * @param g graphic to be rendered
     */
    public void render(Graphics g) {
        Graphics2D g2d=(Graphics2D)g;
        g2d.translate(-Camera.getInstance().getX(), -Camera.getInstance().getY());

        // To render background and titles
        g.drawImage(background, 1088, -576, null);
        g.drawImage(shopBackground, 1088*2, -576, null);
        g.drawImage(musicVolume, 1118, -526, null);
        g.drawImage(gameVolume, 1118, -426, null);
        g.drawImage(saveAndExit, 1118, -326, null);
        g.drawImage(resumeGame, 1118, -226, null);
        g.drawImage(shopButton, 1118, -126, null);

        // To render plus and minus for volume changes
        g.drawImage(plus, 1538, -526, null);
        g.drawImage(minus, 1438, -526, null);
        g.drawImage(plus, 1538, -426, null);
        g.drawImage(minus, 1438, -426, null);

        // To render full Soundbars
        for(int i = 0; i < Music.getSimpleMusicVolume() * 10; i += 10)
            g.drawImage(soundBarFull, 550 + 1088 + ( i / 10 ) * 50, -526, null);

        for(int i = 0; i < Music.getSimpleGameVolume() * 10; i += 10)
            g.drawImage(soundBarFull, 550 + 1088 + ( i / 10 ) * 50, -426, null);

        // To render empty soundbars
        for(int i = 0; i < 5; i++) {
            g.drawImage(soundBarEmpty, 550 + 1088 + i * 50, -526, null);
            g.drawImage(soundBarEmpty, 550 + 1088 + i * 50, -426, null);
        }

        // To draw back button in shop
        g.drawImage(backButton, 2186, -566, null);

        // To draw icons in shop
        g.drawImage(playerSpeedIcon, 2186, -500, null);
        g.drawImage(bulletSpeedIcon, 2186, -300, null);
        g.drawImage(bulletRangeIcon, 2186, -100, null);

        g2d.translate(Camera.getInstance().getX(), Camera.getInstance().getY());
    }
    /**
     * Sets the temporary camera coordinates
     * @param tempX double for x-coordinate
     * @param tempY double for y-coordinate
     */
    public static void setTempCamCoordinates(double tempX, double tempY){
        tempCamX = (int) tempX;
        tempCamY = (int) tempY;
    }
    /**
     * Returns the temporary camera x-coordinate
     * @return temporary camera x-coordinate
     */
    public static double getTempCamX() {
        return tempCamX;
    }
    /**
     * Returns the temporary camera y-coordinate
     * @return temporary camera y-coordinate
     */
    public static double getTempCamY() {
        return tempCamY;
    }

}
