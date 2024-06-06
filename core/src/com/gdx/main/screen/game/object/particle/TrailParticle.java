package com.gdx.main.screen.game.object.particle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.handler.ParticleHandler;

public class TrailParticle extends Particle {

    private final float fadeSpeed;
    public TrailParticle(Texture texture, int cols, int rows, Vector2 center,
                         float scale, float alpha, float speed, float fadeSpeed, boolean loop,
                         Stage stage) {
        super(texture, cols, rows, center, scale, alpha, speed, loop, stage);

        this.fadeSpeed = fadeSpeed;
    }

    @Override
    public void animate() {

    }

    @Override
    public void kill() {
        this.remove();
        ParticleHandler.remove(this);
        Debugger.remove(this);
    }

    @Override
    public void update(float delta, Mouse mouse) {
        alpha -= fadeSpeed * delta;
        sprite.setAlpha(alpha);

        if(alpha <= 0) {done = true;}
        if(done) {kill();}
        System.out.println(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void debug(ShapeRenderer shape) {

    }
}