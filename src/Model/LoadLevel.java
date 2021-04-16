package Model;

import Controller.Handler1;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LoadLevel {

    public LoadLevel(Handler1 handler, BufferedImage wall, BufferedImage floor, BufferedImage door, Camera camera, Graphics g){
        GenerateLevel generatedLevel = new GenerateLevel();
//        int [][] level = new int[6][6];

//        System.out.println(level[0][0]);
        handler.addObject(new Dante(64 * 17 * 2 + (64 * 8), 64 * 9 * 2 + (64 * 4), ID.Dante, handler, camera));

        // Loops through generated level
        for(int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                // If there is supposed to be a level
                // at the given place, a border and
                // a background image get added
                // accordingly
                System.out.print(generatedLevel.getLevel()[i][j]);
                if(generatedLevel.getLevel()[i][j] != 0){

                    // Loops 17 times for rows and
                    // 9 times for columns
                    for(int x = 0; x < 17; x++){
                        for(int y = 0; y < 9; y++){

                            // if its the border, draw the wall
                            if(y == 0 || y == 8 || x == 0 || x == 16) {
                                // 64 * x to draw all the walls
                                // i * 64 * 16 => 64 px * 17 px of the room width
                                // we used 16 and 8 instead of 17 and 9 so that
                                // the walls overlap and the walls aren't 2 thick

//                                if(generatedLevel.getLevel()[i - 1][j] != 0 && y == 0 && x != 8)
//                                    handler.addObject(new Box(64 * x + (j * 64 * 16), 64 * y + (i * 64 * 8),
//                                    ID.Block, wall));
//
//                                if(generatedLevel.getLevel()[i + 1][j] != 0 && y == 8 && x != 8)
//                                    handler.addObject(new Box(64 * x + (j * 64 * 16), 64 * y + (i * 64 * 8),
//                                            ID.Block, wall));
//
//                                if(generatedLevel.getLevel()[i][j - 1] != 0 && y != 4 && x == 0)
//                                    handler.addObject(new Box(64 * x + (j * 64 * 16), 64 * y + (i * 64 * 8),
//                                    ID.Block, wall));
//
//                                if(generatedLevel.getLevel()[i][j + 1] != 0 && y != 4 && x == 16)
                                    handler.addObject(new Box(64 * x + (j * 64 * 16), 64 * y + (i * 64 * 8),
                                    ID.Block, wall));

                                if(generatedLevel.getLevel()[i - 1][j] != 0 && y == 0 && x == 8){
                                    handler.removeLastObject();
                                    handler.addObject(new Door(64 * x + (j * 64 * 16), 64 * y + (i * 64 * 8),
                                    ID.Door, door));
                                }

                                if(generatedLevel.getLevel()[i][j - 1] != 0 && x == 0 && y == 4){
                                    handler.removeLastObject();
                                    handler.addObject(new Door(64 * x + (j * 64 * 16), 64 * y + (i * 64 * 8),
                                    ID.Door, door));
                                }

                            }
                        }
                    }
                }
            }
            System.out.println();
        }


    }

}
