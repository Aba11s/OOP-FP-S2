package com.gdx.main.helper.actor.custom_items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.debug.Debuggable;

public class CustomBackground extends Actor implements Debuggable {

    TextureRegion region;
    Vector2 scrollVel;
    Vector2 scroll;
    Viewport viewport;
    int tilesX, tilesY;

    public CustomBackground(String path, float scale, float scrollX, float scrollY, Viewport viewport) {
        region = new TextureRegion(new Texture(path));
        this.viewport = viewport;

        scrollVel = new Vector2(scrollX, scrollY);
        scroll = new Vector2(0,0);
        tilesX = (int) Math.ceil(viewport.getWorldWidth() / region.getRegionWidth()) + 1;
        tilesY = (int) Math.ceil(viewport.getWorldHeight() / region.getRegionHeight()) + 1;
    }

    public void update(float delta) {
        scroll.add(scrollVel.x * delta,  scrollVel.y * delta);

        //resets scroll
        if(Math.abs(scroll.x) > region.getRegionWidth()) {scroll.x = 0;}
        if(Math.abs(scroll.y) > region.getRegionHeight()) {scroll.y = 0;}
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        for(int i = 0; i < tilesY; i++) {
            for(int j = 0; j < tilesX; j++) {
                batch.draw(region,
                        (j * region.getRegionWidth() + scroll.x),
                        (i * region.getRegionHeight() + scroll.y));
            }
        }
    }

    @Override
    public void debug(ShapeRenderer shape) {

    }
}
