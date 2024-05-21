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

/**
 * The BasketBallScreen class implements a mini-game where the player attempts to score baskets
 * by bouncing a ball into a hoop within a limited time frame.
 */
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
    private final BitmapFont font;
    private boolean goal;
    private boolean gameOver;
    private int score;

    /**
     * Constructor for the BasketBallScreen.
     * Initializes the game screen, ball, hoop, physics, and other necessary components.
     * @param game The game instance.
     */
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
    }

    /**
     * Updates the game state.
     * @param delta Time elapsed since the last frame.
     */
    public void update(float delta) {
        // Limit delta time to avoid large jumps
        if (delta >= 0.1) return;

        camera.update();
        gameClock.update(delta);
        if (gameOver) return;

        // Check if a goal is scored
        if (!goal && ballHoop.isGoal(ball, delta)) {
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
        //Schedule end of game event and update achievements
        gameClock.addEvent(f -> {
            if (score >= 8) {
                game.getAchievementHandler().getAchievement("Baller", Achievement.Type.BRONZE).unlock();
            }
            if (score >= 10) {
                game.getAchievementHandler().getAchievement("Baller", Achievement.Type.SILVER).unlock();
            }
            if (score >= 12) {
                game.getAchievementHandler().getAchievement("Baller", Achievement.Type.GOLD).unlock();
            }
            gameOver = true;
            gameClock.addEvent(g -> game.getScreenManager().setScreen(ScreenType.GAME_SCREEN), 4f);
        }, 30f);
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear Screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        vp.apply();

        //Set screen scale bounds using scissor test//
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(vp.getScreenX(), vp.getScreenY(), vp.getScreenWidth(), vp.getScreenHeight());

        Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        /////////////////////////////////////////////

        //Update camera and set projection matrix
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        //Render Objects
        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, 800, 600);

        ball.render(game.getBatch());

        game.getBatch().draw(hoopTexture, 0, 0, 800, 600);

        //Check game over condition and display message accordingly
        if (gameOver) {
            font.draw(game.getBatch(), "Times Up!", 100, 580);
        } else {
            font.draw(game.getBatch(), "Press SPACE to bounce the ball into the hoop.", 100, 580);
            font.draw(game.getBatch(), "You have 30 seconds!", 270, 540);
        }

        //Display score
        font.draw(game.getBatch(), "Score: " + score, 350, 500);

        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
    }

    @Override
    public void pause() {
        // This method is called when the game is paused.
    }

    @Override
    public void resume() {
        // This method is called when the game is resumed.
    }

    @Override
    public void hide() {
        // This method is called when the screen is hidden.
    }

    @Override
    public void dispose() {
        resourceManager.disposeAll();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
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