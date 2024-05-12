package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.List;
import java.util.Random;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HesHustle;
import java.time.LocalDateTime;
import java.time.Duration;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Utils.Achievement;
import com.mygdx.game.Utils.ScreenType;

import java.util.concurrent.ThreadLocalRandom;

public class DuckGameScreen implements Screen, InputProcessor{
    private boolean isEndGame = false;
    private BitmapFont displayText;
    private BitmapFont title;
    private float displayTextY, displayTextHeight;
    private LocalDateTime startTime;
    private long timeToComplete;
    private Random random;
    private
    final HesHustle game;
    int numberDucksClicked = 0;
    int numberRounds = 15;
    boolean isDuckOnScreen = false;
    private Texture duck;
    private float duckX;
    private float duckY;
    private int duckHeight = 100;
    private int duckWidth = 100;
    private final OrthographicCamera camera;
    private final Viewport vp;
    private GlyphLayout glyphLayout;
    private Achievement duckAchievement;

    public DuckGameScreen(HesHustle game){
        this.game = game;
        String achievementDescription = "How fast can you click the ducks?";
        duckAchievement = game.achievementHandler.createAchievement("Duck duck go", achievementDescription);

        displayText = new BitmapFont();
        title = new BitmapFont();
        startTime = LocalDateTime.now();
        glyphLayout = new GlyphLayout();
        random = new Random();
        camera = new OrthographicCamera();
        vp = new FitViewport(Gdx.graphics.getWidth()-250, Gdx.graphics.getHeight()-250, camera);
        camera.position.set(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 2f, 0);
        camera.update();
        // Update this
        duck = new Texture("Activitys/duck game/duck.png");
        ((GameScreen) game.screenManager.getScreen(ScreenType.GAME_SCREEN)).addRecreational();
        spawnDuck();
    }

    public void spawnDuck(){
        if (numberDucksClicked < numberRounds){
            isDuckOnScreen = true;
            this.duckX = random.nextFloat() * vp.getWorldWidth();
            this.duckY = random.nextFloat() * vp.getWorldHeight();
        } else {
            Duration duration = Duration.between(startTime, LocalDateTime.now());
            long totalSeconds = duration.toSeconds();
            timeToComplete = totalSeconds % 60;

            if (timeToComplete == 18){
                duckAchievement.setBronzeAchievement();
            } else if (timeToComplete == 17){
                duckAchievement.setSilverAchievement();
            } else if (timeToComplete <= 16){
                duckAchievement.setGoldAchievement();
            }

            isEndGame = true;
            delay(2, this::endGame);
        }
    }

    public void endGame(){
        game.screenManager.setScreen(ScreenType.GAME_SCREEN);

    }

    public void delay(float delaySeconds, Runnable runnable) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                runnable.run();
            }
        }, delaySeconds);
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
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        if (!isEndGame){
            String text = "Click the ducks in as little time as possble!";
            glyphLayout.setText(displayText, text);
            displayText.draw(game.batch, text, (vp.getWorldWidth() - glyphLayout.width) / 2, vp.getWorldHeight() - 10);
        }

        if (isDuckOnScreen){
            game.batch.draw(duck, duckX, duckY, duckWidth, duckHeight);
        } else if (isEndGame) {
            String finalText = "Completed in " + timeToComplete + " seconds!";

            if (duckAchievement.isGoldAchieved()){
                finalText = finalText + " Current Achievement: gold";
            } else if (duckAchievement.isSilverAchieved()){
                finalText = finalText + " Current Achievement: silver";
            } else if (duckAchievement.isBronzeAchieved()){
                finalText = finalText + " Current Achievement: bronze";
            }

            glyphLayout.setText(displayText, finalText);
            displayText.draw(game.batch, finalText, (vp.getWorldWidth() - glyphLayout.width) / 2, vp.getWorldHeight() / 2);
        }


        game.batch.end();
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
            delay(0.4f, this::spawnDuck);
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
