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
import com.gdx.main.screen.game.object.cannon.BasicCannon;
import com.gdx.main.screen.game.object.cannon.Cannon;
import com.gdx.main.screen.game.object.particle.TrailParticle;
import com.gdx.main.screen.game.object.projectile.Projectile;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class EnemyFighter extends com.gdx.main.screen.game.object.entity.GameEntity {

    private final Player player;

    private final float rotationSpeed;
    private final float maxSpeed;
    private float speed;

    // Cannon
    private Cannon cannon;

    // SFX
    private boolean isPlayed = false;

    // Particle
    private final Vector2 particleOffset1;
    private final float particleOffsetAngle1;

    public EnemyFighter(Player player, float x, float y, Vector2 initialDirection,
                      Viewport viewport, OrthographicCamera camera, Stage stage, Stage subStage,
                      Debugger debugger, Settings gs, Manager manager, Stats stats) {
        super(x, y, initialDirection, viewport, camera, stage, subStage, debugger, gs, manager, stats);

        this.player = player;
        isDense = true;

        // default settings
        rotationSpeed = gs.fighterRotation;
        maxSpeed = gs.fighterSpeed;
        speed = maxSpeed;

        hp = gs.fighterHP;
        dmg = gs.fighterDMG;
        rect.setSize(gs.fighterSize);

        // sets initial direction;
        target.set(player.getCenter().x - center.x, player.getCenter().y - center.y);
        direction.set(target).nor();

        // Cannon setup
        cannon = new BasicCannon(false, center, new Vector2(0,5), stage, subStage, gs, manager);
        // Cannon settings
        cannon.setSettings(gs.fighterFireRate);
        cannon.setSFX("audio/sfx/laser-1.wav", 0.02f, 0.5f);
        // Cannon bullet settings
        cannon.setBulletSettings(gs.bullet2Speed, gs.bullet2Damage, gs.bullet2Size);
        cannon.setBulletSFX("audio/sfx/impact-1.mp3", 0.1f, 0.5f);
        cannon.setBulletTexture("02.png", 0.3f);

        // Audio
        deathSFX = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/explosion-1.mp3"));

        // Particle
        particleOffset1 = new Vector2(0,-3);
        particleOffsetAngle1 = particleOffset1.angleDeg();
    }

    @Override
    protected void loadSprites() {
        // manually load assets
        Texture texture = new Texture(Gdx.files.internal("textures/object/entity/enemy/fighter-2.png"));
        int tWidth = texture.getWidth(); int tHeight = texture.getHeight();
        int tiles = 9;

        // splits texture
        TextureRegion[][] temp = TextureRegion.split(texture, tWidth/tiles, tHeight);
        baseRegions = new TextureRegion[tiles];

        // converts to 1D array
        for(int i = 0; i < tiles; i++) {baseRegions[i] = temp[0][i];}

        // sets active region
        baseSprite = new Sprite(baseRegions[frameIndex]);
        baseSprite.setCenter(center.x, center.y);
        baseSprite.setRotation(rotation);
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
            deathSFX.setPitch(id, 2f);
            isPlayed = true;
        }
    }

    private void animateDeath() {
        baseSprite.setRegion(baseRegions[frameIndex]);
        if(frameIndex < 8) {
            frameIncrement += 0.5f;
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
        deathSFX.dispose();
        cannon.kill();
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

        // scales speed based on distance
        double distance = Math.sqrt(Math.pow(player.getCenter().x - center.x, 2) + Math.pow(player.getCenter().y - center.y, 2));
        if(distance < 300f) {
            speed = (float) (speed * ((distance+300)/600));
        }

        // scales rotation according to delta
        float scaledRotationSpeed = rotationSpeed * ((minDelta+180)/180);

        // decides wether to turn clockwise or anti-clockwise
        currentAngle = (deltaAngle > 180) ?
                currentAngle + (scaledRotationSpeed * delta):
                currentAngle - (scaledRotationSpeed * delta);

        // sets rotation and direction
        rotation = currentAngle - 90;
        direction.setAngleDeg(currentAngle);
        baseSprite.setRotation(rotation);
    }

    public void spawnParticle() {
        Vector2 particleCenter = new Vector2(center);
        Vector2 particleSpawnPos = new Vector2(particleCenter.add(particleOffset1.setAngleDeg(direction.angleDeg() + particleOffsetAngle1 - 90)));
        new TrailParticle(manager.get("textures/object/particle/particle-trail-1.png"), 1, 1, particleSpawnPos,
                gs.trailScale, 0.5f, 0, gs.trailFadeSpeed/2, false, subStage);
    }

    @Override
    public void update(float delta, Mouse mouse) {
        this.delta = delta;
        if(isAlive) {
            setActive();

            // rotation & movement
            rotate();
            move();

            // cannon and states
            cannon.update(delta, center, direction);
            if(isActive) {cannon.fire();}
            if(hp <= 0) {isAlive = false; isActive = false;}

            // trail particle
            spawnParticle();

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
