package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Utils.Achievement;
import com.mygdx.game.Utils.AchievementHandler;
import com.mygdx.game.Utils.ResourceManager;

import java.util.List;

public class AchievementsDisplay implements Disposable {
    final private Texture background;
    final private Texture tick;
    final private Texture padlock;
    final private Texture scrollBar;
    final private Texture highlight;
    final private UIElements uiElements;
    final private AchievementHandler achievementHandler;
    final private ResourceManager resourceManager;

    private final float posX, posY, scrollX;
    private final float tickX, padlockX, selectY, selectWidth, selectHeight;
    private final float bgWidth, bgHeight, achievementWidth, achievementHeight, scrollWidth, scrollHeight;

    private final float scale = 1f;

    private boolean scrolling;
    private boolean unlocked;
    private boolean visible;
    private float relY;
    private float scrollY;


    public AchievementsDisplay(UIElements uiElements, AchievementHandler achievementHandler, float posX, float posY){
        this.achievementHandler = achievementHandler;
        this.uiElements = uiElements;
        this.posX = posX;
        this.posY = posY;

        resourceManager = new ResourceManager();

        unlocked = true;
        background = resourceManager.addDisposable(new Texture(Gdx.files.internal("AchievementsDisplay/AchievementsBackground.png")));
        tick = resourceManager.addDisposable(new Texture(Gdx.files.internal("AchievementsDisplay/check-mark.png")));
        padlock = resourceManager.addDisposable(new Texture(Gdx.files.internal("AchievementsDisplay/lock-padlock-symbol-for-security-interface.png")));
        scrollBar = resourceManager.addDisposable(new Texture(Gdx.files.internal("AchievementsDisplay/ScrollBar2.png")));
        highlight = resourceManager.addDisposable(new Texture(Gdx.files.internal("AchievementsDisplay/HighlightSelected.png")));

        bgWidth = background.getWidth() * scale;
        bgHeight = background.getHeight() * scale;
        achievementWidth = 210f * scale;
        achievementHeight = 70f * scale;
        scrollWidth = 13f * scale;
        scrollHeight = 67f * scale;
        scrollY = bgHeight*0.67f + posY;
        scrollX = posX+bgWidth*0.87f;
        tickX = posX + bgWidth * 0.3f;
        padlockX = posX + bgWidth * 0.65f;
        selectY = posY + bgHeight * 0.88f;
        selectWidth = 25;
        selectHeight = 25;
    }

    public void render(SpriteBatch batch){
        if (!visible) return;

        updateScroller();

        uiElements.drawUI(batch, background, posX, posY, bgWidth, bgHeight);
        uiElements.drawUI(batch, scrollBar, scrollX, scrollY, scrollWidth, scrollHeight);
        if (unlocked) uiElements.drawUI(batch, highlight, tickX, selectY, selectWidth, selectHeight);
        else uiElements.drawUI(batch, highlight, padlockX, selectY, selectWidth, selectHeight);
        uiElements.drawUI(batch, tick, tickX, selectY, 25, 25);
        uiElements.drawUI(batch, padlock, padlockX, selectY, 25, 25);
        drawAchievements(batch);
    }

    private void updateScroller(){
        //Handle Scroll Logic
        if (scrolling){
            scrollY = uiElements.screenToGame(0, Gdx.input.getY()).y - relY;
            if (scrollY < posY + bgHeight*0.17f){
                scrollY = posY + bgHeight*0.17f;
            } else if (scrollY > posY + bgHeight*0.67f){
                scrollY = posY + bgHeight*0.67f;
            }
        }
    }

    private void drawAchievements(SpriteBatch batch){
        //Calculate the screen coords of scissor box
        Vector2 blCorner = uiElements.toScreen(posX, posY + 25 * scale);
        Vector2 trCorner = uiElements.toScreen(posX+bgWidth, posY+bgHeight - 55 * scale);

        int scissorX = (int) blCorner.x;
        int scissorY = (int) blCorner.y;
        int scissorWidth = (int) (trCorner.x - blCorner.x);
        int scissorHeight = (int) (trCorner.y - blCorner.y);

        //Draw Achievements
        batch.draw(highlight, 0, 0, 0, 0);
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
        List<Achievement> achievements = achievementHandler.getAchievements();
        int c = 0;
        for (Achievement achievement : achievements){
            if (achievement.isUnlocked() == unlocked){
                c++;
                uiElements.drawUI(batch, achievement.getAchievementTexture(), posX + (bgWidth - achievementWidth)/2f, posY + bgHeight * 0.95f + (bgHeight*0.67f + posY - scrollY)*3 - (bgHeight/10 + achievementHeight) * c, achievementWidth, achievementHeight);
            }
        }
        batch.draw(highlight, 0, 0, 0, 0);
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
    }

    @Override
    public void dispose() {
        resourceManager.disposeAll();
    }

    public void touchDown(int screenX, int screenY) {
        Vector2 gamePos = uiElements.screenToGame(screenX, screenY);
        if (gamePos.x >= scrollX && gamePos.x <= scrollX + scrollWidth && gamePos.y >= scrollY && gamePos.y <= scrollY + scrollHeight){
            scrolling = true;
            relY = gamePos.y - scrollY;
        } else if (gamePos.x >= tickX && gamePos.x <= tickX + selectWidth && gamePos.y >= selectY && gamePos.y <= selectY + selectHeight){
            unlocked = true;
        } else if (gamePos.x >= padlockX && gamePos.x <= padlockX + selectWidth && gamePos.y >= selectY && gamePos.y <= selectY + selectHeight){
            unlocked = false;
        }
    }

    public void touchUp() {
        scrolling = false;
    }

    @SuppressWarnings("unused")
    public void show(){
        visible = true;
    }

    @SuppressWarnings("unused")
    public void hide(){
        visible = false;
    }
}
