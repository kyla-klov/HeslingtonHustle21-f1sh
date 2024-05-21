package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HesHustle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.Utils.ResourceManager;
import com.mygdx.game.Utils.ScreenType;


public class MenuScreen implements Screen {
    private final HesHustle game;
    private final Stage stage;
    private final ResourceManager resourceManager;
    private final OrthographicCamera camera;


    public MenuScreen(HesHustle game, ResourceManager resourceManager, Stage stage){
        this.game = game;
        this.resourceManager = resourceManager;
        this.stage = stage;

        camera = new OrthographicCamera();
        Viewport vp = new FitViewport(1600, 900, camera);
        vp.apply();

        stage.setViewport(vp);
        initialiseMenu();
    }
    public MenuScreen(HesHustle game) {
        this(game, new ResourceManager(), new Stage());
    }

    private void initialiseMenu(){
        Gdx.input.setInputProcessor(stage);

        Skin skin = resourceManager.addDisposable(new Skin(Gdx.files.internal("Craftacular_UI_Skin/craftacular-ui.json")));

        Label titleLabel = new Label("Heslington Hustle", skin, "default");
        titleLabel.setFontScale(2.0f);

        TextButton playButton = new TextButton("Play", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.getScreenManager().setScreen(ScreenType.GAME_SCREEN);
            }
        });

        TextButton leaderBoardButton = new TextButton("Leader Board", skin);
        leaderBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.getScreenManager().setScreen(ScreenType.LEADERBOARD_SCREEN);
            }
        });


        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(titleLabel).padBottom(50).row(); // Add the title label and move to the next row
        table.add(playButton).pad(10).row();
        table.add(leaderBoardButton).pad(10).row();// Add the play button and move to the next row
        table.add(exitButton).pad(10); // Add the exit button

        stage.addActor(table);
    }

    @Override
    public void show() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.396f, 0.263f, 0.129f, 1);
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        resourceManager.disposeAll();
    }
}
