package Model;

import Controller.Handler1;
import View.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class generates all the levels by loading the sprites.
 */
public class GenerateLevel implements Serializable {


    // Array with 7 so you have a "buffer"
    // left and right and you don't get errors
    // when trying to do something like
    // level[i - 1]
    private int [][] level = new int[7][7];

    private static GenerateLevel instance = null;

    public void setLevel(int[][] level) {
        this.level = level;
    }

    /**
     * This returns the current instance
     * @return current instance of GenerateLevel
     */
    public static GenerateLevel getInstance(){
        if(instance == null){
            instance = new GenerateLevel();
        }
        return instance;
    }

    /**
     * This method generates different levels. The number of levels depends on the parameter given.
     * @param amountRoomsGenerated number of levels which should be generated
     */
    public  void generateLevel(int amountRoomsGenerated){
        int roomCount = 1;
        BufferedImageLoader loader = new BufferedImageLoader();

        //obstacle.add(loader.loadImage("../Levels/" + Game.getFolder() + "/Obstacles1.png"));

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

    public void loadObstacles(){
        BufferedImageLoader loader = new BufferedImageLoader();
        for(int i = 0; i <= GenerateLevel.getInstance().getLevel().length - 1; i++) {
            for (int j = 0; j <= GenerateLevel.getInstance().getLevel().length - 1; j++) {
                if(GenerateLevel.getInstance().getLevel()[i][j] > 0){
                    Random rand = new Random();
                    int r = rand.nextInt(3);


//                    if (true) {
                    if (r == 1) {
                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 1, i * 576 + 64 * 1, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));

                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 2, i * 576 + 64 * 1, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));

                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 3, i * 576 + 64 * 1, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));

                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 1, i * 576 + 64 * 2, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));


                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 3, i * 576 + 64 * 6, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));

                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 4, i * 576 + 64 * 6, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));

                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 4, i * 576 + 64 * 5, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));

                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 5, i * 576 + 64 * 5, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));


                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 11, i * 576 + 64 * 2, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));

                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 11, i * 576 + 64 * 4, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));

                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 11, i * 576 + 64 * 5, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));

                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 12, i * 576 + 64 * 2, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));

                        Handler1.getInstance().addObject(new Box(j * 1088 + 64 * 12, i * 576 + 64 * 3, ID.Obstacle, loader.loadImage(
                                "../Levels/" + Game.getFolder() + "/Obstacle.png"), true));

                        Handler1.getInstance().addObject(new DamageObstacle(j * 1088 + 64 * 12, i * 576 + 64 * 4,false));
                    }
                }
            }
        }
    }

    /**
     * This method clears the levels by setting all values to 0
     */
    public  void clearLevel(){
        for(int i = 0; i <= level.length - 1; i++) {
            for (int j = 0; j <= level.length - 1; j++) {
                level[i][j] = 0;
            }
        }
    }

    /**
     * This method prints the level's coordinates
     */
    public  void printLevel(){
        for(int i = 0; i <= level.length - 1; i++) {
            for (int j = 0; j <= level.length - 1; j++) {
                System.out.print(level[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * This method is the getter-method of the level-2D-Array
     * @return all levels saved in the 2D array
     */
    public  int[][] getLevel() {
        return level;
    }

}
