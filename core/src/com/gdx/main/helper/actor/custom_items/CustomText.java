package com.gdx.main.helper.actor.custom_items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.main.helper.debug.Debuggable;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.util.TTFGenerator;

/*
Custom text class to generate texts
 */

public class CustomText extends Actor implements MouseListener, Debuggable {
    // text & font
    BitmapFont font;
    Rectangle rect;
    private String text;

    // position & vectors
    Vector2 pos1, pos2;
    public Vector2 velocity;
    Vector2 direction;
    float acceleration = 0;

    public CustomText(String text, String path, float x, float y, boolean centered,
                      float paddingX, float paddingY, int size, Color color) {
        pos1 = new Vector2(x,y);
        pos2 = new Vector2();
        velocity = new Vector2();
        direction = new Vector2(0,1);

        this.text = text;
        font = TTFGenerator.generateBMF(path, size);
        font.setColor(color);

        GlyphLayout layout = new GlyphLayout(font, text);
        rect = new Rectangle();
        rect.setSize(layout.width+paddingX, layout.height+paddingY);

        if(centered) {
            pos2.x = pos1.x - (layout.width/2);
            pos2.y = pos1.y + (layout.height/2);
            rect.setCenter(pos1);
        } else {
            pos2.set(pos1);
            rect.setPosition(pos2);
        }
    }

    public void setColor(Color color) {
        font.setColor(color);
    }

    public void setText(String text) {
        this.text = text;
        GlyphLayout newLayout = new GlyphLayout(font, text);
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    // for screen transition
    public void move(float delta) {
        velocity.add(direction.x * acceleration * delta, direction.y * acceleration * delta);
        pos1.add(velocity.x * delta, velocity.y * delta);
        pos2.add(velocity.x * delta, velocity.y * delta);
        rect.setCenter(pos1);
    }

    public void update(float delta, Mouse mouse) {
        action();

        if(rect.overlaps(mouse.rect)) {
            hovered();
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                clicked();
            }
        }

        move(delta);
    }

    @Override
    public void action() {}

    @Override
    public void hovered() {}

    @Override
    public void clicked() {}

    @Override // Actor
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, text, pos2.x, pos2.y);
    }

    @Override // Debuggable
    public void debug(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        shape.rect(rect.x, rect.y, rect.width, rect.height);
        shape.end();
    }
}
