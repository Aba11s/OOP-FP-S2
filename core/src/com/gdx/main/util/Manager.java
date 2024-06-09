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
        loadMisc();
    }

    private void loadParticles() {
        // basic shapes
        super.load("textures/object/particle/circle.png", Texture.class);
        super.load("textures/object/particle/hollow-circle.png", Texture.class);
        super.load("textures/object/particle/hollow-circle-gradient.png", Texture.class);

        // trail
        super.load("textures/object/particle/particle-trail-1.png", Texture.class);

        // projectile impact
        super.load("textures/object/particle/bullet-impact-sprite-1.png", Texture.class);
        super.load("textures/object/particle/bullet-impact-ani-explosion-1.png", Texture.class);
        super.load("textures/object/particle/bullet-impact-ani-explosion-2.png", Texture.class);

        super.load("textures/object/particle/engine-particle-1.png", Texture.class);
        super.load("textures/object/particle/engine-particle-2.png", Texture.class);
    }

    private void loadEntities() {
        super.load("textures/object/entity/player/player-pointer.png", Texture.class);

        super.load("textures/object/entity/enemy/scout-2.png",Texture.class);
        super.load("textures/object/entity/enemy/fighter-2.png",Texture.class);
        super.load("textures/object/entity/enemy/bomber-2.png",Texture.class);
    }

    private void loadProjectiles() {
        super.load("01.png", Texture.class);
        super.load("02.png", Texture.class);
    }

    private void loadMisc() {
        super.load("1x1.png", Texture.class);
    }
}
