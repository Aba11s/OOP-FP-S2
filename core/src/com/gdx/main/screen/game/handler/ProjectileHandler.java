package com.gdx.main.screen.game.handler;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.screen.game.object.projectile.Projectile;

import java.util.ArrayList;

public class ProjectileHandler {

    Viewport viewport;

    private static final ArrayList<Projectile> projectiles = new ArrayList<>();
    private static final ArrayList<Projectile> removeProjectile = new ArrayList<>();

    public ProjectileHandler(Viewport viewport) {
        this.viewport = viewport;
    }

    public static void add(Projectile projectile) {
        projectiles.add(projectile);
    }

    public static void remove(Projectile projectile) {
        removeProjectile.add(projectile);
    }

    private void checkOffScreen(Projectile projectile) {
        if(projectile.center.x < 0 || projectile.center.x > viewport.getWorldWidth() ||
        projectile.center.y < 0 || projectile.center.y > viewport.getWorldHeight()) {
            projectile.toRemove = true; // mark for removal
        }
    }

    public void update(float delta) {
        for(Projectile projectile : projectiles) {
            projectile.update(delta, null);

            checkOffScreen(projectile);
        }

        // removes projectile marked with toRemove
        projectiles.remove(removeProjectile);
    }

}
