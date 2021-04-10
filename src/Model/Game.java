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
    private Handler1 handler;
    private BufferedImage level = null;
    private BufferedImage floor=null;
    private Model.Camera camera;

    public Game(){
        new View.Window(960,540,"Memento Mori",this);
        start();
        handler = new Handler1();
        camera = new Camera(0,0);
        this.addKeyListener(new KeyInput(handler));
//      this.addMouseListener(new Controller.MouseInput(handler,camera));

        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("../Background.png");
        floor= loader.loadImage("../Tile5.png");
        loadlevel(level);
        //handler.addObject(new Box(100,100,ID.Block));


    }

    private void start(){
        isRunning=true;
        thread=new Thread(this); //this because we call the run methode
        thread.start();
    }

    private void stop(){
        isRunning=false;
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

        for(int i=0;i<handler.objects.size();i++){
            GameObject temp= handler.objects.get(i);
            if(temp.getId()== ID.Dante){
                camera.tick(handler.objects.get(i));
            }

        }

        handler.tick();
    }

    public void render(){

        BufferStrategy bs= this.getBufferStrategy();
        if(bs==null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g=bs.getDrawGraphics();
        Graphics2D g2d=(Graphics2D)g;

        g.setColor(Color.red);
        g.fillRect(0,0,1000,563);

        g2d.translate(-camera.getX(),-camera.getY());

        for(int xx=0;xx<30*72;xx+=64){
            for(int yy = 0; yy < 30 * 72; yy+=64){
                g.drawImage(floor,xx,yy,null);
            }
        }

        handler.render(g);

        g2d.translate(camera.getX(),camera.getY());

        g.dispose();
        bs.show();



    }

    private void loadlevel(BufferedImage image){
        int w=image.getWidth();
        int h=image.getHeight();

        for(int xx=0;xx<w;xx++){
            for(int yy=0;yy<h;yy++){
                int pixel=image.getRGB(xx,yy);

                int red=(pixel>>16) &0xff;
                int green=(pixel>>8) &0xff;
                int blue=(pixel) &0xff;

                if(red==255)
                    handler.addObject(new Box(xx*32,yy*32, ID.Block));
                if(blue==255)
                    handler.addObject(new Dante(xx*32,yy*32, ID.Dante,handler));
                if (green==255){
                    handler.addObject(new Enemy(xx*32,yy*32, ID.Enemy,handler));
                }

            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
