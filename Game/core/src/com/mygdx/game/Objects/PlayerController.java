package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.HesHustle;

public class PlayerController extends GameObject {
    public static final float width = 32;
    public static final float height = 64;
    public enum state {
        IDLE_LEFT,
        IDLE_UP,
        IDLE_RIGHT,
        IDLE_DOWN,
        WALK_LEFT,
        WALK_UP,
        WALK_RIGHT,
        WALK_DOWN,
    }
    Anim idle;
    public state Pstate;
    TextureRegion txr;
    public PlayerController(float xPos, float yPos)
    {
        super(xPos,yPos,width,height);
        Pstate = state.IDLE_DOWN;
        idle = new Anim(new Texture(Gdx.files.internal("Amelia_idle_16x16.png")),4);

    }
    public void update (float deltaTime) {

        bounds.x = pos.x - bounds.width / 2;
        bounds.y = pos.y - bounds.height / 2;

        pos = pos.mulAdd(getDir(),deltaTime*200);

        txr = idle.GetFrame(deltaTime);
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
        game.batch.end();
        game.batch.begin();
        game.batch.draw(txr,pos.x, pos.y, bounds.width, bounds.height);
        game.batch.end();
        game.batch.begin();
    }
    @Override
    public void Dispose() {

    }
}
