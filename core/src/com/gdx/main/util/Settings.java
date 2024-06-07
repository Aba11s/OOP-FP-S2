package com.gdx.main.util;

public class Settings {

    // -- Game settings -- //
    public float initialSpawnDelay = 5f;

    // -- Score settings -- //
    public int scoutScore = 10;
    public int fighterScore = 50;
    public int chargerScore = 200;

    /*
    OBJECTS
     */

    // -- ENTITIES -- //

    // PLAYER
    public int playerSpawnSize = 0;

    public float playerFireRate = 4f;
    public float playerHP = 100f;
    public float playerDMG = 0;

    // SCOUT
    public int scoutWeight = 120;
    public int scoutSpawnNumber = 10;
    public int scoutSpawnSize = 1;

    public float scoutSpeed = 120f;
    public float scoutRotation = 100f;
    public float scoutHP = 10f;
    public float scoutDMG = 10f;
    public float scoutSize = 12f;

    // FIGHTER
    public int fighterWeight = 40;
    public int fighterSpawnNumber = 4;
    public int fighterSpawnSize = 5;

    public float fighterFireRate = .25f;
    public float fighterSpeed = 80f;
    public float fighterRotation = 50f;
    public float fighterHP = 50f;
    public float fighterDMG = 10f;
    public float fighterSize = 18f;

    // CHARGER
    public int chargerWeight = 20;
    public int chargerSpawnNumber = 2;
    public int chargerSpawnSize = 10;

    public float chargerChargeTime = 3.5f;
    public float chargerReloadTime = 6.6f;
    public float chargerSpeed = 50f;
    public float chargerRotation = 120f;
    public float chargerHP = 120f;
    public float chargerDMG = 10f;
    public float chargerSize = 20f;


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

    // CHARGING-BULLET
    public float bulletCInitialSize = 10f;
    public float bulletCDamage = 20f;

    // BIG-BULLET-1
    public float bigBullet1Speed = 300f;
    public float bigBullet1Damage = 25f;

    // MISSILE

    // -- PARTICLES -- //

    // TRAIL
    public float trailScale = 0.25f;
    public float trailFadeSpeed = 1f;
}
