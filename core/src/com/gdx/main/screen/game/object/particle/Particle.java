package com.gdx.main.screen.game.object.particle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.main.screen.game.handler.ParticleHandler;

public abstract class Particle extends Actor {

    Animation<TextureRegion> animation;
    Rectangle rect;
    Vector2 center;
    float speed; // animation speed
    float stateTime;
    public Particle(String path, int sheetRows, int sheetCols,
            float x, float y, float speed) {
        // loads texture
        Texture texture = new Texture(path);
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth()/sheetRows,
                texture.getHeight()/sheetCols);

        // converts 2d into 1d
        TextureRegion[] regionFrames = new TextureRegion[sheetRows * sheetCols];
        int index = 0;
        for(int i = 0; i < sheetRows; i++) {
            for(int j = 0; j < sheetCols; j++) {
                regionFrames[index++] = tmp[i][j];
            }
        }

        // animation
        animation = new Animation<>(1/speed, regionFrames);
        stateTime = 0f;

        this.center = new Vector2(x,y);
        rect = new Rectangle();
        rect.setSize(regionFrames[0].getRegionWidth(), regionFrames[0].getRegionHeight());
        rect.setCenter(center);

        ParticleHandler.add(this);
    }
    public abstract void update(float delta);

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime);
        batch.draw(currentFrame, rect.x, rect.y);
    }
}
