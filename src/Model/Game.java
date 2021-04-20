package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Controller.*;

public class Game extends Canvas implements Runnable {

    private Thread thread;
    private boolean isRunning = false;
    private final Handler1 handler;
    private final BufferedImage floor;

    private final Model.Camera camera;

    public Game(){
        new View.Window(1100,611,"Memento Mori",this);
        start();
        handler = new Handler1();
        camera = new Camera(3264,1152);
        this.addKeyListener(new KeyInput(handler));
//      this.addMouseListener(new Controller.MouseInput(handler,camera));

        BufferedImageLoader loader = new BufferedImageLoader();
//        floor = loader.loadImage("../Anger/AngerBackground.png");
        floor = loader.loadImage("../Anger/Background.png");

        ArrayList<BufferedImage> wallSprites = new ArrayList<>();
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
        LoadLevel level = new LoadLevel(handler, wallSprites, floor, camera, this.getBufferStrategy().getDrawGraphics());
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

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
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
                g.drawImage(floor, i * 1088, j * 576, null);
            }
        }

        handler.render(g);

        for(GameObject temp : handler.objects){
            if(temp.getId() == ID.Door){
                g.setColor(Color.green);
                g.fillRect(temp.getX() - 32, temp.getY(), 10,10);
                g.fillRect(temp.getX() + 64, temp.getY(), 10,10);
            }
            else if(temp.getId() == ID.Dante){
                g.setColor(Color.blue);
                g.fillRect(temp.getX(), temp.getY(), 10,10);
            }
        }

        g2d.translate(camera.getX(),camera.getY());

        g.dispose();
        bs.show();

    }

    public static void main(String[] args) {
        new Game();
    }
}
