package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UIElements {
    private final Viewport vp;

    public UIElements(Viewport vp) {
        this.vp = vp;
    }

    public void drawUI(SpriteBatch batch, Texture txt, float x, float y, float width, float height){
        batch.draw(txt, x + vp.getCamera().position.x - vp.getWorldWidth()/2f, y + vp.getCamera().position.y - vp.getWorldHeight()/2f, width, height);
    }

    public Vector2 toScreen(float x, float y){
        return new Vector2(x * vp.getScreenWidth() / vp.getWorldWidth() + (Gdx.graphics.getWidth() - vp.getScreenWidth()) / 2f, y * vp.getScreenHeight() / vp.getWorldHeight() + (Gdx.graphics.getHeight() - vp.getScreenHeight()) / 2f);
    }

    public Vector2 screenToGame (float screenX, float screenY){
        float transX = (screenX - (Gdx.graphics.getWidth() - vp.getScreenWidth())/2f) * vp.getWorldWidth() / vp.getScreenWidth();
        float transY = (Gdx.graphics.getHeight() - screenY - (Gdx.graphics.getHeight() - vp.getScreenHeight())/2f) * vp.getWorldHeight() / vp.getScreenHeight();
        return new Vector2(transX, transY);
    }
}
