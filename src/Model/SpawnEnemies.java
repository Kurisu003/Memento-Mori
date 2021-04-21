package Model;

import Controller.Handler1;

import java.util.Random;

public class SpawnEnemies {
    public static void spawnEnemies(int amountOfEnemies, Handler1 handler, GenerateLevel level){
        int amountOfEnemiesCounter = 0;
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
//                System.out.print(level.getLevel()[i][j]);
                if(level.getLevel()[i][j] > 0){
                    amountOfEnemiesCounter = 0;
                    while (amountOfEnemiesCounter < amountOfEnemies) {
                        Random rn = new Random();
                        handler.addObject(new Enemy(i * 1088 + rn.nextInt(960) + 128, j * 576 +
                                                    rn.nextInt(448) + 128, ID.Enemy, handler));
                        amountOfEnemiesCounter++;
                    }
                }
            }
//            System.out.println();
        }
    }
}
