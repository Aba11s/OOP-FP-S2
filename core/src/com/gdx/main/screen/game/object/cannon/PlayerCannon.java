package com.gdx.main.screen.game.object.cannon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.screen.game.object.projectile.BasicBullet1;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;

public class PlayerCannon extends Cannon {

    Sound shootSFX;

    public PlayerCannon(boolean isFriendly, Vector2 center, Vector2 offset, Stage stage, Settings gs, Manager manager) {
        super(isFriendly, center, offset, stage, gs, manager);

        // default settings
        fireRate = gs.playerFireRate;

        // audio
        shootSFX = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/laser-1.wav"));
    }

    @Override
    public void fire() {
        if(timer > 1/fireRate) {
            // adjust bullet spawn pos according to the offset
            spawnPos.set(center.add(offset.setAngleDeg(direction.angleDeg() + offsetAngle)));

            // creates new bullet
            BasicBullet1 newBullet = new BasicBullet1(
                    isFriendly ,spawnPos.x, spawnPos.y, 5,
                    direction, stage, gs, manager);

            // play sound
            long id = shootSFX.play();
            shootSFX.setVolume(id, 0.05f);
            shootSFX.setPitch(id, 1.5f);

            // resets timer
            timer = 0;
        }
    }

    @Override
    public void update(float delta, Vector2 center, Vector2 direction) {
        timer += delta;
        this.center.set(center);
        this.direction.set(direction);
    }
}
