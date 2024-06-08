package com.gdx.main.screen.game.handler;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.screen.game.object.projectile.Projectile;

import java.util.ArrayList;

public class ProjectileHandler {

    Viewport viewport;

    public static final ArrayList<Projectile> projectiles = new ArrayList<>();
    private static final ArrayList<Projectile> projectileTrash = new ArrayList<>();

    public ProjectileHandler(Viewport viewport) {
        this.viewport = viewport;
    }

    public static void add(Projectile projectile) {
        projectiles.add(projectile);
    }

    public static void remove(Projectile projectile) {
        projectileTrash.add(projectile);
    }

    private void checkOffScreen(Projectile projectile) {
        if(projectile.center.x < 0 || projectile.center.x > viewport.getWorldWidth() ||
        projectile.center.y < 0 || projectile.center.y > viewport.getWorldHeight()) {
            projectile.kill(); // mark for removal
        }
    }

    public void update(float delta) {
        for(Projectile projectile : projectiles) {
            projectile.update(delta, null);
            checkOffScreen(projectile);
        }
        // removes projectile present in trash bin
        for(Projectile projectile : projectileTrash) {
            projectiles.remove(projectile);
        }

        // clears trash
        projectileTrash.clear();

        // debug
//        System.out.println("projectiles:" + projectiles.size() +","+ projectileTrash.size());
    }

    public void clear() {
        projectiles.clear();
    }

}
