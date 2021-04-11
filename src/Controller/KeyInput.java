package Controller;

import Controller.Handler1;
import Model.GameObject;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handler1 handler1;

    public KeyInput(Handler1 handler1){
        this.handler1=handler1;
    }

    public void keyPressed(KeyEvent e){
        int key=e.getKeyCode();
        for(int i=0; i < handler1.objects.size(); i++){
            GameObject temp= handler1.objects.get(i);
            if(temp.getId()== Model.ID.Dante){
                if(key == KeyEvent.VK_W) handler1.setUp(true);
                if(key == KeyEvent.VK_S) handler1.setDown(true);
                if(key == KeyEvent.VK_A) handler1.setLeft(true);
                if(key == KeyEvent.VK_D) handler1.setRight(true);

                if(key == KeyEvent.VK_UP) handler1.setShootUp(true);
                if(key == KeyEvent.VK_DOWN) handler1.setShootDown(true);
                if(key == KeyEvent.VK_LEFT) handler1.setShootLeft(true);
                if(key == KeyEvent.VK_RIGHT) handler1.setShootRight(true);
            }
        }

    }

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