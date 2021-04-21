package Model;

import Controller.Handler1;

import java.util.Random;

public class SpawnEnemies {
    public static void spawnEnemies(int amountOfEnemies, Handler1 handler, GenerateLevel level){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
//                System.out.print(level.getLevel()[i][j]);
                if(level.getLevel()[i][j] > 0){
                    Random rn = new Random();
                    int randX = rn.nextInt(17) + 1;
                    int randY = rn.nextInt(9) + 1;
                    int tempX = i * 64 * 17;
                    int tempY = j * 64 * 9;
                    System.out.print("I: " + " " + i + " " + tempX);
                    System.out.println("   J: " + " " + j + " " + tempY);
                    handler.addObject(new Enemy( tempX - i * 64,  tempY - j * 64, ID.Enemy, handler));
                }
            }
//            System.out.println();
        }
    }
}
