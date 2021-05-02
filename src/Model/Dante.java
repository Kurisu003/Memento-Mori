package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

// To implement:
// recoil player and enemy on contact
// in collision

public class Dante extends GameObject {

//    private BufferedImage playerBodyDown = null;
//    private BufferedImage playerBodyUp = null;
//    private BufferedImage playerBodyLeft = null;
//    private BufferedImage playerBodyRight = null;

    private final BufferedImage playerGunUp;
    private final BufferedImage playerGunDown;
    private final BufferedImage playerGunLeft;
    private final BufferedImage playerGunRight;
    private final BufferedImage playerGunIdle;

    private final ArrayList<BufferedImage> playerBodyUpAnimation = new ArrayList<>();
    private final ArrayList<BufferedImage> playerBodyDownAnimation = new ArrayList<>();
    private final ArrayList<BufferedImage> playerBodyLeftAnimation = new ArrayList<>();
    private final ArrayList<BufferedImage> playerBodyRightAnimation = new ArrayList<>();
    private final BufferedImage playerIdle;

    private final BufferedImage fullHeart;
    private final BufferedImage fullArmor;

    private BufferedImage bulletImage;
    private BufferedImage miniMapFull;
    private BufferedImage miniMapUndiscovered;
    private BufferedImage minimapStartRoom;
    private BufferedImage miniMapPlayerLocation;
    private BufferedImage minibossHealth;

    private int roomXCoordinate;
    private int roomYCoordinate;
    private final int [][] wherePlayerHasBeen = new int[7][7];

    private int timeSinceLastShot = 20;
    private int fireSpeed = 20;
    private int range = 30;
    private int health = 5;
    private int armor = 2;

    private int frameCount = 0;

    private BufferedImage bufferedBodyImage;
    private BufferedImage bufferedGunImage;
    Controller.Handler1 handler;
    private final Camera camera;

    private final Graphics g;

    private Levels currentLevel = Levels.Limbo;

    public Dante(int x, int y, ID id, Controller.Handler1 handler1, Camera camera, Graphics g) {
        super(x, y, id);
        this.handler = handler1;
        this.camera = camera;
        this.g = g;

        roomXCoordinate = 3;
        roomYCoordinate = 3;

        wherePlayerHasBeen[roomXCoordinate][roomYCoordinate] = 1;

        // Different images according to the direction
        // the player is looking in
        BufferedImageLoader loader = new BufferedImageLoader();
//        playerBodyDown = loader.loadImage("../playerDown.png");
//        playerBodyUp = loader.loadImage("../playerUp.png");
//        playerBodyLeft = loader.loadImage("../playerLeft.png");
//        playerBodyRight = loader.loadImage("../playerRight.png");


        miniMapFull = loader.loadImage("../Minimap/FullMinimap.png");
        miniMapPlayerLocation = loader.loadImage("../Minimap/MinimapPlayerLocation.png");
        miniMapUndiscovered = loader.loadImage("../Minimap/MiniMapUndiscovered.png");
        minimapStartRoom = loader.loadImage("../Minimap/StartRoom.png");

        playerGunUp = loader.loadImage("../Guns/M4/M4Up.png");
        playerGunDown = loader.loadImage("../Guns/M4/M4Down.png");
        playerGunLeft = loader.loadImage("../Guns/M4/M4Right.png");
        playerGunRight = loader.loadImage("../Guns/M4/M4Left.png");
        playerGunIdle = loader.loadImage("../Guns/M4/M4Idle.png");

        playerBodyUpAnimation.add(loader.loadImage("../Character/BackAnimation1&3.png"));
        playerBodyUpAnimation.add(loader.loadImage("../Character/BackAnimation2.png"));
        playerBodyUpAnimation.add(loader.loadImage("../Character/BackAnimation1&3.png"));
        playerBodyUpAnimation.add(loader.loadImage("../Character/BackAnimation4.png"));

        playerBodyDownAnimation.add(loader.loadImage("../Character/FrontAnimation1&3.png"));
        playerBodyDownAnimation.add(loader.loadImage("../Character/FrontAnimation2.png"));
        playerBodyDownAnimation.add(loader.loadImage("../Character/FrontAnimation1&3.png"));
        playerBodyDownAnimation.add(loader.loadImage("../Character/FrontAnimation4.png"));

        playerBodyLeftAnimation.add(loader.loadImage("../Character/LeftAnimation1&3.png"));
        playerBodyLeftAnimation.add(loader.loadImage("../Character/LeftAnimation2.png"));
        playerBodyLeftAnimation.add(loader.loadImage("../Character/LeftAnimation1&3.png"));
        playerBodyLeftAnimation.add(loader.loadImage("../Character/LeftAnimation4.png"));

        playerBodyRightAnimation.add(loader.loadImage("../Character/RightAnimation1&3.png"));
        playerBodyRightAnimation.add(loader.loadImage("../Character/RightAnimation2.png"));
        playerBodyRightAnimation.add(loader.loadImage("../Character/RightAnimation1&3.png"));
        playerBodyRightAnimation.add(loader.loadImage("../Character/RightAnimation4.png"));

        playerIdle = loader.loadImage("../Character/IdleWithoutWeapon.png");

//        bulletImage = loader.loadImage("../Tile.png");
        fullHeart = loader.loadImage("../Assets/FullHeart.png");
        fullArmor = loader.loadImage("../Assets/FullShield.png");
        minibossHealth = loader.loadImage("../Assets/redRec.png");
    }

    // to set firespeed of weapon
    public void setFireSpeed(int speed){
        this.fireSpeed = speed;
    }

    // to set range of Weapon
    public void setRange(int range){
        this.range = range;
    }

    public void setBulletImage(BufferedImage bulletImage){
        this.bulletImage = bulletImage;
    }

    // preferably to be fixed with array
    // so it doesnt have to be checked each tick
    public void setBodyImage(int bodyImageCount){
        switch (bodyImageCount) {
            case (0) -> bufferedBodyImage = playerBodyUpAnimation.get(frameCount);
            case (1) -> bufferedBodyImage = playerBodyDownAnimation.get(frameCount);
            case (2) -> bufferedBodyImage = playerBodyRightAnimation.get(frameCount);
            case (3) -> bufferedBodyImage = playerBodyLeftAnimation.get(frameCount);
            case (4) -> bufferedBodyImage = playerIdle;
        }
    }

    private void setGunImage(int gunImageCount) {
        switch (gunImageCount) {
            case (0) -> bufferedGunImage = playerGunUp;
            case (1) -> bufferedGunImage = playerGunDown;
            case (2) -> bufferedGunImage = playerGunLeft;
            case (3) -> bufferedGunImage = playerGunRight;
            case (4) -> bufferedGunImage = playerGunIdle;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x + 7,y,50,64);
    }

    // To do damage to player character
    @Override
    public int doAction(int action) {
        if(armor == 0) health -= action;
        else armor -= action;
        return 0;
    }

    private void spawnBulletOnPress(int shotY, int shotYStart, int shotX, int shotXStart){
        int damage = 100;
        if(shotY != y || shotX != x){
            handler.addObject(new Bullet(shotXStart, shotYStart, ID.Bullet, handler, shotX, shotY, range, damage, bulletImage));
            new Thread(new Music("res/Sounds/Guns/M4/GunSound1.wav", ID.ShootingSound)).start();
        }
    }

    //Checks shooting direction
    private void checkBulletDirection(){
        // Version like the binding of isaac
        if(handler.isShootUp() && !handler.isShootRight() && !handler.isShootDown() && !handler.isShootLeft())
            spawnBulletOnPress(y-132, y-32, x+32, x+30);
        else if(handler.isShootDown() && !handler.isShootRight() && !handler.isShootLeft() && !handler.isShootUp())
            spawnBulletOnPress(y+175, y+75, x+30, x+30);
        else if(handler.isShootLeft() && !handler.isShootRight() && !handler.isShootDown() && !handler.isShootUp())
            spawnBulletOnPress(y+40, y+40, x-130, x);
        else if(handler.isShootRight() && !handler.isShootLeft() && !handler.isShootDown() && !handler.isShootUp())
            spawnBulletOnPress(y+40, y+40, x+164, x+64);
    }

    // Checks for collision
    private void checkCollision(){
        boolean shouldSpawnEnemy = false;
        boolean shouldChangeLevel = false;

        for(Iterator<GameObject> iterator = handler.objects.iterator(); iterator.hasNext();){
            GameObject temp = iterator.next();

            if(temp.getId() == ID.Block && getBounds().intersects(temp.getBounds())){
                x+=velX*-1;
                y+=velY*-1;
            }
            if(temp.getId() == ID.Door && getBounds().intersects(temp.getBounds())){
                if( temp.getX() >  x && (y + 32 > temp.getY() && y + 32 < temp.getY() + 64) &&
                        handler.isRight() && !handler.isLeft()){
                    x += 200;
                    camera.setX(camera.getX() + 1088);
                    roomXCoordinate++;
                }
                else if(temp.getX() <  x && (y + 32 > temp.getY() && y + 32 < temp.getY() + 64) &&
                        handler.isLeft() && !handler.isRight()){
                    x -= 200;
                    camera.setX(camera.getX() - 1088);
                    roomXCoordinate--;
                }
                else if(temp.getY() < y && (x + 32 > temp.getX() && x + 32 < temp.getX() + 64) &&
                        handler.isUp() && !handler.isDown()){
                    y -= 210;
                    camera.setY(camera.getY() - 576);
                    roomYCoordinate--;
                }
                else if(temp.getY() > y && (x + 32 > temp.getX() && x + 32 < temp.getX() + 64) &&
                        handler.isDown() && !handler.isUp()){
                    y += 210;
                    camera.setY(camera.getY() + 576);
                    roomYCoordinate++;
                }
                if(wherePlayerHasBeen[roomXCoordinate][roomYCoordinate] == 0)
                    shouldSpawnEnemy = true;

                wherePlayerHasBeen[roomXCoordinate][roomYCoordinate] = 1;
            }
            if(temp.getId() == ID.Enemy && getBounds().intersects(temp.getBounds())){
                // To do damage to player
                doAction(1);
            }
            if(temp.getId() == ID.Portal && getBounds().intersects(temp.getBounds())){
                shouldChangeLevel = true;
            }
        }

        // Used because object cant be added
        // to list within a loop
        if(shouldSpawnEnemy){
            SpawnEnemiesInRoom.spawnEnemies(roomXCoordinate * 1088, roomYCoordinate * 576, 2, ID.SmartEnemy, handler);
        }
        if(shouldChangeLevel){
            Game.removePortal();
            shouldChangeLevel = false;
            changeToNextLevel();
        }
    }

    private void drawMinimap(Graphics g){
        for(int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                if(i == 3 && j == 3){
                    g.drawImage(minimapStartRoom, (int)camera.getX() + 948,
                            (int)camera.getY() + 59, null);
                }
                else if(wherePlayerHasBeen[i][j] == 1){
                    g.drawImage(miniMapFull, (int)camera.getX() + 831 + i * 45,
                            (int)camera.getY() + j * 25 - 16, null);
                }
                else if(wherePlayerHasBeen[i][j] == 0 && GenerateLevel.getLevel()[j][i] > 0){
                    g.drawImage(miniMapUndiscovered,(int)camera.getX() + 831 + i * 45,
                            (int)camera.getY() + j * 25 - 16, null);
                }
            }
        }
        g.drawImage(miniMapPlayerLocation,  (int)camera.getX() + 768 + (int)camera.getX() / 1088 * 45 + 45,
                                            (int)camera.getY() + (int)camera.getY() / 576 * 25 - 16, null);
    }

    private void changeToNextLevel(){
        currentLevel = currentLevel.next();
        System.out.println(currentLevel);
        Game.changeLevel(currentLevel.name(), Handler1.getInstance(), g);
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                wherePlayerHasBeen[i][j] = 0;
            }
        }
        wherePlayerHasBeen[3][3] = 1;
    }

    // Used to render image of player gun and body
    public void render(Graphics g) {
        g.drawImage(bufferedBodyImage, x, y, null);
        g.drawImage(bufferedGunImage, x - 20, y - 30, null);
        drawMinimap(g);

//        To draw hitboxes
//        Graphics2D g2 = (Graphics2D)g;
//        g2.setColor(Color.green);
//        g2.draw(getBounds());

        // Sets health to a min value of 0
        if (health < 0)
            health = 0;

        for(int i = 0; i < health; i++)
            g.drawImage(fullHeart, (int)camera.getX() + i * 35 + 10, (int)camera.getY() + 10, null);

        for(int i = 0; i < armor; i++)
            g.drawImage(fullArmor, (int)camera.getX() + i * 35 + 10 + health * 35, (int)camera.getY() + 10, null);


    }

    public void tick() {
        x += velX;
        y += velY;

        // checks for collision with objects
        checkCollision();

        // Needed so that shooting is always available when button is pressed
        // and enough time has passed
        timeSinceLastShot++;

        // Reuses timeSinceLastShot as a sort
        // of way to keep way of ticks since it
        // counts up every time. This is needed as we dont
        // want to up animation counter every time as the
        // animations are too fast that way
        int animationSteps = 4;
        if(timeSinceLastShot % 7 == 0){
            frameCount = ++frameCount % animationSteps;
        }

        // Used to keep track of which direction the player is looking in
        // 4 = Default value used for idle animation
        int setBodyImgCounter = 4;
        setGunImage(4);

        if(handler.isUp() && !handler.isDown()){
            if (handler.isRight()||handler.isLeft()) velY=-3;
            else velY = -5;

            setBodyImgCounter = 0;
            setGunImage(0);
        }
        else if(handler.isDown()) velY = 0;

        if(handler.isDown() && !handler.isUp()){
            if (handler.isRight()||handler.isLeft()) velY=3;
            else velY = 5;

            setBodyImgCounter = 1;
            setGunImage(1);
        }
        else if(!handler.isUp()) velY = 0;

        if(handler.isRight() && !handler.isLeft()){
            if (handler.isUp()||handler.isDown()) velX=3;
            else velX = 5;

            setBodyImgCounter = 2;
            setGunImage(2);
        }
        else if(handler.isLeft()) velX = 0;

        if(handler.isLeft() && !handler.isRight()) {
            if (handler.isUp()||handler.isDown()) velX=-3;
            else velX = -5;

            setBodyImgCounter = 3;
            setGunImage(3);
        }
        else if (!handler.isRight()) velX = 0;

        // Needed so character looks int the
        // same direction he shoots
        if(handler.isShootUp() && !handler.isShootRight() && !handler.isShootDown() && !handler.isShootLeft()){
            setBodyImgCounter = 0;
            setGunImage(0);
        }
        if(handler.isShootDown() && !handler.isShootRight() && !handler.isShootUp() && !handler.isShootLeft()){
            setBodyImgCounter = 1;
            setGunImage(1);
        }
        if(handler.isShootRight() && !handler.isShootUp() && !handler.isShootDown() && !handler.isShootLeft()){
            setBodyImgCounter = 2;
            setGunImage(2);
        }
        if(handler.isShootLeft() && !handler.isShootRight() && !handler.isShootDown() && !handler.isShootUp()){
            setBodyImgCounter = 3;
            setGunImage(3);
        }

        // Needed so that walking animation doesnt play while
        // shooting and not moving
        if(!handler.isDown() && !handler.isUp() && !handler.isLeft() && !handler.isRight()){
            frameCount = 0;
        }

        setBodyImage(setBodyImgCounter);

        if(timeSinceLastShot % 1000 == 0){
            timeSinceLastShot = 30;
        }
        if  (timeSinceLastShot > fireSpeed &&
            (handler.isShootUp() || handler.isShootDown() || handler.isShootLeft() || handler.isShootRight())){
            checkBulletDirection();
            timeSinceLastShot = 0;
        }
    }
}