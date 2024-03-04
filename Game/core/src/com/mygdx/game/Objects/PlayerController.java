package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.HesHustle;

public class PlayerController extends GameObject {
    public static final float width = 40;
    public static final float height = 40;

    public PlayerController(float xPos, float yPos)
    {
        super(xPos,yPos,width,height);

    }
    public void update (float deltaTime) {

        bounds.x = pos.x - bounds.width / 2;
        bounds.y = pos.y - bounds.height / 2;

        pos = pos.mulAdd(getDir(),deltaTime*200);
    }

    public Vector2 getDir() {
        //find overall direction of inputs and normalize vector2
        Vector2 dir = new Vector2(0,0);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dir.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dir.x += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dir.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dir.y -= 1;
        }
        return dir.nor();
    }
    @Override
    public void render(Matrix4 projection, HesHustle game, ShapeRenderer shape){
        //player box
        shape.setProjectionMatrix(projection);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.CHARTREUSE);
        shape.rect(pos.x, pos.y, bounds.width, bounds.height);
        shape.end();
    }
    @Override
    public void Dispose() {

    }
}
