package com.dordchn;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by Dor Cohen (dordchn@gmail.com) on 2/10/16.
 */

public class Main implements NativeKeyListener {

    private static class Direction {
        public Vector2d vec;
        public boolean enabled = false;
        public Direction(int x, int y){ this.vec = new Vector2d(x,y); }
    }

    static MouseController mc = new MouseController();
    static HashMap<Integer,Direction> arrows;

    public static void main(String[] args) {

        updatePower();
        initArrows();
        mc.setSpeed(6);

        try {
            GlobalScreen.registerNativeHook();
            LogManager.getLogManager().reset();
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new Main());

        // GlobalScreen.unregisterNativeHook();
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()) + "==" + e.getKeyCode());

        if (e.getKeyCode() == NativeKeyEvent.VC_NUM_LOCK)
            updatePower();

        switch (e.getKeyCode()){
            case 78:
                mc.setSpeed(mc.getSpeed()+2);
                break;
            case 74:
                mc.setSpeed(mc.getSpeed()-2);
                break;
            case 76:
                mc.click();
        }

        if (arrows.containsKey(e.getKeyCode()) && !arrows.get(e.getKeyCode()).enabled) {
            arrows.get(e.getKeyCode()).enabled=true;
            updateDirection();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (arrows.containsKey(e.getKeyCode())) {
            arrows.get(e.getKeyCode()).enabled=false;
            updateDirection();
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_NUM_LOCK)
            updatePower();
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    private static void updatePower(){
        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK))
            mc.stop();
        else
            mc.start();
    }

    private static void initArrows(){
        arrows = new HashMap<>();
        arrows.put(79,new Direction(-1,1)); // 1
        arrows.put(80,new Direction(0,1)); // 2
        arrows.put(81,new Direction(1,1)); // 3

        arrows.put(75,new Direction(-1,0)); // 4
        arrows.put(77,new Direction(1,0)); // 6

        arrows.put(71,new Direction(-1,-1)); // 7
        arrows.put(72,new Direction(0,-1)); // 8
        arrows.put(73,new Direction(1,-1)); // 9

        System.out.println(arrows.size());
    }

    private static void updateDirection(){
        Vector2d vec = new Vector2d(0,0);
        for (Map.Entry<Integer,Direction> entry : arrows.entrySet()){
            if (entry.getValue().enabled)
                vec.add(entry.getValue().vec);
        }
        mc.setDirection(vec);
    }
}

