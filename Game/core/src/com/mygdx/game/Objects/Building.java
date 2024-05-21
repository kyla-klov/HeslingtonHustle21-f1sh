package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Basic Building object the player can interact with
 * Basic Red Square but easily changed, String name is public so accessed by GameScreen
 */
public class Building {
    private final String name;
    private final Vector2 pos;
    private final Texture txt;


    public Building(float x, float y, String name, Texture txt) {
        pos = new Vector2(x, y);
        this.name = name;
        this.txt = txt;
    }

    public Vector2 getPos(){
        return pos;
    }

    public String getName() {
        return name;
    }

    public void render(SpriteBatch batch) {
        batch.draw(txt, pos.x, pos.y, 64, 64);
    }

}
