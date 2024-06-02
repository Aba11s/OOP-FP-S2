package com.gdx.main.helper.debug;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class Debugger {
    ShapeRenderer shape = new ShapeRenderer();
    ArrayList<Debuggable> items = new ArrayList<>();

    public Debugger(Camera camera) {
        shape.setProjectionMatrix(camera.combined);
    }

    public void add(Debuggable item) {
        items.add(item);
    }

    public void update() {
        for(Debuggable item : items) {
            item.debug(shape);
        }
    }
}
