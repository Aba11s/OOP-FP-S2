package com.gdx.main.screen.game.object.cannon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
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
    protected Stage subStage;

    // position and rotation
    public Vector2 center;
    public Vector2 offset;
    protected Vector2 direction;
    protected Vector2 spawnPos;
    protected float offsetAngle;

    // settings
    protected float fireRate;
    public float timer = 0;
    protected float delta;

    // booleans
    protected boolean isFriendly;

    // Audio
    protected Sound shootSFX;
    protected float volume;
    protected float pitch;

    // Bullet settings
    float speed;
    float damage;
    float size;

    // bullet texture
    Texture bulletTexture;
    float scale;

    // bullet sfx
    protected Sound bulletSFX;
    protected float bulletSFXVolume;
    protected float bulletSFXPitch;

    public Cannon(boolean isFriendly, Vector2 center, Vector2 offset,
                  Stage stage, Stage subStage, Settings gs, Manager manager) {

        // load parameters
        this.stage = stage;
        this.subStage = subStage;
        this.manager = manager;
        this.gs = gs;
        this.center = new Vector2(center);
        this.offset = new Vector2(offset);
        this.offsetAngle = this.offset.angleDeg() - 90;
        this.spawnPos = new Vector2();
        this.direction = new Vector2();
        this.isFriendly = isFriendly;
    }
    public void setSettings(float fireRate) {
        this.fireRate = fireRate;
    }

    public void setSFX(String path, float volume, float pitch) {
        shootSFX = Gdx.audio.newSound(Gdx.files.internal(path));
        this.volume = volume;
        this.pitch = pitch;
    }

    public void setBulletSettings(float speed, float damage, float size) {
        this.speed = speed;
        this.damage = damage;
        this.size = size;
    }

    public void setBulletSFX(String path, float volume, float pitch) {
        this.bulletSFX = Gdx.audio.newSound(Gdx.files.internal(path));
        this.bulletSFXVolume = volume;
        this.bulletSFXPitch = pitch;
    }

    public void setBulletTexture(String path, float scale) {
        this.bulletTexture = new Texture(path);
        this.scale = scale;
    }

    public abstract void fire();

    public abstract void update(float delta, Vector2 center, Vector2 direction);
}
