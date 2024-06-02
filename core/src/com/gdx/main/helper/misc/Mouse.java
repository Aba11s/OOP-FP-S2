package com.gdx.main.helper.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.debug.Debuggable;

public class Mouse implements Debuggable {
    Vector3 mouse3D; // Actual mouse pos in 3D
    Vector2 mouse2D; // Projected mouse pos
    public float x,y; // ease of access

    Viewport viewport;
    public Rectangle rect;

    public Mouse(Viewport viewport) {
        mouse3D = new Vector3(0,0,0);
        mouse2D = new Vector2(0,0);
        this.rect = new Rectangle(0,0,1,1);
        this.viewport = viewport;
    }

    public void update() {
        // Adjust projection according to viewport size
        mouse3D.x = Gdx.input.getX();
        mouse3D.y = Gdx.input.getY();
        mouse3D.z = 0;
        viewport.unproject(mouse3D);
        mouse2D.x = mouse3D.x;
        mouse2D.y = mouse3D.y;
        rect.setPosition(mouse2D.x, mouse2D.y);
        x = mouse2D.x; y = mouse2D.y;
    }

    @Override // Debuggable
    public void debug(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        shape.rect(rect.x, rect.y, rect.width, rect.height);
        shape.end();
    }
}
