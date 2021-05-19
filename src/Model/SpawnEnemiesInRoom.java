package Model;

import Controller.Handler1;

import java.util.Random;

public class SpawnEnemiesInRoom {
    private static int minibosses;
    public static void spawnEnemies(int roomStartX, int roomStartY, ID[] typesOfEnemy, Levels currentLevel){
        Random rn = new Random();
        int amountOfEnemies = 2 + rn.nextInt(currentLevel.ordinal() + 1);
        for (int i = 0; i < amountOfEnemies; i++){
            rn = new Random();
            int pos = 0;
            pos = rn.nextInt(typesOfEnemy.length);
            if(typesOfEnemy[pos].equals(ID.SmartEnemy))
                Handler1.getInstance().addObject(new SmartEnemy(roomStartX + 64 + (rn.nextInt(832)),
                                                                roomStartY + 64 + (rn.nextInt(320)),
//                                        typesOfEnemy[pos], currentLevel.ordinal() * 20, currentLevel.ordinal()));
                                        typesOfEnemy[pos], 0, 0));
            else if (typesOfEnemy[pos].equals(ID.ShotEnemy))
                Handler1.getInstance().addObject(new ShotEnemy( roomStartX + 64 + (rn.nextInt(832)),
                                                                roomStartY + 64 + (rn.nextInt(320)), typesOfEnemy[pos],
//                                                            currentLevel.ordinal() * 20, currentLevel.ordinal()));
                                                            0, 0));
            else if(typesOfEnemy[pos].equals(ID.Enemy)) {
                Handler1.getInstance().addObject(new Enemy( roomStartX + 64 + (rn.nextInt(832)),
                                                            roomStartY + 64 + (rn.nextInt(320)), ID.Enemy,
//                                                            currentLevel.ordinal() * 20, currentLevel.ordinal()));
                                                            0, 0));
            }
            /*
            else if(typesOfEnemy[pos].equals(ID.Miniboss)){
                if(minibosses == 0){
                    Handler1.getInstance().addObject(new Miniboss(roomStartX+(rn.nextInt(960)),
                                                roomStartY+(rn.nextInt(448)), ID.Miniboss, 0, 0));
                    minibosses = 1;
                }
            }

             */
            
            //Spawns 1 miniboss in the level Heresy
            if(currentLevel.equals(Levels.Heresy)){
                if(minibosses == 0){
                    Handler1.getInstance().addObject(new Miniboss(roomStartX+(rn.nextInt(960)),
                            roomStartY+(rn.nextInt(448)), ID.Miniboss, 0, 0));
                    minibosses = 1;
                }
            }

        }
    }
}
