package com.gdx.main.screen.game.object.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.handler.ParticleHandler;
import com.gdx.main.screen.game.object.GameObject;
import com.gdx.main.util.Manager;
import org.w3c.dom.Text;

public abstract class Particle extends Actor implements GameObject {

    TextureRegion[] regions;
    Sprite sprite;
    Manager manager;

    Vector2 center; // position vector
    float scale; // scales size of image
    float alpha; // controls opacity
    boolean loop; // loops particle animation or not
    boolean done; // checks if particle is done animating

    int frameIndex = 0;
    float frameIncrement = 0;
    float speed;

    public Particle(Texture texture, int cols, int rows, Vector2 center,
                    float scale, float alpha, float speed, boolean loop,
                    Stage stage) {
        this.center = new Vector2(center);
        this.scale = scale;
        this.alpha = alpha;
        this.speed = speed;
        this.loop = loop;

        int tWidth = texture.getWidth(); int tHeight = texture.getHeight();

        // splits spritesheet into a 2d array
        TextureRegion[][] tmp = TextureRegion.split(texture,
                tWidth / cols,
                tHeight / rows);

        // converts 2d into 1d array
        regions = new TextureRegion[cols*rows];
        int idx = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                regions[idx++] = tmp[i][j];
            }
        }

        // creates sprite
        sprite = new Sprite(regions[0]);
        sprite.setCenter(center.x, center.y);
        sprite.setScale(scale);
        sprite.setAlpha(alpha);

        // adds this to handlers and stage
        stage.addActor(this);
        ParticleHandler.add(this);
        Debugger.add(this);
    }

    public abstract void animate(float delta); // iterates over array

    public abstract void kill(); // deletes all references to particle

    @Override
    public abstract void update(float delta, Mouse mouse);

    @Override
    public abstract void draw(Batch batch, float parentAlpha);

    @Override
    public abstract void debug(ShapeRenderer shape);
}
