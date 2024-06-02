package com.gdx.main.screen.game.object;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gdx.main.helper.debug.Debuggable;
import com.gdx.main.helper.misc.Mouse;

public interface GameObject extends Debuggable {

    void update(float delta, Mouse mouse); // logic updates
}
