package com.mygdx.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.HesHustle;


public class EndScreen implements Screen{
    private final HesHustle game;
    private final Stage stage;
    private final Skin skin;
    private final TextButton playAgainButton;
    private final TextButton mainMenuButton;
    private final TextButton exitButton;


    public EndScreen(final HesHustle game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Craftacular_UI_Skin/craftacular-ui.json"));
        playAgainButton = new TextButton("Play Again", skin);
        mainMenuButton = new TextButton("Main Menu", skin);
        exitButton = new TextButton("Exit", skin);
        setupUi();
    }

    // Constructor for testing
    public EndScreen(final HesHustle game,
                     final Stage stage,
                     final TextButton playAgainButton,
                     final TextButton mainMenuButton,
                     final TextButton exitButton) {
        this.game = game;
        this.stage = stage;
        this.skin = null;
        Gdx.input.setInputProcessor(stage);
        this.playAgainButton = playAgainButton;
        this.mainMenuButton = mainMenuButton;
        this.exitButton = exitButton;
        setupUi();
    }

    private void setupUi(){

        Table table = new Table ();
        table.setFillParent(true);
        stage.addActor(table);


        // Add functionality to buttons
        playAgainButton.addListener(event -> {
            if (!event.isHandled()) return false;
            game.setScreen(new GameScreen(game)); // Restart the game
            return true;
        });

        mainMenuButton.addListener(event -> {
            if (!event.isHandled()) return false;
            game.setScreen(new MenuScreen(game)); // Go back to the main menu
            return true;
        });

        exitButton.addListener(event -> {
            if (!event.isHandled()) return false;
            Gdx.app.exit(); // Exit the game
            return true;
        });

        // Layout the buttons in the table
        table.add(playAgainButton).pad(10).row();
        table.add(mainMenuButton).pad(10).row();
        table.add(exitButton).pad(10);

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
