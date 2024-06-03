package com.gdx.main.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.Core;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.helper.ui.custom_items.CustomBackground;
import com.gdx.main.screen.game.object.entity.Player;
import com.gdx.main.screen.game.object.particle.Particle;
import com.gdx.main.screen.stage.GameStage;
import com.gdx.main.screen.stage.MenuStage;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class GameScreen implements Screen, Buildable {

    Core core;
    Manager manager;
    Settings gs;
    Mouse mouse;
    Debugger debugger;
    Stats stats;

    Viewport viewport;
    OrthographicCamera camera;

    // Stage
    GameStage backStage; // backgrounds
    GameStage mainStage; // objects
    GameStage hudStage; // hud, ui

    // background
    CustomBackground bg1;
    CustomBackground bg2;
    CustomBackground bg3;

    // Objects
    Player player;


    // misc
    float beginTimer = 3f;
    boolean flag1 = false;

    public GameScreen(Core core, Manager manager, Settings gs,
                      Viewport viewport, OrthographicCamera camera,
                      Mouse mouse, Debugger debugger, Stats stats) {
        this.core = core;
        this.manager = manager;
        this.gs = gs;
        this.viewport = viewport;
        this.camera = camera;
        this.mouse = mouse;
        this.debugger = debugger;
        this.stats = stats;

        build();
    }

    public void loadPreviousBackground(CustomBackground bg1, CustomBackground bg2, CustomBackground bg3) {
        this.bg1 = bg1;
        this.bg2 = bg2;
        this.bg3 = bg3;
        backStage.addActor(bg1);
        backStage.addActor(bg2);
        backStage.addActor(bg3);
    }

    private void beginGame(float delta) {

        if(beginTimer >= 3) {
            // load UI
        }
        else if(beginTimer > 1.5) {


        }

        if(beginTimer <= 0) {flag1 = true;}
        beginTimer -= delta;
    }

    @Override
    public void build() {
        this.backStage = new GameStage(viewport);
        this.mainStage = new GameStage(viewport);
        this.hudStage = new GameStage(viewport);
        Gdx.input.setInputProcessor(hudStage);

        // Backgrounds

        // objects
        player = new Player(viewport.getWorldWidth()/2, -20, 10,
                new Vector2(1,0), viewport, camera, mainStage, gs, manager, stats);
        mainStage.addActor(player);
        debugger.add(player);
        // HUD & UI
    }

    @Override
    public void show() {

    }

    private void update(float delta) {
        mouse.update();

        // backgrounds
        bg1.update(delta);
        bg2.update(delta);
        bg3.update(delta);

        // objects
        player.update(delta, mouse);

        // hud & ui

        // misc
        if(!flag1) {beginGame(delta);}
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        // draw methods
        backStage.draw();
        mainStage.draw();
        hudStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // For android / ios only
    }

    @Override
    public void resume() {
        // for android / ios only
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //stages
        backStage.dispose();
        mainStage.dispose();
        hudStage.dispose();
    }
}
