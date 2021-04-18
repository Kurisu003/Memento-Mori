package Model;

import Controller.Handler1;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LoadLevel {

    public LoadLevel(Handler1 handler, ArrayList<BufferedImage> wallSprites, BufferedImage floor, Camera camera, Graphics g){
        GenerateLevel generatedLevel = new GenerateLevel();
//        int [][] level = new int[6][6];

//        System.out.println(level[0][0]);
        handler.addObject(new Dante(64 * 17 * 2 + (64 * 8) - 128, 64 * 9 * 2 + (64 * 4) - 128, ID.Dante, handler, camera));
        camera.setX(64 * 17 * 2 - 128 + 64 * 2);
        camera.setY(64 * 9 * 2 - 128 + 64 * 2);

        // Loops through generated level
        for(int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                // If there is supposed to be a level
                // at the given place, a border and
                // a background image get added
                // accordingly
                if(generatedLevel.getLevel()[i][j] != 0){

                    // Loops 17 times for rows and
                    // 9 times for columns
                    for(int x = 0; x < 17; x++){
                        for(int y = 0; y < 9; y++){

                            // if its the border, draw the wall
                            if(y == 0 || y == 8 || x == 0 || x == 16) {
                                // 64 * x to draw all the walls
                                // 64 px * 17 px of the room width

                                // 1088 Refers to 64 * width (aka. 64 * 17)
                                // 576 Refers to 64 * height (aka. 64 * 9)

                                // To check if its a CORNER's
                                // TOP LEFT CORNER
                                if(x == 0 && y == 0){
                                    handler.addObject(new Box(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Block, wallSprites.get(5)));
                                }
                                // BOTTOM LEFT CORNER
                                else if(x == 0 && y == 8){
                                    handler.addObject(new Box(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Block, wallSprites.get(0)));
                                }
                                // TOP RIGHT CORNER
                                else if(x == 16 && y == 0){
                                    handler.addObject(new Box(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Block, wallSprites.get(7)));
                                }
                                // BOTTOM RIGHT CORNER
                                else if(x == 16 && y == 8){
                                    handler.addObject(new Box(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Block, wallSprites.get(2)));
                                }

                                // To check Walls
                                // TOP WALL
                                else if(x > 0 && x < 16 && y == 0){
                                    handler.addObject(new Box(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Block, wallSprites.get(6)));
                                }
                                // BOTTOM WALL
                                else if(x > 0 && x < 16){
                                    handler.addObject(new Box(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Block, wallSprites.get(1)));
                                }
                                // LEFT WALL
                                else if(x == 0){
                                    handler.addObject(new Box(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Block, wallSprites.get(3)));
                                }
                                // RIGHT WALL
                                else {
                                    handler.addObject(new Box(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Block, wallSprites.get(4)));
                                }

                                if(generatedLevel.getLevel()[i - 1][j] != 0 && y == 0 && x == 8){
                                    handler.removeLastObject();
                                    handler.addObject(new Door(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Door, wallSprites.get(11)));
                                }

                                if(generatedLevel.getLevel()[i + 1][j] != 0 && y == 8 && x == 8){
                                    handler.removeLastObject();
                                    handler.addObject(new Door(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Door, wallSprites.get(8)));
                                }

                                if(generatedLevel.getLevel()[i][j - 1] != 0 && x == 0 && y == 4){
                                    handler.removeLastObject();
                                    handler.addObject(new Door(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Door, wallSprites.get(9)));
                                }
                                if(generatedLevel.getLevel()[i][j + 1] != 0 && x == 16 && y == 4){
                                    handler.removeLastObject();
                                    handler.addObject(new Door(64 * x + (j * 1088), 64 * y + (i * 576),
                                    ID.Door, wallSprites.get(10)));
                                }

                            }
                        }
                    }
//                    i = 7;
//                    j = 20;
                }
            }
        }


    }

}
