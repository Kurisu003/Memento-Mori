package Model;

import Controller.Handler1;

import java.util.Random;

/**
 * This class spawns the enemies in a room depending on the desired amount and types of enemies.
 */
public class SpawnEnemiesInRoom {
    private static int minibosses;
    private static int endboss;

    /**
     * Spawns enemies randomly. What kind of enemy should be spawned can be given to that method and the program
     * chooses randomly how many of a certain type get spawned, but with a limit for a maximum of enemies.
     * @param roomStartX x-coordinate where the room position starts
     * @param roomStartY y-coordinate where the room position starts
     * @param typesOfEnemy array of desired types of enemies which are allowed to be spawn
     * @param currentLevel indicates which level is the current one
     */
    public static void spawnEnemies(int roomStartX, int roomStartY, ID[] typesOfEnemy, Levels currentLevel){
        Random rn = new Random();
        int amountOfEnemies = 2 + rn.nextInt(5) + 3;

        for (int i = 0; i < amountOfEnemies; i++){
            rn = new Random();
            int pos = 0;
            pos = rn.nextInt(typesOfEnemy.length);
            if(typesOfEnemy[pos].equals(ID.SmartEnemy))
                Handler1.getInstance().addObject(new SmartEnemy(roomStartX + 64 + (rn.nextInt(832)),
                                                                roomStartY + 64 + (rn.nextInt(320)),
                                        typesOfEnemy[pos], currentLevel.ordinal() * 30, 0));
            else if (typesOfEnemy[pos].equals(ID.ShotEnemy))
                Handler1.getInstance().addObject(new ShotEnemy( roomStartX + 64 + (rn.nextInt(832)),
                                                                roomStartY + 64 + (rn.nextInt(320)), typesOfEnemy[pos],
                                                            currentLevel.ordinal() * 20, 0));
            else if(typesOfEnemy[pos].equals(ID.Enemy)) {
                Handler1.getInstance().addObject(new Enemy( roomStartX + 64 + (rn.nextInt(832)),
                                                            roomStartY + 64 + (rn.nextInt(320)), ID.Enemy,
                                                        currentLevel.ordinal() * 10, 0));
            }
            if(currentLevel.equals(Levels.Heresy)){
                if(minibosses == 0){
                    Handler1.getInstance().addObject(new Miniboss(roomStartX+(rn.nextInt(960)),
                            roomStartY+(rn.nextInt(448)), ID.Miniboss, 10000));
                    minibosses = 1;
                }
            }
            /*
            if(endboss == 0) {
                Handler1.getInstance().addObject(new EndBoss(roomStartX + (rn.nextInt(960)),
                        roomStartY + (rn.nextInt(448)), ID.EndBoss));
                endboss = 1;
            }
            
             */
        }

    }
}
