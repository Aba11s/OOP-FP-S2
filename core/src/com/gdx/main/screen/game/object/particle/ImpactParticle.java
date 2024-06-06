package com.gdx.main.screen.game.object.particle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.handler.ParticleHandler;

public class ImpactParticle extends Particle {
    public ImpactParticle(String path, int cols, int rows, Vector2 center,
                          float scale, float alpha, float speed, boolean loop,
                          Stage stage) {
        super(path, cols, rows, center, scale, alpha, speed, loop, stage);
    }

    @Override
    public void animate() {
        sprite.setScale(scale);
        sprite.setAlpha(alpha);

        if(frameIndex < regions.length) {
            sprite.setRegion(regions[frameIndex]);
            frameIncrement += speed/60f;
            frameIndex = (int)frameIncrement;
        }
        else if(frameIndex == regions.length) {
            done = true;
        }
    }

    @Override
    public void kill() {
        this.remove();
        ParticleHandler.remove(this);
        Debugger.remove(this);
    }

    @Override
    public void update(float delta, Mouse mouse) {
        if(!done) {
            animate();
        }
        else {kill();}
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void debug(ShapeRenderer shape) {

    }
}
