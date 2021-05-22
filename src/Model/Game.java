package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;


import Controller.*;

/**
 * This is the main class of the whole program. It starts the entire gameplay and calls methods in order to build the
 * game step by step.
 */
public class Game extends Canvas implements Runnable {

    /*
        ALL ATTRIBUTES OF THIS CLASS
     */
    private static Game instance = null;

    private boolean isRunning = false;
    public static boolean showHitbox = false;

    private static BufferedImage floor;
    private final ArrayList<BufferedImage> enemySprites = new ArrayList<>();

    private static final ArrayList<BufferedImage> wallSprites = new ArrayList<>();
    private static BufferedImageLoader loader;
    private Graphics g;

    private int selectedSaveState;

    private final ArrayList<BufferedImage> coinSprites;
    private final ArrayList<BufferedImage> damageObstacleSprites;

    int updates = 0;

    private static GameState state = GameState.MainMenu;

    public static void setFolder() {
        Game.folder = Levels.Heresy.name();
    }

    public static void setFolder(String folder) {
        Game.folder = folder;
    }

    private static String folder;

    private MainMenu mainMenu;
    private EscMenu escMenu;



    /**
     * This is the private Constructor of the Game class so only one instance can be created.
     */
    private Game(){
        mainMenu = new MainMenu();
        escMenu = new EscMenu();
        new View.Window(1100,611,"Memento Mori",this);
        start();
        Camera.getInstance().setX(1088);
        this.addKeyListener(new KeyInput(Handler1.getInstance()));
        this.addMouseListener(mainMenu);
        this.addMouseListener(escMenu);
        folder = Levels.Heresy.name();
        loader = new BufferedImageLoader();
        coinSprites = new ArrayList<>();
        for(int i = 1; i <= 11; i++)
            coinSprites.add(loader.loadImage("../Assets/Coin/Coins (" + i + ").png").getSubimage(7, 46, 26, 28));

        damageObstacleSprites = new ArrayList<>();

        for (int i = 0; i <= 7; i++)
            damageObstacleSprites.add(loader.loadImage("../Assets/DamageObstacles/Fire" + i + ".png"));

        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null){
            this.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        Music.getThreadPool().execute(new Music("res/music/bg_music.wav", ID.BG_music));
        render();

        //how many rooms should be generated per level
        loadsprites(5);
        loadEnemySprites();
        //Dante.getInstance();
//        Handler1.getInstance().addObject(new Dante(3 * 64 * 17 + 8 * 64, 3 * 64 * 9 + 4 * 64, ID.Dante));
        //Handler1.getInstance().addObject(new InGameDialog(200, 50, ID.Dialog, folder));


    }

    private void loadEnemySprites(){
        // Adds hitting animation right
        for(int i = 1; i <= 20; i++)
            enemySprites.add(loader.loadImage("../Enemies/SmartEnemy/Sprite (" + i + ").png").getSubimage(130, 22,80,72));
        // Adds hitting animation left
        for(int i = 1; i <= 20; i++)
            enemySprites.add(loader.loadImage("../Enemies/SmartEnemy/Sprite (" + i + ").png").getSubimage(94, 246 ,80,72));

        // Adds walking animation right
        for(int i = 1; i <= 14; i++)
            enemySprites.add(loader.loadImage("../Enemies/SmartEnemy/Sprite (" + i + ").png").getSubimage(224, 28,44,66));
        // Adds walking animation left
        for(int i = 1; i <= 14; i++)
            enemySprites.add(loader.loadImage("../Enemies/SmartEnemy/Sprite (" + i + ").png").getSubimage(36, 254,44,66));

        enemySprites.add(loader.loadImage("../Enemies/DumbEnemy/SpriteLeft.png"));
        enemySprites.add(loader.loadImage("../Enemies/DumbEnemy/SpriteRight.png"));
    }

    /**
     * This is the main which starts the whole game by calling {@link #getInstance()}.
     * @param args
     */
    public static void main(String[] args) {
        Game.getInstance();
    }

    /**
     * This method creates a single instance of the Game class by calling the private constructor {@link #Game()}.
     * If an instance already exists it will just return the existing one.
     * For this implementation the singleton pattern is used.
     * @return the one and only instance of Game
     */
    public static Game getInstance(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }

    /**
     * To set the game to its state (in-game: Game or menu: EscMenu) in order to display the right window
     * @param state which GameState should be set
     */
    public static void setState(GameState state) {
        if(state.equals(GameState.Game)) {
            Camera.getInstance().setX(3264);
            Camera.getInstance().setY(1728);
        }
        else if(state.equals(GameState.EscMenu)){
            Camera.getInstance().setX(1088);
            Camera.getInstance().setY(-576);
        }
        Game.state = state;
    }

    public static void setOnlyState(GameState state) {

        Game.state = state;
    }

    /**
     * Getter of the game state.
     * @return the current state of the game
     */
    public static GameState getState() {
        return state;
    }

    /**
     * Setter of the selected game state to set if the player chooses the first, second or third saving slot
     * @param selectedSaveState integer to set 1, 2, or 3
     */
    public void setSelectedSaveState(int selectedSaveState) {
        this.selectedSaveState = selectedSaveState;
    }

    /**
     * Getter of the selected save slot
     * @return integer which can be 1, 2 or 3 depending on the currently seleced save state
     */
    public int getSelectedSaveState() {
        return selectedSaveState;
    }

    /**
     * Adds the images which should be loaded for this level to the array.
     * Calls the method to load the sprites for the level.
     * @param amountRoomsGenerated how many rooms should be generated
     */
    public  void loadsprites(int amountRoomsGenerated){

        wallSprites.clear();

        BufferedImageLoader loader = new BufferedImageLoader();

        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(0,192,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(64,192,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(128,192,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(0,128,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(128,128,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(0,0,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(64,0,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(128,0,64,64));


        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(64,128,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(0,64,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(128,64,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(64,64,64,64));


        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(192,192,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(192,64,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(192,128,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(192,0,64,64));

        floor = loader.loadImage("../Levels/" + folder + "/Background.png");

        LoadLevel.clearAndLoadLevel(wallSprites, amountRoomsGenerated);
        GenerateLevel.getInstance().loadObstacles();

    }

    public  void loadspritesOfSavedGame(int amountRoomsGenerated){

        wallSprites.clear();

        BufferedImageLoader loader = new BufferedImageLoader();

        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(0,192,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(64,192,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(128,192,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(0,128,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(128,128,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(0,0,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(64,0,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(128,0,64,64));


        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(64,128,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(0,64,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(128,64,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(64,64,64,64));


        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(192,192,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(192,64,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(192,128,64,64));
        wallSprites.add(loader.loadImage("../Levels/" + folder + "/SpriteSheet.png").getSubimage(192,0,64,64));

        floor = loader.loadImage("../Levels/" + folder + "/Background.png");

        LoadLevel.clearAndLoadLevel(wallSprites, amountRoomsGenerated);
        //GenerateLevel.getInstance().loadObstacles();

    }

    /**
     * This method creates and starts a Game-Thread
     * It sets the attribute isRunning to true to indicate that the thread is running now.
     */
    private void start(){
        isRunning = true;
        Thread thread = new Thread(this); //this because we call the run methode
        thread.start();
    }
    /**
     * This is the code which the thread calls and so it is running continually. Methods are called to get all functions
     * recquired to play the game
     */
    public void run(){
        //Get the focus for this thread
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                long startTime = System.nanoTime();
                if(state.equals(GameState.Game) || state.equals(GameState.MainMenu) || state.equals(GameState.GameOver)) {
                    tick();
                }
                if(state.equals(GameState.MainMenu)) {
                    Music.setIsMenu(true);
                    mainMenu.calculations();
                }
                else
                    Music.setIsMenu(false);
                updates++;
                delta--;


//                long stopTime = System.nanoTime();
//                if((stopTime - startTime) / 10000 > 4000)
//                    System.out.println("Tick: " + (stopTime - startTime) / 10000);
            }
//            long startTime = System.nanoTime();
            render();
//            long stopTime = System.nanoTime();
//            if((stopTime - startTime) / 10000 > 4000)
//                System.out.println("Render: " + (stopTime - startTime) / 10000);

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                if(showHitbox)
                System.out.println(updates);
                updates = 0;
            }
        }
        //stop();
    }

    /*
    private void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

     */

    /**
     * Checks in a certain period of time if changes are made and reacts to them. For example it checks if every
     * enemy is dead so it can unlock the doors.
     */
    public void tick(){

        /*
        try {
            for (ListIterator<GameObject> iterator = Handler1.getInstance().objects.listIterator(); iterator.hasNext(); ) {
                GameObject temp = iterator.next(); //FIXME CONCURRENTMODIFICATIONEXCEPTION
                if (temp.getId() == ID.Dante) {
                    Camera.getInstance().tick(temp);
                }
            }
        }
        catch(ConcurrentModificationException e){
            e.printStackTrace();
        }

         */
        //FIXME concurrentmodificationexception
        boolean enemiesLeft = false;
        for (GameObject temp : Handler1.getInstance().objects) {
            if (temp.getId().equals(ID.Enemy) || temp.getId().equals(ID.SmartEnemy) || temp.getId().equals(ID.ShotEnemy)) {
                enemiesLeft = true;
                break;
            }
        }
        if(enemiesLeft)
            changeDoors(1);
        else
            changeDoors(0);

        Handler1.getInstance().tick();
    }

    /**
     * Set the doors in the level locked or unlocked with this method by giving it a value depending
     * on whether every enemy is dead or not
     * @param state 1 = enemies left -> lock every door, 0 = no enemy left -> unlock every door
     */
    private void changeDoors(int state){
        for(ListIterator<GameObject> iterator = Handler1.getInstance().objects.listIterator(); iterator.hasNext();){
            GameObject temp = iterator.next();
            if(temp.getId() == ID.Door){
                if(state == 1)
                    ((Door) temp).lockDoor();
                else if(state == 0)
                    ((Door) temp).unlockDoor();
            }
        }
    }

    /**
     * This method renders every object-image so it is displayed.
     */
    public void render(){

        //Render frame by frame
        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null){
            this.createBufferStrategy(2);
            return;
        }
        g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D)g;

        if(state.equals(GameState.Game) || state.equals(GameState.GameOver)) {
            g2d.translate(-Camera.getInstance().getX(), -Camera.getInstance().getY());
            Music.setIsMenu(false);
            // Repeats sprites over entire level
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    g.drawImage(floor, i * 1088, j * 576, null);
                }
            }

            //Render all images
            Handler1.getInstance().render(g);

            g2d.translate(Camera.getInstance().getX(), Camera.getInstance().getY());
        } else if(state==GameState.MainMenu){
            mainMenu.render(g);
        } else if(state == GameState.EscMenu){
            escMenu.render(g);
        }
        g.dispose();
        bs.show();

    }

    /**
     * Adds the portal to the game.
     * @param x x-coordinate of the portal's position
     * @param y y-coordinate of the portal's position
     */
    public static void addPortal(int x, int y){
        Handler1.getInstance().addObject(new Box(x,y, ID.Portal,loader.loadImage("../Levels/Limbo/BLC.png"), false));
    }

    /**
     * This method removes the portal from the linked list with all GameObjects
     */
    public static void removePortal() {
        for(int i = 0; i < Handler1.getInstance().objects.size(); i++){
            if(Handler1.getInstance().objects.get(i).getId() == ID.Portal)
                Handler1.getInstance().objects.remove(Handler1.getInstance().objects.get(i));
        }
    }

    /**
     * If the current level is completed the next level has to be loaded.
     * @param level which level should be loaded next
     * @param amountRoomsGenerated how many rooms should be generated
     */
    public void changeLevel(String level, int amountRoomsGenerated){
        folder = level;
        loadsprites(amountRoomsGenerated);
    }

    /**
     * Getter of the graphics
     * @return the current drawn graphics
     */
    public Graphics getG() {
        return g;
    }

    /**
     * Getter of the folder of level
     * @return name of the folder/level
     */
    public static String getFolder() {
        return folder;
    }

    /**
     * Getter of the arraylist which stores the sprites of the enemy
     * @return arraylist of the enemy's images for different positions
     */
    public ArrayList<BufferedImage> getEnemySprites(){
        if(enemySprites.size() == 0)
            loadEnemySprites();
        return enemySprites;
    }

    public ArrayList<BufferedImage> getCoinSprites() {
        return coinSprites;
    }

    public ArrayList<BufferedImage> getDamageObstacleSprites() {
        return damageObstacleSprites;
    }

}
