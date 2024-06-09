package com.gdx.main.screen.game.object.particle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.object.entity.GameEntity;

public class EngineParticle extends FadeParticle {

    GameEntity entity;
    Vector2 center2, offset;
    float offsetAngle;

    public EngineParticle(Texture texture, int cols, int rows, Vector2 center, float scale, float scaleSpeed, float alpha, float fadeSpeed, float speed, boolean loop, float rotation, Color color, Stage stage) {
        super(texture, cols, rows, center, scale, scaleSpeed, alpha, fadeSpeed, speed, loop, rotation, color, stage);
        offset = new Vector2(0,0);
        center2 = new Vector2(center);
    }

    public void setEntity(GameEntity entity, float offsetX, float offsetY) {
        this.entity = entity;
        offset.set(offsetX, offsetY);
        offsetAngle = offset.angleDeg();

        center.set(entity.center);
        center2.set(center.add(offset.setAngleDeg(entity.direction.angleDeg() + offsetAngle - 90)));
    }

    private void followPlayer(float delta) {
        center2.add(entity.velocity.x * delta * 0.9f, entity.velocity.y * delta * 0.8f);
        sprite.setCenter(center2.x, center2.y);
    }

    @Override
    public void update(float delta, Mouse mouse) {
        followPlayer(delta);
        super.update(delta, mouse);
    }
}
