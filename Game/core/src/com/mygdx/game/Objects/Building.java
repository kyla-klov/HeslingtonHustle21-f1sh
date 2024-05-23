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

    /**
     * Constructs a Building with the specified position, name, and texture.
     *
     * @param x    the x position of the building
     * @param y    the y position of the building
     * @param name the name of the building
     * @param txt  the texture of the building
     */
    public Building(float x, float y, String name, Texture txt) {
        pos = new Vector2(x, y);
        this.name = name;
        this.txt = txt;
    }

    /**
     * Gets the position of the building.
     *
     * @return the position of the building
     */
    public Vector2 getPos(){
        return pos;
    }

    /**
     * Gets the name of the building.
     *
     * @return the name of the building
     */
    public String getName() {
        return name;
    }

    /**
     * Renders the building using the specified sprite batch.
     *
     * @param batch the sprite batch used for rendering
     */
    public void render(SpriteBatch batch) {
        batch.draw(txt, pos.x, pos.y, 64, 64);
    }

}
