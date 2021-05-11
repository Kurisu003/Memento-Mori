package Model;

import Controller.Handler1;

import java.util.Random;

public class SpawnEnemiesInRoom {
    private static int minibosses;
    public static void spawnEnemies(int roomStartX, int roomStartY, int amountOfEnemies, ID[] typesOfEnemy){

        for (int i = 0; i < amountOfEnemies; i++){
            Random rn = new Random();
            int pos = 0;
            pos = rn.nextInt(typesOfEnemy.length);
            if(typesOfEnemy[pos].equals(ID.SmartEnemy))
                Handler1.getInstance().addObject(new SmartEnemy(roomStartX + (rn.nextInt(960)),roomStartY +
                                (rn.nextInt(448)), typesOfEnemy[pos]));
            else if (typesOfEnemy[pos].equals(ID.ShotEnemy))
                Handler1.getInstance().addObject(new ShotEnemy(roomStartX + (rn.nextInt(960)),roomStartY +
                                (rn.nextInt(448)), typesOfEnemy[pos]));
            else if(typesOfEnemy[pos].equals(ID.Enemy)) {
                Handler1.getInstance().addObject(new Enemy(roomStartX + (rn.nextInt(960)),
                        roomStartY + (rn.nextInt(448)), ID.Enemy));
            }
            else if(typesOfEnemy[pos].equals(ID.Miniboss)){
                if(minibosses == 0){
                    Handler1.getInstance().addObject(new Miniboss(roomStartX+(rn.nextInt(960)),
                                                roomStartY+(rn.nextInt(448)), ID.Miniboss));
                    minibosses = 1;
                }
            }
        }
    }
}
