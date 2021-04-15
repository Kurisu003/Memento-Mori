package Model;

import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

import Controller.*;

public class Game extends Canvas implements Runnable {

    private Thread thread;
    private boolean isRunning=false;
    private final Handler1 handler;
    private BufferedImage level = null;
    private BufferedImage floor = null;
    private BufferedImage wall = null;

    private Model.Camera camera;

    public Game(){
        new View.Window(64 * 17 + 12,64 * 9 + 35,"Memento Mori",this);
        start();
        handler = new Handler1();
        camera = new Camera(64 * 17 * 3,64 * 9 * 2);
        this.addKeyListener(new KeyInput(handler));
//      this.addMouseListener(new Controller.MouseInput(handler,camera));

        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("../Level1.png");
        floor = loader.loadImage("../Hintergrund.png");
        wall = loader.loadImage("../Baum.png");
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
//  Old version
//        for(int i=0;i<handler.objects.size();i++){
//            GameObject temp= handler.objects.get(i);
//            if(temp.getId()== ID.Dante){
//                camera.tick(handler.objects.get(i));
//            }
//        }

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
        for(int i = 1; i <= 5; i++){
            for(int j = 1; j <= 5; j++){
                g.drawImage(floor, i * 64 * 17 - 128, j * 64 * 9 - 128, null);
            }
        }

        handler.render(g);

        g2d.translate(camera.getX(),camera.getY());

        g.dispose();
        bs.show();

    }

    private void loadlevel(BufferedImage floor){

        LoadLevel level = new LoadLevel(handler, wall, floor, camera, this.getBufferStrategy().getDrawGraphics());

//        int w = image.getWidth();
//        int h = image.getHeight();
//
//        for(int xx=0;xx<w;xx++){
//            for(int yy=0;yy<h;yy++){
//                int pixel=image.getRGB(xx,yy);
//
//                int red=(pixel>>16) &0xff;
//                int green=(pixel>>8) &0xff;
//                int blue=(pixel) &0xff;
//
//                if(red==255)
//                    handler.addObject(new Box(xx*64,yy*64, ID.Block, wall));
//                if(blue==255)
//                    handler.addObject(new Dante(xx*32,yy*32, ID.Dante, handler));
//                if (green==255){
//                    handler.addObject(new Enemy(xx*32,yy*32, ID.Enemy, handler));
//                }
//
//            }
//        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
