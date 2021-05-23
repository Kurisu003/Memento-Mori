package Model;

import Controller.Handler1;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class loads all the levels which are displayed in the game.
 */
public class LoadLevel{

    /**
     * Clears all levels and loads the current so the old levels are not displayed anymore in the background.
     * @param wallSprites all images which should be displayed for this level
     * @param amountRoomsGenerated how many rooms should be generated in this level
     */
    public static void clearAndLoadLevel(ArrayList<BufferedImage> wallSprites, int amountRoomsGenerated){

        // Clears list from all objects that aren't player Character
        if(amountRoomsGenerated!=0) {
            GenerateLevel.getInstance().clearLevel();
            GenerateLevel.getInstance().generateLevel(amountRoomsGenerated);
            Handler1.getInstance().objects.removeIf(temp -> !temp.getId().equals(ID.Dante));
        }

        // Loops through generated level
        for(int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                // If there is supposed to be a level
                // at the given place, a border gets added
                // accordingly
                if(GenerateLevel.getInstance().getLevel()[i][j] != 0){

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

                                // Actual Code
                                // Handler1.getInstance().addObject(new Box(64 * x + (j * 1088), 64 * y + (i * 576),
                                // 64 * x or 64 * y removed in some places where its 0

                                // To check if its a CORNER's
                                // TOP LEFT CORNER
                                if(x == 0 && y == 0){
                                    Handler1.getInstance().addObject(new Box(j * 1088, i * 576, ID.Block,
                                            wallSprites.get(5), false, false));
                                }
                                // y is always 8 so it can be multiplied
                                // BOTTOM LEFT CORNER
                                else if(x == 0 && y == 8){
                                    Handler1.getInstance().addObject(new Box(j * 1088, 64 * y + i * 576,
                                    ID.Block, wallSprites.get(0), false, false));
                                }
                                // TOP RIGHT CORNER
                                else if(x == 16 && y == 0){
                                    Handler1.getInstance().addObject(new Box(64 * x + j * 1088, i * 576,
                                    ID.Block, wallSprites.get(7), false, false));
                                }
                                // BOTTOM RIGHT CORNER
                                else if(x == 16 && y == 8){
                                    Handler1.getInstance().addObject(new Box(64 * x + j * 1088, 64 * y + i * 576,
                                    ID.Block, wallSprites.get(2), false, false));
                                }

                                // To check Walls
                                // TOP WALL
                                else if(x > 0 && x < 16 && y == 0){
                                    Handler1.getInstance().addObject(new Box(64 * x + j * 1088, i * 576,
                                    ID.Block, wallSprites.get(6), false, false));
                                }
                                // BOTTOM WALL
                                else if(x > 0 && x < 16){
                                    Handler1.getInstance().addObject(new Box(64 * x + j * 1088, 64 * y + i * 576,
                                    ID.Block, wallSprites.get(1), false, false));
                                }
                                // LEFT WALL
                                else if(x == 0){
                                    Handler1.getInstance().addObject(new Box(j * 1088, 64 * y + i * 576,
                                    ID.Block, wallSprites.get(3), false, false));
                                }
                                // RIGHT WALL
                                else {
                                    Handler1.getInstance().addObject(new Box(64 * x + j * 1088, 64 * y + i * 576,
                                    ID.Block, wallSprites.get(4), false, false));
                                }

                                if(GenerateLevel.getInstance().getLevel()[i - 1][j] != 0 && y == 0 && x == 8){
                                    Handler1.getInstance().removeLastObject();
                                    Handler1.getInstance().addObject(new Door(64 * x + j * 1088, i * 576,
                                    ID.Door, wallSprites.get(11), wallSprites.get(15)));
                                }

                                if(GenerateLevel.getInstance().getLevel()[i + 1][j] != 0 && y == 8 && x == 8){
                                    Handler1.getInstance().removeLastObject();
                                    Handler1.getInstance().addObject(new Door(64 * x + j * 1088, 64 * y + i * 576,
                                    ID.Door, wallSprites.get(8), wallSprites.get(12)));
                                }

                                if(GenerateLevel.getInstance().getLevel()[i][j - 1] != 0 && x == 0 && y == 4){
                                    Handler1.getInstance().removeLastObject();
                                    Handler1.getInstance().addObject(new Door(j * 1088, 64 * y + i * 576,
                                    ID.Door, wallSprites.get(9), wallSprites.get(13)));
                                }
                                if(GenerateLevel.getInstance().getLevel()[i][j + 1] != 0 && x == 16 && y == 4){
                                    Handler1.getInstance().removeLastObject();
                                    Handler1.getInstance().addObject(new Door(64 * x + j * 1088, 64 * y + i * 576,
                                    ID.Door, wallSprites.get(10), wallSprites.get(14)));
                                }
                            }
                        }
                    }
                }
            }
        }

        if(Dante.currentLevel == Levels.Limbo) {
            Handler1.getInstance().addObject(new Coin(3 * 1088 + 490, 3 * 576 + 350));
            Handler1.getInstance().addObject(new Coin(3 * 1088 + 490 + 36, 3 * 576 + 350));
            Handler1.getInstance().addObject(new Coin(3 * 1088 + 490 + 34 + 36, 3 * 576 + 350));
            Handler1.getInstance().addObject(new Coin(3 * 1088 + 490 + 18, 3 * 576 + 30 + 350));
            Handler1.getInstance().addObject(new Coin(3 * 1088 + 490 + 18 + 36, 3 * 576 + 30 + 350));
        }
    }
}
