package com.gdx.main.screen.game.object.cannon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.screen.game.object.projectile.Projectile;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;

public abstract class Cannon {
    // utils
    protected Settings gs;
    protected Manager manager;
    protected Stage stage;

    // position and rotation
    protected Vector2 center;
    protected Vector2 offset;
    protected Vector2 direction;
    protected Vector2 spawnPos;
    protected float offsetAngle;

    // settings
    protected float fireRate;
    public float timer = 0;
    protected float delta;

    // booleans
    protected boolean isFriendly;

    public Cannon(boolean isFriendly, Vector2 center, Vector2 offset,
                  Stage stage, Settings gs, Manager manager) {

        // load parameters
        this.stage = stage;
        this.manager = manager;
        this.gs = gs;
        this.center = new Vector2(center);
        this.offset = new Vector2(offset);
        this.offsetAngle = this.offset.angleDeg() - 90;
        this.spawnPos = new Vector2();
        this.direction = new Vector2();
        this.isFriendly = isFriendly;
    }

    public abstract void fire();

    public abstract void update(float delta, Vector2 center, Vector2 direction);
}
