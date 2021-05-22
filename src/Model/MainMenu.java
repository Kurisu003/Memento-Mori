package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * Menu which the player sees at first in order to decide whether to start a new game, continue the game or to set
 * the music and sound volume.
 */
public class MainMenu extends MouseAdapter {

    private final ArrayList<BufferedImage> background;
    private final transient BufferedImage title;

    private final transient BufferedImage startNewGame;
    private final transient BufferedImage continueGame;
    private final transient BufferedImage exitGame;
    private final transient BufferedImage settings;
    private final transient BufferedImage saveIcon;

    private final transient BufferedImage soundBarEmpty;
    private final transient BufferedImage soundBarFull;
    private final transient BufferedImage musicVolume;
    private final transient BufferedImage gameVolume;

    private final transient BufferedImage plus;
    private final transient BufferedImage minus;

    private final transient BufferedImage backspaceLeft;
    private final transient BufferedImage backspaceRight;
    private final transient BufferedImage backspaceUp;

    private static int desiredCameraX;
    private static int desiredCameraY;

    private int frameCounter;
    private int animationCounter;

    /**
     * This is the constructor for the MainMenu to set up everything
     */
    public MainMenu(){
        //Set the position of where the MainMenu gets displayed
        Camera.getInstance().setX(1088);
        Camera.getInstance().setY(0);

        //Set the values of the Camera to the attribute desiredCamera
        desiredCameraX = (int) Camera.getInstance().getX();
        desiredCameraY = (int) Camera.getInstance().getY();

        //Creates instance of BufferedImageLoader to load images
        BufferedImageLoader loader = new BufferedImageLoader();
        background = new ArrayList<>();
        //Load all background frames
        for(int i = 1; i < 9; i++){
            background.add(loader.loadImage("../MainMenuAssets/BackgroundFrames/BackgroundFrames ("+ i +").png"));
        }
        background.add(loader.loadImage("../MainMenuAssets/BackgroundFrames/EscMenuBackground.png"));

        title = loader.loadImage("../MainMenuAssets/Title.png");
        startNewGame = loader.loadImage("../MainMenuAssets/StartNewGame.png");
        continueGame = loader.loadImage("../MainMenuAssets/ContinueGame.png");
        exitGame = loader.loadImage("../MainMenuAssets/ExitGame.png");
        settings = loader.loadImage("../MainMenuAssets/Settings.png");

        saveIcon = loader.loadImage("../MainMenuAssets/SaveIcons.png");

        musicVolume = loader.loadImage("../MainMenuAssets/MusicVolume.png");
        gameVolume = loader.loadImage("../MainMenuAssets/GameVolume.png");
        soundBarEmpty = loader.loadImage("../MainMenuAssets/GameSoundbarEmpty.png");
        soundBarFull = loader.loadImage("../MainMenuAssets/GameSoundbarFiller.png");

        plus = loader.loadImage("../MainMenuAssets/Plus.png");
        minus = loader.loadImage("../MainMenuAssets/Minus.png");

        backspaceLeft = loader.loadImage("../MainMenuAssets/BackspaceLeft.png");
        backspaceRight = loader.loadImage("../MainMenuAssets/BackspaceRight.png");
        backspaceUp = loader.loadImage("../MainMenuAssets/BackspaceUp.png");

        Music.getThreadPool().execute(new Music("res/music/RiseUpDeadMan.wav", ID.Menu_music));
    }

    /**
     * This method is called whenever the mouse is pressed and depending on its position it will do some action or not.
     * @param e detects the mouse button which is clicked in order to get information about it
     */
    public void mousePressed(MouseEvent e) {
        //coordinates where the mouse button was clicked
        int mx = (int) (e.getX() + Camera.getInstance().getX());
        int my = (int) (e.getY() + Camera.getInstance().getY());

        //check if one of the "buttons" is clicked
        if(mx > 1098 && mx < 1598) {
            //"Start new game" camera goes right
            if (my > 300 && my < 350)
                desiredCameraX += 1088;
            //"Continue game" camera goes left
            else if (my > 375 && my < 425)
                desiredCameraX = 0;
            //"Settings" camera goes down
            else if (my > 450 && my < 500)
                desiredCameraY = 576;
        }
        else if(mx >= 1618 && mx <= 2118 && my >= 450 && my <= 500){
            System.exit(0);
        }
        //get back to the main menu
        if((mx - Camera.getInstance().getX() >= 20 && mx - Camera.getInstance().getX() <= 273) &&
            (my - Camera.getInstance().getY() >= 20 && my - Camera.getInstance().getY() <= 70)){
            desiredCameraX = 1088;
            desiredCameraY = 0;
        }

        // To check for Music Volume
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
        if(mx >= 2257 && mx <= 2457 && my >= 153 && my <= 353) {

            Dante.setInstance(null);
            Game.setFolder();
            Dante.getInstance();
            Game.getInstance().loadsprites(5);
            Handler1.getInstance().addObject(new InGameDialog(200, 50, ID.Dialog,Game.getFolder()));

            Game.setState(GameState.Game);


            selectAndSetSaveState(1);
        }

        if(mx >= 2618 && mx <= 2818 && my >= 153 && my <= 353){

            Dante.setInstance(null);
            Game.setFolder();
            Dante.getInstance();
            Game.getInstance().loadsprites(5);
            Handler1.getInstance().addObject(new InGameDialog(200, 50, ID.Dialog,Game.getFolder()));

            Game.setState(GameState.Game);

            selectAndSetSaveState(2);
        }

        if(mx >= 2981 && mx <= 3181 && my >= 153 && my <= 353) {

            Dante.setInstance(null);
            Game.setFolder();
            Dante.getInstance();
            Game.getInstance().loadsprites(5);
            Handler1.getInstance().addObject(new InGameDialog(200, 50, ID.Dialog,Game.getFolder()));

            Game.setState(GameState.Game);

            selectAndSetSaveState(3);
        }


        // CONTINUE GAME
        // To check for click on save icons
        if(mx >= 81 && mx <= 281 && my >= 153 && my <= 353) {
            double tempx = 0;
            double tempy = 0;
            BufferedImageLoader loader = new BufferedImageLoader();
            try {

                FileInputStream out = new FileInputStream("out1.ser");
                ObjectInputStream out1 = new ObjectInputStream(out);
                Handler1.getInstance().objects.clear();
                Dante.setInstance(null);
                try{
                for (int i = 0; i < 500; i++) {
                    Object d1 = out1.readObject();
                    if (d1 instanceof Dante) {
                        Dante.getInstance();
                        Dante.getInstance().setX(((Dante)d1).x);
                        Dante.getInstance().setY(((Dante)d1).y);
                        Dante.setCurrentLevel(((Dante)d1).currentLevel);
                        Game.setFolder(((Dante)d1).currentLevel.name());
                    } else if (d1 instanceof Camera) {
                        tempx=((Camera)d1).getTempx();
                        tempy=((Camera)d1).getTempy();
                    }else if (d1 instanceof Box){
                        if(((Box)d1).getId()==ID.Obstacle) {
                            Handler1.getInstance().addObject(new Box(((Box) d1).x, ((Box) d1).y, ID.Obstacle, loader.loadImage(
                                    "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));
                        }
                    } else if (d1 instanceof Enemy) {
                        Handler1.getInstance().objects.add(new Enemy(((Enemy) d1).x,((Enemy)d1).y,ID.Enemy,((Enemy)d1).getHp(),((Enemy)d1).getSpeed()));
                    } else if (d1 instanceof SmartEnemy) {
                        Handler1.getInstance().objects.add(new SmartEnemy(((SmartEnemy) d1).x,((SmartEnemy)d1).y,ID.SmartEnemy,((SmartEnemy)d1).getHp(),((SmartEnemy)d1).getSpeed()));
                    } else if (d1 instanceof ShotEnemy) {
                        Handler1.getInstance().objects.add(new ShotEnemy(((ShotEnemy) d1).x,((ShotEnemy)d1).y,ID.ShotEnemy,((ShotEnemy)d1).getHp(),((ShotEnemy)d1).getSpeed()));
                    } else if (d1 instanceof Miniboss) {
                        System.out.println("Test");
                        Handler1.getInstance().objects.add(new Miniboss(((Miniboss) d1).x,((Miniboss)d1).y,ID.Miniboss,((Miniboss)d1).getHp(),((Miniboss)d1).getSpeed()));
                    }else if(d1 instanceof GenerateLevel){
                        GenerateLevel.getInstance().setLevel(((GenerateLevel)d1).getLevel());
                    }
                }
                }catch (EOFException r){
                }
                } catch (FileNotFoundException | ClassNotFoundException exception) {
                    exception.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            Game.getInstance().loadspritesOfSavedGame(Dante.currentLevel.ordinal());
            Handler1.getInstance().addObject(new InGameDialog(200, 50, ID.Dialog,Game.getFolder()));
            Camera.getInstance().setX(tempx);
            Camera.getInstance().setY(tempy);
            Game.setOnlyState(GameState.Game);

        }

        if(mx >= 442 && mx <= 642 && my >= 153 && my <= 353)
            Game.setState(GameState.Game);
        if(mx >= 805 && mx <= 1005 && my >= 153 && my <= 353)
            Game.setState(GameState.Game);

    }

    /**
     * This method is called by every click on a saving icon to set on which saving slot the player is currently
     * playing
     * @param saveState integer of the actual saving slot
     */
    private void selectAndSetSaveState(int saveState){
        Game.setState(GameState.Game);
        Music.setIsMenu(false);
        Game.getInstance().setSelectedSaveState(saveState);
        try {
            FileOutputStream out = new FileOutputStream("out" + Game.getInstance().getSelectedSaveState() + ".ser");
            out.write(("").getBytes());
            ObjectOutputStream out1= new ObjectOutputStream(out);
            for (int i =0;i<Handler1.getInstance().objects.size(); i++) {
                out1.writeObject(Handler1.getInstance().objects.get(i));
            }
            out1.writeObject(Camera.getInstance());
            out1.writeObject(GenerateLevel.getInstance());
            out1.flush();
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        EscMenu.setTempCamCoordinates(3264, 1728);
    }

    /**
     * Set the position of the main menu so it can be seen.
     */
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

    /**
     * This method renders every graphic object which is displayed at the main menu
     * @param g the graphics to be rendered
     */
    public void render(Graphics g){

        Graphics2D g2d=(Graphics2D)g;
        g2d.translate(-Camera.getInstance().getX(), -Camera.getInstance().getY());

        g.drawImage(background.get(animationCounter), 0, -150, null);

        g.drawImage(title, 100 + 1088, 10, null);
        g.drawImage(startNewGame, 1118, 300, null);
        g.drawImage(continueGame, 1118, 375, null);
        g.drawImage(settings, 1118, 450, null);
        g.drawImage(exitGame, 1618, 450, null);

        // Save Icons for new Game
        g.drawImage(saveIcon, 2176, 0, null);
        // Save Icons for continue Game
        g.drawImage(saveIcon, 0, 0, null);

        g.drawImage(musicVolume, 30 + 1088, 743, null);
        g.drawImage(gameVolume, 30 + 1088, 937, null);

        g.drawImage(backspaceLeft,  2196, 20, null);
        g.drawImage(backspaceRight,  20, 20, null);
        g.drawImage(backspaceUp,  1108, 596, null);

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

    /**
     * Sets the camera coordinates to the desired coordinates.
     * @param x x-coordinate of the desired camera position
     * @param y y-coordinate of the desired camera position
     */
    public static void setCamera(int x, int y){
        desiredCameraX = x;
        desiredCameraY = y;
    }
}
