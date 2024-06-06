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
import com.gdx.main.screen.game.handler.CollisionHandler;
import com.gdx.main.screen.game.handler.EntityHandler;
import com.gdx.main.screen.game.handler.ProjectileHandler;
import com.gdx.main.screen.game.object.entity.EnemyScout;
import com.gdx.main.screen.game.object.entity.GameEntity;
import com.gdx.main.screen.game.object.entity.Player;
import com.gdx.main.screen.game.object.particle.Particle;
import com.gdx.main.screen.stage.GameStage;
import com.gdx.main.screen.stage.MenuStage;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class GameScreen implements Screen, Buildable {
    float delta;

    Core core;
    Manager manager;
    Settings gs;
    Mouse mouse;
    Debugger debugger;
    Stats stats;

    Viewport viewport;
    OrthographicCamera camera;

    // Handlers
    EntityHandler entityHandler;
    ProjectileHandler projectileHandler;
    CollisionHandler collisionHandler;

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

    GameEntity testEntity;

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

        // Handlers
        projectileHandler = new ProjectileHandler(viewport);
        entityHandler = new EntityHandler(viewport, camera, mainStage, debugger, stats, manager, gs);
        collisionHandler = new CollisionHandler();

        // Backgrounds

        // objects
        player = new Player(viewport.getWorldWidth()/2, -20, new Vector2(1,0),
                viewport, camera, mainStage, debugger, gs, manager, stats);
        mainStage.addActor(player);
        debugger.add(player);

        entityHandler.setPlayer(player);
        collisionHandler.setPlayer(player);
    }

    private void update(float delta) {
        this.delta = delta;

        mouse.update();

        // handlers
        projectileHandler.update(this.delta);
        entityHandler.update(this.delta, mouse);
        collisionHandler.update(this.delta);

        // backgrounds
        bg1.update(this.delta);
        bg2.update(this.delta);
        bg3.update(this.delta);

        // hud & ui

        // misc
        if(!flag1) {beginGame(this.delta);}
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
        viewport.apply();
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
    public void show() {

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
