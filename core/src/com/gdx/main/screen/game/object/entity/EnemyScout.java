package com.gdx.main.screen.game.object.entity;

import com.badlogic.gdx.Gdx;
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
import com.gdx.main.screen.game.object.particle.TrailParticle;
import com.gdx.main.screen.game.object.projectile.Projectile;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class EnemyScout extends com.gdx.main.screen.game.object.entity.GameEntity {

    private final Player player;

    private final float rotationSpeed;
    private final float maxSpeed;
    private float speed;

    // SFX
    private boolean isPlayed = false;

    // Particle
    private final Vector2 particleOffset1;
    private final float particleOffsetAngle1;

    public EnemyScout(Player player, float x, float y, Vector2 initialDirection,
                      Viewport viewport, OrthographicCamera camera, Stage stage, Stage subStage,
                      Debugger debugger, Settings gs, Manager manager, Stats stats) {
        super(x, y, initialDirection, viewport, camera, stage, subStage, debugger, gs, manager, stats);

        this.player = player;
        isDense = false;

        // default settings
        rotationSpeed = gs.scoutRotation;
        maxSpeed = gs.scoutSpeed;
        speed = maxSpeed;

        hp = gs.scoutHP;
        dmg = gs.scoutDMG;
        rect.setSize(gs.scoutSize);

        // sets initial direction;
        target.set(player.getCenter().x - center.x, player.getCenter().y - center.y);
        direction.set(target).nor();

        // audio
        deathSFX = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/explosion-1.mp3"));

        // Particle
        particleOffset1 = new Vector2(0,-1);
        particleOffsetAngle1 = particleOffset1.angleDeg();
    }

    @Override
    protected void loadSprites() {
        // manually load assets
        baseTexture = manager.get("textures/object/entity/enemy/scout-2.png", Texture.class);
        int tWidth = baseTexture.getWidth(); int tHeight = baseTexture.getHeight();
        int tiles = 9;

        // splits texture
        TextureRegion[][] temp = TextureRegion.split(baseTexture, tWidth/tiles, tHeight);
        baseRegions = new TextureRegion[tiles];

        // converts to 1D array
        for(int i = 0; i < tiles; i++) {baseRegions[i] = temp[0][i];}

        // sets active region
        baseSprite = new Sprite(baseRegions[frameIndex]);
        baseSprite.setCenter(center.x, center.y);
        baseSprite.setRotation(rotation);
        baseSprite.setScale(0.8f);
    }

    // collision
    @Override
    public void collide(GameEntity object) {

    }

    @Override
    public void collide(Projectile projectile) {
        if(!collideWith.contains(projectile)) {
            collideWith.add(projectile);
            hp -= projectile.damage;
            System.out.println(hp);
        }
    }

    private void setActive() {
        // if not in screen - sets isActive to false
        isActive = !(center.x < 0) && !(center.x > viewport.getWorldWidth())
                && !(center.y < 0) && !(center.y > viewport.getWorldHeight());
    }

    private void playSound() {
        if(!isPlayed) {
            long id = deathSFX.play();
            deathSFX.setVolume(id, 0.1f);
            deathSFX.setPitch(id, 1.5f);
            isPlayed = true;
        }
    }

    private void animateDeath() {
        baseSprite.setRegion(baseRegions[frameIndex]);
        if(frameIndex < 8) {
            frameIncrement += 0.5f * 60 * delta;
            frameIndex = (int)frameIncrement;
        } else {
            kill();
        }
    }

    @Override
    public void kill() {
        this.remove(); // remove actor
        EntityHandler.remove(this); // remove final reference
        Debugger.remove(this); // removes from debugger

        // disposes disposable assets
        deathSFX.dispose();
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

        // scales speed according to delta
        speed = maxSpeed;
        speed = speed * (1 - (minDelta)/180 * 1/1.5f);

        // scales rotation according to delta
        float scaledRotationSpeed = rotationSpeed * ((minDelta*2.5f)/180);

        // decides wether to turn clockwise or anti-clockwise
        currentAngle = (deltaAngle > 180) ?
                currentAngle + (scaledRotationSpeed * delta):
                currentAngle - (scaledRotationSpeed * delta);

        // sets rotation and direction
        rotation = currentAngle - 90;
        direction.setAngleDeg(currentAngle);
        baseSprite.setRotation(rotation);
    }

    public void spawnTrailParticle() {
        if(delta != 0) {
            Vector2 particleCenter = new Vector2(center);
            Vector2 particleSpawnPos = new Vector2(particleCenter.add(particleOffset1.setAngleDeg(direction.angleDeg() + particleOffsetAngle1 - 90)));
            new TrailParticle(manager.get("textures/object/particle/particle-trail-1.png"), 1, 1, particleSpawnPos,
                    gs.trailScale, 0.5f, 0, gs.trailFadeSpeed, false, subStage).setColor(Color.FIREBRICK);
        }
    }

    @Override
    public void update(float delta, Mouse mouse) {
        this.delta = delta;

        if(isAlive) {
            setActive();
            rotate();
            move();
            spawnTrailParticle();
            if(hp <= 0) {
                isAlive = false;
                isActive = false;
                stats.addScore(gs.scoutScore);
            }

        } else {
            playSound();
            animateDeath();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        baseSprite.draw(batch);
    }

    @Override // Debuggable
    public void debug(ShapeRenderer shape) {
        if(isAlive) {
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.setColor(Color.RED);
            shape.rect(rect.x, rect.y, rect.width, rect.height);
            shape.end();
        }
    }
}
