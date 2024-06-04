package com.gdx.main.screen.game.handler;

import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.object.GameObject;
import com.gdx.main.screen.game.object.entity.GameEntity;
import com.gdx.main.screen.game.object.entity.Player;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.LinkedList;

/* Handles all game objects */
public class EntityHandler {

    private final static LinkedList<GameEntity> gameEntities = new LinkedList<>();
    private final static ArrayList<GameEntity> removeEntities = new ArrayList<>();
    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }
    public static void add(GameEntity ent){
        gameEntities.add(ent);
    }

    public static void remove(GameEntity ent) {removeEntities.add(ent);}

    public void update(float delta, Mouse mouse) {

        for(GameEntity entity : gameEntities) {
            entity.update(delta, mouse);
        }
        // remove entities
        gameEntities.remove(removeEntities);
    }
}
