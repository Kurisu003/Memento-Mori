package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import Controller.*;

public class Game extends Canvas implements Runnable {

    private Thread thread;
    private boolean isRunning = false;
    private final Handler1 handler;
    private static BufferedImage floor;

    int updates = 0;

    public static GameState getState() {
        return state;
    }

    public static void setState(GameState state) {
        Game.state = state;
    }

    private static GameState state = GameState.MainMenu;

    private static final ArrayList<BufferedImage> wallSprites = new ArrayList<>();
    
    private static String folder;

    private static Model.Camera camera;

    private Graphics g;

    private final MainMenu mainMenu;

    private static BufferedImageLoader loader;

    public Game(){
        mainMenu = new MainMenu();
        new View.Window(1100,611,"Memento Mori",this);
        start();
        handler = Handler1.getInstance();
        camera = new Camera(3264,1728);
//        mainMenu.init();
        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(mainMenu);
        folder = Levels.Limbo.name();
        loader = new BufferedImageLoader();

        new Thread(new Music("res/music/bg_music.wav", ID.BG_music)).start();
        render();

//        floor = loader.loadImage("../Anger/AngerBackground.png");
        loadsprites(handler, this.getBufferStrategy().getDrawGraphics());

        handler.addObject(new Dante(3500, 1800, ID.Dante, handler, camera, g));
//        camera.setX(3264);
//        camera.setY(1728);

//        LoadLevel.loadLevel(handler, wallSprites, this.getBufferStrategy().getDrawGraphics());


//        handler.addObject(new SmartEnemy(100,100,ID.Enemy,handler));
//        handler.addObject(new ShotEnemy(150,150,ID.Enemy,handler,floor));
    }

    public static void addPortal(int x, int y){
        Handler1.getInstance().addObject(new Box(x,y, ID.Portal,loader.loadImage("../Levels/Limbo/BLC.png")));
    }

    private static void loadsprites(Handler1 handler, Graphics g){

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

        LoadLevel.clearAndLoadLevel(wallSprites, g, 5);

    }

    public static void removePortal() {
        for(int i = 0; i < Handler1.getInstance().objects.size(); i++){
            if(Handler1.getInstance().objects.get(i).getId().equals(ID.Portal))
                Handler1.getInstance().objects.remove(Handler1.getInstance().objects.get(i));
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
                System.out.println(updates);
                updates = 0;
            }
        }
        stop();
    }

    public void tick(){
        for(ListIterator<GameObject> iterator = handler.objects.listIterator(); iterator.hasNext();){
            GameObject temp = iterator.next();
            if(temp.getId() == ID.Dante){
                camera.tick(temp);
            }
        }
        boolean enemiesLeft = false;
        for(ListIterator<GameObject> iterator = handler.objects.listIterator(); iterator.hasNext();){
            GameObject temp = iterator.next();
            if(temp.getId() == ID.Enemy || temp.getId() == ID.SmartEnemy || temp.getId() == ID.ShotEnemy){
                enemiesLeft = true;
            }
        }
        if(enemiesLeft)
            changeDoors(1);
        else
            changeDoors(0);



        handler.tick();
    }

    private void changeDoors(int state){
        for(ListIterator<GameObject> iterator = handler.objects.listIterator(); iterator.hasNext();){
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
            g2d.translate(-camera.getX(), -camera.getY());

            // Repeats sprites over entire level
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    g.drawImage(floor, i * 1088, j * 576, null);
                }
            }

            handler.render(g);

            g2d.translate(camera.getX(), camera.getY());
        } else if(state==GameState.MainMenu){
            mainMenu.render(g);
        }

        g.dispose();
        bs.show();

    }
    
    public static void changeLevel(String level, Handler1 handler, Graphics g){
        folder = level;
        loadsprites(handler, g);
    }

    public static Camera getCamera() {
        return camera;
    }

    public static void main(String[] args) {
        new Game();
    }
}
