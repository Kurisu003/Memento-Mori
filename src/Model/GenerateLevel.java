package Model;

import View.BufferedImageLoader;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GenerateLevel {

    private BufferedImage bufferedImage;

    public GenerateLevel(){
        BufferedImageLoader loader = new BufferedImageLoader();
        this.bufferedImage = loader.loadImage("../LevelSprites.png");
        ArrayList<BufferedImage> availableLevels = new ArrayList<>();
        ArrayList<ArrayList<BufferedImage>> level = new ArrayList<>();

        for(int i = 0; i < bufferedImage.getHeight(); i += 9) {
            for (int j = 0; j < bufferedImage.getWidth(); j += 17) {
                availableLevels.add(bufferedImage.getSubimage(j, i, 17, 9));
            }
        }

        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

            }
        }

    }
}
