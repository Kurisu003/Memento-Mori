package Controller;

import Controller.Handler1;
import Model.Bullet;
import Model.Camera;
import Model.GameObject;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    
    private Camera camera;

    public MouseInput(Camera camera){
        this.camera=camera;
    }

    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());
        System.out.println(e.getX());
    }
}