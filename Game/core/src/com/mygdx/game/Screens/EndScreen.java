package com.mygdx.game.Screens;

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
    final HesHustle game;
    private Stage stage;
    private Skin skin;

    TextButton playAgainButton = new TextButton("Play Again", skin);
    TextButton mainMenuButton = new TextButton("Main Menu", skin);
    TextButton exitButton = new TextButton("Exit", skin);


    public EndScreen(final HesHustle game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("Craftacular_UI_Skin/craftacular-ui.json"));
        setupUi();
    }

    public TextButton getPlayAgainButton() {
        return playAgainButton;
    }

    public TextButton getMainMenuButton() {
        return mainMenuButton;
    }

    public TextButton getExitButton() {
        return exitButton;
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
