package com.gdx.main.screen.game.object.entity;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.handler.EntityHandler;
import com.gdx.main.screen.game.object.GameObject;
import com.gdx.main.screen.game.object.projectile.Projectile;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

import java.util.ArrayList;

public abstract class GameEntity extends Actor implements GameObject {

    // -- Util -- //
    protected Settings gs;
    protected Manager manager;
    protected Stats stats;

    // -- External classes -- //
    protected Viewport viewport;
    protected OrthographicCamera camera;
    protected Stage stage;
    protected Stage subStage;

    // -- Logics -- //
    protected float delta;

    // vectors and rect
    public Vector2 center;
    public Vector2 direction;
    protected Vector2 target;
    public Vector2 velocity;
    public float rotation;
    public Rectangle rect;

    // states
    public boolean isPlayer = false;
    public boolean isDense = true;
    public boolean isAlive = true;
    public boolean isActive = false;
    protected boolean isInvincible = false;

    protected ArrayList<Projectile> collideWith = new ArrayList<>();

    public float hp, dmg;

    // -- Sprite -- //
    protected Texture baseTexture;
    protected TextureRegion baseRegion;
    protected TextureRegion[] baseRegions;
    protected Sprite baseSprite;

    protected int frameIndex = 0;
    protected float frameIncrement = 0;

    // -- Sound -- //
    protected Sound deathSFX;
    protected boolean isPlayed = false;

    public GameEntity(
            float x, float y, Vector2 initialDirection,
            Viewport viewport, OrthographicCamera camera, Stage stage, Stage subStage,
            Debugger debugger, Settings gs, Manager manager, Stats stats
    ) {
        // loads parameter
        this.gs = gs;
        this.manager = manager;
        this.stats = stats;
        this.viewport = viewport;
        this.camera = camera;
        this.stage = stage;
        this.subStage = subStage;

        // setup vectors
        this.center = new Vector2(x,y);
        this.direction = new Vector2(initialDirection);
        this.target = new Vector2();
        this.velocity = new Vector2(0,0);

        // setup hitbox
        this.rect = new Rectangle();
        this.rect.setSize(10,10); // default
        this.rect.setCenter(center);

        // adds to handlers
        this.stage.addActor(this);
        EntityHandler.add(this);
        Debugger.add(this);

        loadSprites();
    }

    protected abstract void loadSprites();

    public abstract void collide(GameEntity entity);

    public abstract void collide(Projectile projectile);

    public abstract void kill(); // force remove

    public abstract void update(float delta, Mouse mouse);
}
