package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.HesHustle;

/**
 * Basic Building object the player can interact with
 * Basic Red Square but easily changed, String name is public so accessed by GameScreen
 */
public class Building extends GameObject{
    public String name;


    public Building(float x, float y, float width, float height,String name) {
        super(x, y, width, height);
        this.name = name;
    }


}
