package Controller;

import Controller.Handler1;
import Model.Bullet;
import Model.Camera;
import Model.GameObject;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class indicates when certain mouse buttons are pressed
 */
public class MouseInput extends MouseAdapter {

    private Camera camera;

    /**
     * Constructor to create an object
     * @param camera the camera on which the mouse buttons should be determined
     */
    public MouseInput(Camera camera){
        this.camera=camera;
    }

    /**
     * Indicates what happens when a certain mouse button is pressed
     * @param e MouseEvent to determine which button was pressed
     */
    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());
//        System.out.println(e.getX());
    }
}