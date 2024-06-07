package com.gdx.main.screen.game.display.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.debug.Debuggable;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;
import com.gdx.main.util.TTFGenerator;

public class ScoreDisplay extends Actor implements Debuggable {

    // GDX
    Stage stage;
    Viewport viewport;
    OrthographicCamera camera;

    // UTIL
    Settings gs;
    Manager manager;
    Stats stats;

    // Logic
    int score;
    String scoreStr;

    BitmapFont font;
    GlyphLayout layout;
    Vector2 pos1, pos2;
    Rectangle rect;


    public ScoreDisplay(Stage stage, Viewport viewport,
                        Settings gs, Manager manager, Stats stats) {
        this.stage = stage;
        this.viewport = viewport;

        this.gs = gs;
        this.manager = manager;
        this.stats = stats;

        pos1 = new Vector2(viewport.getWorldWidth()/2, viewport.getWorldHeight() - 60);
        pos2 = new Vector2();

        // loads font
        String defaultPath = "fonts/Minecraft.ttf";
        int defaultSize = 20;
        Color defaultColor = Color.YELLOW;

        font = TTFGenerator.generateBMF(defaultPath, defaultSize);
        font.setColor(defaultColor);

        // initial text
        score = stats.getScore();
        scoreStr = String.valueOf(score);

        // font layout
        layout = new GlyphLayout(font, scoreStr);
        rect = new Rectangle();
        rect.setSize(layout.width, layout.height);
        rect.setCenter(pos1);
        pos2.x = pos1.x - (layout.width/2);
        pos2.y = pos1.y + (layout.height/2);

        // ADD TO HANDLER
        stage.addActor(this);
        Debugger.add(this);

    }

    public void update(float delta) {
        score = stats.getScore();
        scoreStr = String.valueOf(score);

        layout.setText(font, scoreStr);
        rect.setSize(layout.width, layout.height);
        rect.setCenter(pos1);
        pos2.x = pos1.x - (layout.width/2);
        pos2.y = pos1.y + (layout.height/2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, scoreStr, pos2.x, pos2.y);
    }

    @Override
    public void debug(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        shape.rect(rect.x, rect.y, rect.width, rect.height);
        shape.end();
    }
}
