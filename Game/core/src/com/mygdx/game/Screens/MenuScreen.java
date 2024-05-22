package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.HesHustle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.Utils.ResourceManager;
import com.mygdx.game.Utils.ScreenType;


public class MenuScreen implements Screen {
    private final HesHustle game;
    private final Stage stage;
    private final ResourceManager resourceManager;
    private Image backgroundImage;

    public MenuScreen(HesHustle game) {
        this.game = game;
        this.resourceManager = new ResourceManager();
        this.stage = resourceManager.addDisposable(new Stage(new FitViewport(1600, 900)));
        initialiseMenu();
    }

    private void initialiseMenu(){
        Gdx.input.setInputProcessor(stage);

        // Load background image
        Texture bgTexture = resourceManager.addDisposable(new Texture(Gdx.files.internal("blurred_background.png")));
        backgroundImage = new Image(bgTexture);
        backgroundImage.setScaling(Scaling.fill);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

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

        TextButton controlsButton = new TextButton ("Controls", skin);
        controlsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.getScreenManager().setScreen(ScreenType.CONTROLS_SCREEN);
            }
        });

        TextButton leaderBoardButton = new TextButton("Leader Board", skin);
        leaderBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.getScreenManager().setScreen(ScreenType.LEADERBOARD_SCREEN);
            }
        });

        TextButton settingsButton = new TextButton ("Settings", skin);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.getScreenManager().setScreen(ScreenType.SETTINGS_SCREEN, 10f);
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
        table.add(playButton).pad(10).row(); // Add the play button and move to the next row
        table.add(controlsButton).pad(10).row(); // Add the controls button to see controls information
        table.add(leaderBoardButton).pad(10).row(); // Player can now see leaderboard whenever
        table.add(settingsButton).pad(10).row(); // Add the settings button so user can adjust the settings
        table.add(exitButton).pad(10); // Add the exit button

        stage.addActor(table);
    }

    @Override
    public void show() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        backgroundImage.setSize(width, height);
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