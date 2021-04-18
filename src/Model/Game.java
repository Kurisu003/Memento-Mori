package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

import Controller.*;

public class Game extends Canvas implements Runnable {

    private Thread thread;
    private boolean isRunning=false;
    private final Handler1 handler;
    private BufferedImage level = null;
    private BufferedImage floor = null;
    private BufferedImage doorHorizontally = null;
    private BufferedImage doorVertically = null;

    private ArrayList<BufferedImage> wallSprites = new ArrayList<>();

    private Model.Camera camera;

    public Game(){
        new View.Window(64 * 17 + 12,64 * 9 + 35,"Memento Mori",this);
        start();
        handler = new Handler1();
        camera = new Camera(64 * 17 * 3,64 * 9 * 2);
        this.addKeyListener(new KeyInput(handler));
//      this.addMouseListener(new Controller.MouseInput(handler,camera));

        BufferedImageLoader loader = new BufferedImageLoader();
//        floor = loader.loadImage("../Anger/AngerBackground.png");
        floor = loader.loadImage("../Anger/Background.png");

        wallSprites.add(loader.loadImage("../Anger/BLC.png"));
        wallSprites.add(loader.loadImage("../Anger/BMW.png"));
        wallSprites.add(loader.loadImage("../Anger/BRC.png"));
        wallSprites.add(loader.loadImage("../Anger/LMW.png"));
        wallSprites.add(loader.loadImage("../Anger/RMW.png"));
        wallSprites.add(loader.loadImage("../Anger/TLC.png"));
        wallSprites.add(loader.loadImage("../Anger/TMW.png"));
        wallSprites.add(loader.loadImage("../Anger/TRC.png"));

        wallSprites.add(loader.loadImage("../Anger/DoorB.png"));
        wallSprites.add(loader.loadImage("../Anger/DoorL.png"));
        wallSprites.add(loader.loadImage("../Anger/DoorR.png"));
        wallSprites.add(loader.loadImage("../Anger/DoorT.png"));

        render();
        loadlevel(floor);
//        handler.addObject(new SmartEnemy(100,100,ID.Enemy,handler));
//        handler.addObject(new ShotEnemy(150,150,ID.Enemy,handler,floor));
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
        int frames = 0;
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                //updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                //updates = 0;
            }
        }
        stop();
    }

    public void tick(){
        for(GameObject temp : handler.objects){
            if(temp.getId() == ID.Dante){
                camera.tick(temp);
            }
        }
        handler.tick();
    }

    public void render(){

        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g=bs.getDrawGraphics();
        Graphics2D g2d=(Graphics2D)g;

//        g.setColor(Color.black);
//        g.fillRect(0,0,9999,9999);

        g2d.translate(-camera.getX(),-camera.getY());

//        for(int xx=0;xx<30*72;xx+=64){
//            for(int yy = 0; yy < 30 * 72; yy+=64){
//                g.drawImage(floor,0,0,null);
//            }
//        }

        // Repeats sprites over entire level
        for(int i = 1; i <= 6; i++){
            for(int j = 1; j <= 6; j++){
                g.drawImage(floor, i * 960, j * 448, null);
            }
        }

        handler.render(g);

        g2d.translate(camera.getX(),camera.getY());

        g.dispose();
        bs.show();

    }

    private void loadlevel(BufferedImage floor){
        LoadLevel level = new LoadLevel(handler, wallSprites, floor, camera, this.getBufferStrategy().getDrawGraphics());
    }

    public static void main(String[] args) {
        new Game();
    }
}
