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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.handler.EntityHandler;
import com.gdx.main.screen.game.object.cannon.BasicCannon;
import com.gdx.main.screen.game.object.cannon.Cannon;
import com.gdx.main.screen.game.object.particle.TrailParticle;
import com.gdx.main.screen.game.object.projectile.Projectile;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class Player extends GameEntity {

    // settings variables
    private final float acceleration;
    private final float rotationSpeed;
    private final float maxSpeed;
    private final float speedDecay;

    private float alpha = 1;
    private float invincibleTimer;

    // Cannon
    private final Cannon cannon1;
    private final Cannon cannon2;

    // Particle
    private final Vector2 particleOffset1;
    private final Vector2 particleOffset2;
    private final float particleOffsetAngle1;
    private final float particleOffsetAngle2;


    public Player(float x, float y, Vector2 initialDirection,
                  Viewport viewport, OrthographicCamera camera, Stage stage, Stage subStage,
                  Debugger debugger, Settings gs, Manager manager, Stats stats) {
        super(x, y, initialDirection, viewport, camera, stage, subStage, debugger, gs, manager, stats);

        // import from game settings
        acceleration = gs.playerAcceleration;
        rotationSpeed = gs.playerRotation;
        maxSpeed = gs.playerSpeed;
        speedDecay = -0.012f * 60;
        hp = gs.playerHP;
        dmg = 0;
        invincibleTimer = gs.playerInvTime;

        // initial velocity and direction during screen transition
        velocity.set(0f, maxSpeed);
        direction.set(0,1);
        rect.setSize(15);

        // hell yeah
        isPlayer = true;
        isDense = false;

        // setups cannon
        cannon1 = new BasicCannon(true, center, new Vector2(6, 8), stage, subStage, gs, manager);
        cannon2 = new BasicCannon(true, center, new Vector2(-6, 8), stage, subStage, gs, manager);

        // setups particle
        particleOffset1 = new Vector2(10,-5);
        particleOffset2 = new Vector2(-10, -5);
        particleOffsetAngle1 = particleOffset1.angleDeg();
        particleOffsetAngle2 = particleOffset2.angleDeg();
    }

    public Vector2 getCenter() {
        return center;
    }

    // loads textures, particles, etc
    @Override
    protected void loadSprites() {
        // manually load assets
        baseRegions = new TextureRegion[] {
                new TextureRegion(new Texture("textures/object/entity/player/player-ship-1.png")),
                new TextureRegion(new Texture("textures/object/entity/player/player-ship-2.png")),
                new TextureRegion(new Texture("textures/object/entity/player/player-ship-3.png")),
                new TextureRegion(new Texture("textures/object/entity/player/player-ship-4.png"))
        };

        baseRegion = baseRegions[0];
        baseSprite = new Sprite(baseRegion);
        baseSprite.setCenter(center.x, center.y);
        baseSprite.setRotation(rotation);
    }

    // checks if cannon should be fired
    private void checkFire() {
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            cannon1.fire();
            cannon2.fire();
        }
    }

    // Teleport player to opposite side of world if
    // it goes off-screen
    private void wrapWorld() {
        if (center.x < -20) {
            center.x = viewport.getWorldWidth() + 10;
        }
        if (center.x > viewport.getWorldWidth() + 20) {
            center.x = -10;
        }
        if (center.y < -20) {
            center.y = viewport.getWorldHeight() + 10;
        }
        if (center.y > viewport.getWorldHeight() + 20) {
            center.y = -10;
        }
    }
    // collisions
    @Override
    public void collide(GameEntity entity) {
        if(entity.isActive && !isInvincible) {
            hp -= entity.dmg;
            isInvincible = true;
        }
    }

    @Override
    public void collide(Projectile projectile) {
        if(!isInvincible) {
            hp -= projectile.damage;
            isInvincible = true;
        }
    }

    private void updateInvincibility() {
        if(invincibleTimer < 0) {
            isInvincible = false;
            invincibleTimer = gs.playerInvTime;
        } else {invincibleTimer -= delta;}
    }

    @Override
    public void kill() {
        this.remove();
        EntityHandler.remove(this);
        Debugger.remove(this);
        cannon1.kill();
        cannon2.kill();
    }

    // Updates player movement
    private void move() {
        /* It is important to multiply everything with delta */
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)
        || Gdx.input.isKeyPressed(Input.Keys.W)) {
            // If button pressed is pressed, accelerate along the direction vector
            velocity.add(direction.x * acceleration * delta, direction.y * acceleration * delta);
            velocity.clamp(0, maxSpeed);
        }
        // reduce velocity by a percentage each iteration
        else {
            velocity.set(
                    velocity.x + (velocity.x * speedDecay * delta),
                    velocity.y + (velocity.y * speedDecay * delta));
        }
        // updates position relative to its center
        center.add(velocity.x * delta, velocity.y * delta);
        rect.setCenter(center);

        // updates sprite position
        baseSprite.setCenter(center.x, center.y);
        baseSprite.setAlpha(alpha);
    }

    private void realisticMove() {}


    // Updates player rotation towards mouse cursor
    private void rotate(Mouse mouse) {
        // sets target vector by subtracting mouse position vector
        // and center position vector
        target.set(mouse.x - center.x, mouse.y - center.y);

        // gets angle from vectors and difference between them
        // whilst accounting for angles > 360 or < 0;
        float currentAngle = direction.angleDeg();
        float targetAngle = target.angleDeg();
        float deltaAngle = (((currentAngle - targetAngle) % 360) + 360) % 360; // keeps value between 0 and 360
        float deltaAngle2 = (((targetAngle - currentAngle) % 360) + 360) % 360;
        float minDelta = Math.min(deltaAngle, deltaAngle2);

        // approximates angles near 0 to be equal to 0
        // important to prevent jitters at low deltas
        if (minDelta > rotationSpeed * delta) {
            // determines whether to rotate clockwise or anti-clockwise, whichever is more efficient
            currentAngle = (deltaAngle > 180) ? currentAngle + (rotationSpeed * delta) : currentAngle - (rotationSpeed * delta);
        } else {
            // sets angle to target angle
            currentAngle = targetAngle;
        }
        // updates direction based on new rotation
        rotation = currentAngle - 90;
        direction.setAngleDeg(currentAngle);

        // updates sprite rotation
        baseSprite.setRotation(rotation);
    }

    public void spawnParticle() {
        Vector2 particleCenter = new Vector2(center);
        Vector2 particleSpawnPos = new Vector2(particleCenter.add(particleOffset1.setAngleDeg(direction.angleDeg() + particleOffsetAngle1 - 90)));
        new TrailParticle(manager.get("textures/object/particle/particle-trail-1.png"), 1, 1, particleSpawnPos,
                gs.trailScale, 0.5f, 0, gs.trailFadeSpeed, false, subStage);

        particleCenter.set(center);
        particleSpawnPos.set(particleCenter.add(particleOffset2.setAngleDeg(direction.angleDeg() + particleOffsetAngle2 - 90)));
        new TrailParticle(manager.get("textures/object/particle/particle-trail-1.png"), 1, 1, particleSpawnPos,
                gs.trailScale, 0.5f, 0, gs.trailFadeSpeed, false, subStage);
    }

    @Override
    public void update(float delta, Mouse mouse) {
        this.delta = delta;

        // if player is still alive
        if(isAlive) {
            updateInvincibility();

            // update cannons
            cannon1.update(this.delta, center, direction);
            cannon2.update(this.delta, center, direction);
            checkFire();

            // updates movement
            rotate(mouse);
            move();
            wrapWorld();

            // particle
            spawnParticle();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            hp -= 10f;
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
