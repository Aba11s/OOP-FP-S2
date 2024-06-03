package com.gdx.main.screen.game.object.particle;

import com.badlogic.gdx.graphics.g2d.Batch;

public class TrailParticle extends Particle {
    public TrailParticle(String path, int sheetRows, int sheetCols, float x, float y, float speed) {
        super(path, sheetRows, sheetCols, x, y, speed);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }
}
