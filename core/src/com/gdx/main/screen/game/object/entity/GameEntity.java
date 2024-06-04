package com.gdx.main.screen.game.object.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public abstract class GameEntity extends Actor implements GameObject {

    // -- Util -- //
    protected Settings gs;
    protected Manager manager;
    protected Stats stats;

    // -- External classes -- //
    protected Viewport viewport;
    protected OrthographicCamera camera;
    protected Stage stage;

    // -- Logics -- //
    // delta time
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
    protected TextureRegion[] baseRegions;
    protected TextureRegion[] particleRegions;
    protected TextureRegion[] deathRegions;
    protected Sprite baseSprite;
    protected Sprite particleSprite;
    protected Sprite deathSprite;

    public GameEntity(
            float x, float y, float rectSize, Vector2 initialDirection,
            Viewport viewport, OrthographicCamera camera, Stage stage,
            Debugger debugger, Settings gs, Manager manager, Stats stats
    ) {
        // loads parameter
        this.gs = gs;
        this.manager = manager;
        this.stats = stats;
        this.viewport = viewport;
        this.camera = camera;
        this.stage = stage;

        // setup vectors
        this.center = new Vector2(x,y);
        this.direction = new Vector2(initialDirection);
        this.target = new Vector2();
        this.velocity = new Vector2(0,0);

        // setup hitbox
        this.rect = new Rectangle();
        this.rect.setSize(rectSize);
        this.rect.setCenter(center);

        // adds to handlers
        this.stage.addActor(this);
        EntityHandler.add(this);
        debugger.add(this);

        loadSprites();
    }
    protected abstract void loadSprites();

    @Override
    public void update(float delta, Mouse mouse) {
        this.delta = delta;
        if(toRemove) {
            this.remove();
            EntityHandler.remove(this);
        }
    }
}
