package com.gdx.main.screen.game.object.projectile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.handler.ProjectileHandler;
import com.gdx.main.screen.game.object.GameObject;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;

public abstract class Projectile extends Actor implements GameObject {

    // -- Util -- //
    protected Settings sets;
    protected Manager manager;

    // -- External classes -- //
    protected Stage stage;

    // -- Logics -- //
    // delta time
    protected float delta;

    // vectors and rect
    public Vector2 center;
    protected Vector2 direction;
    protected Vector2 target;
    protected float velocity;
    protected Rectangle rect;
    protected float rotation;

    // other settings
    protected float damage;
    protected float scale;

    // booleans
    protected boolean isFriendly;
    public boolean toRemove;

    // -- Sprite -- //
    protected TextureRegion baseRegion;
    protected TextureRegion[] baseRegions;
    protected TextureRegion[] deathRegions;
    protected Sprite baseSprite;
    protected Sprite deathSprite;

    public Projectile( boolean isFriendly,
            float x, float y, float rectSize, Vector2 initialDirection,
            Stage stage, Settings sets, Manager manager
    ) {
        this.isFriendly = isFriendly;
        this.sets = sets;
        this.manager = manager;
        this.stage = stage;

        // setup vectors
        this.center = new Vector2(x,y);
        this.direction = new Vector2(initialDirection);
        this.target = new Vector2();

        // setup hitbox
        this.rect = new Rectangle();
        this.rect.setSize(rectSize);
        this.rect.setCenter(center);
    }

    protected abstract void loadSprites();

    @Override
    public void update(float delta, Mouse mouse) {
        this.delta = delta;
        if(toRemove) {
            this.remove();  // removes actor
            ProjectileHandler.remove(this);
        }
    }
}
