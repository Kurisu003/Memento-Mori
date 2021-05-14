package Model;

import java.io.Serializable;
import java.util.Random;

public class GenerateLevel implements Serializable {

    // Array with 7 so you have a "buffer"
    // left and right and you don't get errors
    // when trying to do something like
    // level[i - 1]
    private static int [][] level = new int[7][7];

    private static GenerateLevel instance = null;

    public static GenerateLevel getInstance(){
        if(instance == null){
            instance = new GenerateLevel();
        }
        return instance;
    }

    public  void generateLevel(int amountRoomsGenerated){
        int roomCount = 1;

        level[3][3] = 1;

        // Roomcount starts at 1
        while(roomCount <= amountRoomsGenerated){
            for(int i = 1; i <= 5; i++) {
                for (int j = 1; j <= 5; j++) {

                    if(roomCount >= amountRoomsGenerated)
                        return;

                    if(level[i][j] > 0){
                        Random rn = new Random();
                        int rand = rn.nextInt(4) + 1;
                        if(i > 1 && i < 5 && j > 1 && j < 5) {
                            // Decides which random direction to generate a room in
                            // if theres already a room in that direction nothing happens
                            // and roomcount DOESN'T get incremented
                            switch (rand) {
                                case (1) -> level[i - 1][j] = level[i - 1][j] == 0 ? roomCount++ : level[i - 1][j];
                                case (2) -> level[i][j + 1] = level[i][j + 1] == 0 ? roomCount++ : level[i][j + 1];
                                case (3) -> level[i + 1][j] = level[i + 1][j] == 0 ? roomCount++ : level[i + 1][j];
                                case (4) -> level[i][j - 1] = level[i][j - 1] == 0 ? roomCount++ : level[i][j - 1];
                            }
                        }
                    }
                }
            }
        }
    }

    public  void clearLevel(){
        for(int i = 0; i <= level.length - 1; i++) {
            for (int j = 0; j <= level.length - 1; j++) {
                level[i][j] = 0;
            }
        }
    }

    public  void printLevel(){
        for(int i = 0; i <= level.length - 1; i++) {
            for (int j = 0; j <= level.length - 1; j++) {
                System.out.print(level[i][j]);
            }
            System.out.println();
        }
    }

    public  int[][] getLevel() {
        return level;
    }

}
