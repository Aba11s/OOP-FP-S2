package com.gdx.main.screen.game.display;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public abstract class Menu {
    // GDX objects
    Stage stage;
    Viewport viewport;
    OrthographicCamera camera;

    // Util
    Settings gs;
    Manager manager;
    Stats stats;

    // Logic
    boolean visible;

    public Menu(Stage stage, Viewport viewport,
                Settings gs, Manager manager, Stats stats) {

        this.stage = stage;
        this.viewport = viewport;
        this.gs = gs;
        this.manager = manager;
        this.stats = stats;
    }

    public void setVisible(boolean visible) {}

    public abstract void update(float delta, Mouse mouse);

}
