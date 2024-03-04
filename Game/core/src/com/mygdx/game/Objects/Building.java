package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.HesHustle;

public class Building extends GameObject{
    public String name;
    public Boolean interact;
    public BitmapFont font2;
    public Building(float x, float y, float width, float height,String name, Boolean interact) {
        super(x, y, width, height);
        this.name = name;
        this.interact = interact;
        font2 = new BitmapFont(Gdx.files.internal("font.fnt"));
    }

    @Override
    public void Render(Matrix4 projection, HesHustle game, ShapeRenderer shape, SpriteBatch sb) {
        sb.end();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.rect(pos.x, pos.y, 100, 100);
        shape.end();
        sb.begin();
        font2.draw(game.batch, name, pos.x+3, pos.y +bounds.height-3);
        sb.end();
        sb.begin();


    }

    @Override
    public void Dispose() {
        font2.dispose();
    }
}
