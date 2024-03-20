package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.HesHustle;
/** Base Class for most objects, Allows render and update to be inherited so in the main game objects can be looped through in a list

 */
public class GameObject {
    /**Vector2 storing x,y position of all GameObject
     */
    public Vector2 pos;
    /**Rectangle storing width and height of GameObject
     */
    public final Rectangle bounds;

    /**
     * Constructor of GameObject
     * @param x starting x position
     * @param y starting y position
     * @param width width of object
     * @param height height of object
     */
    public GameObject (float x, float y, float width, float height) {
        this.pos = new Vector2(x, y);
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    /**
     * Update GameObject before rendering.
     * @param delta deltaTime
     */
    public void update(float delta){

    }

    /**
     * Render object
     * @param projection Projection matrix of the viewport
     * @param game to access spritebatch and fonts
     * @param shape ShapeRenderer for debug rectangles and such
     */
    public void render(Matrix4 projection, HesHustle game, ShapeRenderer shape) {

    }
    public void Dispose(){

    }
}
