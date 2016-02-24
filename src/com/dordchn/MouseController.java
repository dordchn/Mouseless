package com.dordchn;


import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Created by Dor Cohen (dordchn@gmail.com) on 2/10/16.
 */
public class MouseController {
    boolean power = false;
    int mouseSpeed = 0;
    Point mouseDirection = new Point(0,0);

    Point zeroPoint = new Point(0,0);
    MotionThread mt = null;

    public MouseController(){
        mouseDirection = new Point(0,0);
    }

    public int getSpeed(){
        return mouseSpeed;
    }

    public void setSpeed(int speed){
        if (speed>=0) {
            mouseSpeed = speed;
            this.updateMotion();
        }
    }

    public void setDirection(Vector2d direction){
        mouseDirection.setLocation(direction.getNormalized());
        this.updateMotion();
    }

    public void click(){
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void start(){
        power=true;
        this.updateMotion();
    }

    public void stop(){
        power=false;
        this.updateMotion();
    }

    /*************************************/

    private void updateMotion(){
        System.out.println("### power: " + power + ", speed: " + mouseSpeed + ", direction: ("+mouseDirection.x+","+mouseDirection.y+")");
        if (mt!=null && mt.isAlive()) {
            mt.stop_it();
            System.out.println("stopped");
        }

        if (power && mouseSpeed!=0 && !mouseDirection.equals(zeroPoint)){
            mt = new MotionThread(mouseSpeed,mouseDirection );
            mt.start();
            System.out.println("started");
        }
    }
}
