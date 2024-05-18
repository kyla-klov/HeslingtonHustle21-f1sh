package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Utils.Achievement;
import com.mygdx.game.Utils.AchievementHandler;

import java.util.List;

public class AchievementsDisplay implements Disposable {
    final private Texture background;
    final private Texture tick;
    final private Texture padlock;
    final private Texture scrollBar;
    final private Texture highlightSelected;
    final private Viewport vp;
    final private AchievementHandler achievementHandler;

    private final float posX, posY, scrollX;
    private final float tickX, padlockX, selectY, selectWidth, selectHeight;
    private final float bgWidth, bgHeight, achievementWidth, achievementHeight, scrollWidth, scrollHeight;

    private boolean scrolling;
    private boolean unlocked;
    private boolean visible;
    private float relY;
    private float scrollY;


    public AchievementsDisplay(AchievementHandler achievementHandler, float posX, float posY, Viewport vp){
        this.achievementHandler = achievementHandler;
        this.posX = posX;
        this.posY = posY;
        this.vp = vp;
        unlocked = true;
        background = new Texture(Gdx.files.internal("AchievementsBackground.png"));
        tick = new Texture(Gdx.files.internal("check-mark.png"));
        padlock = new Texture(Gdx.files.internal("lock-padlock-symbol-for-security-interface.png"));
        scrollBar = new Texture(Gdx.files.internal("ScrollBar2.png"));
        highlightSelected = new Texture(Gdx.files.internal("HighlightSelected.png"));

        float scale = 1f;
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
        Vector2 pos = gameToWorld(posX, posY);
        float worldX = pos.x;
        float worldY = pos.y * 1.05f;

        Vector3 screenCoordMin = vp.project(new Vector3(worldX, worldY, 0));
        Vector3 screenCoordMax = vp.project(new Vector3(worldX + bgWidth, worldY + bgHeight * 0.77f, 0));

        int scissorX = (int) screenCoordMin.x;
        int scissorY = (int) screenCoordMin.y;
        int scissorWidth = (int) (screenCoordMax.x - screenCoordMin.x);
        int scissorHeight = (int) (screenCoordMax.y - screenCoordMin.y);


        batch.setProjectionMatrix(vp.getCamera().combined);
        batch.begin();
        drawUI(batch, background, posX, posY, bgWidth, bgHeight);
        drawUI(batch, scrollBar, scrollX, scrollY, scrollWidth, scrollHeight);
        if (unlocked) drawUI(batch, highlightSelected, tickX, selectY, selectWidth, selectHeight);
        else drawUI(batch, highlightSelected, padlockX, selectY, selectWidth, selectHeight);

        drawUI(batch, tick, tickX, selectY, 25, 25);
        drawUI(batch, padlock, padlockX, selectY, 25, 25);
        batch.end();

        if (scrolling){
            scrollY = screenToGame(0, Gdx.input.getY()).y - relY;
            if (scrollY < posY + bgHeight*0.17f){
                scrollY = posY + bgHeight*0.17f;
            } else if (scrollY > posY + bgHeight*0.67f){
                scrollY = posY + bgHeight*0.67f;
            }
        }

        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
        batch.begin();
        List<Achievement> achievements = achievementHandler.getAchievements();
        int c = 0;
        for (Achievement achievement : achievements){
            if (achievement.isUnlocked() == unlocked){
                c++;
                drawUI(batch, achievement.getAchievementTexture(), posX + (bgWidth - achievementWidth)/2f, posY + bgHeight * 0.95f + (bgHeight*0.67f + posY - scrollY)*3 - (bgHeight/10 + achievementHeight) * c, achievementWidth, achievementHeight);
            }
        }
        batch.end();
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
    }

    @Override
    public void dispose() {
        background.dispose();
    }

    public void touchDown(int screenX, int screenY) {
        Vector2 gamePos = screenToGame(screenX, screenY);
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

    public void hide(){
        visible = false;
    }

    private void drawUI(SpriteBatch batch, Texture txt, float x, float y, float width, float height){
        batch.draw(txt, x + vp.getCamera().position.x - vp.getWorldWidth()/2f, y + vp.getCamera().position.y - vp.getWorldHeight()/2f, width, height);
    }

    private Vector2 gameToWorld(float x, float y){
        return new Vector2(x + vp.getCamera().position.x - vp.getWorldWidth()/2f, y + vp.getCamera().position.y - vp.getWorldHeight()/2f);
    }

    private Vector2 screenToGame (float screenX, float screenY){
        float transX = (screenX - (Gdx.graphics.getWidth() - vp.getScreenWidth())/2f) * vp.getWorldWidth() / vp.getScreenWidth();
        float transY = (Gdx.graphics.getHeight() - screenY - (Gdx.graphics.getHeight() - vp.getScreenHeight())/2f) * vp.getWorldHeight() / vp.getScreenHeight();
        return new Vector2(transX, transY);
    }
}
