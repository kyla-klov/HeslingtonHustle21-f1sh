package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Utils.Achievement;
import com.mygdx.game.Utils.AchievementHandler;
import com.mygdx.game.Utils.ResourceManager;
import com.mygdx.game.Utils.ViewportAdapter;

import java.util.*;

/**
 * The AchievementsDisplay class is responsible for displaying achievements in the game.
 * It handles the rendering of achievements, scrolling, and user interactions with the achievements display.
 */
public class AchievementsDisplay implements Disposable {
    final private Texture background;
    final private Texture tick;
    final private Texture padlock;
    final private Texture scrollBar;
    final private Texture highlight;
    final private BitmapFont font;
    final private AchievementHandler achievementHandler;
    final private ResourceManager resourceManager;
    final private Viewport vp;
    final private HashMap<Achievement, Rectangle> achievementDims;

    private final float posX, posY, scrollX;
    private final float tickX, padlockX, selectY, selectWidth, selectHeight;
    private final float bgWidth, bgHeight, achievementWidth, achievementHeight, scrollWidth, scrollHeight;

    private final float scale = 1f;

    private boolean scrolling;
    private boolean unlocked;
    private boolean visible;
    private boolean buttonPressed;
    private float relY;
    private float scrollY;

    /**
     * Constructs an AchievementsDisplay with the specified parameters.
     *
     * @param vp                the viewport to use for rendering
     * @param font              the font to use for rendering text
     * @param achievementHandler the achievement handler to use for retrieving achievements
     * @param posX              the x position of the achievements display
     * @param posY              the y position of the achievements display
     */
    public AchievementsDisplay(Viewport vp, BitmapFont font, AchievementHandler achievementHandler, float posX, float posY){
        this.vp = vp;
        this.achievementHandler = achievementHandler;
        this.posX = posX;
        this.posY = posY;

        resourceManager = new ResourceManager();
        achievementDims = new HashMap<>();

        for (Achievement achievement : achievementHandler.getAchievements()) {
            achievementDims.put(achievement, new Rectangle());
        }

        visible = false;
        unlocked = true;
        buttonPressed = false;
        this.font = resourceManager.addDisposable(font);
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

    /**
     * Constructs an AchievementsDisplay with the specified parameters, using a default font.
     *
     * @param vp                the viewport to use for rendering
     * @param achievementHandler the achievement handler to use for retrieving achievements
     * @param posX              the x position of the achievements display
     * @param posY              the y position of the achievements display
     */
    public AchievementsDisplay(Viewport vp, AchievementHandler achievementHandler, float posX, float posY){
        this(vp, new BitmapFont(Gdx.files.internal("font.fnt")), achievementHandler, posX, posY);
    }

    /**
     * Renders the achievements display.
     *
     * @param batch the sprite batch to use for rendering
     */
    public void render(SpriteBatch batch){
        if (!visible) return;
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            buttonPressed = true;
            touchDown(Gdx.input.getX(), Gdx.input.getY());
        }
        if (buttonPressed && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            buttonPressed = false;
            touchUp();
        }

        updateScroller();

        ViewportAdapter.drawUI(vp, batch, background, posX, posY, bgWidth, bgHeight);
        ViewportAdapter.drawUI(vp, batch, scrollBar, scrollX, scrollY, scrollWidth, scrollHeight);
        if (unlocked) ViewportAdapter.drawUI(vp, batch, highlight, tickX, selectY, selectWidth, selectHeight);
        else ViewportAdapter.drawUI(vp, batch, highlight, padlockX, selectY, selectWidth, selectHeight);
        ViewportAdapter.drawUI(vp, batch, tick, tickX, selectY, 25, 25);
        ViewportAdapter.drawUI(vp, batch, padlock, padlockX, selectY, 25, 25);
        drawAchievements(batch);
        writeDescriptions(batch, Gdx.input.getX(), Gdx.input.getY());
    }

    /**
     * Updates the scroller position based on user input.
     */
    private void updateScroller(){
        //Handle Scroll Logic
        if (scrolling){
            scrollY = ViewportAdapter.screenToGame(vp, 0, Gdx.input.getY()).y - relY;
            if (scrollY < posY + bgHeight*0.17f){
                scrollY = posY + bgHeight*0.17f;
            } else if (scrollY > posY + bgHeight*0.67f){
                scrollY = posY + bgHeight*0.67f;
            }
        }
    }

    /**
     * Draws the achievements within the display.
     *
     * @param batch the sprite batch to use for rendering
     */
    private void drawAchievements(SpriteBatch batch){
        //Calculate the screen coords of scissor box
        Vector2 blCorner = ViewportAdapter.toScreen(vp, posX, posY + 25 * scale);
        Vector2 trCorner = ViewportAdapter.toScreen(vp, posX+bgWidth, posY+bgHeight - 55 * scale);

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
                float x = posX + (bgWidth - achievementWidth)/2f;
                float y = posY + bgHeight * 0.95f + (bgHeight*0.67f + posY - scrollY)*6 - (bgHeight/10 + achievementHeight) * c;
                achievementDims.put(achievement, new Rectangle(x, y, achievementWidth, achievementHeight));
                ViewportAdapter.drawUI(vp, batch, achievement.getAchievementTexture(), x, y, achievementWidth, achievementHeight);
            }
        }
        batch.draw(highlight, 0, 0, 0, 0);
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
    }

    /**
     * Handles touch down events.
     *
     * @param screenX the x position of the touch
     * @param screenY the y position of the touch
     */
    public void touchDown(int screenX, int screenY) {
        Vector2 gamePos = ViewportAdapter.screenToGame(vp,  screenX, screenY);

        if (ViewportAdapter.isOver(gamePos.x, gamePos.y, scrollX, scrollY, scrollWidth, scrollHeight)){
            scrolling = true;
            relY = gamePos.y - scrollY;
        } else if (ViewportAdapter.isOver(gamePos.x, gamePos.y, tickX, selectY, selectWidth, selectHeight)){
            unlocked = true;
            scrollY = bgHeight*0.67f + posY;
        } else if (ViewportAdapter.isOver(gamePos.x, gamePos.y, padlockX, selectY, selectWidth, selectHeight)){
            unlocked = false;
            scrollY = bgHeight*0.67f + posY;
        }
    }

    /**
     * Writes descriptions for achievements.
     *
     * @param batch    the sprite batch to use for rendering
     * @param screenX  the x position of the touch
     * @param screenY  the y position of the touch
     */
    public void writeDescriptions(SpriteBatch batch, int screenX, int screenY){
        Vector2 gamePos = ViewportAdapter.screenToGame(vp,  screenX, screenY);
        for (Achievement achievement : achievementHandler.getAchievements()) {
            Rectangle dims = achievementDims.get(achievement);
            if (achievement.isUnlocked() == unlocked && ViewportAdapter.isOver(gamePos.x, gamePos.y, dims)){
                ViewportAdapter.drawFont(vp, font, batch, achievement.getName(), dims.x - 250, dims.y + 50);
                ViewportAdapter.drawFont(vp, font, batch, achievement.getDescription(), dims.x - 250, dims.y);
            }
        }
    }

    /**
     * Handles touch up events.
     */
    public void touchUp() {
        scrolling = false;
    }

    /**
     * Shows the achievements display.
     */
    public void show(){
        visible = true;
    }

    /**
     * Hides the achievements display.
     */
    public void hide(){
        scrollY = bgHeight*0.67f + posY;
        visible = false;
    }

    /**
     * Checks if the achievements display is visible.
     *
     * @return true if the display is visible, false otherwise
     */
    public boolean isVisible(){
        return visible;
    }

    /**
     * Disposes of all resources used by this display.
     */
    @Override
    public void dispose() {
        resourceManager.disposeAll();
    }

}


