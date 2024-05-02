package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Ball {
    private final float radius;
    private final Vector2 position;
    private final Vector2 velocity;
    private final Vector2 acceleration;
    private final ShapeRenderer shapeRenderer;

    public Ball(float x, float y, float radius) {
        this.radius = radius;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(100, 0);
        this.acceleration = new Vector2(0, 0);
        this.shapeRenderer = new ShapeRenderer();

    }

    public void update(float deltaTime){
        position.add(velocity.cpy().scl(deltaTime));
        velocity.add(acceleration.cpy().scl(deltaTime));

        if (position.x > Gdx.graphics.getWidth()){
            position.x -= Gdx.graphics.getWidth();
        }
        if (position.x < 0){
            position.x += Gdx.graphics.getWidth();
        }
    }

    public void render(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // or ShapeType.Line for an outline
        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.circle(position.x, position.y, radius);
        shapeRenderer.end();
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
