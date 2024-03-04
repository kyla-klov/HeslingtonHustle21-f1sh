package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.HesHustle;
import com.mygdx.game.HesHustle;

public class GameObject {
    public Vector2 pos;
    public final Rectangle bounds;
    public GameObject (float x, float y, float width, float height) {
        this.pos = new Vector2(x, y);
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    public void Render(Matrix4 projection, HesHustle game, ShapeRenderer shape, SpriteBatch sb) {

    }
    public void Dispose(){

    }
}
