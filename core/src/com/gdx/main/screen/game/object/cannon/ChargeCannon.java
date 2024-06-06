package com.gdx.main.screen.game.object.cannon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.screen.game.object.projectile.BasicBullet;
import com.gdx.main.screen.game.object.projectile.ChargingBullet;
import com.gdx.main.screen.game.object.projectile.HeavyBullet;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;

public class ChargeCannon extends Cannon {

    public enum State{
        RELOADING,
        CHARGING,
        FIRE,
    }

    public State state;

    private final float reloadTime;
    private float reloadTimer;
    private final float chargeTime;
    private float chargeTimer;

    ChargingBullet chargingBullet;

    public Vector2 chargedPosition;
    private float chargeProjectileScale;
    private final float chargeProjectileBaseScale;

    public Sound chargingSFX;

    public ChargeCannon(boolean isFriendly, Vector2 center, Vector2 offset,
                       Stage stage, Stage subStage, Settings gs, Manager manager) {
        super(isFriendly, center, offset, stage, subStage, gs, manager);

        state = State.RELOADING;

        // default reload & charge rate
        // timer always starts at 0
        reloadTime = gs.chargerReloadTime;
        chargeTime = gs.chargerChargeTime;
        reloadTimer = 0;
        chargeTimer = 0;

        chargedPosition = new Vector2();
        chargeProjectileBaseScale = 0.1f;
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
        bulletSFXPitch = 0.3f;
    }

    @Override
    public void kill() {
        if(state == State.CHARGING) {
            chargingBullet.kill();
            chargingSFX.dispose();
        }
    }

    private void stateCheck() {
        // Only God knows what's happening here
        switch (state) {
            case RELOADING:
                if(reloadTimer >= reloadTime) {
                    spawnNewCharged();
                    state = State.CHARGING;
                    reloadTimer = 0;
                } else {
                    reloadTimer += delta;
                }
                break;
            case CHARGING:
                if(chargeTimer >= chargeTime) {
                    chargingBullet.kill();
                    chargeProjectileScale = chargeProjectileBaseScale;
                    chargingSFX.dispose();

                    state = State.FIRE;
                    chargeTimer = 0;
                } else {
                    chargedPosition.set(center.add(offset.setAngleDeg(direction.angleDeg() + offsetAngle)));
                    chargeProjectileScale += 0.2f * delta;
                    chargingBullet.chargeUpdate(chargedPosition, chargeProjectileScale, direction.angleDeg());
                    chargeTimer += delta;
                }
                break;
            case FIRE:
                fire();
                state = State.RELOADING;
                break;
        }
    }

    private void spawnNewCharged() {
        chargedPosition.set(center.add(offset.setAngleDeg(direction.angleDeg() + offsetAngle)));
        chargingBullet = new ChargingBullet(
                false, chargedPosition.x, chargedPosition.y, size, direction,
                stage, subStage, gs, manager);

        chargingSFX = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/charging-1.mp3"));
        long charge_id =  chargingSFX.play();
        chargingSFX.setVolume(charge_id, 0.25f);
        chargingSFX.setPitch(charge_id, 0.5f);
    }

    @Override
    public void fire() {
        // adjust bullet spawn pos according to the offset
        spawnPos.set(center.add(offset.setAngleDeg(direction.angleDeg() + offsetAngle)));

        // setups angle for side bullet
        float secondaryOffset = 25f;

        Vector2 direction2, direction3;
        direction2 = new Vector2(direction);
        direction3 = new Vector2(direction);
        direction2.setAngleDeg(direction.angleDeg() + secondaryOffset);
        direction3.setAngleDeg(direction.angleDeg() - secondaryOffset);


        // creates new bullet - this settings by default
        // call setter methods to change variables
        HeavyBullet newBullet = new HeavyBullet(
                isFriendly ,spawnPos.x, spawnPos.y, size,
                direction, stage, subStage, gs, manager);

        // overrides default settings
        newBullet.setSettings(speed, damage, size);
        newBullet.setTexture(bulletTexture, scale);
        newBullet.setSFX(bulletSFX, bulletSFXVolume, bulletSFXPitch);

        HeavyBullet newBullet2 = new HeavyBullet(
                isFriendly ,spawnPos.x, spawnPos.y, size,
                direction2, stage, subStage, gs, manager);

        // overrides default settings
        newBullet2.setSettings(speed, damage, size);
        newBullet2.setTexture(bulletTexture, scale);
        newBullet2.setSFX(bulletSFX, bulletSFXVolume, bulletSFXPitch);

        HeavyBullet newBullet3 = new HeavyBullet(
                isFriendly ,spawnPos.x, spawnPos.y, size,
                direction3, stage, subStage, gs, manager);

        // overrides default settings
        newBullet3.setSettings(speed, damage, size);
        newBullet3.setTexture(bulletTexture, scale);
        newBullet3.setSFX(bulletSFX, bulletSFXVolume, bulletSFXPitch);

        // play sound
        long id = shootSFX.play();
        shootSFX.setVolume(id, volume);
        shootSFX.setPitch(id, pitch);

        // resets timer
        timer = 0;

    }

    @Override
    public void update(float delta, Vector2 center, Vector2 direction) {
        stateCheck();
        this.delta = delta;
        this.center.set(center);
        this.direction.set(direction);
    }
}

