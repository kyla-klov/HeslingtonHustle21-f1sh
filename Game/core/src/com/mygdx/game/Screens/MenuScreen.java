package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.HesHustle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;





public class MenuScreen implements Screen {
    final HesHustle game;
    OrthographicCamera camera;
    private Skin skin;
    private Stage stage;
    private Label titleLabel;


    public MenuScreen(final HesHustle game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("Craftacular_UI_Skin/craftacular-ui.json"));

        titleLabel = new Label("Heslington Hustle", skin, "default");
        titleLabel.setFontScale(2.0f);

        TextButton playButton = new TextButton("Play", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        Table table = new Table();
        table.setFillParent(true);
        table.add(titleLabel).padBottom(50).row(); // Add the title label and move to the next row
        table.add(playButton).pad(10).row(); // Add the play button and move to the next row
        table.add(exitButton).pad(10); // Add the exit button

        stage.addActor(table);

        playButton.addListener(event -> {
            if (!event.isHandled())
                return false;
            game.setScreen(new GameScreen(game));
            return true;
        });

        exitButton.addListener(event -> {
            if (!event.isHandled()) return false;
            Gdx.app.exit();
            return true;
        });



    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.396f, 0.263f, 0.129f, 1);
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
