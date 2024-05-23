package com.mygdx.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The ViewportAdapter class provides utility methods for drawing UI elements and handling screen-to-world coordinate transformations.
 */
public class ViewportAdapter {
    /**
     * Draws a texture on the UI.
     *
     * @param vp     the viewport
     * @param batch  the sprite batch
     * @param txt    the texture to draw
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param width  the width of the texture
     * @param height the height of the texture
     */
    public static void drawUI(Viewport vp, SpriteBatch batch, Texture txt, float x, float y, float width, float height){
        batch.draw(txt, x + vp.getCamera().position.x - vp.getWorldWidth()/2f, y + vp.getCamera().position.y - vp.getWorldHeight()/2f, width, height);
    }

    /**
     * Draws text using a BitmapFont.
     *
     * @param vp     the viewport
     * @param font   the bitmap font
     * @param batch  the sprite batch
     * @param text   the text to draw
     * @param x      the x coordinate
     * @param y      the y coordinate
     */
    public static void drawFont(Viewport vp, BitmapFont font, SpriteBatch batch, String text, float x, float y){
        font.draw(batch, text, x + vp.getCamera().position.x - vp.getWorldWidth()/2f, y + vp.getCamera().position.y - vp.getWorldHeight()/2f);
    }

    /**
     * Converts to screen coordinates.
     *
     * @param vp the viewport
     * @param x  the x coordinate in world space
     * @param y  the y coordinate in world space
     * @return the corresponding screen coordinates as a Vector2
     */
    public static Vector2 toScreen(Viewport vp, float x, float y){
        return new Vector2(x * vp.getScreenWidth() / vp.getWorldWidth() + (Gdx.graphics.getWidth() - vp.getScreenWidth()) / 2f, y * vp.getScreenHeight() / vp.getWorldHeight() + (Gdx.graphics.getHeight() - vp.getScreenHeight()) / 2f);
    }

    /**
     * Converts screen coordinates to game coordinates.
     *
     * @param vp      the viewport
     * @param screenX the x coordinate in screen space
     * @param screenY the y coordinate in screen space
     * @return the corresponding world coordinates as a Vector2
     */
    public static Vector2 screenToGame (Viewport vp, float screenX, float screenY){
        float transX = (screenX - (Gdx.graphics.getWidth() - vp.getScreenWidth())/2f) * vp.getWorldWidth() / vp.getScreenWidth();
        float transY = (Gdx.graphics.getHeight() - screenY - (Gdx.graphics.getHeight() - vp.getScreenHeight())/2f) * vp.getWorldHeight() / vp.getScreenHeight();
        return new Vector2(transX, transY);
    }

    /**
     * Checks if a touch point is over a specified rectangular area.
     *
     * @param touchX the x coordinate of the touch point
     * @param touchY the y coordinate of the touch point
     * @param x      the x coordinate of the top-left corner of the area
     * @param y      the y coordinate of the top-left corner of the area
     * @param width  the width of the area
     * @param height the height of the area
     * @return true if the touch point is over the area, false otherwise
     */
    public static boolean isOver(float touchX, float touchY, float x, float y, float width, float height) {
        return (touchX > x && touchX < x + width && touchY > y && touchY < y + height);
    }

    /**
     * Checks if a touch point is over a specified rectangle.
     *
     * @param touchX the x coordinate of the touch point
     * @param touchY the y coordinate of the touch point
     * @param rect   the rectangle
     * @return true if the touch point is over the rectangle, false otherwise
     */
    public static boolean isOver(float touchX, float touchY, Rectangle rect) {
        return (touchX > rect.x && touchX < rect.x + rect.width && touchY > rect.y && touchY < rect.y + rect.height);
    }
}
