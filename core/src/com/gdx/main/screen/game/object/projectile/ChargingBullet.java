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
import com.gdx.main.screen.game.object.cannon.Cannon;
import com.gdx.main.screen.game.object.cannon.ChargeCannon;
import com.gdx.main.screen.game.object.entity.GameEntity;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;

public class ChargingBullet extends Projectile{

    public ChargingBullet(boolean isFriendly, float x, float y, float rectSize, Vector2 initialDirection,
                       Stage stage, Stage subStage, Settings gs, Manager manager) {
        super(isFriendly, x, y, rectSize, initialDirection, stage, subStage, gs, manager);

        // default settings
        velocity = gs.bullet1Speed;
        damage = gs.bullet1Damage;
        scale = gs.bullet1Scale;

        // load assets
        loadSprites();
        loadAudio();

    }

    @Override
    protected void loadSprites() {
        baseRegions = new TextureRegion[] {
                new TextureRegion(new Texture("02.png"))
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
        if(entity.isDense) {isAlive = false;}
    }

    public void kill() {
        isAlive = false;
        this.remove();
        ProjectileHandler.remove(this);
        Debugger.remove(this);
    }

    // updates position
    public void move(Vector2 position) {
        center.set(position);
        baseSprite.setCenter(center.x, center.y);
    }

    @Override
    public void update(float delta, Mouse mouse) {
        //
    }

    public void chargeUpdate(Vector2 position, float scale, float rotation) {
        baseSprite.setScale(scale);
        baseSprite.setRotation(rotation);
        move(position);
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
