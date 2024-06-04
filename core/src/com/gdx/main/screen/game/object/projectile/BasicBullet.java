package com.gdx.main.screen.game.object.projectile;

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

public class BasicBullet extends Projectile{

    public BasicBullet(boolean isFriendly, float x, float y, float rectSize, Vector2 initialDirection,
                       Stage stage, Settings sets, Manager manager) {
        super(isFriendly, x, y, rectSize, initialDirection, stage, sets, manager);

        // default settings
        velocity = 700f;
        damage = 100f;
        scale = 0.3f;

        // load texture
        loadSprites();
        // rotate
        rotate();

    }

    @Override
    protected void loadSprites() {
        baseRegions = new TextureRegion[] {
                new TextureRegion(new Texture("01.png"))
        };

        baseRegion = baseRegions[0];
        baseSprite = new Sprite(baseRegion);
        baseSprite.setCenter(center.x, center.y);
        baseSprite.setRotation(rotation);
        baseSprite.setScale(scale);
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
        baseSprite.rotate(direction.angleDeg());
    }

    @Override
    public void update(float delta, Mouse mouse) {
        super.update(delta, mouse);
        move();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        baseSprite.draw(batch);
    }

    @Override
    public void debug(ShapeRenderer shape) {

    }
}
