package com.gdx.main.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.Core;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.helper.ui.custom_items.CustomBackground;
import com.gdx.main.helper.ui.custom_items.CustomText;
import com.gdx.main.screen.stage.MenuStage;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;


public class MenuScreen implements Screen, Buildable {

    Core core;
    Manager manager;
    Settings gs;
    Mouse mouse;
    Debugger debugger;
    Stats stats;

    Viewport viewport;
    OrthographicCamera camera;

    // Stage
    MenuStage backStage; // backgrounds
    MenuStage menuStage; // text, buttons, ui

    // background
    CustomBackground bg1;
    CustomBackground bg2;
    CustomBackground bg3;

    // Text
    CustomText headText;
    CustomText playText;
    CustomText exitText;

    // transition
    boolean transition = false;
    float transitionTimer = 1.5f; // in seconds

    public MenuScreen(Core core, Manager manager, Settings gs,
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

    @Override
    public void build() {
        this.backStage = new MenuStage(viewport);
        this.menuStage = new MenuStage(viewport);
        Gdx.input.setInputProcessor(menuStage);

        /* BackStage */
        bg1 = new CustomBackground("background/bg-1-2.png", 1, -3f, -3f, viewport);
        backStage.addActor(bg1);
        debugger.add(bg1);

        bg2 = new CustomBackground("background/bg-2-2.png", 1, -6f, -6f, viewport);
        backStage.addActor(bg2);
        debugger.add(bg2);

        bg3 = new CustomBackground("background/bg-3-2.png", 1, -12f, -12f, viewport);
        backStage.addActor(bg3);
        debugger.add(bg3);

        /* MenuStage */

        // title text
        headText = new CustomText("Fortnite Redux", "fonts/Minecraft.ttf",
                viewport.getWorldWidth()/2, viewport.getWorldHeight() - 100, true,
                20,20,100, Color.WHITE);
        menuStage.addActor(headText);
        debugger.add(headText);

        // start text button
        playText = new CustomText("START", "fonts/Minecraft.ttf",
                viewport.getWorldWidth()/2, viewport.getWorldHeight() - 280, true,
                30,20,48, Color.WHITE) {
            @Override
            public void action() {setColor(Color.WHITE);}
            @Override
            public void hovered() {setColor(Color.YELLOW);}
            @Override
            public void clicked() {transition = true;}
        };
        menuStage.addActor(playText);
        debugger.add(playText);

        // Exit text button
        exitText = new CustomText("EXIT", "fonts/Minecraft.ttf",
                viewport.getWorldWidth()/2, viewport.getWorldHeight() - 400, true,
                30,20,48, Color.WHITE) {

            @Override
            public void action() {setColor(Color.WHITE);}
            @Override
            public void hovered() {setColor(Color.YELLOW);}
            @Override
            public void clicked() {Gdx.app.exit();}
        };
        menuStage.addActor(exitText);
        debugger.add(exitText);
    }

    private void transitionScreen(float delta) {
        if(transitionTimer >= 1.5) {
            headText.setAcceleration(500f);
            playText.setAcceleration(500f);
            exitText.setAcceleration(500f);
        }
        else if (transitionTimer <= 0) {
            switchScreen();
        }
        transitionTimer -= delta;
    }

    private void switchScreen() {
        GameScreen gameScreen = new GameScreen(core, manager, gs, viewport, camera, mouse, debugger, stats);
        gameScreen.loadPreviousBackground(bg1,bg2,bg3);
        core.setScreen(gameScreen);

        this.dispose();
    }

    /* --- Render & Updates --- */

    private void update(float delta) {
        mouse.update();

        bg1.update(delta);
        bg2.update(delta);
        bg3.update(delta);

        headText.update(delta, mouse);
        playText.update(delta, mouse);
        exitText.update(delta, mouse);

        if(transition) {
            transitionScreen(delta);
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        // draw methods
        backStage.draw();
        menuStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // Irrelevant on desktop - IGNORE
    }

    @Override
    public void resume() {
        // Irrelevant on desktop - IGNORE
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        backStage.dispose();
        menuStage.dispose();
    }
}
