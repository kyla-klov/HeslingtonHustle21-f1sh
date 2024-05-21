package com.mygdx.game.Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Basic Building object the player can interact with
 * Basic Red Square but easily changed, String name is public so accessed by GameScreen
 */
public class Building {
    private final String name;
    private final Vector2 pos;


    public Building(float x, float y, String name) {
        pos = new Vector2(x, y);
        this.name = name;
    }

    public Vector2 getPos(){
        return pos;
    }

    public String getName() {
        return name;
    }

}
