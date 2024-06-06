package com.gdx.main.screen.game.object.cannon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.screen.game.object.projectile.BasicBullet;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;

public class ChargeCannon extends Cannon {

    private enum State{
        RELOADING,
        CHARGING,
        FIRE,
    }

    private State state;

    private float reloadTime;
    private float reloadTimer;
    private float chargeTime;
    private float chargeTimer;

    private Vector2 chargeProjectileOffset;
    private float chargeProjectileScale;
    private float chargeProjectileBaseScale;

    public ChargeCannon(boolean isFriendly, Vector2 center, Vector2 offset,
                       Stage stage, Stage subStage, Settings gs, Manager manager) {
        super(isFriendly, center, offset, stage, subStage, gs, manager);

        state = State.RELOADING;

        // default reload & charge rate
        // timer always starts at 0
        reloadTime = 2f;
        chargeTime = 3f;
        reloadTimer = 0;
        chargeTimer = 0;

        chargeProjectileOffset = new Vector2(0,40);
        chargeProjectileBaseScale = 1f;
        chargeProjectileScale = chargeProjectileBaseScale;

        shootSFX = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/laser-1.wav"));
        volume = 0.1f;
        pitch = 1f;

        // default bullet settings
        speed = gs.bullet1Speed;
        damage = gs.bullet1Damage;
        size = gs.bullet1Size;

        bulletTexture = new Texture("01.png");
        scale = 0.3f;

        bulletSFX = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/impact-1.mp3"));
        bulletSFXVolume = 0.1f;
        bulletSFXPitch = 1f;
    }

    private void stateCheck() {

        switch (state) {
            case RELOADING:
                if(reloadTimer >= reloadTime) {
                    state = State.CHARGING;
                    reloadTimer = 0;
                } else {
                    reloadTimer += delta;
                }
                break;
            case CHARGING:
                if(chargeTimer >= chargeTime) {
                    state = State.FIRE;
                    chargeTimer = 0;
                } else {
                    chargeProjectile();
                    chargeTimer += delta;
                }
                break;
            case FIRE:
                fire();
                state = State.RELOADING;
                break;
        }
    }

    public void chargeProjectile() {
        chargeProjectileScale += 0.5f * delta;
    }

    @Override
    public void fire() {
        if(timer > 1/fireRate) {
            // adjust bullet spawn pos according to the offset
            spawnPos.set(center.add(offset.setAngleDeg(direction.angleDeg() + offsetAngle)));

            // creates new bullet - this settings by default
            // call setter methods to change variables
            BasicBullet newBullet = new BasicBullet(
                    isFriendly ,spawnPos.x, spawnPos.y, size,
                    direction, stage, subStage, gs, manager);

            // overrides default settings
            newBullet.setSettings(speed, damage, size);
            newBullet.setTexture(bulletTexture, scale);
            newBullet.setSFX(bulletSFX, bulletSFXVolume, bulletSFXPitch);

            // play sound
            long id = shootSFX.play();
            shootSFX.setVolume(id, volume);
            shootSFX.setPitch(id, pitch);

            // resets timer
            timer = 0;
        }
    }

    @Override
    public void update(float delta, Vector2 center, Vector2 direction) {
        this.delta = delta;
        this.center.set(center);
        this.direction.set(direction);
    }
}

