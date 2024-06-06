package com.gdx.main.util;

public class Settings {

    // -- Game settings -- //
    public float initialSpawnDelay = 5f;

    /*
    OBJECTS
     */

    // -- ENTITIES -- //

    // PLAYER
    public int playerSpawnSize = 1;

    public float playerFireRate = 2f;

    // SCOUT
    public int scoutWeight = 120;
    public int scoutSpawnNumber = 5;
    public int scoutSpawnSize = 1;

    // FIGHTER
    public int fighterWeight = 4000;
    public int fighterSpawnNumber = 3;
    public int fighterSpawnSize = 5 * 3;

    public float fighterFireRate = .5f;
    public float fighterSpeed = 80f;
    public float fighterRotation = 50f;
    public float fighterHP = 50f;
    public float fighterDMG = 100f;
    public float fighterSize = 20f;

    // -- PROJECTILES -- //

    // BULLET-1 - DEFAULT
    public float bullet1Speed = 600f;
    public float bullet1Damage = 10f;
    public float bullet1Size = 5f;
    public float bullet1Scale = 0.3f;

    // BULLET-2
    public float bullet2Speed = 400f;
    public float bullet2Damage = 10f;
    public float bullet2Size = 6f;
    public float bullet2Scale = 0.3f;

    // BIG-BULLET-1
    public float bigBullet1Speed = 300f;
    public float bigBullet1Damage = 20f;

    // MISSILE
}
