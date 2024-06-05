package com.gdx.main.screen.game.handler;

import com.gdx.main.screen.game.object.entity.GameEntity;
import com.gdx.main.screen.game.object.entity.Player;
import com.gdx.main.screen.game.object.projectile.Projectile;

public class CollisionHandler {

    Player player;
    public void setPlayer(Player player) {
        this.player = player;
    }

    // player - enemy collision
    private void checkPlayerEnemyCollision() {
        // O(n)
        for(GameEntity entity : EntityHandler.gameEntities) {
            if(!entity.isPlayer && entity.rect.overlaps(player.rect)) {
                player.collide(entity);
            }
        }
    }

    private void checkEntityProjectileCollision() {
        // O(n ^ only God knows)
        for(GameEntity entity : EntityHandler.gameEntities) {
            if(!entity.isPlayer) {
                for(Projectile projectile : ProjectileHandler.projectiles) {
                    if(projectile.isFriendly && projectile.rect.overlaps(entity.rect)) {
                        System.out.println("Bullet hits enemy");
                        if(entity.isAlive) {
                            projectile.collide(entity);
                            entity.collide(projectile);
                        }
                    }
                }
            } else {
                for(Projectile projectile : ProjectileHandler.projectiles) {
                    if(!projectile.isFriendly && projectile.rect.overlaps(entity.rect)) {
                        System.out.println("Player got hit");
                    }
                }
            }
        }
    }

    public void update(float delta) {
        checkPlayerEnemyCollision();
        checkEntityProjectileCollision();
    }
}
