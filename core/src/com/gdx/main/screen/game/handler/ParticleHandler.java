package com.gdx.main.screen.game.handler;

import com.gdx.main.screen.game.object.particle.Particle;

import java.util.ArrayList;

public class ParticleHandler {

    static ArrayList<Particle> particles = new ArrayList<>();

    public ParticleHandler() {

    }

    public static void add(Particle particle) {
        particles.add(particle);
    }

    public static void remove(Particle particle) {
        particles.remove(particle);
    }

    public void update(float delta) {
        for(Particle particle : particles) {
            particle.update(delta);
        }
    }
}
