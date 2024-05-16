package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HesHustle;

import java.util.Random;

public class CookieClickerScreen extends InputAdapter implements Screen {
    public HesHustle game;
    private boolean showCookie = true;
    private final OrthographicCamera camera;
    private final Viewport vp;
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

        isColliding();

        startingPosition.y -= speed * deltaTime;

        if (startingPosition.y + cookieHeight < 0 || startingPosition.x + cookieWidth < 0 || startingPosition.x > vp.getWorldWidth()) {
            startingPosition.y = vp.getWorldHeight() + cookieHeight;
            startingPosition.x = MathUtils.random(vp.getWorldWidth() - cookieWidth);
        }

        game.batch.draw(cookie, startingPosition.x, startingPosition.y, cookieWidth, cookieHeight);
        game.batch.draw(plate, plateX, (vp.getWorldHeight() - plateHeight) / 2 - 200, plateWidth, plateHeight);
        game.batch.end();
    }

    public boolean isColliding() {
        // Calculate cookie boundaries
        float cookieRight = cookieX + cookieWidth;
        float cookieTop = cookieY + cookieHeight;

        // Calculate plate boundaries
        float plateBottom = (vp.getWorldHeight() - plateHeight) / 2 + 200 + plateHeight; // Plate positioned 200 pixels under the center of the viewport
        float plateRight = plateX + plateWidth;
        float plateTop = plateBottom - plateHeight;

        // Check for collision
        boolean collisionX = cookieX < plateRight && cookieRight > plateX;
        boolean collisionY = cookieY < plateBottom && cookieTop > plateTop;

        if (collisionX && collisionY){
            System.out.println("yes");
        }

        return collisionX && collisionY;
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
