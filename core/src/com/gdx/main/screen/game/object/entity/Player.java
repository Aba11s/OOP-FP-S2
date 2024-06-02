package com.gdx.main.screen.game.object.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class Player extends GameEntity{

    private float acceleration;
    private float rotationSpeed;
    private float maxSpeed;
    private float speedDecay;
    public float hp, dmg;

    public Player(float x, float y, float rectSize, Vector2 initialDirection,
                  Viewport viewport, OrthographicCamera camera, Stage stage,
                  Settings sets, Manager manager, Stats stats) {
        super(x, y, rectSize, initialDirection, viewport, camera, stage, sets, manager, stats);
    }

    @Override
    protected void loadSprites() {
        // manually load assets
    }

    // Updates player movement
    private void move() {
        /* It is important to multiply everything with delta */
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.add(direction.x * acceleration * delta, direction.y * acceleration * delta);
            velocity.clamp(0, maxSpeed);
        } else {
            velocity.x *= speedDecay;
            velocity.x *= speedDecay;
        }
        center.add(velocity.x * delta, velocity.y * delta);
        rect.setCenter(center);
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
    }
}
