package com.gdx.main.screen.game.object.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class Player extends GameEntity{

    private final float acceleration;
    private final float rotationSpeed;
    private final float maxSpeed;
    private final float speedDecay;
    public final float hp, dmg;

    public Player(float x, float y, float rectSize, Vector2 initialDirection,
                  Viewport viewport, OrthographicCamera camera, Stage stage,
                  Settings sets, Manager manager, Stats stats) {
        super(x, y, rectSize, initialDirection, viewport, camera, stage, sets, manager, stats);

        // initial velocity for transiiton
        velocity.set(0,300f);
        direction.set(0,1);

        // import from gs
        acceleration = 300f;
        rotationSpeed = 200f;
        maxSpeed = 300f;
        speedDecay = .98f;
        hp = 100;
        dmg = 0;
    }

    @Override
    protected void loadSprites() {
        // manually load assets
        baseRegion = new TextureRegion(new Texture(
                "textures/object/entity/player/player-ship-1.png"));
        baseSprite = new Sprite(baseRegion);
        baseSprite.setCenter(center.x, center.y);
        baseSprite.setRotation(rotation);
    }

    // Updates player movement
    private void move() {
        /* It is important to multiply everything with delta */
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.add(direction.x * acceleration * delta, direction.y * acceleration * delta);
            velocity.clamp(0, maxSpeed);
        } else {
            velocity.x *= speedDecay;
            velocity.y *= speedDecay;
        }
        center.add(velocity.x * delta, velocity.y * delta);
        rect.setCenter(center);
        baseSprite.setRotation(rotation);
        baseSprite.setCenter(center.x, center.y);
    }

    // Updates player rotation towards mouse cursor
    private void rotate(Mouse mouse) {
        target.set(mouse.x - center.x, mouse.y - center.y);

        // gets angle from vectors and difference between them
        float currentAngle = direction.angleDeg();
        float targetAngle = target.angleDeg();
        float deltaAngle = (((currentAngle - targetAngle) % 360) + 360) % 360; // keeps value between 0 and 360 !IMPORTANT

        // determines whether to rotate clockwise or anti-clockwise, whichever is more efficient
        currentAngle = (deltaAngle > 180) ? currentAngle + (rotationSpeed * delta) : currentAngle - (rotationSpeed * delta);
        rotation = currentAngle - 90;
        direction.setAngleDeg(currentAngle);
    }

    @Override // Debuggable
    public void debug(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        shape.rect(rect.x, rect.y, rect.width, rect.height);
        shape.end();
    }

    @Override
    public void update(float delta, Mouse mouse) {
        this.delta = delta;
        if(isAlive) {
            rotate(mouse);
            move();
        }

        System.out.println(
                new Matrix3()
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        baseSprite.draw(batch);
    }
}
