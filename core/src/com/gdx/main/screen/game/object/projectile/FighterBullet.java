package com.gdx.main.screen.game.object.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.handler.ProjectileHandler;
import com.gdx.main.screen.game.object.entity.GameEntity;
import com.gdx.main.screen.game.object.particle.AnimatedParticle;
import com.gdx.main.screen.game.object.particle.FadeParticle;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;


public class FighterBullet extends Projectile {

    float alpha = 1;

    public FighterBullet(boolean isFriendly, float x, float y, float rectSize, Vector2 initialDirection,
                       Stage stage, Stage subStage, Settings gs, Manager manager) {
        super(isFriendly, x, y, rectSize, initialDirection, stage, subStage, gs, manager);

        // default settings
        velocity = gs.bullet1Speed;
        damage = gs.bullet1Damage;
        scale = gs.bullet1Scale;

        // load assets
        loadSprites();
        loadAudio();

        // rotate
        rotate();

    }

    @Override
    protected void loadSprites() {
        baseRegions = new TextureRegion[] {
                new TextureRegion(manager.get("02.png", Texture.class))
        };
        baseRegion = baseRegions[0];
        baseSprite = new Sprite(baseRegion);
        baseSprite.setCenter(center.x, center.y);
        baseSprite.setRotation(rotation);
        baseSprite.setScale(scale);
    }

    @Override
    protected void loadAudio() {
        impact = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/impact-1.mp3"));
    }

    // collision
    @Override
    public void collide(GameEntity entity) {
        if(entity.isDense) {
            isAlive = false; spawnParticle();
        }
    }

    private void spawnParticle() {
        new FadeParticle(
                manager.get("textures/object/particle/bullet-impact-sprite-1.png"), 1, 1, center, 0.1f,
                4, 1, -6, 1, false, rotation, Color.valueOf("cbfff5"), stage
        );

        // spawn small explosion
        new AnimatedParticle(
                manager.get("textures/object/particle/bullet-impact-ani-explosion-2.png"), 6,1, center,
                0.55f, 1, 30, false, stage);
    }

    private void death() {
        long id = impact.play();
        impact.setVolume(id, 0.1f);
        impact.setPitch(id, 3f);
        kill();
    }

    public void kill() {
        isAlive = false;
        this.remove();
        ProjectileHandler.remove(this);
        Debugger.remove(this);
    }

    // updates position
    public void move() {
        // linear movement
        direction.nor();
        center.add(direction.x * velocity * delta, direction.y * velocity * delta);
        rect.setCenter(center);

        // sprite updates
        baseSprite.setCenter(center.x, center.y);
    }

    // rotate method only called in constructor because the bullet
    // moves linearly and in a straight line
    public void rotate() {
        rotation = direction.angleDeg();
        baseSprite.rotate(rotation);
    }

    @Override
    public void update(float delta, Mouse mouse) {
        this.delta = delta;

        move();

        alpha -= 0.5f * delta;
        baseSprite.setAlpha(alpha);

        if(!isAlive) {death();}
        if(alpha <= 0) {kill();}
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        baseSprite.draw(batch);
    }

    @Override
    public void debug(ShapeRenderer shape) {
        if(isAlive) {
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.setColor(Color.RED);
            shape.rect(rect.x, rect.y, rect.width, rect.height);
            shape.end();
        }
    }
}
