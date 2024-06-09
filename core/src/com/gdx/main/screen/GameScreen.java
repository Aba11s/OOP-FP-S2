package com.gdx.main.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.Core;
import com.gdx.main.helper.actor.custom_items.CustomText;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.display.Background;
import com.gdx.main.screen.game.display.EndMenu;
import com.gdx.main.screen.game.display.PauseMenu;
import com.gdx.main.screen.game.display.hud.HUD;
import com.gdx.main.screen.game.handler.*;
import com.gdx.main.screen.game.object.entity.GameEntity;
import com.gdx.main.screen.game.object.entity.Player;
import com.gdx.main.screen.game.object.particle.FadeParticle;
import com.gdx.main.screen.game.stage.GameStage;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class GameScreen implements Screen, Buildable {
    boolean paused = false;
    boolean end = false;
    float delta;
    float timeElapsed = 0;
    int secondsElapsed = 0;

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
    ParticleHandler particleHandler;
    CollisionHandler collisionHandler;


    // Stage
    GameStage backStage; // backgrounds
    GameStage subStage; // particles
    GameStage mainStage; // objects
    GameStage hudStage; // hud, ui
    GameStage pauseStage;
    GameStage endStage; // pause stage

    // background
    Background background;

    // Objects
    Player player;
    GameEntity testEntity;

    // HUD
    HUD hud;
    PauseMenu pauseMenu;
    EndMenu endMenu;

    CustomText beginText;

    // event flags
    boolean startFlag;
    boolean beginFlag;
    boolean begin2Flag;

    Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/music/cybergrind.ogg"));

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

    public void setBackground(Background background) {
        this.background = background;
        this.background.addActor(backStage);
    }

    @Override
    public void build() {
        this.backStage = new GameStage(viewport);
        this.subStage = new GameStage(viewport);
        this.mainStage = new GameStage(viewport);
        this.hudStage = new GameStage(viewport);
        this.pauseStage = new GameStage(viewport);
        this.endStage = new GameStage(viewport);
        // sets input processor to top level stage
        //Gdx.input.setInputProcessor(hudStage);

        // Handlers
        projectileHandler = new ProjectileHandler(viewport);
        entityHandler = new EntityHandler(viewport, camera, mainStage, subStage, debugger, stats, manager, gs);
        particleHandler = new ParticleHandler();
        collisionHandler = new CollisionHandler();

        // Backgrounds

        // objects
        player = new Player(viewport.getWorldWidth()/2, -20, new Vector2(1,0),
                viewport, camera, mainStage, subStage, debugger, gs, manager, stats);

        entityHandler.setPlayer(player);
        collisionHandler.setPlayer(player);

        //testEntity = new EnemyCharger(player, 100, 100, new Vector2(0,1), viewport, camera, mainStage, subStage, debugger, gs, manager, stats);

        // HUD
        hud = new HUD(player ,hudStage, viewport, gs, manager, stats);
        pauseMenu = new PauseMenu(this, pauseStage, viewport, gs, manager, stats);
        endMenu = new EndMenu(this, endStage, viewport, gs, manager, stats);
    }

    private void sequenceStart() {
        switch (secondsElapsed) {
            case 1:
                if(!startFlag) {
                    startFlag = true;
                }
                break;
            case 2:
                // BEGIN
                if(!beginFlag) {
                    music.play();
                    music.play();
                    music.setVolume(0.05f);
                    music.setLooping(true);

                    hud.showBeginText();

                    beginFlag = true;
                }
                break;

            case 3:
                // END BEGIN
                if(!begin2Flag) {
                    hud.fadeBeginText();
                    begin2Flag = true;
                }
        }
    }


    private void pauseGame(float delta) {
        // if escape key is pressed pause/resume game
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {setPaused(!paused);}
        this.delta = (paused) ? 0 : delta;
        timeElapsed += this.delta;
        secondsElapsed = (int) timeElapsed;

        if(paused && !end) {pauseMenu.update(delta, mouse);}
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    private void endGame(float delta) {
        if(player.hp <= 0) {
            this.end = true;
            this.paused = true;
            music.pause();
        }

        if(end) {
            endMenu.update(delta, mouse);
        }
    }

    public void switchScreen() {
        stats.finalizeHighScore();

        core.setScreen(core.menuScreen);

        // clear objects
        entityHandler.clear();
        particleHandler.clear();
        projectileHandler.clear();
        debugger.clear();

        core.menuScreen.setBackground(background);
    }

    private void keyInputs() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            stats.addScore(1000);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            stats.resetHighScore();
        }
    }

    private void update(float delta) {
        pauseGame(delta);
        endGame(this.delta);
        mouse.update();
        keyInputs();

        // handlers
        projectileHandler.update(this.delta);
        entityHandler.update(this.delta, mouse);
        particleHandler.update(this.delta);
        collisionHandler.update(this.delta);;

        // backgrounds
        background.update(this.delta);

        // hud & ui
        hud.update(this.delta, mouse);

        // event sequence
        sequenceStart();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        // draw methods
        backStage.draw();
        subStage.draw();
        mainStage.draw();
        hudStage.draw();
        if(paused && !end) {pauseStage.draw();}
        if(end) {endStage.draw();}
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
        music.dispose();
        this.dispose();
    }

    @Override
    public void dispose() {
        //stages
        backStage.dispose();
        subStage.dispose();
        mainStage.dispose();
        hudStage.dispose();
        pauseStage.dispose();
        endStage.dispose();
    }
}
