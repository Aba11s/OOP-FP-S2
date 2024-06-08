package com.gdx.main.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.Core;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.helper.actor.custom_items.CustomBackground;
import com.gdx.main.helper.actor.custom_items.CustomText;
import com.gdx.main.screen.game.display.Background;
import com.gdx.main.screen.game.stage.MenuStage;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

import java.util.Arrays;


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
    Background background;

    // Text
    CustomText headText;
    CustomText playText;
    CustomText exitText;

    // Spritebatch
    SpriteBatch batch1;

    // transition
    boolean transition = false;
    float transitionTimer = 1.5f; // in seconds

    // Sound


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

        batch1 = new SpriteBatch();
        batch1.setProjectionMatrix(camera.combined);

        build();
    }

    @Override
    public void build() {
        this.backStage = new MenuStage(viewport);
        this.menuStage = new MenuStage(viewport);
        Gdx.input.setInputProcessor(menuStage);

        /* BackStage */
        background = new Background(backStage, viewport);

        /* MenuStage */

        // title text
        headText = new CustomText("Fortnite Redux", "fonts/Minecraft.ttf",
                viewport.getWorldWidth()/2, viewport.getWorldHeight() - 100, true,
                20,20,100, Color.WHITE);
        menuStage.addActor(headText);
        Debugger.add(headText);

        // start text button
        playText = new CustomText("START", "fonts/Minecraft.ttf",
                viewport.getWorldWidth()/2, viewport.getWorldHeight() - 280, true,
                30,20,48, Color.WHITE) {
            @Override
            public void action() {setColor(Color.WHITE);}
            @Override
            public void hovered() {setColor(Color.YELLOW);}
            @Override
            public void clicked() {switchScreen();}
        };
        menuStage.addActor(playText);
        Debugger.add(playText);

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
        Debugger.add(exitText);
    }

    public void setBackground(Background background) {
//        this.background = new Background(backStage, viewport);
        this.background.addActor(backStage);
    }

    private void transitionScreen(float delta) {
        if(transitionTimer >= 1.5) {
            headText.setAcceleration(500f);
            playText.setAcceleration(500f);
            exitText.setAcceleration(500f);
        }
        else if (transitionTimer <= 0) {
            switchScreen();
            transitionTimer = 1.5f;
            transition = false;
        }
        transitionTimer -= delta;
    }

    private void switchScreen() {
        debugger.clear();

        GameScreen newScreen = new GameScreen(core, manager, gs, viewport, camera, mouse, debugger, stats);
        newScreen.setBackground(background);
        core.setScreen(newScreen);
    }

    /* --- Render & Updates --- */

    private void update(float delta) {
        mouse.update();

        background.update(delta);

        headText.update(delta, mouse);
        playText.update(delta, mouse);
        exitText.update(delta, mouse);

        if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            switchScreen();
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


        System.out.println("MENU");

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
        for(Actor actor : backStage.getActors()) {
            actor.setVisible(true);
        }
        for(Actor actor : menuStage.getActors()) {
            actor.setVisible(true);
        }

        // reset score
        stats.resetScore();
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
