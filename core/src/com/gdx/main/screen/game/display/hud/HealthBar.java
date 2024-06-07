package com.gdx.main.screen.game.display.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.debug.Debuggable;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.screen.game.object.entity.Player;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;
import org.w3c.dom.Text;

public class HealthBar extends Actor implements Debuggable {

    // GDX
    Stage stage;
    Viewport viewport;
    OrthographicCamera camera;

    // UTIL
    Settings gs;
    Manager manager;
    Stats stats;

    // LOGIC
    Player player;

    float barHeight = 4f;
    float maxBarLength = 600f;
    float currentBarLength;
    float transitionBarLength;
    float underlayBarLength;

    Vector2 position, center;
    Rectangle rect;

    Sprite healthBar;
    Sprite transitionBar;
    Sprite underlayBar;



    public HealthBar(Player player, Stage stage, Viewport viewport,
                     Settings gs, Manager manager, Stats stats) {
        this.player = player;
        this.stage = stage;
        this.viewport = viewport;

        this.gs = gs;
        this.manager = manager;
        this.stats = stats;

        this.player = player;

        // setups vector
        center = new Vector2(viewport.getWorldWidth()/2, 20);

        // setups bar sprite
        Texture texture = new Texture("1x1.png");

        // manually creating a rectangle by scaling the 1x1 pixel
        healthBar = new Sprite(texture);
        healthBar.setScale(maxBarLength, barHeight);
        healthBar.setCenter(center.x , center.y);
        healthBar.setColor(Color.WHITE);
        healthBar.setAlpha(1f);

        transitionBar = new Sprite(texture);
        transitionBar.setScale(maxBarLength, barHeight);
        transitionBar.setCenter(center.x, center.y);
        transitionBar.setColor(Color.RED);

        underlayBar = new Sprite(texture);
        underlayBar.setScale(maxBarLength+4, barHeight+4);
        underlayBar.setCenter(center.x, center.y);
        underlayBar.setColor(Color.RED);
        underlayBar.setAlpha(0.1f);

        currentBarLength = maxBarLength;
        transitionBarLength = maxBarLength;

        rect = new Rectangle();
        rect.setSize(maxBarLength, barHeight);
        rect.setCenter(center);


        // ADD TO HANDLERS
        stage.addActor(this);
        Debugger.add(this);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void updateBarLength(float delta) {
        // updates green health bar size
        currentBarLength = (player.hp < 0) ? 0 : (player.hp / gs.playerHP) * maxBarLength;
        healthBar.setOrigin(0,0);
        healthBar.setScale(currentBarLength, barHeight);
        healthBar.setPosition(rect.x, rect.y);

        // updates red health bar size
        if(currentBarLength < transitionBarLength) {
            transitionBarLength -= 300 * delta;
        } else {transitionBarLength = currentBarLength;}
        transitionBar.setOrigin(0,0);
        transitionBar.setScale(transitionBarLength, barHeight);
        transitionBar.setPosition(rect.x, rect.y);
    }

    public void update(float delta) {
        updateBarLength(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        underlayBar.draw(batch);
        transitionBar.draw(batch);
        healthBar.draw(batch);
    }

    @Override
    public void debug(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        shape.rect(rect.x, rect.y, rect.width, rect.height);
        shape.end();
    }
}
