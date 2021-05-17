package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

// To implement:
// recoil player and enemy on contact
// in collision

public class Dante extends GameObject {

    private transient final ArrayList<BufferedImage> playerAnimations = new ArrayList<>();
    private transient final ArrayList<BufferedImage> playerGun = new ArrayList<>();
    private transient final ArrayList<BufferedImage> minimapSprites = new ArrayList<>();

    private transient final BufferedImage fullHeart;
    private transient final BufferedImage fullArmor;

    private transient BufferedImage bulletImage;
    private transient final BufferedImage minibossHealth;

    private Levels currentLevel = Levels.Limbo;
    private static GameObject instance;

    private int roomXCoordinate;
    private int roomYCoordinate;
    private final int [][] wherePlayerHasBeen = new int[7][7];

    private int timeSinceLastShot = 20;
    private int timeSinceLastDamage = 20;
    private int fireSpeed = 20;
    private int range = 30;
    private static int health = 5;
    private static int armor = 2;

    private int frameCount = 0;
    private boolean portalExists;

    private transient BufferedImage bufferedBodyImage;
    private transient BufferedImage bufferedGunImage;

    public Dante(int x, int y, ID id) {
        super(x, y, id);
        BufferedImageLoader loader = new BufferedImageLoader();

        instance = this;
        portalExists = false;

        roomXCoordinate = 3;
        roomYCoordinate = 3;
        wherePlayerHasBeen[roomXCoordinate][roomYCoordinate] = 1;

        // Gun images
        playerGun.add(loader.loadImage("../Guns/M4/M4Up.png"));
        playerGun.add(loader.loadImage("../Guns/M4/M4Down.png"));
        playerGun.add(loader.loadImage("../Guns/M4/M4Right.png"));
        playerGun.add(loader.loadImage("../Guns/M4/M4Left.png"));
        playerGun.add(loader.loadImage("../Guns/M4/M4Idle.png"));

        // Minimap images
        minimapSprites.add(loader.loadImage("../Minimap/FullMinimap.png"));
        minimapSprites.add(loader.loadImage("../Minimap/MinimapPlayerLocation.png"));
        minimapSprites.add(loader.loadImage("../Minimap/MiniMapUndiscovered.png"));
        minimapSprites.add(loader.loadImage("../Minimap/StartRoom.png"));

        // Player Images
        // 4 Back, Front, Left, Right and 1 Idle
        playerAnimations.add(loader.loadImage("../Character/BackAnimation1&3.png"));
        playerAnimations.add(loader.loadImage("../Character/BackAnimation2.png"));
        playerAnimations.add(loader.loadImage("../Character/BackAnimation1&3.png"));
        playerAnimations.add(loader.loadImage("../Character/BackAnimation4.png"));

        playerAnimations.add(loader.loadImage("../Character/FrontAnimation1&3.png"));
        playerAnimations.add(loader.loadImage("../Character/FrontAnimation2.png"));
        playerAnimations.add(loader.loadImage("../Character/FrontAnimation1&3.png"));
        playerAnimations.add(loader.loadImage("../Character/FrontAnimation4.png"));

        playerAnimations.add(loader.loadImage("../Character/LeftAnimation1&3.png"));
        playerAnimations.add(loader.loadImage("../Character/LeftAnimation2.png"));
        playerAnimations.add(loader.loadImage("../Character/LeftAnimation1&3.png"));
        playerAnimations.add(loader.loadImage("../Character/LeftAnimation4.png"));

        playerAnimations.add(loader.loadImage("../Character/RightAnimation1&3.png"));
        playerAnimations.add(loader.loadImage("../Character/RightAnimation2.png"));
        playerAnimations.add(loader.loadImage("../Character/RightAnimation1&3.png"));
        playerAnimations.add(loader.loadImage("../Character/RightAnimation4.png"));

        playerAnimations.add(loader.loadImage("../Character/IdleWithoutWeapon.png"));

        fullHeart = loader.loadImage("../Assets/FullHeart.png");
        fullArmor = loader.loadImage("../Assets/FullShield.png");
        minibossHealth = loader.loadImage("../Assets/redRec.png");

        bulletImage = loader.loadImage("../Assets/Bullet.png");
    }

    public static GameObject getInstance() {
        return instance;
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
            case (0) -> bufferedBodyImage = playerAnimations.get(frameCount);
            case (1) -> bufferedBodyImage = playerAnimations.get(frameCount + 4);
            case (2) -> bufferedBodyImage = playerAnimations.get(frameCount + 12);
            case (3) -> bufferedBodyImage = playerAnimations.get(frameCount + 8);
            case (4) -> bufferedBodyImage = playerAnimations.get(16);
        }
    }

    private void setGunImage(int gunImageCount) {
        switch (gunImageCount) {
            case (0) -> bufferedGunImage = playerGun.get(0);
            case (1) -> bufferedGunImage = playerGun.get(1);
            case (2) -> bufferedGunImage = playerGun.get(2);
            case (3) -> bufferedGunImage = playerGun.get(3);
            case (4) -> bufferedGunImage = playerGun.get(4);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x + 7,y,50,64);
    }

    // To do damage to player character
    public int doAction(int action) {
        // Needed so that damage doesn't get dealt
        // every frame, but only every 30th frame
        if(timeSinceLastDamage > 30) {
            if (armor == 0) health -= action;
            else armor -= action;
            timeSinceLastDamage = 0;
        }
        return 0;
    }

    private void spawnBulletOnPress(int shotY, int shotYStart, int shotX, int shotXStart){
        int damage = 100;
        if(shotY != y || shotX != x){
            Handler1.getInstance().addObject(new Bullet(shotXStart, shotYStart, ID.Bullet, shotX, shotY,
                                            range, damage, bulletImage));
            new Thread(new Music("res/Sounds/Guns/M4/GunSound1.wav", ID.ShootingSound)).start();
        }
    }

    //Checks shooting direction
    private void checkBulletDirection(){
        // Version like the binding of isaac
        if(Handler1.getInstance().isShootUp() && !Handler1.getInstance().isShootRight() && !Handler1.getInstance().isShootDown() && !Handler1.getInstance().isShootLeft())
            spawnBulletOnPress(y-132, y-32, x+25, x+25);
        else if(Handler1.getInstance().isShootDown() && !Handler1.getInstance().isShootRight() && !Handler1.getInstance().isShootLeft() && !Handler1.getInstance().isShootUp())
            spawnBulletOnPress(y+175, y+50, x+25, x+25);
        else if(Handler1.getInstance().isShootLeft() && !Handler1.getInstance().isShootRight() && !Handler1.getInstance().isShootDown() && !Handler1.getInstance().isShootUp())
            spawnBulletOnPress(y+40, y+40, x-130, x);
        else if(Handler1.getInstance().isShootRight() && !Handler1.getInstance().isShootLeft() && !Handler1.getInstance().isShootDown() && !Handler1.getInstance().isShootUp())
            spawnBulletOnPress(y+40, y+40, x+164, x+64);
    }

    private void setNewCoordinates(int newCoordinate, int newCameraCoordinate, int newRoomCoordinate, boolean isX){
        if(isX){
            x += newCoordinate;
            Camera.getInstance().setX(Camera.getInstance().getX() + newCameraCoordinate);
            roomXCoordinate += newRoomCoordinate;
        }
        else{
            y += newCoordinate;
            Camera.getInstance().setY(Camera.getInstance().getY() + newCameraCoordinate);
            roomYCoordinate += newRoomCoordinate;
        }
    }

    private boolean levelIsComplete(){

        boolean levelIsDone = true;

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                if(GenerateLevel.getInstance().getLevel()[i][j] > 0)
                    if(wherePlayerHasBeen[j][i] == 0)
                        levelIsDone = false;
            }
        }

//        System.out.println(levelIsDone);

//        GenerateLevel.printLevel();
//        System.out.println("----------------");
//        for(int i = 0; i <= wherePlayerHasBeen.length - 1; i++) {
//            for (int j = 0; j <= wherePlayerHasBeen.length - 1; j++) {
//                System.out.print(wherePlayerHasBeen[i][j]);
//            }
//            System.out.println();
//        }
//        System.out.println("*****************");

        return levelIsDone;
    }

    // Checks for collision
    private void checkCollision(){
        boolean shouldSpawnEnemy = false;
        boolean shouldChangeLevel = false;

        for(ListIterator<GameObject> iterator = Handler1.getInstance().objects.listIterator(); iterator.hasNext();){
            GameObject temp = iterator.next();

            if(temp.getId() == ID.Block && getBounds().intersects(temp.getBounds())){
                x+=velX*-1;
                y+=velY*-1;
            }
            if(temp.getId() == ID.Door && getBounds().intersects(temp.getBounds()) && !((Door) temp).isLocked()){

                if( temp.getX() >  x && (y + 32 > temp.getY() && y + 32 < temp.getY() + 64) &&
                        Handler1.getInstance().isRight() && !Handler1.getInstance().isLeft())
                    setNewCoordinates(250, 1088, 1, true);
                else if(temp.getX() <  x && (y + 32 > temp.getY() && y + 32 < temp.getY() + 64) &&
                        Handler1.getInstance().isLeft() && !Handler1.getInstance().isRight())
                    setNewCoordinates(-250, -1088, -1, true);
                else if(temp.getY() < y && (x + 32 > temp.getX() && x + 32 < temp.getX() + 64) &&
                        Handler1.getInstance().isUp() && !Handler1.getInstance().isDown())
                    setNewCoordinates(-260, -576, -1, false);
                else if(temp.getY() > y && (x + 32 > temp.getX() && x + 32 < temp.getX() + 64) &&
                        Handler1.getInstance().isDown() && !Handler1.getInstance().isUp())
                    setNewCoordinates(260, 576, 1, false);
                if(wherePlayerHasBeen[roomXCoordinate][roomYCoordinate] == 0)
                    shouldSpawnEnemy = true;

                wherePlayerHasBeen[roomXCoordinate][roomYCoordinate] = 1;
            }
            else if(temp.getId() == ID.Door && getBounds().intersects(((Door) temp).getSmallerBounds()) &&
                    ((Door) temp).isLocked()){
                x+=velX*-1;
                y+=velY*-1;
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
            //Types of enemies can be put in ID[]{...} for a random spawn of each of them
            SpawnEnemiesInRoom.spawnEnemies(roomXCoordinate * 1088 + 64, roomYCoordinate * 576 + 64,
                                            new ID[]{ID.SmartEnemy, ID.ShotEnemy}, currentLevel);
        }
        if(shouldChangeLevel){
            Game.removePortal();
            x += 20;
            y += 20;
            portalExists = false;
            changeToNextLevel();
        }
    }

    private void drawMinimap(Graphics g){
        for(int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                if(i == 3 && j == 3){
                    g.drawImage(minimapSprites.get(3), (int)Camera.getInstance().getX() + 948,
                            (int)Camera.getInstance().getY() + 59, null);
                }
                else if(wherePlayerHasBeen[i][j] == 1){
                    g.drawImage(minimapSprites.get(0), (int)Camera.getInstance().getX() + 813 + i * 45,
                            (int)Camera.getInstance().getY() + j * 25 - 16, null);
                }
                else if(wherePlayerHasBeen[i][j] == 0 && GenerateLevel.getInstance().getLevel()[j][i] > 0){
                    g.drawImage(minimapSprites.get(2),(int)Camera.getInstance().getX() + 813 + i * 45,
                            (int)Camera.getInstance().getY() + j * 25 - 16, null);
                }
            }
        }
        g.drawImage(minimapSprites.get(1),  (int)Camera.getInstance().getX() + (int)Camera.getInstance().getX() / 1088 * 45 + 813 ,
                                            (int)Camera.getInstance().getY() + (int)Camera.getInstance().getY() / 576 * 25 - 16, null);
    }

    private void changeToNextLevel(){
        currentLevel = currentLevel.next();
        Game.getInstance().changeLevel(currentLevel.name(), currentLevel.ordinal() + 5);

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
        if(Game.showHitbox) {
            //Graphics2D g2 = (Graphics2D) g;
            //g2.setColor(Color.green);
            //g2.draw(getBounds());
        }

        // Sets health to a min value of 0
        if (health < 0)
            health = 0;

        for(int i = 0; i < health; i++)
            g.drawImage(fullHeart, (int)Camera.getInstance().getX() + i * 35 + 10, (int)Camera.getInstance().getY() + 10, null);

        for(int i = 0; i < armor; i++)
            g.drawImage(fullArmor, (int)Camera.getInstance().getX() + i * 35 + 10 + health * 35, (int)Camera.getInstance().getY() + 10, null);

    }

    public void tick() {
        System.out.println(Handler1.getInstance().objects.size());
        x += velX;
        y += velY;

        double t1 = System.currentTimeMillis();

        if(levelIsComplete() && !portalExists) {
            Game.addPortal(3392 + 64, 1856 + 64);
            portalExists = true;
        }

        double t2 = System.currentTimeMillis();

        double zeit=t2-t1;

        //System.out.println("Teil 1: "+zeit);

        double t3 = System.currentTimeMillis();


        // checks for collision with objects
        checkCollision();

        double t4 = System.currentTimeMillis();

        double zeit2=t4-t3;

        //System.out.println("Teil 2: "+zeit2);

        double t5 = System.currentTimeMillis();

        // Needed so that shooting is always available when button is pressed
        // and enough time has passed
        timeSinceLastShot++;
        timeSinceLastDamage++;

        // Needed so that timeSinceLastDamage doesn't overflow
        if(timeSinceLastDamage % 9999 == 0)
            timeSinceLastDamage = 0;

        // Reuses timeSinceLastShot as a sort
        // of way to keep track of ticks since it
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

        if(Handler1.getInstance().isUp() && !Handler1.getInstance().isDown()){
            if (Handler1.getInstance().isRight()||Handler1.getInstance().isLeft()) velY=-3;
            else velY = -5;

            setBodyImgCounter = 0;
            setGunImage(0);
        }
        else if(Handler1.getInstance().isDown()) velY = 0;

        if(Handler1.getInstance().isDown() && !Handler1.getInstance().isUp()){
            if (Handler1.getInstance().isRight()||Handler1.getInstance().isLeft()) velY=3;
            else velY = 5;

            setBodyImgCounter = 1;
            setGunImage(1);
        }
        else if(!Handler1.getInstance().isUp()) velY = 0;

        if(Handler1.getInstance().isRight() && !Handler1.getInstance().isLeft()){
            if (Handler1.getInstance().isUp()||Handler1.getInstance().isDown()) velX=3;
            else velX = 5;

            setBodyImgCounter = 2;
            setGunImage(2);
        }
        else if(Handler1.getInstance().isLeft()) velX = 0;

        if(Handler1.getInstance().isLeft() && !Handler1.getInstance().isRight()) {
            if (Handler1.getInstance().isUp()||Handler1.getInstance().isDown()) velX=-3;
            else velX = -5;

            setBodyImgCounter = 3;
            setGunImage(3);
        }
        else if (!Handler1.getInstance().isRight()) velX = 0;

        // Needed so character looks int the
        // same direction he shoots
        if(Handler1.getInstance().isShootUp() && !Handler1.getInstance().isShootRight() && !Handler1.getInstance().isShootDown() && !Handler1.getInstance().isShootLeft()){
            setBodyImgCounter = 0;
            setGunImage(0);
        }
        if(Handler1.getInstance().isShootDown() && !Handler1.getInstance().isShootRight() && !Handler1.getInstance().isShootUp() && !Handler1.getInstance().isShootLeft()){
            setBodyImgCounter = 1;
            setGunImage(1);
        }
        if(Handler1.getInstance().isShootRight() && !Handler1.getInstance().isShootUp() && !Handler1.getInstance().isShootDown() && !Handler1.getInstance().isShootLeft()){
            setBodyImgCounter = 2;
            setGunImage(2);
        }
        if(Handler1.getInstance().isShootLeft() && !Handler1.getInstance().isShootRight() && !Handler1.getInstance().isShootDown() && !Handler1.getInstance().isShootUp()){
            setBodyImgCounter = 3;
            setGunImage(3);
        }

        // Needed so that walking animation doesnt play while
        // shooting and not moving
        if(!Handler1.getInstance().isDown() && !Handler1.getInstance().isUp() && !Handler1.getInstance().isLeft() && !Handler1.getInstance().isRight()){
            frameCount = 0;
        }

        setBodyImage(setBodyImgCounter);

        if(timeSinceLastShot % 1000 == 0){
            timeSinceLastShot = 30;
        }
        if  (timeSinceLastShot > fireSpeed &&
            (Handler1.getInstance().isShootUp() || Handler1.getInstance().isShootDown() || Handler1.getInstance().isShootLeft() || Handler1.getInstance().isShootRight())){
            checkBulletDirection();
            timeSinceLastShot = 0;
        }
        double t6 = System.currentTimeMillis();
        //System.out.println(t6);
        double zeit3=t6-t5;
        //System.out.println("Teil 3: "+zeit3);
    }

}