package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.Ball;
import com.mygdx.game.Objects.BallHoop;
import com.mygdx.game.Utils.*;


public class BasketBallScreen implements Screen, InputProcessor {
    private final Ball ball;
    private final BallPhysics ballPhysics;
    private final BallHoop ballHoop;
    private final GameClock gameClock;
    private final HesHustle game;
    private final OrthographicCamera camera;
    private final Viewport vp;
    private final ResourceManager resourceManager;
    private final Texture hoopTexture;
    private final Texture backgroundTexture;
    private final Vector2 startingPos;
    BitmapFont font;
    private boolean goal;
    private int score;

    @SuppressWarnings("unused")
    public BasketBallScreen(HesHustle game) {
        this.game = game;
        resourceManager = new ResourceManager();
        hoopTexture = resourceManager.addDisposable(new Texture("Activitys/basketball/basketball_hoop.png"));
        backgroundTexture = resourceManager.addDisposable(new Texture("Activitys/basketball/basketball_background.png"));
        startingPos = new Vector2(129, 100);
        ball = new Ball(resourceManager.addDisposable(new Texture("Activitys/basketball/basketball.png")), startingPos.x, startingPos.y, 40);
        ballHoop = new BallHoop(535, 330, 100, 15, 20);
        ballPhysics = new BallPhysics(ball);
        gameClock = new GameClock();
        camera = new OrthographicCamera();
        vp = new FitViewport(800, 600, camera);
        goal = false;
        font = resourceManager.addDisposable(new BitmapFont());
        font.getData().setScale(2f);
        camera.position.set(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 2f, 0);
        camera.update();

        Collider floor = new Collider();
        floor.addSurface(new Vector3(-200,0,1000), true);
        ballPhysics.addCollider(floor);
        ballPhysics.addCollider(ballHoop.getCollider());

        ((GameScreen) game.screenManager.getScreen(ScreenType.GAME_SCREEN)).addRecreational();
    }

    public void update(float delta){
        camera.update();
        gameClock.update(delta);
        if (!goal && ballHoop.isGoal(ball, delta)){
            goal = true;
            gameClock.addEvent(f -> {
                ball.setPosition(startingPos);
                ball.setVelocity(new Vector2(0, 0));
                goal = false;
            }, 0.75f);
            score++;
        }
        ballPhysics.adjustBall(delta);
        ball.update(delta, vp.getWorldWidth());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        gameClock.addEvent(f -> game.screenManager.setScreen(ScreenType.GAME_SCREEN), 30f);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        vp.apply();

        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(vp.getScreenX(), vp.getScreenY(), vp.getScreenWidth(), vp.getScreenHeight());


        Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, 800, 600);

        ball.render(game.batch);

        game.batch.draw(hoopTexture, 0, 0, 800, 600);

        font.draw(game.batch, "Score: " + score, 350, 500);

        game.batch.end();
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
        resourceManager.disposeAll();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE){
            ball.setVelocity(new Vector2(300, 900));
        }
        return true;
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
}
