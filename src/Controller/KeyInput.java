package Controller;

import Model.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This handles all key inputs so the player can move around and shoot. With x the hitboxes can be seen and with esc
 * pressed in-game a menu appears to save the game or to set the music and sound volume.
 */
public class KeyInput extends KeyAdapter {

    Handler1 handler1;

    /**
     * Constructor to create an instance
     * @param handler1 the object which should be handled
     */
    public KeyInput(Handler1 handler1){
        this.handler1=handler1;
    }

    /**
     * Indicates what happens when certain keys on the keyboard are pressed
     * @param e KeyEvent to determine which key was pressed
     */
    public void keyPressed(KeyEvent e){
        int key=e.getKeyCode();
        for(int i=0; i < handler1.objects.size(); i++){
            GameObject temp= handler1.objects.get(i);
            if(temp.getId()== Model.ID.Dante){
                if(key == KeyEvent.VK_W) {handler1.setUp   (true);}
                if(key == KeyEvent.VK_S) {handler1.setDown (true);}
                if(key == KeyEvent.VK_A) {handler1.setLeft (true);}
                if(key == KeyEvent.VK_D) {handler1.setRight(true);}

                if(key == KeyEvent.VK_UP) handler1.setShootUp(true);
                if(key == KeyEvent.VK_DOWN) handler1.setShootDown(true);
                if(key == KeyEvent.VK_LEFT) handler1.setShootLeft(true);
                if(key == KeyEvent.VK_RIGHT) handler1.setShootRight(true);
                if(key == KeyEvent.VK_X) handler1.setHitbox(!Game.showHitbox);


                if(key == KeyEvent.VK_ESCAPE && Game.getState().equals(GameState.MainMenu)){
//                    Camera.getInstance().setX(1088);
                    MainMenu.setCamera(1088,0);
                }
                else if(key == KeyEvent.VK_ESCAPE && Game.getState().equals(GameState.Game)){
                    EscMenu.setTempCamCoordinates(Camera.getInstance().getX(), Camera.getInstance().getY());
                    Game.setState(GameState.EscMenu);
                }
                else if(key == KeyEvent.VK_ESCAPE && Game.getState().equals(GameState.EscMenu)){
                    Game.setState(GameState.Game);
                    Camera.getInstance().setX(EscMenu.getTempCamX());
                    Camera.getInstance().setY(EscMenu.getTempCamY());
                }
            }
        }
    }

    /**
     * Indicates what happens when certain keys on the keyboard are released
     * @param e KeyEvent to determine which key was released
     */
    public void keyReleased(KeyEvent e){
        int key=e.getKeyCode();
        for(int i=0;i<handler1.objects.size();i++){
            GameObject temp= handler1.objects.get(i);
            if(temp.getId()== Model.ID.Dante){
                if(key == KeyEvent.VK_W) handler1.setUp(false);
                if(key == KeyEvent.VK_S) handler1.setDown(false);
                if(key == KeyEvent.VK_A) handler1.setLeft(false);
                if(key == KeyEvent.VK_D) handler1.setRight(false);

                if(key == KeyEvent.VK_UP) handler1.setShootUp(false);
                if(key == KeyEvent.VK_DOWN) handler1.setShootDown(false);
                if(key == KeyEvent.VK_LEFT) handler1.setShootLeft(false);
                if(key == KeyEvent.VK_RIGHT) handler1.setShootRight(false);
            }
        }
    }

}
