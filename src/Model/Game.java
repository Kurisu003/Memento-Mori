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

    private static ArrayList<BufferedImage> wallSprites = new ArrayList<>();
    
    private static String folder;

    private final Model.Camera camera;

    public Game(){
        new View.Window(1100,611,"Memento Mori",this);
        start();
        handler = new Handler1();
        camera = new Camera(3264,1152);
        this.addKeyListener(new KeyInput(handler));
//      this.addMouseListener(new Controller.MouseInput(handler,camera));
        folder="Anger";
        BufferedImageLoader loader = new BufferedImageLoader();
//        floor = loader.loadImage("../Anger/AngerBackground.png");
        floor = loader.loadImage("../" + folder + "/Background.png");
        loadsprites();


        handler.addObject(new Dante(3500, 1800, ID.Dante, handler, camera));
        handler.addObject(new Box(3*64*17+128,3*64*9+128, ID.Portal,loader.loadImage("../Limbo/BLC.png")));
        camera.setX(3264);
        camera.setY(1728);

        render();
        LoadLevel level = new LoadLevel(handler, wallSprites, floor, camera, this.getBufferStrategy().getDrawGraphics());
//        handler.addObject(new SmartEnemy(100,100,ID.Enemy,handler));
//        handler.addObject(new ShotEnemy(150,150,ID.Enemy,handler,floor));
    }

    private static void loadsprites(){

        wallSprites.clear();

        BufferedImageLoader loader = new BufferedImageLoader();

        wallSprites.add(loader.loadImage("../" + folder + "/BLC.png"));
        wallSprites.add(loader.loadImage("../" + folder + "/BMW.png"));
        wallSprites.add(loader.loadImage("../" + folder + "/BRC.png"));
        wallSprites.add(loader.loadImage("../" + folder + "/LMW.png"));
        wallSprites.add(loader.loadImage("../" + folder + "/RMW.png"));
        wallSprites.add(loader.loadImage("../" + folder + "/TLC.png"));
        wallSprites.add(loader.loadImage("../" + folder + "/TMW.png"));
        wallSprites.add(loader.loadImage("../" + folder + "/TRC.png"));

        wallSprites.add(loader.loadImage("../" + folder + "/DoorB.png"));
        wallSprites.add(loader.loadImage("../" + folder + "/DoorL.png"));
        wallSprites.add(loader.loadImage("../" + folder + "/DoorR.png"));
        wallSprites.add(loader.loadImage("../" + folder + "/DoorT.png"));


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




//        for(GameObject temp : handler.objects){
//            if(temp.getId() == ID.Door){
//                g.setColor(Color.green);
//                g.fillRect(temp.getX() - 32, temp.getY(), 10,10);
//                g.fillRect(temp.getX() + 64, temp.getY(), 10,10);
//            }
//            else if(temp.getId() == ID.Dante){
//                g.setColor(Color.blue);
//                g.fillRect(temp.getX(), temp.getY(), 10,10);
//            }
//        }

        g2d.translate(camera.getX(),camera.getY());

        g.dispose();
        bs.show();

    }
    
    public static void changeLevel(int levelCounter){

        switch (levelCounter) {
            case 1 -> folder = "Violence";
        }
        loadsprites();
        
    }

    public static void main(String[] args) {
        new Game();
    }
}
