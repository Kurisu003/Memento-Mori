package Model;

import Controller.Handler1;

import java.util.Random;

public class SpawnEnemiesInRoom {
    public static void spawnEnemies(int roomStartX, int roomStartY, int amountOfEnemies, ID typeOfEnemy, Handler1 handler){

        for (int i = 0; i < amountOfEnemies; i++){
            Random rn = new Random();
            if(typeOfEnemy.equals(ID.Enemy))
                handler.addObject(new Enemy(roomStartX + (rn.nextInt(900) + 64),
                                    roomStartY + (rn.nextInt(410) + 64), ID.Enemy, handler));
            else if(typeOfEnemy.equals(ID.SmartEnemy))
                handler.addObject(new SmartEnemy(roomStartX + (rn.nextInt(900) + 64),
                                roomStartY + (rn.nextInt(410) + 64), ID.SmartEnemy, handler));
        }
    }
}
