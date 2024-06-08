package com.gdx.main.screen.game.display;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.actor.custom_items.CustomBackground;
import com.gdx.main.helper.actor.custom_items.CustomSprite;
import com.gdx.main.helper.debug.Debugger;

public class Background {

    CustomBackground bg1;
    CustomBackground bg2;
    CustomBackground bg3;

    public Background(Stage stage, Viewport viewport) {
        bg1 = new CustomBackground("background/bg-1-2.png", 1, -3f, -3f, viewport);
        stage.addActor(bg1);
        Debugger.add(bg1);

        bg2 = new CustomBackground("background/bg-2-2.png", 1, -6f, -6f, viewport);
        stage.addActor(bg2);
        Debugger.add(bg2);

        bg3 = new CustomBackground("background/bg-3-2.png", 1, -12f, -12f, viewport);
        stage.addActor(bg3);
        Debugger.add(bg3);
    }

    public void addActor(Stage stage) {
        stage.addActor(bg1);
        stage.addActor(bg2);
        stage.addActor(bg3);
    }

    public void update(float delta) {
        bg1.update(delta);
        bg2.update(delta);
        bg3.update(delta);
    }
}
