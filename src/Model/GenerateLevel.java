package Model;

import View.BufferedImageLoader;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GenerateLevel {

    private BufferedImage bufferedImage;

    public GenerateLevel(){
        BufferedImageLoader loader = new BufferedImageLoader();
        this.bufferedImage = loader.loadImage("../LevelSprites.png");
//        BufferedImage test = this.bufferedImage;
//        ArrayList<BufferedImage> availableLevels = new ArrayList<>();
        int [][] level = new int[5][5];
        int roomCount = 1;

//        for(int i = 0; i < bufferedImage.getHeight(); i += 9) {
//            for (int j = 0; j < bufferedImage.getWidth(); j += 17) {
//                availableLevels.add(bufferedImage.getSubimage(j, i, 17, 9));
//            }
//        }

        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                level[i][j]=0;
            }
        }

        level[2][2] = 1;

        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                level[i][j]=0;
            }
        }

//        while(roomCount <= 5){
            for(int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if(level[i][j] > 0){
                        Random rn = new Random();
                        int rand = rn.nextInt(4) + 1;
                        System.out.println(rand);
                        roomCount++;
                        switch (rand) {
                            case (1) -> level[i - 1][j] = roomCount;
                            case (2) -> level[i][j + 1] = roomCount;
                            case (3) -> level[i + 1][j] = roomCount;
                            case (4) -> level[i][j - 1] = roomCount;
                        }
                    }
                }
//            }
        }

        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(level[i][j]);
            }
            System.out.println("\n");
        }
    }
}
