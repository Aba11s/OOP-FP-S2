package com.gdx.main.screen.game.handler;

import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.object.GameObject;

import java.util.LinkedList;

/* Handles all game objects */
public class ObjectHandler {

    LinkedList<GameObject> gameObjects = new LinkedList<>();

    public void update(float delta, Mouse mouse) {

        for(GameObject object : gameObjects) {
            object.update(delta, mouse);
        }
    }
}
