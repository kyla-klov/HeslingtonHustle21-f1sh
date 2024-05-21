package com.mygdx.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewportAdapter {
    public static void drawUI(Viewport vp, SpriteBatch batch, Texture txt, float x, float y, float width, float height){
        batch.draw(txt, x + vp.getCamera().position.x - vp.getWorldWidth()/2f, y + vp.getCamera().position.y - vp.getWorldHeight()/2f, width, height);
    }

    public static void drawFont(Viewport vp, BitmapFont font, SpriteBatch batch, String text, float x, float y){
        font.draw(batch, text, x + vp.getCamera().position.x - vp.getWorldWidth()/2f, y + vp.getCamera().position.y - vp.getWorldHeight()/2f);
    }

    public static Vector2 toScreen(Viewport vp, float x, float y){
        return new Vector2(x * vp.getScreenWidth() / vp.getWorldWidth() + (Gdx.graphics.getWidth() - vp.getScreenWidth()) / 2f, y * vp.getScreenHeight() / vp.getWorldHeight() + (Gdx.graphics.getHeight() - vp.getScreenHeight()) / 2f);
    }

    public static Vector2 screenToGame (Viewport vp, float screenX, float screenY){
        float transX = (screenX - (Gdx.graphics.getWidth() - vp.getScreenWidth())/2f) * vp.getWorldWidth() / vp.getScreenWidth();
        float transY = (Gdx.graphics.getHeight() - screenY - (Gdx.graphics.getHeight() - vp.getScreenHeight())/2f) * vp.getWorldHeight() / vp.getScreenHeight();
        return new Vector2(transX, transY);
    }

    public static boolean isOver(float touchX, float touchY, float x, float y, float width, float height) {
        return (touchX > x && touchX < x + width && touchY > y && touchY < y + height);
    }

    public static boolean isOver(float touchX, float touchY, Rectangle rect) {
        return (touchX > rect.x && touchX < rect.x + rect.width && touchY > rect.y && touchY < rect.y + rect.height);
    }
}
