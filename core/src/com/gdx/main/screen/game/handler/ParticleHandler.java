package com.gdx.main.screen.game.handler;

import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.game.object.particle.Particle;

import java.util.ArrayList;

public class ParticleHandler {

    private final static ArrayList<Particle> particles = new ArrayList<>();
    private final static ArrayList<Particle> particleTrash = new ArrayList<>();

    public ParticleHandler() {

    }

    public static void add(Particle particle) {particles.add(particle);}

    public static void remove(Particle particle) {particleTrash.add(particle);}

    public void update(float delta) {
        // updates every particle
        for(Particle particle : particles) {
            particle.update(delta, null);
        }

        // removes particle in particles present in trash
        for(Particle particle : particleTrash) {
            particles.remove(particle);
        }

        // clears trash
        particleTrash.clear();

        // debug
//        System.out.println("particles:" + particles.size() +","+ particleTrash.size());
//        for(int i = 0; i < 1000; i++) {
//            System.out.print("A");
//        }
    }

    public void clear() {
        particles.clear();
    }
}
