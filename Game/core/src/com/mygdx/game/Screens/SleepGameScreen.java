package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Utils.GameClock;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HesHustle;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.Utils.ScreenType;


import java.util.Random;

public class SleepGameScreen extends InputAdapter implements Screen {
    HesHustle game;
    private final GameClock gameClock;
    private final GlyphLayout glyphLayout;
    private final OrthographicCamera camera;
    private final Viewport vp;
    private final SpriteBatch batch;
    private final BitmapFont mainText;
    private final int zRange = 5;
    boolean roundInPlay = true;
    private Random random;
    private String zText = "";
    private int numberRounds = 2;
    private int roundNumber = 0;
    private int numberZ = 0;
    private float waitTime = 0.2f;
    private final int numberGames = 2;
    private int gameNumber = 0;
    private int gameWins = 0;
    private boolean endGame = false;

    public SleepGameScreen(HesHustle game){
        this.game = game;
        gameClock = new GameClock();
        random = new Random();
        batch = game.getBatch();
        glyphLayout = new GlyphLayout();
        mainText = new BitmapFont();
        camera = new OrthographicCamera();
        vp = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 2f, 0);
        camera.update();
        gameNumber += 1;
        playRound();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    public String generateRandomZ(){
        int randomNumber = random.nextInt(zRange) + 1;
        numberZ = numberZ + randomNumber;
        String text = "";

        for (int i = 0; i<randomNumber; i++){
            text = text + "z";
        }
        return text;
    }

    public void playRound(){
        roundNumber += 1;

        if (roundNumber <= numberRounds){
            zText = generateRandomZ();
            gameClock.addEvent(f -> clearText(), 4f);
        } else {
            roundInPlay = false;
            zText = "Guess: ";
        }
    }

    public void nextRound(){
        gameClock.addEvent(f -> playRound(), waitTime);
    }


    public void endGame(){
        game.getScreenManager().setScreen(ScreenType.GAME_SCREEN);
    }

    public void displayEndText(){
        zText = "You won " + gameWins;
        gameClock.addEvent(f -> endGame(), 2f);
    }

    public void clearText(){
        zText = "";
        nextRound();
    }


    @Override
    public void render(float delta) {
        gameClock.update(delta);
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        String gameOverText = zText;
        glyphLayout.setText(mainText, gameOverText);
        float x = (vp.getWorldWidth() - glyphLayout.width) / 2;
        float y = (vp.getWorldHeight() - glyphLayout.height) / 2;
        mainText.draw(batch, gameOverText, x, y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
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
        // Dispose logic
        batch.dispose();
        mainText.dispose();
    }

    @Override
    public boolean keyDown(int keycode){
        if (keycode == Input.Keys.NUM_1 && !roundInPlay) {
            zText = zText + "1";
            return true;
        } else if (keycode == Input.Keys.NUM_2 && !roundInPlay) {
            zText = zText + "2";
            return true;
        } else if (keycode == Input.Keys.NUM_3 && !roundInPlay) {
            zText = zText + "3";
            return true;
        } else if (keycode == Input.Keys.NUM_4 && !roundInPlay) {
            zText = zText + "4";
            return true;
        } else if (keycode == Input.Keys.NUM_5 && !roundInPlay) {
            zText = zText + "5";
            return true;
        } else if (keycode == Input.Keys.NUM_6 && !roundInPlay) {
            zText = zText + "6";
            return true;
        } else if (keycode == Input.Keys.NUM_7 && !roundInPlay) {
            zText = zText + "7";
            return true;
        } else if (keycode == Input.Keys.NUM_8 && !roundInPlay) {
            zText = zText + "8";
            return true;
        } else if (keycode == Input.Keys.NUM_9 && !roundInPlay) {
            zText = zText + "9";
            return true;
        } else if (keycode == Input.Keys.NUM_0 && !roundInPlay) {
            zText = zText + "0";
            return true;
        } else if (keycode == Input.Keys.ENTER && !roundInPlay) {
            String number = "";
            for (char x : zText.toCharArray()){
                if (Character.isDigit(x)){
                    number = number + x;
                }
            }
            int guess = Integer.parseInt(number);
            if (guess == numberZ){
                numberZ = 0;
                gameWins = gameWins + 1;
            }

            if (gameNumber < numberGames){
                gameNumber = gameNumber + 1;
                roundNumber = 0;
                roundInPlay = true;
                gameClock.addEvent(f -> playRound(), 0.5f);
            } else {
                displayEndText();
            }

            return true;
        } else if (keycode == Input.Keys.BACKSPACE && !roundInPlay){
            char lastChar = zText.charAt(zText.length() - 1);
            if (!(lastChar == ' ')){
                zText = zText.substring(0, zText.length() - 1);
            }
        }
        return false;
    }
}
