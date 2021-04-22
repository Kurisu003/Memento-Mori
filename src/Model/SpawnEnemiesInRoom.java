package Model;

import Controller.Handler1;

import java.util.Random;

public class SpawnEnemiesInRoom {
    public static void spawnEnemies(int roomStartX, int roomStartY, int amountOfEnemies, ID typeOfEnemy, Handler1 handler){

        for (int i = 0; i < amountOfEnemies; i++){
            Random rn = new Random();
            if(typeOfEnemy.equals(ID.Enemy)) {
                handler.addObject(new Enemy(roomStartX + (rn.nextInt(960) + 128),
                        roomStartY + (rn.nextInt(448) + 128), ID.Enemy, handler));
            }
            else if(typeOfEnemy.equals(ID.SmartEnemy)) {
                handler.addObject(new SmartEnemy(roomStartX + (rn.nextInt(960) + 128),
                        roomStartY + (rn.nextInt(448) + 128), ID.SmartEnemy, handler));
            }
            else if(typeOfEnemy.equals(ID.ShotEnemy)) {
                handler.addObject(new ShotEnemy(roomStartX + (rn.nextInt(960) + 128),
                        roomStartY + (rn.nextInt(448) + 128), ID.SmartEnemy,handler));
            }

        }
    }
}
