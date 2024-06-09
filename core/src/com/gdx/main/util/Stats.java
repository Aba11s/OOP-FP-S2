package com.gdx.main.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class Stats {

    Preferences pref = Gdx.app.getPreferences("myStats");
    String highscore = "highscore";

    private int score;

    public Stats() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return pref.getInteger(highscore);
    }

    public void addScore(int points) {
        this.score += points;
        System.out.println(score + " " + pref.getInteger(highscore));
    }

    public void resetScore() {
        this.score = 0;
    }

    public void resetHighScore() {
        pref.putInteger(highscore, 0);
        pref.flush();
    }

    public void finalizeHighScore() {
        pref.putInteger(highscore, (Math.max(score, pref.getInteger(highscore))));
        pref.flush();
    }
}
