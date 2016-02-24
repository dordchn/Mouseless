package com.dordchn;

import java.awt.*;

/**
 * Created by Dor Cohen (dordchn@gmail.com) on 2/10/16.
 */
public class MotionThread extends Thread {
    int speed = 0;
    Point direction = new Point(0,0);
    boolean activeness = true;

    public MotionThread(int speed, Point direction){
        this.speed = speed;
        this.direction.setLocation(direction);
    }

    public void run(){
        while(activeness) {
            m_sleep(30);
            //System.out.println("moving at: " + speed + ", activeness: " + activeness);
            Point currLoc = MouseInfo.getPointerInfo().getLocation();
            try {
                Robot robot = new Robot();
                robot.mouseMove(currLoc.x+speed*direction.x, currLoc.y+speed*direction.y);

            } catch (AWTException e) { }
        }
    }

    public void stop_it(){
        activeness=false;
    }

    private void m_sleep(long l){
        try {
            sleep(l);
        } catch (InterruptedException e) {
        }
    }
}
