package com.gdx.main.screen.game.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.actor.custom_items.CustomSprite;
import com.gdx.main.helper.actor.custom_items.CustomText;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.GameScreen;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class PauseMenu extends Menu {

    boolean paused = false;
    GameScreen screen;

    // text objects
    CustomText pauseHead;
    CustomText score;
    CustomText scoreCounter;
    CustomText highScore;
    CustomText highScoreCounter;
    CustomText resumeButton;
    CustomText menuButton;
    CustomSprite overlay;

    public PauseMenu(GameScreen screen, Stage stage, Viewport viewport, Settings gs, Manager manager, Stats stats) {
        super(stage, viewport, gs, manager, stats);
        this.screen = screen;

        float middleScreenX = viewport.getWorldWidth()/2;

        // Rectangle Overlay
        overlay = new CustomSprite(manager.get("1x1.png"));
        overlay.setScale(viewport.getWorldWidth(), viewport.getWorldHeight());
        overlay.setOrigin(0,0);
        overlay.setPosition(0,0);
        overlay.setColor(Color.BLACK);
        overlay.setAlpha(0.5f);

        stage.addActor(overlay);
        Debugger.add(overlay);

        // pause Header
        pauseHead = new CustomText(
                "PAUSED", "fonts/Minecraft.ttf", middleScreenX, 500,
                true, 10, 10, 48, Color.WHITE);
        stage.addActor(pauseHead);
        Debugger.add(pauseHead);

        // score
        highScore = new CustomText(
                "HIGHSCORE : " + stats.getHighScore(), "fonts/Minecraft.ttf", middleScreenX, 440,
                true, 10, 10, 16, Color.YELLOW
        );
        stage.addActor(highScore);
        Debugger.add(highScore);

        // resume button
        resumeButton = new CustomText(
                "RESUME", "fonts/Minecraft.ttf", middleScreenX, 350,
                true, 10, 10, 24, Color.WHITE) {

            @Override
            public void action() {setColor(Color.WHITE);}

            @Override
            public void hovered() {setColor(Color.YELLOW);}

            @Override
            public void clicked() {screen.setPaused(false);}
        };
        stage.addActor(resumeButton);
        Debugger.add(resumeButton);

        menuButton = new CustomText(
                "MAIN MENU", "fonts/Minecraft.ttf", middleScreenX, 250,
                true, 10, 10, 24, Color.WHITE) {

            @Override
            public void action() {setColor(Color.WHITE);}

            @Override
            public void hovered() {setColor(Color.YELLOW);}

            @Override
            public void clicked() {screen.switchScreen();}
        };
        stage.addActor(menuButton);
        Debugger.add(menuButton);
    }

    @Override
    public void update(float delta, Mouse mouse) {
        pauseHead.update(delta, mouse);
        highScore.update(delta, mouse);
        resumeButton.update(delta, mouse);
        menuButton.update(delta, mouse);
    }
}
