package com.gdx.main.screen.game.object.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.handler.ProjectileHandler;
import com.gdx.main.screen.game.object.GameObject;
import com.gdx.main.screen.game.object.entity.GameEntity;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;

public abstract class Projectile extends Actor implements GameObject {

    // -- Util -- //
    protected Settings gs;
    protected Manager manager;

    // -- External classes -- //
    protected Stage stage;
    protected Stage subStage;

    // -- Logics -- //
    // delta time
    protected float delta;

    // vectors and rect
    public Vector2 center;
    protected Vector2 direction;
    protected Vector2 target;
    protected float velocity;
    protected float rotation;
    public Rectangle rect;
    protected float size;

    // other settings
    public float damage;
    protected float scale;

    // booleans
    public boolean isFriendly;
    public boolean isAlive = true;

    // -- Sprite -- //
    protected Texture texture;
    protected TextureRegion baseRegion;
    protected TextureRegion[] baseRegions;
    protected TextureRegion[] deathRegions;
    protected Sprite baseSprite;
    protected Sprite deathSprite;

    // -- Sound -- //
    protected Sound impact;
    protected float impactVolume;
    protected float impactPitch;

    public Projectile( boolean isFriendly,
            float x, float y, float rectSize, Vector2 initialDirection,
            Stage stage, Stage subStage, Settings gs, Manager manager
    ) {
        this.isFriendly = isFriendly;
        this.gs = gs;
        this.manager = manager;
        this.stage = stage;
        this.subStage = subStage;

        // setup vectors
        this.center = new Vector2(x,y);
        this.direction = new Vector2(initialDirection);
        this.target = new Vector2();

        // setup hitbox
        this.rect = new Rectangle();
        this.rect.setSize(rectSize);
        this.rect.setCenter(center);

        // adds to groups
        this.stage.addActor(this);
        ProjectileHandler.add(this);
        Debugger.add(this);
    }

    public void setSettings(float speed, float damage, float size) {
        this.velocity = speed;
        this.damage = damage;
        this.size = size;
        this.rect.setSize(size);
    }

    public void setSFX(Sound SFX, float volume, float pitch) {
        this.impact = SFX;
        this.impactVolume = volume;
        this.impactPitch = pitch;
    }

    public void setTexture(Texture texture, float scale) {
        this.texture = texture;
        this.baseRegions[0] = new TextureRegion(texture);
        this.baseSprite.setRegion(baseRegions[0]);
        this.scale = scale;
        this.baseSprite.setScale(scale);
    }

    protected abstract void loadSprites();

    protected abstract void loadAudio();

    public abstract void collide(GameEntity entity);

    public abstract void kill();

    public abstract void update(float delta, Mouse mouse);
}
