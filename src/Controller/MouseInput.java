/*package Controller;

import Controller.Handler1;
import Model.Bullet;
import Model.Camera;
import Model.GameObject;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private Handler1 handler;
    private Camera camera;

    public MouseInput(Handler1 handler,Camera camera){
        this.handler=handler;
        this.camera=camera;

    }

    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject temp = handler.objects.get(i);
            if (temp.getId() == ID.Dante) {
                handler.addObject(new Bullet(temp.getX() + 16, temp.getY() + 24, ID.Bullet, handler, mx, my));

            }
        }
    }
}
*/