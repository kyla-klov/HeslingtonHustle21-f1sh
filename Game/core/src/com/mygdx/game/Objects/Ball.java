package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Ball {
    private final float radius;
    private final Vector2 position;
    private final Vector2 velocity;
    private final Vector2 acceleration;
    private final Texture txt;

    private float angle = 0;

    public Ball(Texture txt, float x, float y, float radius) {
        this.radius = radius;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(100, 0);
        this.acceleration = new Vector2(0, 0);
        this.txt = txt;
    }

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

    public void render(SpriteBatch batch){
        batch.draw(new TextureRegion(txt), position.x - radius, position.y - radius, (int) radius, (int) radius, (int) (2*radius), (int) (2*radius), 1, 1, angle);
    }

    public float getRadius() {
        return radius;
    }

    public Vector2 getPosition() {
        return position.cpy();
    }
    public Vector2 nextPosition(float deltaTime){
        return position.cpy().add(velocity.cpy().scl(deltaTime));
    }
    public void setPosition(Vector2 position) {
        this.position.set(position);
    }
    public Vector2 getVelocity() {
        return velocity.cpy();
    }
    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }
    public Vector2 getAcceleration() {
        return acceleration.cpy();
    }
    public void setAcceleration(Vector2 acceleration) {
        this.acceleration.set(acceleration);
    }
}
