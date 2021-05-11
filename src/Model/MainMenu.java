package Model;

import Controller.Handler1;
import Controller.MouseInput;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class MainMenu extends MouseAdapter {

    private ArrayList<BufferedImage> background;
    private transient BufferedImage title;

    private transient BufferedImage mainMenu;
    private transient BufferedImage startNewGame;
    private transient BufferedImage continueGame;
    private transient BufferedImage settings;
    private transient BufferedImage saveIcon;

    private transient BufferedImage soundBarEmpty;
    private transient BufferedImage soundBarFull;
    private transient BufferedImage musicVolume;
    private transient BufferedImage gameVolume;

    private transient BufferedImage backspace;
    private transient BufferedImage plus;
    private transient BufferedImage minus;

    private static int desiredCameraX;
    private static int desiredCameraY;

    private int frameCounter;
    private int animationCounter;

    public MainMenu(){
        Camera.getInstance().setX(1088);
        Camera.getInstance().setY(0);

        desiredCameraX = (int) Camera.getInstance().getX();
        desiredCameraY = (int) Camera.getInstance().getY();

        BufferedImageLoader loader = new BufferedImageLoader();
        background = new ArrayList<>();
        background.add(loader.loadImage("../MainMenuAssets/BackgroundFrames/BackgroundFrames (1).png"));
        background.add(loader.loadImage("../MainMenuAssets/BackgroundFrames/BackgroundFrames (2).png"));
        background.add(loader.loadImage("../MainMenuAssets/BackgroundFrames/BackgroundFrames (3).png"));
        background.add(loader.loadImage("../MainMenuAssets/BackgroundFrames/BackgroundFrames (4).png"));
        background.add(loader.loadImage("../MainMenuAssets/BackgroundFrames/BackgroundFrames (5).png"));
        background.add(loader.loadImage("../MainMenuAssets/BackgroundFrames/BackgroundFrames (6).png"));
        background.add(loader.loadImage("../MainMenuAssets/BackgroundFrames/BackgroundFrames (7).png"));
        background.add(loader.loadImage("../MainMenuAssets/BackgroundFrames/BackgroundFrames (8).png"));

        title = loader.loadImage("../MainMenuAssets/Title.png");
        startNewGame = loader.loadImage("../MainMenuAssets/StartNewGame.png");
        continueGame = loader.loadImage("../MainMenuAssets/ContinueGame.png");
        settings = loader.loadImage("../MainMenuAssets/Settings.png");

        saveIcon = loader.loadImage("../MainMenuAssets/SaveIcons.png");

        musicVolume = loader.loadImage("../MainMenuAssets/MusicVolume.png");
        gameVolume = loader.loadImage("../MainMenuAssets/GameVolume.png");
        soundBarEmpty = loader.loadImage("../MainMenuAssets/GameSoundbarEmpty.png");
        soundBarFull = loader.loadImage("../MainMenuAssets/GameSoundbarFiller.png");

        backspace = loader.loadImage("../MainMenuAssets/Backspace.png");
        plus = loader.loadImage("../MainMenuAssets/Plus.png");
        minus = loader.loadImage("../MainMenuAssets/Minus.png");

    }

    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + Camera.getInstance().getX());
        int my = (int) (e.getY() + Camera.getInstance().getY());

        if(mx > 1098 && mx < 1598) {
            if (my > 300 && my < 350) {
                desiredCameraX += 1088;
            } else if (my > 375 && my < 425) {
                desiredCameraX = 0;
            } else if (my > 450 && my < 500) {
                desiredCameraY = 576;
            }
        }
        if((mx - Camera.getInstance().getX() >= 20 && mx - Camera.getInstance().getX() <= 273) &&
            (my - Camera.getInstance().getY() >= 20 && my - Camera.getInstance().getY() <= 70)){
            desiredCameraX = 1088;
            desiredCameraY = 0;
        }

        // To check for Music Volume
        System.out.println();
        if(mx >= 1538 && mx <= 1588 && my >= 743 && my <= 793) {
            Music.setMusicVolume((float) (Music.getMusicVolume() + Math.log(Math.abs(Music.getMusicVolume())) * 2.5));
            Music.setSimpleMusicVolume(Music.getSimpleMusicVolume() + 1);
        }
        if(mx >= 1418 && mx <= 1468 && my >= 743 && my <= 793) {
            Music.setMusicVolume((float) (Music.getMusicVolume() - Math.log(Math.abs(Music.getMusicVolume())) * 2.5));
            Music.setSimpleMusicVolume(Music.getSimpleMusicVolume() - 1);
        }

        // To check for Sound volume
        if(mx >= 1538 && mx <= 1588 && my >= 943 && my <= 993) {
            Music.setSoundVolume((float) (Music.getSoundVolume() + Math.log(Math.abs(Music.getSoundVolume())) * 2.5));
            Music.setSimpleGameVolume(Music.getSimpleGameVolume() + 1);
        }
        if(mx >= 1418 && mx <= 1468 && my >= 943 && my <= 993) {
            Music.setSoundVolume((float) (Music.getSoundVolume() - Math.log(Math.abs(Music.getSoundVolume())) * 2.5));
            Music.setSimpleGameVolume(Music.getSimpleGameVolume() - 1);
        }
        // NEW GAME
        // To check for click on save icons
        if(mx >= 2257 && mx <= 2457 && my >= 153 && my <= 353){
            Game.setState(GameState.Game);

            try {
                FileOutputStream out = new FileOutputStream("out.ser");
                ObjectOutputStream out1= new ObjectOutputStream(out);
                for (int i =0;i<Handler1.getInstance().objects.size(); i++) {
                    out1.writeObject(Handler1.getInstance().objects.get(i));
                }
                out1.flush();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

        if(mx >= 2618 && mx <= 2818 && my >= 153 && my <= 353) {
            Game.setState(GameState.Game);

            try {
                FileOutputStream out = new FileOutputStream("out.ser");
                ObjectOutputStream out1 = new ObjectOutputStream(out);
                for (int i = 0; i < Handler1.getInstance().objects.size(); i++) {
                    out1.writeObject(Handler1.getInstance().objects.get(i));
                }
                out1.flush();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        if(mx >= 2981 && mx <= 3181 && my >= 153 && my <= 353) {
            Game.setState(GameState.Game);

            try {
                FileOutputStream out = new FileOutputStream("out.ser");
                ObjectOutputStream out1 = new ObjectOutputStream(out);
                for (int i = 0; i < Handler1.getInstance().objects.size(); i++) {
                    out1.writeObject(Handler1.getInstance().objects.get(i));
                }
                out1.flush();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        // CONTINUE GAME
        // To check for click on save icons
        if(mx >= 81 && mx <= 281 && my >= 153 && my <= 353) {
            Game.setState(GameState.Game);

            try {
                FileInputStream out = new FileInputStream("out.ser");
                ObjectInputStream out1 = new ObjectInputStream(out);

                Handler1.getInstance().objects.clear();
                for (int i = 0; i < Handler1.getInstance().objects.size(); i++) {
                    Object d1 = out1.readObject();
                    if (d1 instanceof Dante) {
                        //Handler1.getInstance().objects.add(new Dante(((Dante) d1).x,((Dante)d1).y, ID.Dante,Handler1.getInstance(),Camera.getInstance,Game.getInstance().getG());
                    } else if (d1 instanceof Box){
                        Handler1.getInstance().objects.add((Box)d1);
                    }else if (d1 instanceof Enemy){
                        Handler1.getInstance().objects.add((Enemy)d1);
                    }else if (d1 instanceof SmartEnemy){
                        Handler1.getInstance().objects.add((SmartEnemy)d1);
                    }else if (d1 instanceof ShotEnemy){
                        Handler1.getInstance().objects.add((ShotEnemy)d1);
                    }else if (d1 instanceof Enemy){
                        Handler1.getInstance().objects.add((Enemy)d1);
                    }else if (d1 instanceof Miniboss){
                        Handler1.getInstance().objects.add((Miniboss)d1);
                    }else if (d1 instanceof Door){
                        Handler1.getInstance().objects.add((Door)d1);
                    }
                }

                for (int i = 0; i <Handler1.getInstance().objects.size(); i++){
                    System.out.println(Handler1.getInstance().objects.get(i).id);
                }
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }

        }

        if(mx >= 442 && mx <= 642 && my >= 153 && my <= 353)
            Game.setState(GameState.Game);
        if(mx >= 805 && mx <= 1005 && my >= 153 && my <= 353)
            Game.setState(GameState.Game);

    }

    public void calculations(){
        if(Game.getState().equals(GameState.MainMenu)) {
            if (desiredCameraX < Camera.getInstance().getX())
                Camera.getInstance().setX(Camera.getInstance().getX() - 64);
            else if (desiredCameraX > Camera.getInstance().getX())
                Camera.getInstance().setX(Camera.getInstance().getX() + 64);
            else if (desiredCameraY > Camera.getInstance().getY())
                Camera.getInstance().setY(Camera.getInstance().getY() + 64);
            else if (desiredCameraY < Camera.getInstance().getY())
                Camera.getInstance().setY(Camera.getInstance().getY() - 64);

            frameCounter = (frameCounter + 1) % 20;
            if (frameCounter % 10 == 0) {
                animationCounter = (animationCounter + 1) % 8;
            }
        }

    }

    public void render(Graphics g){

        Graphics2D g2d=(Graphics2D)g;
        g2d.translate(-Camera.getInstance().getX(), -Camera.getInstance().getY());

        g.drawImage(background.get(animationCounter), 0, -150, null);

        g.drawImage(title, 100 + 1088, 10, null);
        g.drawImage(startNewGame, 30 + 1088, 300, null);
        g.drawImage(continueGame, 30 + 1088, 375, null);
        g.drawImage(settings, 30 + 1088, 450, null);

        // Save Icons for new Game
        g.drawImage(saveIcon, 2176, 0, null);
        // Save Icons for continue Game
        g.drawImage(saveIcon, 0, 0, null);

        g.drawImage(musicVolume, 30 + 1088, 743, null);
        g.drawImage(gameVolume, 30 + 1088, 937, null);

        g.drawImage(backspace,  2196, 20, null);
        g.drawImage(backspace,  20, 20, null);
        g.drawImage(backspace,  1108, 596, null);

        // To render full Soundbars
        for(int i = 0; i < Music.getSimpleMusicVolume() * 10; i += 10)
            g.drawImage(soundBarFull, 550 + 1088 + ( i / 10 ) * 50, 743, null);

        for(int i = 0; i < Music.getSimpleGameVolume() * 10; i += 10)
            g.drawImage(soundBarFull, 550 + 1088 + ( i / 10 ) * 50, 937, null);

        // To render empty soundbars
        for(int i = 0; i < 5; i++){
            g.drawImage(soundBarEmpty, 550 + 1088 + i * 50, 743, null);
            g.drawImage(soundBarEmpty, 550 + 1088 + i * 50, 937, null);
        }

        // To render plus and minus signs to change volume
        g.drawImage(plus, 450+1088, 743, null);
        g.drawImage(plus, 450+1088, 937, null);
        g.drawImage(minus, 330+1088, 743, null);
        g.drawImage(minus, 330+1088, 937, null);

        g2d.translate(Camera.getInstance().getX(), Camera.getInstance().getY());
    }

    public static void setCamera(int x, int y){
        desiredCameraX = x;
        desiredCameraY = y;
    }
}
