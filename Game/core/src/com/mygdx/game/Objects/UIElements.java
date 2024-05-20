package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Utils.AchievementHandler;

public class UIElements {
    private final Viewport vp;
    private final BitmapFont font;
    private final Texture achievementsButton;
    private final Texture tap;
    private final EnergyBar energyBar;
    private final AchievementsDisplay achievementsDisplay;

    private final float startX, startY, endX, endY;
    private float curX, curY;
    private float progress;
    private boolean tapActive;
    private boolean buttonPressed;

    public UIElements(Viewport vp, AchievementHandler achievementHandler) {
        this.vp = vp;
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        achievementsButton  = new Texture(Gdx.files.internal("badge.png"));
        tap = new Texture(Gdx.files.internal("tap.png"));
        energyBar = new EnergyBar(this, 80, 600, 270, 50, 27);
        achievementsDisplay = new AchievementsDisplay(this, achievementHandler, 1200, 370);
        startX = 1490; startY = 750;
        endX = 1475; endY = 775;
        tapActive = true;
    }

    public void update(float deltaTime){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            buttonPressed = true;
            touchDown(Gdx.input.getX(), Gdx.input.getY());
        }
        if (buttonPressed && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            buttonPressed = false;
            touchUp();
        }
        if (!tapActive) return;
        float duration = 1.0f; // Duration of the glide in seconds
        float easingFactor = deltaTime / duration; // Adjust this value to control the speed
        progress += easingFactor;

        // Quadratic easing out: f(t) = -t*(t-2)
        float easedProgress = -progress * (progress - 2);

        // Update current position based on eased progress
        curX = startX + (endX - startX) * easedProgress;
        curY = startY + (endY - startY) * easedProgress;

        if (progress >= 2f){
            progress = 0.0f;
            curX = startX;
            curY = startY;
        }
    }

    public void render(SpriteBatch batch, String time, int day, int sleep, int rec, int eat, int study, float energy, float score){
        energyBar.render(batch, energy);
        achievementsDisplay.render(batch);
        drawUI(batch, achievementsButton, 1450, 800, 50, 50);
        if (tapActive) drawUI(batch, tap, curX, curY, 50, 50);
        font.getData().setScale(1.4f);
        drawFont(font, batch, "Score: " + score, 100, 825);
        font.getData().setScale(1f);
        drawFont(font, batch, time, 1200, 840);
        drawFont(font, batch, "Day: " + day, 1050, 840);
        drawFont(font, batch, "Sleep Count: " + sleep, 100, 780);
        drawFont(font, batch, "Rec Count: " + rec, 100, 750);
        drawFont(font, batch, "Eat Count: " + eat, 100, 720);
        drawFont(font, batch, "Study Hours: " + study, 100, 690);
    }

    public void drawUI(SpriteBatch batch, Texture txt, float x, float y, float width, float height){
        batch.draw(txt, x + vp.getCamera().position.x - vp.getWorldWidth()/2f, y + vp.getCamera().position.y - vp.getWorldHeight()/2f, width, height);
    }

    public void drawFont(BitmapFont font, SpriteBatch batch, String text, float x, float y){
        font.draw(batch, text, x + vp.getCamera().position.x - vp.getWorldWidth()/2f, y + vp.getCamera().position.y - vp.getWorldHeight()/2f);
    }

    public Vector2 toScreen(float x, float y){
        return new Vector2(x * vp.getScreenWidth() / vp.getWorldWidth() + (Gdx.graphics.getWidth() - vp.getScreenWidth()) / 2f, y * vp.getScreenHeight() / vp.getWorldHeight() + (Gdx.graphics.getHeight() - vp.getScreenHeight()) / 2f);
    }

    public Vector2 screenToGame (float screenX, float screenY){
        float transX = (screenX - (Gdx.graphics.getWidth() - vp.getScreenWidth())/2f) * vp.getWorldWidth() / vp.getScreenWidth();
        float transY = (Gdx.graphics.getHeight() - screenY - (Gdx.graphics.getHeight() - vp.getScreenHeight())/2f) * vp.getWorldHeight() / vp.getScreenHeight();
        return new Vector2(transX, transY);
    }

    public boolean isPressed(float touchX, float touchY, float x, float y, float width, float height) {
        return (touchX > x && touchX < x + width && touchY > y && touchY < y + height);
    }

    public void touchDown(int screenX, int screenY){
        Vector2 gamePos = screenToGame(screenX, screenY);
        if (isPressed(gamePos.x, gamePos.y, 1450, 800, 50, 50)){
            tapActive = false;
            if (achievementsDisplay.isVisible()){
                achievementsDisplay.hide();
            } else{
                achievementsDisplay.show();
            }
        }
    }

    public void touchUp(){
    }
}
