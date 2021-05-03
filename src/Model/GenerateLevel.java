package Model;

import java.util.Random;

public class GenerateLevel {

    // Array with 7 so you have a "buffer"
    // left and right and you don't get errors
    // when trying to do something like
    // level[i - 1]
    private static int [][] level = new int[7][7];

    public static void generateLevel(){
        int roomCount = 1;

        level[3][3] = 1;

        // Roomcount starts at 1
        while(roomCount <= 5){
            for(int i = 1; i <= 5; i++) {
                for (int j = 1; j <= 5; j++) {

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

    public static void clearLevel(){
        for(int i = 0; i <= level.length - 1; i++) {
            for (int j = 0; j <= level.length - 1; j++) {
                level[i][j] = 0;
            }
        }
    }

    public static int[][] getLevel() {
        return level;
    }

}
