package Model;

import Controller.Handler1;

import java.util.Random;

public class SpawnEnemiesInRoom {
    private static int minibosses;
    public static void spawnEnemies(int roomStartX, int roomStartY, int amountOfEnemies, ID[] typesOfEnemy, Handler1 handler){

        for (int i = 0; i < amountOfEnemies; i++){
            Random rn = new Random();
            int pos = 0;
            pos = rn.nextInt(typesOfEnemy.length);
            if(typesOfEnemy[pos].equals(ID.SmartEnemy))
                handler.addObject(new SmartEnemy(roomStartX + (rn.nextInt(960)),roomStartY + (rn.nextInt(448)), typesOfEnemy[pos], handler));
            else if (typesOfEnemy[pos].equals(ID.ShotEnemy))
                handler.addObject(new ShotEnemy(roomStartX + (rn.nextInt(960)),roomStartY + (rn.nextInt(448)), typesOfEnemy[pos], handler));
            else if(typesOfEnemy[pos].equals(ID.Enemy)) {
                handler.addObject(new Enemy(roomStartX + (rn.nextInt(960)),
                        roomStartY + (rn.nextInt(448)), ID.Enemy, handler));
            }
            else if(typesOfEnemy[pos].equals(ID.Miniboss)){
                if(minibosses == 0){
                    handler.addObject(new Miniboss(roomStartX+(rn.nextInt(960)), roomStartY+(rn.nextInt(448)), ID.Miniboss,handler));
                    minibosses = 1;
                }
            }
        }
    }
}
