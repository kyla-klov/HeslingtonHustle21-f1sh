package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * The Ball class represents a ball object with position, velocity, and acceleration.
 * It handles updating the ball's position and rendering the ball with a rotation effect.
 */
public class Ball {
    private final float radius;
    private final Vector2 position;
    private final Vector2 velocity;
    private final Vector2 acceleration;
    private final Texture txt;

    private float angle = 0;

    /**
     * Constructs a Ball with the specified texture, position, and radius.
     *
     * @param txt    the texture of the ball
     * @param x      the initial x position of the ball
     * @param y      the initial y position of the ball
     * @param radius the radius of the ball
     */
    public Ball(Texture txt, float x, float y, float radius) {
        this.radius = radius;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 0);
        this.txt = txt;
    }

    /**
     * Updates the ball's position and velocity based on the elapsed time and world width.
     *
     * @param deltaTime the time elapsed since the last update
     * @param wldWidth  the width of the world
     */
    public void update(float deltaTime, float wldWidth){
        position.add(velocity.cpy().scl(deltaTime));
        velocity.add(acceleration.cpy().scl(deltaTime));
        float angularVelocity = (180 * velocity.x) / (3.14f * radius);
        angle -= angularVelocity * deltaTime;
        if (position.x > wldWidth){
            position.x -= wldWidth;
        }
        if (position.x < 0){
            position.x += wldWidth;
        }
    }

    /**
     * Renders the ball using the specified sprite batch.
     *
     * @param batch the sprite batch used for rendering
     */
    public void render(SpriteBatch batch){
        batch.draw(new TextureRegion(txt), position.x - radius, position.y - radius, (int) radius, (int) radius, (int) (2*radius), (int) (2*radius), 1, 1, angle);
    }

    /**
     * Gets the radius of the ball.
     *
     * @return the radius of the ball
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Gets the position of the ball.
     *
     * @return a copy of the position of the ball
     */
    public Vector2 getPosition() {
        return position.cpy();
    }


    /**
     * Calculates the next position of the ball based on the elapsed time.
     *
     * @param deltaTime the time elapsed
     * @return the next position of the ball
     */
    public Vector2 nextPosition(float deltaTime){
        return position.cpy().add(velocity.cpy().scl(deltaTime));
    }

    /**
     * Sets the position of the ball.
     *
     * @param position the new position of the ball
     */
    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    /**
     * Gets the velocity of the ball.
     *
     * @return a copy of the velocity of the ball
     */
    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param velocity the new velocity of the ball
     */
    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    /**
     * Gets the acceleration of the ball.
     *
     * @return a copy of the acceleration of the ball
     */
    public Vector2 getAcceleration() {
        return acceleration.cpy();
    }

    /**
     * Sets the acceleration of the ball.
     *
     * @param acceleration the new acceleration of the ball
     */
    public void setAcceleration(Vector2 acceleration) {
        this.acceleration.set(acceleration);
    }

}
