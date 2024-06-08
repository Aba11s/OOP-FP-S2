package com.gdx.main.helper.actor.custom_items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.main.helper.debug.Debuggable;
import com.gdx.main.helper.misc.Mouse;


public class CustomSprite extends Actor implements MouseListener, Debuggable {

    Sprite sprite;
    Rectangle rect;


    public CustomSprite(Texture texture) {
        sprite = new Sprite(texture);
        rect = new Rectangle();
        rect.setSize(texture.getWidth(), texture.getWidth());
    }

    public void setScale(float x, float y) {
        sprite.setScale(x,y);
        rect.setSize(rect.width * x, rect.height * y);
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x,y);
        rect.setPosition(x,y);
    }

    public void setCenter(float x, float y) {
        sprite.setCenter(x, y);
        rect.setCenter(x,y);
    }

    public void setOrigin(float x, float y) {
        sprite.setOrigin(x,y);
    }

    public void setAlpha(float a) {
        sprite.setAlpha(a);
    }

    public void setColor(Color color) {
        sprite.setColor(color);
    }



    @Override
    public void action() {

    }

    @Override
    public void hovered() {

    }

    @Override
    public void clicked() {

    }

    public void update(float delta, Mouse mouse) {
        action();

        if(rect.overlaps(mouse.rect)) {
            hovered();
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                clicked();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void debug(ShapeRenderer shape) {

    }
}
