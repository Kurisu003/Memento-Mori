package Model;

public class Camera {
    private double x,y;

    public Camera(double x,double y){
        this.x=x;
        this.y=y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void tick(GameObject object){
//        x+=(((object.getX())-x)-1000/2)*0.05;
//        y+=(((object.getY())-y)-563/2)*0.05;

// Used to keep camera in place in case of trying to go off screen
//        if(x<=0)x=0;
//        if(x>=1032)x=1032;
//        if(y<=0)y=0;
//        if(y>=563+16)y=563+16;

    }
}
