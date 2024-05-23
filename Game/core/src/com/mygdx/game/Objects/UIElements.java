package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Utils.AchievementHandler;
import com.mygdx.game.Utils.ViewportAdapter;

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

    public UIElements(Viewport vp, AchievementHandler achievementHandler,
                      EnergyBar energyBar, AchievementsDisplay achievementsDisplay, BitmapFont font){
        this.vp = vp;
        this.energyBar = energyBar;
        this.achievementsDisplay = achievementsDisplay;
        this.font = font;
        achievementsButton  = new Texture(Gdx.files.internal("badge.png"));
        tap = new Texture(Gdx.files.internal("tap.png"));
        startX = 1490; startY = 750;
        endX = 1475; endY = 775;
        tapActive = true;
    }
    public UIElements(Viewport vp, AchievementHandler achievementHandler) {
        this(vp, achievementHandler,
                new EnergyBar(vp, 80, 600, 270, 50, 27),
                new AchievementsDisplay(vp, achievementHandler, 1200, 370),
                new BitmapFont(Gdx.files.internal("font.fnt")));
//        this.vp = vp;
//        font = new BitmapFont(Gdx.files.internal("font.fnt"));
//        achievementsButton  = new Texture(Gdx.files.internal("badge.png"));
//        tap = new Texture(Gdx.files.internal("tap.png"));
//        energyBar = new EnergyBar(vp, 80, 600, 270, 50, 27);
//        achievementsDisplay = new AchievementsDisplay(vp, achievementHandler, 1200, 370);
//        startX = 1490; startY = 750;
//        endX = 1475; endY = 775;
//        tapActive = true;
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
        ViewportAdapter.drawUI(vp, batch, achievementsButton, 1450, 800, 50, 50);
        if (tapActive) ViewportAdapter.drawUI(vp, batch, tap, curX, curY, 50, 50);
        font.getData().setScale(1.4f);
        ViewportAdapter.drawFont(vp, font, batch, "Score: " + score, 100, 825);
        font.getData().setScale(1f);
        ViewportAdapter.drawFont(vp, font, batch, time, 1200, 840);
        ViewportAdapter.drawFont(vp, font, batch, "Day: " + day, 1050, 840);
        ViewportAdapter.drawFont(vp, font, batch, "Sleep Count: " + sleep, 100, 780);
        ViewportAdapter.drawFont(vp, font, batch, "Rec Count: " + rec, 100, 750);
        ViewportAdapter.drawFont(vp, font, batch, "Eat Count: " + eat, 100, 720);
        ViewportAdapter.drawFont(vp, font, batch, "Study Hours: " + study, 100, 690);
    }

    public void touchDown(int screenX, int screenY){
        Vector2 gamePos = ViewportAdapter.screenToGame(vp, screenX, screenY);
        if (ViewportAdapter.isOver(gamePos.x, gamePos.y, 1450, 800, 50, 50)){
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

    public float getCurX() {
        return curX;
    }

    public float getCurY() {
        return curY;
    }
}
