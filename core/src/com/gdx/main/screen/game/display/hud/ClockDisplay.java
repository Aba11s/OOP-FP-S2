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

public class ClockDisplay extends Actor implements Debuggable {

    // GDX
    Stage stage;
    Viewport viewport;
    OrthographicCamera camera;

    // UTIL
    Settings gs;
    Manager manager;
    Stats stats;

    // LOGIC
    double timeElapsed = 0;
    int secondsElapsed = 0;
    int seconds = 0;
    int minutes = 0;

    StringBuilder minutesSB;
    StringBuilder secondsSB;
    StringBuilder string;
    String format = "%02d";

    BitmapFont font;
    GlyphLayout layout;
    Vector2 pos1, pos2;
    Rectangle rect;

    // TRANSITION LOGIC
    Vector2 targetPos;

    public ClockDisplay(Stage stage, Viewport viewport,
                        Settings gs, Manager manager, Stats stats) {
        this.stage = stage;
        this.viewport = viewport;

        this.gs = gs;
        this.manager = manager;
        this.stats = stats;

        pos1 = new Vector2(viewport.getWorldWidth()/2, viewport.getWorldHeight() - 30);
        pos2 = new Vector2();

        // loads font
        String defaultPath = "fonts/Minecraft.ttf";
        int defaultSize = 24;
        Color defaultColor = Color.WHITE;

        font = TTFGenerator.generateBMF(defaultPath, defaultSize);
        font.setColor(defaultColor);

        // initial text
        minutesSB = new StringBuilder();
        secondsSB = new StringBuilder();
        string = new StringBuilder("00 : 00");

        // font display
        layout = new GlyphLayout(font, string.toString());
        rect = new Rectangle();
        rect.setSize(layout.width, layout.height);
        rect.setCenter(pos1);
        pos2.x = pos1.x - (layout.width/2);
        pos2.y = pos1.y + (layout.height/2);

        // TRANSITION
        targetPos = new Vector2(0,0);

        // ADD TO HANDLER
        stage.addActor(this);
        Debugger.add(this);
    }

    private void moveTransition() {

    }

    private void updateClock() {
        secondsElapsed = (int) timeElapsed;
        minutes = (secondsElapsed) / 60;
        seconds = secondsElapsed % 60;
    }

    private void updateText() {
        minutesSB.insert(0, String.format(format, minutes));
        secondsSB.insert(0, String.format(format, seconds));
        string = minutesSB.append(" : ").append(secondsSB);

        layout.setText(font, string);
        rect.setSize(layout.width, layout.height);
    }

    private void resetText() {
        minutesSB.setLength(0);
        secondsSB.setLength(0);
        string.setLength(0);
    }

    private void updatePosition() {
        rect.setCenter(pos1);
        pos2.x = pos1.x - (layout.width/2);
        pos2.y = pos1.y + (layout.height/2);
    }

    public void update(float delta) {
        resetText();

        timeElapsed += delta;
        updateClock();
        updateText();
        updatePosition();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, string.toString(), pos2.x, pos2.y);
    }

    @Override
    public void debug(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        shape.rect(rect.x, rect.y, rect.width, rect.height);
        shape.end();
    }
}
