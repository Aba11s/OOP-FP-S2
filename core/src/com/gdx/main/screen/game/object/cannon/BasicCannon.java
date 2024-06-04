package com.gdx.main.screen.game.object.cannon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.screen.game.handler.ProjectileHandler;
import com.gdx.main.screen.game.object.projectile.BasicBullet;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import sun.jvm.hotspot.debugger.cdbg.CDebugger;

public class BasicCannon extends Cannon {

    public BasicCannon(boolean isFriendly, Vector2 center, Vector2 offset, Stage stage, Settings gs, Manager manager) {
        super(isFriendly, center, offset, stage, gs, manager);

        // default settings
        fireRate = 1f;
    }

    @Override
    public void fire() {
        if(timer > 1/fireRate) {
            // adjust bullet spawn pos according to the offset
            spawnPos.set(center.add(offset.setAngleDeg(direction.angleDeg() + offsetAngle)));

            // creates new bullet
            BasicBullet newBullet = new BasicBullet(
                    isFriendly ,spawnPos.x, spawnPos.y, 8,
                    direction, stage, gs, manager);
            stage.addActor(newBullet);
            ProjectileHandler.add(newBullet);

            System.out.println(offset + "," + center);

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
