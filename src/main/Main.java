package main;

import engine.Window;
import engine.GameObject;
import dataStructure.Transform;
import util.Vector2;

public class Main {
    public static void main(String[] args) {
        Window window = Window.getWindow();
        window.init();

        Thread mainThread = new Thread(window);
        mainThread.start();
    }
}