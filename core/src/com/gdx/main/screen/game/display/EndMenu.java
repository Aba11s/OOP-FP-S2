package com.gdx.main.screen.game.display;

import com.badlogic.gdx.Screen;
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

public class EndMenu extends Menu{

    GameScreen screen;

    CustomSprite overlay;

    CustomText headText;
    CustomText finalScore;
    CustomText highScore;

    CustomText menuButton;

    public EndMenu(GameScreen screen, Stage stage, Viewport viewport, Settings gs, Manager manager, Stats stats) {
        super(stage, viewport, gs, manager, stats);
        this.screen = screen;

        // Rectangle Overlay
        overlay = new CustomSprite(manager.get("1x1.png"));
        overlay.setScale(viewport.getWorldWidth(), viewport.getWorldHeight());
        overlay.setOrigin(0,0);
        overlay.setPosition(0,0);
        overlay.setColor(Color.BLACK);
        overlay.setAlpha(0.5f);

        stage.addActor(overlay);
        Debugger.add(overlay);

        String path = "fonts/Minecraft.ttf";
        float middleScreenX = viewport.getWorldWidth()/2;
        float middleScreenY = viewport.getWorldHeight()/2;

        // head text
        headText = new CustomText(
                "GAME OVER", path, middleScreenX, middleScreenY + 60,
                true, 10, 10, 96, Color.WHITE
        );
        stage.addActor(headText);

        // final score
        finalScore = new CustomText(
                "FINAL SCORE : " + stats.getScore(), path, middleScreenX, middleScreenY - 20,
                true, 10, 10, 24, Color.YELLOW
        ) {
        };
        stage.addActor(finalScore);

        // highscore
        highScore = new CustomText(
                "PREVIOUS HIGHSCORE : " + stats.getHighScore(), path, middleScreenX, middleScreenY - 70,
                true, 10, 10, 24, Color.YELLOW
        );
        stage.addActor(highScore);

        // menu button
        menuButton = new CustomText(
                "MAIN MENU", path, middleScreenX, middleScreenY - 160,
                true, 10, 10, 32, Color.WHITE
        ) {
            @Override
            public void action() {
                setColor(Color.WHITE);
            }

            @Override
            public void hovered() {
                setColor(Color.YELLOW);
            }

            @Override
            public void clicked() {
                screen.switchScreen();
            }
        };
        stage.addActor(menuButton);

    }

    @Override
    public void update(float delta, Mouse mouse) {
        headText.update(delta, mouse);
        finalScore.update(delta, mouse);
        highScore.update(delta, mouse);
        menuButton.update(delta, mouse);

        finalScore.setText("FINAL SCORE : " + stats.getScore());
    }
}
