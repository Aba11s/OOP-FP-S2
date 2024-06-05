package com.gdx.main.screen.game.object.particle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.helper.misc.Mouse;

public class TrailParticle extends Particle {
    public TrailParticle(String path, int cols, int rows, Vector2 center,
                         float scale, float alpha, float speed, boolean loop,
                         Stage stage) {
        super(path, cols, rows, center, scale, alpha, speed, loop, stage);
    }

    @Override
    public void animate() {

    }

    @Override
    public void kill() {

    }

    @Override
    public void update(float delta, Mouse mouse) {
        if(!done) {
            animate();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

    @Override
    public void debug(ShapeRenderer shape) {

    }
}