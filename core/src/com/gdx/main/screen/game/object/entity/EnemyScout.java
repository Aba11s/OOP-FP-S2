package com.gdx.main.screen.game.object.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class EnemyScout extends GameEntity {

    private final Player player;
    private final float rotationSpeed;
    private float maxSpeed;
    private float speed;
    private float hp, dmg;

    public EnemyScout(Player player, float x, float y, float rectSize, Vector2 initialDirection, Viewport viewport, OrthographicCamera camera, Stage stage, Settings gs, Manager manager, Stats stats) {
        super(x, y, rectSize, initialDirection, viewport, camera, stage, gs, manager, stats);
        this.player = player;

        // default settings
        isAlive = true;
        rotationSpeed = 50f;
        maxSpeed = 80f;
        speed = maxSpeed;
        hp = 100;
        dmg = 0;

        // sets initial direction;
        target.set(player.getCenter().x - center.x, player.getCenter().y - center.y);
        direction.set(target).nor();
    }

    @Override
    protected void loadSprites() {
        // manually load assets
        baseRegions = new TextureRegion[] {
                new TextureRegion(new Texture("textures/object/entity/enemy/enemy-scout.png"))
        };

        baseRegion = baseRegions[0];
        baseSprite = new Sprite(baseRegion);
        baseSprite.setCenter(center.x, center.y);
        baseSprite.setRotation(rotation);
    }

    private void move() {
        // updates center position
        center.add(direction.x * speed * delta, direction.y * speed * delta);
        rect.setCenter(center);

        // updates sprite
        baseSprite.setCenter(center.x, center.y);
    }

    private void rotate() {
        // gets target direction
        target.set(player.getCenter().x - center.x, player.getCenter().y - center.y);

        // gets angles
        float currentAngle = direction.angleDeg();
        float targetAngle = target.angleDeg();
        float deltaAngle = (((currentAngle - targetAngle) % 360) + 360) % 360;
        float deltaAngle2 = (((targetAngle - currentAngle) % 360) + 360) % 360;
        float minDelta = Math.min(deltaAngle, deltaAngle2);

        // decides wether to turn clockwise or anti-clockwise
        currentAngle = (deltaAngle > 180) ? currentAngle + (rotationSpeed * delta) : currentAngle - (rotationSpeed * delta);

        // sets rotation and direction
        rotation = currentAngle - 90;
        direction.setAngleDeg(currentAngle);
        baseSprite.setRotation(rotation);

        System.out.println(speed);

    }

    @Override
    public void update(float delta, Mouse mouse) {
        super.update(delta, mouse);

        if(isAlive) {
            rotate();
            move();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        baseSprite.draw(batch);
    }

    @Override // Debuggable
    public void debug(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        shape.rect(rect.x, rect.y, rect.width, rect.height);
        shape.end();
    }
}
