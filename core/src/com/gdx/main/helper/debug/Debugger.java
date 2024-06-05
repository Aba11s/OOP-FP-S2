package com.gdx.main.helper.debug;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class Debugger {
    ShapeRenderer shape = new ShapeRenderer();
    public static ArrayList<Debuggable> items = new ArrayList<>();
    public static ArrayList<Debuggable> itemTrash = new ArrayList<>();

    public Debugger(Camera camera) {
        shape.setProjectionMatrix(camera.combined);
    }

    public static void add(Debuggable item) {items.add(item);}

    public static void remove(Debuggable item) {
        itemTrash.add(item);}


    public void update() {
        for(Debuggable item : items) {
            item.debug(shape);
        }
        // remove item in array that's present in trash
        for(Debuggable item : itemTrash) {
            items.remove(item);
        }
        // clears trash
        itemTrash.clear();

        System.out.println(items.size());
    }
}
