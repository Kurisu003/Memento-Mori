package Model;

import Controller.Handler1;
import Controller.MouseInput;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MainMenu extends MouseAdapter {

    private Camera camera;
    private BufferedImage background;
    private BufferedImage title;

    private BufferedImage mainMenu;
    private BufferedImage startNewGame;
    private BufferedImage continueGame;
    private BufferedImage settings;
    private BufferedImage saveIcon;
    private BufferedImage musicVolume;
    private BufferedImage gameVolume;
    private BufferedImage soundBarEmpty;
    private BufferedImage soundBarFull;
    private BufferedImage backspace;


    private int desiredCameraX;
    private int desiredCameraY;

    public MainMenu(){}

    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        if(mx > 1098 && mx < 1598) {
            if (my > 300 && my < 350) {
                desiredCameraX += 1088;
            } else if (my > 375 && my < 425) {
                desiredCameraX = 0;
            } else if (my > 450 && my < 500) {
                desiredCameraY = 576;
            }
        }
        if((mx - camera.getX() >= 20 && mx - camera.getX() <= 273) &&
            (my - camera.getY() >= 20 && my - camera.getY() <= 70)){
            desiredCameraX = 1088;
            desiredCameraY = 0;
        }
    }

    public void init(){
        this.camera = new Camera(1088,0);

        desiredCameraX = (int) camera.getX();
        desiredCameraY = (int) camera.getY();

        BufferedImageLoader loader = new BufferedImageLoader();
        background = loader.loadImage("../Screen.png");

        title = loader.loadImage("../Title.png");
        startNewGame = loader.loadImage("../StartNewGame.png");
        continueGame = loader.loadImage("../ContinueGame.png");
        settings = loader.loadImage("../Settings.png");
        saveIcon = loader.loadImage("../SaveIcon.png");
        musicVolume = loader.loadImage("../MusicVolume.png");
        gameVolume = loader.loadImage("../GameVolume.png");
        soundBarEmpty = loader.loadImage("../GameSoundbarEmpty.png");
        soundBarFull = loader.loadImage("../GameSoundbarFiller.png");
        backspace = loader.loadImage("../Backspace.png");

    }

    public void calculations(){
        if(desiredCameraX < camera.getX() && desiredCameraX + 50 < camera.getX())
            camera.setX(camera.getX() - 50);
        else if(desiredCameraX > camera.getX())
            camera.setX(camera.getX() + 50);
        else if(desiredCameraY > camera.getY() && desiredCameraY - 50 > camera.getY())
            camera.setY(camera.getY() + 50);
        else if(desiredCameraY < camera.getY())
            camera.setY(camera.getY() - 50);
    }

    public void render(Graphics g){

        Graphics2D g2d=(Graphics2D)g;
        g2d.translate(-camera.getX(), -camera.getY());
        g.drawImage(background, 0, 0, null);

        g.drawImage(title, 110 + 1088, 10, null);
        g.drawImage(startNewGame, 60 + 1088, 300, null);
        g.drawImage(continueGame, 60 + 1088, 375, null);
        g.drawImage(settings, 60 + 1088, 450, null);

        //g.drawImage(saveIcon, 10 + 1088, 750, null);
        g.drawImage(musicVolume, 60 + 1088, 743, null);
        g.drawImage(gameVolume, 60 + 1088, 937, null);
        g.drawImage(soundBarEmpty, 560 + 1088, 743, null);
        g.drawImage(soundBarEmpty, 560 + 1088, 937, null);


        if(!(camera.getX() >= 1088 - 50 && camera.getX() <= 1088 + 50 && camera.getY() >= -50 && camera.getY() <= 50))
            g.drawImage(backspace,  (int)camera.getX() + 20, (int)camera.getY() + 20, null);



        g2d.translate(camera.getX(), camera.getY());
    }
}
