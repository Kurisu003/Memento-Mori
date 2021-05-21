package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;


/**
 * Dante is the main character in this game. It is able to move through doors and to shoot enemies with his
 * weapon. The player can move him with W, A, S, D.
 */
public class Dante extends GameObject {

    private transient final ArrayList<BufferedImage> playerAnimations = new ArrayList<>();
    private transient final ArrayList<BufferedImage> playerGun = new ArrayList<>();
    private transient final ArrayList<BufferedImage> minimapSprites = new ArrayList<>();

    private transient final BufferedImage fullHeart;
    private transient final BufferedImage fullArmor;
    private transient final BufferedImage gameOverScreen;

    private transient BufferedImage bulletImage;
    private transient final BufferedImage minibossHealth;

    private Levels currentLevel = Levels.Heresy;
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

    /**
     * This is the constructor for Dante
     * @param x x-coordinate where Dante should spawn
     * @param y y-coordinate where Dante should span
     * @param id set ID for Dante
     */
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
        gameOverScreen = loader.loadImage("../MainMenuAssets/GameOver/GameOverScreen.png");
    }

    /**
     * This method checks in a certain period of time for Dante's movements in order to set the right images.
     * It also checks if the level is completed and if true it will render the portal and set the portalExists true.
     */
    @Override
    public void tick() {
        x += velX;
        y += velY;

        double t1 = System.currentTimeMillis();

        if(levelIsComplete() && !portalExists) {
            Game.addPortal(3392 + 64, 1856 + 64);
            portalExists = true;
        }

        double t2 = System.currentTimeMillis();

        double zeit=t2-t1;

        //System.out.println(zeit);

        double t3 = System.currentTimeMillis();


        // checks for collision with objects
        checkCollision();

        double t4 = System.currentTimeMillis();

        double zeit2=t4-t3;

        //System.out.println(zeit2);

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

        // Needed so character looks into the
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
        //System.out.println(zeit3);
    }

    /**
     * Set the desired body image depending on the direction Dante moves at
     * @param bodyImageCount to indicate which image should be displayed
     */
    public void setBodyImage(int bodyImageCount){
        switch (bodyImageCount) {
            case (0) -> bufferedBodyImage = playerAnimations.get(frameCount);
            case (1) -> bufferedBodyImage = playerAnimations.get(frameCount + 4);
            case (2) -> bufferedBodyImage = playerAnimations.get(frameCount + 12);
            case (3) -> bufferedBodyImage = playerAnimations.get(frameCount + 8);
            case (4) -> bufferedBodyImage = playerAnimations.get(16);
        }
    }

    /**
     * Set the desired gun image depending on the direction Dante shoots at.
     * @param gunImageCount to indicate which image should be displayed for the gun
     */
    private void setGunImage(int gunImageCount) {
        switch (gunImageCount) {
            case (0) -> bufferedGunImage = playerGun.get(0);
            case (1) -> bufferedGunImage = playerGun.get(1);
            case (2) -> bufferedGunImage = playerGun.get(2);
            case (3) -> bufferedGunImage = playerGun.get(3);
            case (4) -> bufferedGunImage = playerGun.get(4);
        }
    }

    /**
     * This method indicates when Dante was hit to reduce his remaining live hearts
     * @param action how much damage the action did to Dante
     */
    // To do damage to player character
    @Override
    public void doAction(int action) {
        // Needed so that damage doesn't get dealt
        // every frame, but only every 30th frame
        if(timeSinceLastDamage > 50) {
            if (armor == 0) health -= action;
            else armor -= action;
            timeSinceLastDamage = 0;
        }
    }

    /**
     * This method is there to get the current instance
     * @return current instance
     */
    public static GameObject getInstance() {
        return instance;
    }

    /**
     * This method sets the firespeed for Dante's weapon
     * @param speed integer to indicate the speed
     */
    // to set firespeed of weapon
    public void setFireSpeed(int speed){
        this.fireSpeed = speed;
    }

    /**
     * This method is used to set the range of Dante's weapon.
     * @param range integer to indicate the range
     */
    // to set range of Weapon
    public void setRange(int range){
        this.range = range;
    }

    /**
     * Method to set different bullet images
     * @param bulletImage desired bullet image
     */
    public void setBulletImage(BufferedImage bulletImage){
        this.bulletImage = bulletImage;
    }

    /**
     * This method checks the direction in which the bullet should be shot by detecting Dante's position.
     */
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

    private boolean levelIsComplete(){

        boolean levelIsDone = true;

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                if(GenerateLevel.getInstance().getLevel()[i][j] > 0)
                    if(wherePlayerHasBeen[j][i] == 0)
                        levelIsDone = false;
            }
        }

        return levelIsDone;
    }

    /**
     * This method sets the image of the bullet at the right position
     * @param shotY y-coordinate of the bullet's last position
     * @param shotYStart y-coordinate where the bullet should start
     * @param shotX x-coordinate of the bullet's last position
     * @param shotXStart x-coordinate where the bullet should start
     */
    private void spawnBulletOnPress(int shotY, int shotYStart, int shotX, int shotXStart){
        int damage = 100;
        if(shotY != y || shotX != x){
            Handler1.getInstance().addObject(new Bullet(shotXStart, shotYStart, ID.Bullet, shotX, shotY,
                    range, damage, bulletImage));
            new Thread(new Music("res/Sounds/Guns/M4/GunSound1.wav", ID.ShootingSound)).start();
        }
    }

    /**
     * This method checks every collision with an object. If Dante's bounds intersect with a locked door object Dante's
     * movements will stop. If he collides with a unlocked door he will be set to the new room and all the new
     * coordinates will be set by calling {@link #setNewCoordinates(int, int, int, boolean)}. If Dante collides with
     * an enemy he will get damage. It also checks if Dante is going through the portal.
     */
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
            if(temp.getId() == ID.Enemy && getBounds().intersects(temp.getBounds()))
                doAction(1);
            if(temp.getId() == ID.Portal && getBounds().intersects(temp.getBounds()))
                shouldChangeLevel = true;
            if(temp.getId().equals(ID.Miniboss) && getBounds().intersects(temp.getBounds()))
                doAction(1);
        }

        // Used because object cant be added
        // to list within a loop
        if(shouldSpawnEnemy){
            //Types of enemies can be put in ID[]{...} for a random spawn of each of them
            SpawnEnemiesInRoom.spawnEnemies(roomXCoordinate * 1088 + 64, roomYCoordinate * 576 + 64,
                    new ID[]{ID.SmartEnemy, ID.ShotEnemy}, currentLevel);
            //new ID[]{ID.ShotEnemy}, currentLevel);
        }
        if(shouldChangeLevel){
            Game.removePortal();
            x += 20;
            y += 20;
            portalExists = false;
            changeToNextLevel();
        }
    }
    /**
     * This method is in order to set the new coordinates of Dante when passing a door and reaching the next room.
     * @param newCoordinate x- or y-coordinate for Dante's new position
     * @param newCameraCoordinate x- or y-coordinate for the new Camera position
     * @param newRoomCoordinate x- or y-coordinate for the new room
     * @param isX indicates if given coordinate is the x coordinate, if is false, it's the y-coordinate
     **/
    private void setNewCoordinates(int newCoordinate, int newCameraCoordinate, int newRoomCoordinate, boolean isX) {
        if (isX) {
            x += newCoordinate;
            Camera.getInstance().setX(Camera.getInstance().getX() + newCameraCoordinate);
            roomXCoordinate += newRoomCoordinate;
        } else {
            y += newCoordinate;
            Camera.getInstance().setY(Camera.getInstance().getY() + newCameraCoordinate);
            roomYCoordinate += newRoomCoordinate;
        }
    }

    /**
     * Renders every graphic for Dante (health, armor, body, gun) and sets GameState Gameover if there's no health left.
     * @param g graphics where everything should be drawn
     */
    // Used to render image of player gun and body
    public void render(Graphics g) {
        g.drawImage(bufferedBodyImage, x, y, null);
        g.drawImage(bufferedGunImage, x - 20, y - 30, null);
        drawMinimap(g);

//        To draw hitboxes
        if(Game.showHitbox) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.green);
            g2.draw(getBounds());
        }

        for(int i = 0; i < health; i++)
            g.drawImage(fullHeart, (int)Camera.getInstance().getX() + i * 35 + 10, (int)Camera.getInstance().getY() + 10, null);

        for(int i = 0; i < armor; i++)
            g.drawImage(fullArmor, (int)Camera.getInstance().getX() + i * 35 + 10 + health * 35, (int)Camera.getInstance().getY() + 10, null);


        // Sets health to a min value of 0
        if (health <= 0) {
            g.drawImage(gameOverScreen, (int) Camera.getInstance().getX(), (int) Camera.getInstance().getY(), null);
            Game.setState(GameState.GameOver);
        }
    }

    /**
     * This method draws the minimap which shows every generated room in the current level.
     * @param g the graphics where the minimap has to be drawn on
     */
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

    /**
     * The player's position is set to the next level and the currentLevel is changing to the next level.
     */
    private void changeToNextLevel(){
        currentLevel = currentLevel.next();
        Game.getInstance().changeLevel(currentLevel.name(), currentLevel.ordinal() + 5);
        //Game.getInstance().changeLevel(currentLevel.name(), 5);

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                wherePlayerHasBeen[i][j] = 0;
            }
        }
        wherePlayerHasBeen[3][3] = 1;
    }

    /**
     * This method returns Dante's bounds (his shape where he can collide with objects or be hit)
     * @return the bounds of Dante as a rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(x + 7,y,50,64);
    }



















}