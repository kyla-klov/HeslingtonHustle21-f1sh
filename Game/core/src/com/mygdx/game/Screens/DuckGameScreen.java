package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.Random;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HesHustle;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.Utils.Achievement;
import com.mygdx.game.Utils.GameClock;
import com.mygdx.game.Utils.ScreenType;

public class DuckGameScreen implements Screen, InputProcessor{
    private float duckX;
    private float duckY;
    private long timeToComplete;
    private int numberDucksClicked = 0;
    boolean isDuckOnScreen = false;
    private boolean isEndGame = false;

    private final BitmapFont displayText;
    private final Random random;
    private final GameClock gameClock;
    private final HesHustle game;
    private final Texture duck;
    private final int duckHeight = 100;
    private final int duckWidth = 100;
    private final OrthographicCamera camera;
    private final Viewport vp;
    private final GlyphLayout glyphLayout;
    private final SpriteBatch batch;

    public DuckGameScreen(HesHustle game){
        this.game = game;
        batch = game.getBatch();
        gameClock = new GameClock();
        displayText = new BitmapFont();
        displayText.getData().setScale(2.0f);
        glyphLayout = new GlyphLayout();
        random = new Random();
        camera = new OrthographicCamera();
        vp = new FitViewport(Gdx.graphics.getWidth()-250, Gdx.graphics.getHeight()-250, camera);
        camera.position.set(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 2f, 0);
        camera.update();
        // Update this
        duck = new Texture("Activitys/duck game/duck.png");
        spawnDuck();
    }

    public void spawnDuck(){
        int numberRounds = 15;
        if (numberDucksClicked < numberRounds){
            isDuckOnScreen = true;
            this.duckX = random.nextFloat() * (vp.getWorldWidth() - duckWidth);
            this.duckY = random.nextFloat() * (vp.getWorldHeight() - duckHeight);
        } else {
            timeToComplete = (int) gameClock.getRawTime();

            if (timeToComplete <= 18){
                game.getAchievementHandler().getAchievement("Duck Duck Go", Achievement.Type.BRONZE).unlock();
            }
            if (timeToComplete <= 17){
                game.getAchievementHandler().getAchievement("Duck Duck Go", Achievement.Type.SILVER).unlock();
            }
            if (timeToComplete <= 16){
                game.getAchievementHandler().getAchievement("Duck Duck Go", Achievement.Type.GOLD).unlock();
            }

            isEndGame = true;
        }
    }

    public void endGame(){
        game.getScreenManager().setScreen(ScreenType.GAME_SCREEN);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport dimensions when the screen is resized
        vp.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        gameClock.update(delta);
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        vp.apply();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if (!isEndGame){
            String text = "Click on the 15 ducks as quickly as you can!";
            glyphLayout.setText(displayText, text);
            displayText.draw(batch, text, (vp.getWorldWidth() - glyphLayout.width) / 2, vp.getWorldHeight() - 10);
        }

        if (isDuckOnScreen){
            batch.draw(duck, duckX, duckY, duckWidth, duckHeight);
        } else if (isEndGame) {
            String finalText = "Completed in " + timeToComplete + " seconds!";
            glyphLayout.setText(displayText, finalText);
            displayText.draw(batch, finalText, (vp.getWorldWidth() - glyphLayout.width) / 2, vp.getWorldHeight() / 2);
            gameClock.addEvent(f -> endGame(), 2);
        }


        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int worldX, int worldY, int pointer, int button) {
        Vector2 touchPoint = vp.unproject(new Vector2(worldX, worldY));

        if (touchPoint.x >= duckX && touchPoint.x <= duckX + duckWidth &&
                touchPoint.y >= duckY && touchPoint.y <= duckY + duckHeight) {
            // Duck has been successfully clicked
            isDuckOnScreen = false;
            gameClock.addEvent(f -> spawnDuck(), 0.4f);
            numberDucksClicked = numberDucksClicked + 1;
        }

        return false;


    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
