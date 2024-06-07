package com.gdx.main.screen.game.display.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.screen.game.object.entity.Player;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class HUD {

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

    // Player
    Player player;

    public HUD(Player player, Stage stage, Viewport viewport,
               Settings gs, Manager manager, Stats stats) {

        this.player = player;
        this.stage = stage;
        this.viewport = viewport;

        this.gs = gs;
        this.manager = manager;
        this.stats = stats;

        // initialize display objects
        clockDisplay = new ClockDisplay(stage, viewport, gs, manager, stats);
        scoreDisplay = new ScoreDisplay(stage, viewport, gs, manager, stats);
        healthBar = new HealthBar(player, stage, viewport, gs, manager, stats);

    }

    public void update(float delta) {
        clockDisplay.update(delta);
        scoreDisplay.update(delta);
        healthBar.update(delta);
    }


}
