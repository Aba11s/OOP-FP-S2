package com.gdx.main.screen.game.display.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.display.Menu;
import com.gdx.main.screen.game.object.entity.Player;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class HUD extends Menu {

    // GDX objects
    Stage stage;
    Viewport viewport;
    OrthographicCamera camera;

    // Util
    Settings gs;
    Manager manager;
    Stats stats;

    // Display objects
    ClockDisplay clockDisplay;
    ScoreDisplay scoreDisplay;
    HealthBar healthBar;
    BeginText beginText;

    // Player
    Player player;

    public HUD(Player player, Stage stage, Viewport viewport,
               Settings gs, Manager manager, Stats stats) {
        super(stage, viewport, gs, manager, stats);

        // initialize display objects
        clockDisplay = new ClockDisplay(stage, viewport, gs, manager, stats);
        scoreDisplay = new ScoreDisplay(stage, viewport, gs, manager, stats);
        healthBar = new HealthBar(player, stage, viewport, gs, manager, stats);
        beginText = new BeginText(stage, viewport, gs, manager, stats);

    }

    public void showBeginText() {
        beginText.alpha = 1;
    }

    public void fadeBeginText() {
        beginText.decreaseAlpha = true;
    }

    @Override
    public void update(float delta, Mouse mouse) {
        clockDisplay.update(delta);
        scoreDisplay.update(delta);
        healthBar.update(delta);
        beginText.update(delta);
    }


}
