package com.gdx.main.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Manager extends AssetManager {

    public Manager() {

        TextureAtlas atlas = new TextureAtlas();

        loadParticles();
        loadEntities();
        loadProjectiles();
    }

    private void loadParticles() {
        super.load("textures/object/particle/particle-trail-1.png", Texture.class);
    }

    private void loadEntities() {
        super.load("textures/object/entity/enemy/scout-2.png",Texture.class);
        super.load("textures/object/entity/enemy/fighter-2.png",Texture.class);
        super.load("textures/object/entity/enemy/bomber-2.png",Texture.class);
    }

    private void loadProjectiles() {
        super.load("01.png", Texture.class);
        super.load("02.png", Texture.class);
    }
}
