package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenu {

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
    private BufferedImage gameSoundBar;
    private BufferedImage musicSoundBar;

    public MainMenu(){
    }

    public void init(){
        this.camera = new Camera(1088,576);

        BufferedImageLoader loader = new BufferedImageLoader();
        background = loader.loadImage("../Screen.png");

        title = loader.loadImage("../Title.png");
        startNewGame = loader.loadImage("../StartNewGame.png");
        continueGame = loader.loadImage("../ContinueGame.png");
        settings = loader.loadImage("../Settings.png");
        saveIcon = loader.loadImage("../SaveIcon.png");
        musicVolume = loader.loadImage("../MusicVolume.png");
        gameVolume = loader.loadImage("../GameVolume.png");
        musicSoundBar = loader.loadImage("../GameVolume.png");
        gameSoundBar = loader.loadImage("../GameVolume.png");
    }

    public void render(Graphics g){

        Graphics2D g2d=(Graphics2D)g;
        g2d.translate(-camera.getX(), -camera.getY());
        g.drawImage(background, 0, 0, null);

        g.drawImage(title, 90 + 1088, 10, null);
        g.drawImage(startNewGame, 10 + 1088, 300, null);
        g.drawImage(continueGame, 10 + 1088, 375, null);
        g.drawImage(settings, 10 + 1088, 450, null);
        //g.drawImage(saveIcon, 10 + 1088, 750, null);
        g.drawImage(musicVolume, 10 + 1088, 743, null);
        g.drawImage(gameVolume, 10 + 1088, 937, null);

        g2d.translate(camera.getX(), camera.getY());
    }
}
