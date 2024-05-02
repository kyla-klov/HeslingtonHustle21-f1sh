package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.HesHustle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.Utils.ScreenType;


public class PauseScreen implements Screen {
    final HesHustle game;
    private Stage stage;
    private Skin skin;
    private GameScreen gameScreen;

    public PauseScreen(final HesHustle game) {
        this.game = game;
        this.gameScreen = gameScreen;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("Craftacular_UI_Skin/craftacular-ui.json")); // Ensure you have this file in your assets

        setupUi();
    }
    private void setupUi() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton mainMenuButton = new TextButton("Main Menu", skin);

        resumeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                // Ensure this transitions back to your game screen, adjusting as necessary
                game.screenManager.setScreen(ScreenType.GAME_SCREEN);
            }
        });
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.screenManager.setScreen(ScreenType.MENU_SCREEN);
            }
        });
        table.add(resumeButton).pad(10).row();
        table.add(mainMenuButton).pad(10);
    }
    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.396f, 0.263f, 0.129f, 1);
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }@Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }



}




