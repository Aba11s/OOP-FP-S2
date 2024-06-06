package com.gdx.main.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Manager extends AssetManager {

    public Manager() {

        TextureAtlas atlas = new TextureAtlas();

        loadParticles();
    }

    private void loadParticles() {
        super.load("textures/object/particle/particle-trail-1.png", Texture.class);
    }
}
