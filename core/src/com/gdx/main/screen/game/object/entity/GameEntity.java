package com.gdx.main.screen.game.object.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.screen.game.object.GameObject;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

import java.util.LinkedList;

public abstract class GameEntity extends Actor implements GameObject {

    // -- Util -- //
    protected Settings sets;
    protected Manager manager;
    protected Stats stats;

    // -- External classes -- //
    protected Viewport viewport;
    protected OrthographicCamera camera;
    protected Stage stage;

    // -- Logics -- //
    protected float delta;
    // vectors and rect
    protected Vector2 center;
    protected Vector2 direction;
    protected Vector2 target;
    protected Vector2 velocity;
    protected Rectangle rect;
    protected float rotation;
    // states
    protected boolean isAlive = true;
    protected boolean isPlayer = false;
    protected boolean isCollided = false;
    protected boolean isInvincible = false;
    protected boolean isDense = true;
    protected boolean toRemove = false;

    // -- Sprite -- //
    protected TextureRegion baseRegion;
    protected LinkedList<TextureRegion> baseRegions;
    protected TextureRegion[][] particleRegions;
    protected TextureRegion[][] deathRegions;
    // sprite
    protected Sprite baseSprite;
    protected Sprite particleSprite;
    protected Sprite deathSprite;
    // debug renderer
    protected ShapeRenderer shape; // debugging hitbox

    public GameEntity(
            float x, float y, float rectSize, Vector2 initialDirection,
            Viewport viewport, OrthographicCamera camera, Stage stage,
            Settings sets, Manager manager, Stats stats
    ) {
        this.sets = sets;
        this.manager = manager;
        this.stats = stats;
        this.viewport = viewport;
        this.camera = camera;
        this.stage = stage;

        this.center = new Vector2(x,y);
        this.direction = new Vector2(initialDirection);
        this.target = new Vector2();
        this.velocity = new Vector2(0,0);

        this.rect = new Rectangle();
        this.rect.setSize(rectSize);
        this.rect.setCenter(center);
    }

    protected abstract void loadSprites();
}
