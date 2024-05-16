package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HesHustle;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.Utils.ScreenType;
import com.mygdx.game.Utils.GameClock;

import java.util.Random;

public class CookieClickerScreen extends InputAdapter implements Screen {
    public HesHustle game;
    private int cookiesCollected = 0;
    private final OrthographicCamera camera;
    private final Viewport vp;
    private BitmapFont cookiesCollectedText;
    private final GameClock gameClock;
    private final GlyphLayout glyphLayout;
    Texture plate;
    float plateX;
    float plateHeight = 100;
    float plateWidth = 100;
    Texture cookie;
    float cookieX;
    float cookieY;
    int cookieHeight = 50;
    int cookieWidth = 50;
    Random random;
    Vector2 startingPosition;
    Vector2 endPosition;
    float speed = 500f;
    boolean aKeyPressed = false;
    boolean dKeyPressed = false;

    public CookieClickerScreen(HesHustle game){
        this.game = game;
        random = new Random();
        glyphLayout = new GlyphLayout();
        gameClock = new GameClock();
        cookiesCollectedText = new BitmapFont();
        cookie = new Texture("Activitys/duck game/duck.png");
        plate = new Texture("Activitys/basketball/basketball.png");
        camera = new OrthographicCamera();
        vp = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 2f, 0);
        camera.update();

        plateX = (vp.getWorldWidth() - plateWidth) / 2f;
        System.out.println(plateX);
        startingPosition = new Vector2(cookieX, vp.getWorldHeight() - 50);
        endPosition = new Vector2(cookieX, 0);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        float deltaTime = Gdx.graphics.getDeltaTime();
        update(deltaTime);

        boolean collision = isColliding();

        if (collision) {
            cookiesCollected = cookiesCollected + 1;
            speed = speed + 10;
            resetCookie();
        }


        startingPosition.y -= speed * deltaTime;

        if (startingPosition.y + cookieHeight < 0 || startingPosition.x + cookieWidth < 0 || startingPosition.x > vp.getWorldWidth()) {
            String gameOverText = "Game over. You collected " + cookiesCollected + " cookie(s)!";
            glyphLayout.setText(cookiesCollectedText, gameOverText);
            cookiesCollectedText.draw(game.batch, gameOverText, (vp.getWorldWidth() - glyphLayout.width) / 2, (vp.getWorldHeight() - glyphLayout.height) / 2);
            gameClock.addEvent(f -> endGame(), 1f);
            System.out.println("yes");
        } else {
            String text = "Collected " + cookiesCollected + " cookie(s)!";
            glyphLayout.setText(cookiesCollectedText, text);
            cookiesCollectedText.draw(game.batch, text, (vp.getWorldWidth() - glyphLayout.width) / 2, vp.getWorldHeight() - 10);
            game.batch.draw(cookie, startingPosition.x, startingPosition.y, cookieWidth, cookieHeight);
            cookieX = startingPosition.x;
            cookieY = startingPosition.y;
            game.batch.draw(plate, plateX, (vp.getWorldHeight() - plateHeight) / 2 - 200, plateWidth, plateHeight);
        }
        game.batch.end();

    }

    public boolean isColliding() {
        float cookieXMax = cookieX + cookieWidth;
        float cookieXMin = cookieX;
        float cookieYMax = cookieY + cookieHeight;
        float cookieYMin = cookieY;
        float plateXMax = plateX + plateWidth;
        float plateXMin = plateX;
        float plateYMax = (vp.getWorldHeight() - plateHeight) / 2 - 200 + cookieHeight;
        float plateYMin = (vp.getWorldHeight() - plateHeight) / 2 - 200;

        if ((cookieXMin <= plateXMax && cookieXMax >= plateXMin) &&
            (cookieYMin <= plateYMax && cookieYMax >= plateYMin)){
            return true;
        }
        return false;
    }

    public void resetCookie(){
        startingPosition.y = vp.getWorldHeight() + cookieHeight;
        startingPosition.x = MathUtils.random(vp.getWorldWidth() - cookieWidth);
    }

    public void endGame(){
        game.screenManager.setScreen(ScreenType.GAME_SCREEN);
    }


    @Override
    public void resize(int width, int height) {
        vp.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
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

    @Override
    public boolean keyDown(int keycode){
        if (keycode == Input.Keys.D){
            dKeyPressed = true;
            return true;
        } else if (keycode == Input.Keys.A){
            aKeyPressed = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode){
        if (keycode == Input.Keys.D){
            dKeyPressed = false;
            return true;
        } else if (keycode == Input.Keys.A){
            aKeyPressed = false;
            return true;
        }
        return false;
    }

    public void update(float deltaTime){
        if (plateX >= 0 && aKeyPressed){
            plateX = plateX - 15;
        }

        if (plateX < vp.getWorldWidth() - plateWidth && dKeyPressed){
            plateX = plateX + 15;
        }

    }
}
