package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;

import Controller.*;

public class Game extends Canvas implements Runnable {

    private Thread thread;
    private boolean isRunning = false;
    private static BufferedImage floor;
    private final ArrayList<BufferedImage> enemySprites = new ArrayList<>();

    int updates = 0;

    public static GameState getState() {
        return state;
    }

    public static void setState(GameState state) {
        if(state.equals(GameState.Game)) {
            Camera.getInstance().setX(3264);
            Camera.getInstance().setY(1728);

        }
        Game.state = state;
    }

    private static GameState state = GameState.MainMenu;

    private static final ArrayList<BufferedImage> wallSprites = new ArrayList<>();
    
    private static String folder;

    public Graphics getG() {
        return g;
    }

    private Graphics g;

    private final MainMenu mainMenu;

    private static BufferedImageLoader loader;

    public static boolean showHitbox = false;

    private static Game instance = null;

    public static Game getInstance(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }

    private Game(){
        mainMenu = new MainMenu();
        new View.Window(1100,611,"Memento Mori",this);
        start();
        Camera.getInstance().setX(1088);
        this.addKeyListener(new KeyInput(Handler1.getInstance()));
        this.addMouseListener(mainMenu);
        folder = Levels.Limbo.name();
        loader = new BufferedImageLoader();

        new Thread(new Music("res/music/bg_music.wav", ID.BG_music)).start();
        render();
        loadsprites(5);

        Handler1.getInstance().addObject(new Dante(3500, 1800, ID.Dante));
//        handler.addObject(new Dialog(200, 50, ID.Dialog, folder));

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
    }
        public ArrayList<BufferedImage> getEnemySprites(){
            return enemySprites;
    }

    public static void addPortal(int x, int y){
        Handler1.getInstance().addObject(new Box(x,y, ID.Portal,loader.loadImage("../Levels/Limbo/BLC.png")));
    }

    private static void loadsprites(int amountRoomsGenerated){

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
    }

    public static void removePortal() {
        for(int i = 0; i < Handler1.getInstance().objects.size(); i++){
            if(Handler1.getInstance().objects.get(i).getId() == ID.Portal) {
                Handler1.getInstance().objects.remove(Handler1.getInstance().objects.get(i));
                System.out.println();
            }
        }
    }

    private void start(){
        isRunning = true;
        thread = new Thread(this); //this because we call the run methode
        thread.start();
    }

    private void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run(){

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
                tick();
                mainMenu.calculations();
                updates++;
                delta--;
            }
            render();

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.println(updates);
                updates = 0;
            }
        }
        stop();
    }

    public void tick(){
        for(ListIterator<GameObject> iterator = Handler1.getInstance().objects.listIterator(); iterator.hasNext();){
            GameObject temp = iterator.next();
            if(temp.getId() == ID.Dante){
                Camera.getInstance().tick(temp);
            }
        }
        boolean enemiesLeft = false;
        for(ListIterator<GameObject> iterator = Handler1.getInstance().objects.listIterator(); iterator.hasNext();){
            GameObject temp = iterator.next();
            if(temp.getId() == ID.Enemy || temp.getId() == ID.SmartEnemy || temp.getId() == ID.ShotEnemy){
                enemiesLeft = true;
            }
        }
        if(enemiesLeft)
            changeDoors(1);
        else
            changeDoors(0);



        Handler1.getInstance().tick();
    }

    private void changeDoors(int state){
        for(ListIterator<GameObject> iterator = Handler1.getInstance().objects.listIterator(); iterator.hasNext();){
            GameObject temp = iterator.next();
            if(temp.getId() == ID.Door && state == 1){
                ((Door) temp).lockDoor();
            }
            else if(temp.getId() == ID.Door && state == 0){
                ((Door) temp).unlockDoor();

            }

        }
    }

    public void render(){

        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null){
            this.createBufferStrategy(3);
            return;
        }
        g=bs.getDrawGraphics();
        Graphics2D g2d=(Graphics2D)g;


        if(state==GameState.Game) {
            g2d.translate(-Camera.getInstance().getX(), -Camera.getInstance().getY());

            // Repeats sprites over entire level
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    g.drawImage(floor, i * 1088, j * 576, null);
                }
            }

            Handler1.getInstance().render(g);

            g2d.translate(Camera.getInstance().getX(), Camera.getInstance().getY());
        } else if(state==GameState.MainMenu){
            mainMenu.render(g);
        }

        g.dispose();
        bs.show();
    }
    
    public void changeLevel(String level, int amountRoomsGenerated){
        folder = level;
        loadsprites(amountRoomsGenerated);
    }

    public static void main(String[] args) {

        Game.getInstance();
    }
}
