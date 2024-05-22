package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.LeaderBoard;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.Utils.ResourceManager;
import com.mygdx.game.Utils.ScreenType;
import com.mygdx.game.Utils.ViewportAdapter;


public class LeaderBoardScreen implements Screen {
    private final Viewport vp;
    private final LeaderBoard leaderBoard;
    private final HesHustle game;
    private final OrthographicCamera camera;
    private final ResourceManager resourceManager;
    private final Texture backButton;
    private final SpriteBatch batch;


    public LeaderBoardScreen(HesHustle game) {
        this.game = game;
        batch = game.getBatch();
        resourceManager = new ResourceManager();

        backButton = new Texture(Gdx.files.internal("turn-back.png"));

        camera = new OrthographicCamera();
        vp = new FitViewport(1600, 900, camera);
        vp.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        leaderBoard = new LeaderBoard(vp, 800 - 450/2f, 450 - 377/2f, 450, 377);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            touchDown(Gdx.input.getX(), Gdx.input.getY());
        }

        ScreenUtils.clear(0.396f, 0.263f, 0.129f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        vp.apply();
        batch.begin();
        leaderBoard.render(batch);
        batch.draw(backButton,30, 30, 64, 64);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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

    public void touchDown(float screenX, float screenY){
        Vector2 pos = ViewportAdapter.screenToGame(vp, screenX, screenY);
        if (ViewportAdapter.isOver(pos.x, pos.y, 30, 30, 64, 64)){
            game.getGameSound().buttonClickedSoundActivate();
            game.getScreenManager().setScreen(ScreenType.MENU_SCREEN);
        }
    }

    @Override
    public void dispose() {
        resourceManager.disposeAll();
    }
}
