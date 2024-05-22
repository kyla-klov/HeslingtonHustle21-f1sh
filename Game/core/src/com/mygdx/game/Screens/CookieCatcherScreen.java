package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HesHustle;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.Utils.ResourceManager;
import com.mygdx.game.Utils.ScreenType;
import com.mygdx.game.Utils.GameClock;

public class CookieCatcherScreen extends InputAdapter implements Screen {
    public HesHustle game;
    private int cookiesCollected = 0;
    private final OrthographicCamera camera;
    private final Viewport vp;
    private final BitmapFont cookiesCollectedText;
    private final GameClock gameClock;
    private final GlyphLayout glyphLayout;
    private final SpriteBatch batch;
    private final Texture plate;
    private final ResourceManager resourceManager;
    private float plateX;
    private final float plateHeight = 100;
    private final float plateWidth = 100;
    private final Texture cookie;
    private float cookieX;
    private float cookieY;
    private final int cookieHeight = 50;
    private final int cookieWidth = 50;
    private final Vector2 startingPosition;
    private float speed = 500f;
    private boolean aKeyPressed = false;
    private boolean dKeyPressed = false;

    public CookieCatcherScreen(HesHustle game){
        this.game = game;
        resourceManager = new ResourceManager();
        batch = game.getBatch();
        glyphLayout = new GlyphLayout();
        gameClock = new GameClock();
        cookiesCollectedText = resourceManager.addDisposable(new BitmapFont());
        cookie = resourceManager.addDisposable(new Texture(Gdx.files.internal("cookie.png")));
        plate = resourceManager.addDisposable(new Texture(Gdx.files.internal("plate.png")));
        camera = new OrthographicCamera();
        vp = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 2f, 0);
        camera.update();

        plateX = (vp.getWorldWidth() - plateWidth) / 2f;
        startingPosition = new Vector2(cookieX, vp.getWorldHeight() - 50);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        if (delta > 0.1f) return;
        gameClock.update(delta);
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float deltaTime = Gdx.graphics.getDeltaTime();
        update();

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
            cookiesCollectedText.draw(batch, gameOverText, (vp.getWorldWidth() - glyphLayout.width) / 2, (vp.getWorldHeight() - glyphLayout.height) / 2);
            gameClock.addEvent(f -> endGame(), 1f);
        } else {
            String text = "Collected " + cookiesCollected + " cookie(s)!";
            glyphLayout.setText(cookiesCollectedText, text);
            cookiesCollectedText.draw(batch, text, (vp.getWorldWidth() - glyphLayout.width) / 2, vp.getWorldHeight() - 10);
            batch.draw(cookie, startingPosition.x, startingPosition.y, cookieWidth, cookieHeight);
            cookieX = startingPosition.x;
            cookieY = startingPosition.y;
            batch.draw(plate, plateX, (vp.getWorldHeight() - plateHeight) / 2 - 200, plateWidth, plateHeight);
        }
        batch.end();

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

        return (cookieXMin <= plateXMax && cookieXMax >= plateXMin) &&
                (cookieYMin <= plateYMax && cookieYMax >= plateYMin);
    }

    public void resetCookie(){
        startingPosition.y = vp.getWorldHeight() + cookieHeight;
        startingPosition.x = MathUtils.random(vp.getWorldWidth() - cookieWidth);
    }

    public void endGame(){
        game.getScreenManager().setScreen(ScreenType.GAME_SCREEN);
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
        resourceManager.disposeAll();
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

    public void update(){
        if (plateX >= 0 && aKeyPressed){
            plateX = plateX - 25 * speed/500f;
        }

        if (plateX < vp.getWorldWidth() - plateWidth && dKeyPressed){
            plateX = plateX + 20 * speed/500f;
        }

    }
}
