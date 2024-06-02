package com.gdx.main.helper.ui.custom_items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.main.helper.debug.Debuggable;
import com.gdx.main.helper.misc.Mouse;

/*
Custom button class to make custom buttons
 */
public abstract class CustomButton extends Actor implements MouseListener, Debuggable {

    Rectangle rect;

    public CustomButton(float x, float y, boolean centered, float width, float height) {
        rect = new Rectangle();
        rect.setSize(width, height);
        if(centered) {rect.setCenter(x,y);} else {rect.setPosition(x,y);}
    }

    public void update(float delta, Mouse mouse) {
        if(rect.overlaps(mouse.rect)) {
            hovered();
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                clicked();
            }
        }
    }

    @Override // MouseListener
    public void action() {}

    @Override // MouseListener
    public void hovered() {}

    @Override // MouseListener
    public void clicked() {}

    @Override
    public void draw(Batch batch, float parentAlpha) {}

    @Override // Debuggable
    public void debug(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        shape.rect(rect.x, rect.y, rect.width, rect.height);
        shape.end();
    }
}
