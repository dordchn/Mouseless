package com.dordchn;

import java.awt.*;

/**
 * Created by Dor Cohen (dordchn@gmail.com) on 2/11/16.
 */
public class Vector2d extends Point {

    public Vector2d(){ super(); }
    public Vector2d(int x, int y){ super(x,y); }

    public Vector2d getNormalized(){
        double vecLen = Math.sqrt(Math.pow(this.getX(),2) + Math.pow(this.getY(),2));
        Vector2d normalized = new Vector2d();
        normalized.setLocation(this.getX()/vecLen,this.getY()/vecLen);
        return normalized;
    }

    public void add(Vector2d vec){
        this.setLocation(this.getX()+vec.getX(), this.getY()+vec.getY());
    }

    public void subtract(Vector2d vec){
        this.setLocation(this.getX()-vec.getX(), this.getY()-vec.getY());
    }
}
