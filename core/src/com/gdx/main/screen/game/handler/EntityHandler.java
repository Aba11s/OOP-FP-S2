package com.gdx.main.screen.game.handler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.object.entity.*;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/* Handles all game objects */
public class EntityHandler {

    public final static ArrayList<GameEntity> gameEntities = new ArrayList<>();
    private final static ArrayList<GameEntity> entityTrash = new ArrayList<>();

    // stuffs
    private Player player;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage;
    private Stage subStage;
    private Debugger debugger;
    private Stats stats;
    private Manager manager;
    private Settings gs;

    // spawner settings
    private Vector2 position;
    private HashMap<Integer, Integer> weightMap = new HashMap<>();
    private int maxSpace;
    private int currentSpace;
    private float initialSpawnDelay;
    private float spawnDelay;
    private float spawnTimer = 0;

    public EntityHandler(Viewport viewport, OrthographicCamera camera, Stage stage, Stage subStage,
                         Debugger debugger, Stats stats, Manager manager, Settings gs) {
        this.viewport = viewport;
        this.camera = camera;
        this.stage = stage;
        this.subStage = subStage;
        this.debugger  = debugger;
        this.stats = stats;
        this.manager = manager;
        this.gs = gs;

        // spawner
        position = new Vector2(1,1);
        position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2);

        initialSpawnDelay = gs.initialSpawnDelay;
        spawnDelay = 5f;
        maxSpace = 100;
        currentSpace = 100;

        // weight map
        // key = type of enemy, value = weight
        weightMap.put(0, gs.scoutWeight);
        weightMap.put(1, gs.fighterWeight);
        weightMap.put(2, gs.chargerWeight);
    }

    // adds player
    public void setPlayer(Player player) {
        this.player = player;
    }

    // adds entity
    public static void add(GameEntity ent){
        gameEntities.add(ent);
    }

    // removes entity
    public static void remove(GameEntity ent) {
        entityTrash.add(ent);}

    // main updates
    public void update(float delta, Mouse mouse) {

        for(GameEntity entity : gameEntities) {
            entity.update(delta, mouse);
        }

        // remove entities
        for(GameEntity entity : entityTrash) {
            gameEntities.remove(entity);
        }

        entityTrash.clear();

        // spawner
        spawn(delta);

        // debug
        System.out.println("entities:" + gameEntities.size() +","+ entityTrash.size());
    }

    // Spawn Entities
    private void spawn(float delta) {
        if(spawnTimer > spawnDelay) {
            Vector2 spawnPos = new Vector2();

            Vector2 initialDirection = new Vector2();

            switch (randomSpawn()) {
                case 0: // SCOUT
                    for(int i = 0; i < gs.scoutSpawnNumber; i++) {
                        // sets spawn position and direction
                        spawnPos.set(getSpawnPosition());
                        initialDirection.set(player.getCenter().x - spawnPos.x, player.getCenter().y - spawnPos.y);

                        // creates entity
                        new EnemyScout(player, spawnPos.x, spawnPos.y, initialDirection,
                                viewport, camera, stage, subStage, debugger, gs, manager, stats);
                    }
                    break;
                case 1: // FIGHTER
                    for(int i = 0; i < gs.fighterSpawnNumber; i++) {
                        // sets spawn position and direction
                        spawnPos.set(getSpawnPosition());
                        initialDirection.set(player.getCenter().x - spawnPos.x, player.getCenter().y - spawnPos.y);

                        // creates entity
                        new EnemyFighter(player, spawnPos.x, spawnPos.y, initialDirection,
                                viewport, camera, stage, subStage, debugger, gs, manager, stats);
                    }

                    break;
                case 2: // CHARGER
                    for(int i = 0; i < gs.chargerSpawnNumber; i++) {
                        // sets spawn position and direction
                        spawnPos.set(getSpawnPosition());
                        initialDirection.set(player.getCenter().x - spawnPos.x, player.getCenter().y - spawnPos.y);

                        // creates entity
                        new EnemyCharger(player, spawnPos.x, spawnPos.y, initialDirection,
                                viewport, camera, stage, subStage, debugger, gs, manager, stats);
                    }

                    break;
                case 3: // FRIGATE

                    break;
                default:
                    System.out.println("balls");

            }
            spawnDelay = initialSpawnDelay;
            spawnTimer = 0;
        }
        spawnTimer += delta;

    }

    private int randomSpawn() {
        // calcs total weight
        int totalWeight = 0;
        for(int i : weightMap.keySet()) {
            totalWeight += weightMap.get(i);
        }

        // chooses a random integer
        int idx = 0;
        for(double r = Math.random() * totalWeight; idx < weightMap.size() - 1; idx++) {
            r -= weightMap.get(idx);
            if(r <= 0.0) break;
        }
        System.out.println(idx);
        return idx;
    }

    private Vector2 getSpawnPosition() {
        // randomly sets spawn location on a circle
        // with radius 700 (1/2 diagonal of base screen-width = 670px)
        Vector2 spawnPos = new Vector2(0,700);
        float angle = MathUtils.random(0, 360);
        spawnPos.setAngleDeg(angle);
        spawnPos.add(position);
        return spawnPos;
    }

    public void clear() {
        gameEntities.clear();
    }
}
