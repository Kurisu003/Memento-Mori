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

    private static final long serialVersionUID = 6529685098267757690L;

    private transient final ArrayList<BufferedImage> playerAnimations = new ArrayList<>();
    private transient final ArrayList<BufferedImage> playerGun = new ArrayList<>();
    private transient final ArrayList<BufferedImage> minimapSprites = new ArrayList<>();

    private transient final BufferedImage fullHeart;
    private transient final BufferedImage fullArmor;
    private transient final ArrayList<BufferedImage> gameOverScreen;

    private transient BufferedImage bulletImage;

    public static Levels currentLevel = Levels.Limbo;
    private static GameObject instance;

    private int roomXCoordinate;
    private int roomYCoordinate;
    private int [][] wherePlayerHasBeen = new int[7][7];

    private int timeSinceLastShot = 20;
    private int timeSinceLastDamage = 20;
    private int timeSinceLastObstacleDamage = 20;

    private int fireSpeed = 0;
    private int range = 0;
    private int damage = 0;
    private int health = 7;
    private int armor = 5;
    private int coins = 0;

    private int frameCount = 0;
    private int gameOverScreenCounter = 0;
    private boolean portalExists;

    private transient BufferedImage bufferedBodyImage;
    private transient BufferedImage bufferedGunImage;
    private transient BufferedImage darken;

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

        bulletImage = loader.loadImage("../Assets/Bullet.png");

        gameOverScreen = new ArrayList<>();
        gameOverScreen.add(loader.loadImage("../MainMenuAssets/GameOver/GameOver0.png"));
        gameOverScreen.add(loader.loadImage("../MainMenuAssets/GameOver/GameOver1.png"));
        gameOverScreen.add(loader.loadImage("../MainMenuAssets/GameOver/GameOver2.png"));
        gameOverScreen.add(loader.loadImage("../MainMenuAssets/GameOver/GameOver3.png"));
        gameOverScreen.add(loader.loadImage("../MainMenuAssets/GameOver/GameOver4.png"));
        gameOverScreen.add(loader.loadImage("../MainMenuAssets/GameOver/GameOver5.png"));

        darken = loader.loadImage("../Assets/Darken.png");
    }

    /**
     * This method checks in a certain period of time for Dante's movements in order to set the right images.
     * It also checks if the level is completed and if true it will render the portal and set the portalExists true.
     */
    @Override
    public void tick() {
        x += velX;
        y += velY;

        if(levelIsComplete() && !portalExists) {
            if(currentLevel!=Levels.Fraud) {
                Game.addPortal(3776, 1984);
                portalExists = true;
            }else {
                Handler1.getInstance().addObject(new EndBoss(1088 * 3 + 544,3 * 576 + 288,ID.EndBoss));
                portalExists = true;
            }
        }

        // checks for collision with objects
        checkCollision();

        // Needed so that shooting is always available when button is pressed
        // and enough time has passed
        timeSinceLastShot++;
        timeSinceLastDamage++;
        timeSinceLastObstacleDamage++;
        // Needed so that timeSinceLastDamage doesn't overflow
        if(timeSinceLastDamage % 9999 == 0)
            timeSinceLastDamage = 0;

        if(timeSinceLastObstacleDamage % 999 == 0)
            timeSinceLastObstacleDamage = 0;
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
        if  (timeSinceLastShot > 20 - fireSpeed * 2 &&
                (Handler1.getInstance().isShootUp() || Handler1.getInstance().isShootDown() || Handler1.getInstance().isShootLeft() || Handler1.getInstance().isShootRight())){
            checkBulletDirection();
            timeSinceLastShot = 0;
        }

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
     * Returns the amount of coins
     * @return number of coins the player has
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Sets the coin when the player collects it
     * @param coins amount of collected coins
     */
    public void setCoins(int coins) {
        this.coins = coins;
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
        if(timeSinceLastDamage > 50 && health > 0) {
            if (armor == 0) health -= action;
            else armor -= action;
            timeSinceLastDamage = 0;
            Music.getThreadPool().execute(new Music("res/Sounds/Hurt.wav", ID.HurtSound));
        }
    }

    /**
     * Does damage to Dante when he goes through a hurting obstacle
     */
    // Applies damage directly to HP,
    // bypassing any armor
    private void doObstacleDamage(){
        if(timeSinceLastObstacleDamage > 50 && health > 0){
            health--;
            timeSinceLastObstacleDamage = 0;
            Music.getThreadPool().execute(new Music("res/Sounds/Hurt.wav", ID.HurtSound));
        }
    }

    /**
     * This method is there to get the current instance
     * @return current instance
     */
    public static GameObject getInstance() {
        if(instance == null){
            instance = new Dante(3 * 64 * 17 + 8 * 64, 3 * 64 * 9 + 4 * 64, ID.Dante);
            Handler1.getInstance().addObject(instance);
        }
        return instance;
    }

    /**
     * This method sets the firespeed for Dante's weapon
     * @param fireSpeed integer to indicate the speed
     */
    // to set firespeed of weapon
    public void setFireSpeed(int fireSpeed){
        this.fireSpeed = fireSpeed;
    }

    /**
     * Returns the fire speed of a shot
     * @return fire speed
     */
    public int getFireSpeed() {
        return fireSpeed;
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
     * Returns the range of the gun shot
     * @return size of range
     */
    public int getRange() {
        return range;
    }

    /**
     * Returns the amount of damage
     * @return amount of damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the current health
     * @param health amount of health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Returns the current health
     * @return the amount of health
     */
    public int getHealth() {
        return this.health;
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
            spawnBulletOnPress(y+175, y+70, x+25, x+25);
        else if(Handler1.getInstance().isShootLeft() && !Handler1.getInstance().isShootRight() && !Handler1.getInstance().isShootDown() && !Handler1.getInstance().isShootUp())
            spawnBulletOnPress(y+40, y+40, x-130, x-20);
        else if(Handler1.getInstance().isShootRight() && !Handler1.getInstance().isShootLeft() && !Handler1.getInstance().isShootDown() && !Handler1.getInstance().isShootUp())
            spawnBulletOnPress(y+40, y+40, x+164, x+64);
    }

    /**
     * Checks if the level is completed
     * @return true or false
     */
    private boolean levelIsComplete(){

        boolean levelIsDone = true;

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                if(GenerateLevel.getInstance().getLevel()[i][j] > 0)
                    if(wherePlayerHasBeen[j][i] == 0 || bossLeft())
                        levelIsDone = false;
            } //FIXME NoSuchFieldError
        }

        return levelIsDone;
    }

    /**
     * Checks if the boss is defeated
     * @return true -> boss is killed, false -> boss is still alive
     */
    private boolean bossLeft(){
        boolean bossLeft = false;
        for(GameObject temp : Handler1.getInstance().objects){
            if (temp.getId().equals(ID.Miniboss)) {
                bossLeft = true;
                break;
            }
        }
        return bossLeft;
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
                    range, damage + this.damage * 20, bulletImage,10));
            Music.getThreadPool().execute(new Music("res/Sounds/Guns/M4/GunSound1.wav", ID.ShootingSound));
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
        int tempCoin = 0;
        boolean shouldRemoveCoin = false;

        for(ListIterator<GameObject> iterator = Handler1.getInstance().objects.listIterator(); iterator.hasNext();){
            GameObject temp = iterator.next();

            if(temp.getId() == ID.Block && getBounds().intersects(temp.getBounds())||temp.getId() == ID.Obstacle && getBounds().intersects(temp.getBounds())){
                x+=velX*-1;
                y+=velY*-1;
            }
            if(temp.getId() == ID.Door && getBounds().intersects(temp.getBounds()) && !((Door) temp).isLocked()){

                if( temp.getX() > x && (y + 32 > temp.getY() && y + 32 < temp.getY() + 64) &&
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
            if(temp.getId().equals(ID.Bullet) && getBounds().intersects(temp.getBounds()))
                doAction(1);
            if(temp.getId().equals(ID.Coin) && getBounds().intersects(temp.getBounds())){
                Music.getThreadPool().execute(new Music("res/Music/CoinSound.wav", ID.Coin));
                coins++;
                shouldRemoveCoin = true;
                tempCoin = iterator.nextIndex() - 1;
            }
            if(temp.getId().equals(ID.DamageObstacle) && getBounds().intersects(temp.getBounds())){
                assert temp instanceof DamageObstacle;
                if(((DamageObstacle) temp).getIsSpike()){
                    if(((DamageObstacle) temp).getFrameCounter() >= 50 && ((DamageObstacle) temp).getFrameCounter() <= 180){
                        this.doObstacleDamage();
                    }
                }
                else
                this.doObstacleDamage();
            }
        }

        if(shouldRemoveCoin){
            Handler1.getInstance().removeObject(Handler1.getInstance().objects.get(tempCoin));
            shouldRemoveCoin = false;
        }

        // Used because object cant be added
        // to list within a loop
        if(shouldSpawnEnemy){
            //Types of enemies can be put in ID[]{...} for a random spawn of each of them
            SpawnEnemiesInRoom.spawnEnemies(roomXCoordinate * 1088 + 64, roomYCoordinate * 576 + 64,
                    new ID[]{ID.SmartEnemy, ID.ShotEnemy, ID.Enemy}, currentLevel);
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
            g.drawImage(fullHeart,
                    (int)Camera.getInstance().getX() + i * 35 + 10,
                    (int)Camera.getInstance().getY() + 10,
                    null);

        for(int i = 0; i < armor; i++)
            g.drawImage(fullArmor,
                    (int)Camera.getInstance().getX() + i * 35 + 10 + health * 35,
                    (int)Camera.getInstance().getY() + 10,
                    null);


        g.drawImage(darken, (int) Camera.getInstance().getX(), (int) Camera.getInstance().getY() + 60, null);

        g.drawImage(Game.getInstance(20).getCoinSprites().get(0),
                (int) Camera.getInstance().getX() + 20,
                (int) Camera.getInstance().getY() + 64,
                null);


        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("x" + coins,
                (int) Camera.getInstance().getX() + 10,
                (int) Camera.getInstance().getY() + 110);

        // Sets health to a min value of 0
        if (health <= 0) {
            Game.setState(GameState.GameOver);
            gameOverScreenCounter = ++gameOverScreenCounter % 600;
            g.drawImage(gameOverScreen.get(gameOverScreenCounter / 100),
                    (int) Camera.getInstance().getX(),
                    (int) Camera.getInstance().getY(),
                    null);
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
        Game.getInstance(21).changeLevel(currentLevel.name(), currentLevel.ordinal() + 2);
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

    /**
     * Sets the last position of the player
     * @param wherePlayerHasBeen1 x and y of the position
     */
    public void setWherePlayerHasBeen(int[][] wherePlayerHasBeen1) {
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                this.wherePlayerHasBeen[i][j] = wherePlayerHasBeen1[i][j];
//                System.out.print(wherePlayerHasBeen1[i][j]);
            }
//            System.out.println();
        }
//        this.wherePlayerHasBeen = wherePlayerHasBeen;
    }

    /**
     * Returns the last player's position
     * @return coordinates of the last position
     */
    public  int[][] getWherePlayerHasBeen() {
        return wherePlayerHasBeen;
    }

    /**
     * Sets an instance of this class
     * @param instance type of instance
     */
    public static void setInstance(GameObject instance) {
        Dante.instance = instance;
    }

    /**
     * sets the current level where the player is
     * @param currentLevel level where the player is
     */
    public void setCurrentLevel(Levels currentLevel) {
        Dante.currentLevel = currentLevel;
    }

    /**
     * Returns the current level
     * @return which level Dante is in
     */
    public Levels getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets damage to the player
     * @param damage how much damage is set to the player
     */
    public void setDamage(int damage){
        this.damage = damage;
    }

    /**
     * Returns the x-coordinate of the room
     * @return x-coordinate
     */
    public int getRoomXCoordinate() {
        return roomXCoordinate;
    }

    /**
     * Sets the x-coordinate of the room
     * @param roomXCoordinate x-coordinate
     */
    public void setRoomXCoordinate(int roomXCoordinate) {
        this.roomXCoordinate = roomXCoordinate;
    }

    /**
     * Returns the y-coordinate of the room
     * @return y-coordinate
     */
    public int getRoomYCoordinate() {
        return roomYCoordinate;
    }

    /**
     * Sets the y-coordinate of the room
     * @param roomYCoordinate y-coordinate
     */
    public void setRoomYCoordinate(int roomYCoordinate) {
        this.roomYCoordinate = roomYCoordinate;
    }
}