package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.AchievementsDisplay;
import com.mygdx.game.Objects.LeaderBoard;
import com.mygdx.game.Utils.ScreenType;

/**
 * The EndScreen class represents the screen displayed at the end of the game.
 */
public class EndScreen implements Screen{
    private final HesHustle game;
    private final Stage stage;
    private final Skin skin;
    private final TextButton playAgainButton;
    private final TextButton mainMenuButton;
    private final TextButton exitButton;
    private final LeaderBoard leaderBoard;
    private final AchievementsDisplay achievementsDisplay;
    private final FitViewport vp;
    private final float score;

    /**
     * Constructs an EndScreen with the specified game instance and score.
     *
     * @param game  the game instance
     * @param score the player's score
     */
    public EndScreen(final HesHustle game, final float score) {
        this(game, score, new Stage(new FitViewport(1600, 900)),
                new Skin(Gdx.files.internal("Craftacular_UI_Skin/craftacular-ui.json")));

    }

    private EndScreen(final HesHustle game, final float score, final Stage stage, final Skin skin) {
        this(game, score, stage, skin,
                new TextButton("Play Again", skin),
                new TextButton("Main Menu", skin),
                new TextButton("Exit", skin));
    }
    public EndScreen(final HesHustle game, final float score,
                     final Stage stage,
                     final Skin skin,
                     final TextButton playAgainButton,
                     final TextButton mainMenuButton,
                     final TextButton exitButton) {
        this.game = game;
        this.score = score;
        this.stage = stage;
        this.skin = skin;
        Gdx.input.setInputProcessor(stage);
        this.playAgainButton = playAgainButton;
        this.mainMenuButton = mainMenuButton;
        this.exitButton = exitButton;
        vp = (FitViewport) stage.getViewport();
        this.leaderBoard = new LeaderBoard(vp, 60, 450 - 377/2f, 450, 377);
        this.achievementsDisplay = new AchievementsDisplay(vp, game.getAchievementHandler(), 1100, 450 - 377/2f);
        achievementsDisplay.show();
        setupUi();
    }

    /**
     * Sets up the UI elements for the end screen, including labels and buttons.
     */
    private void setupUi(){
        Table table = new Table ();
        table.setFillParent(true);
        stage.addActor(table);

        Label endLabel = new Label((score >= 40f) ? "Congratulations!":"Unlucky!", skin, "default");
        endLabel.setFontScale(2.0f);

        Label scoreLabel = new Label("Score: " + score, skin, "default");
        scoreLabel.setFontScale(1.0f);

        // Add functionality to buttons
        playAgainButton.addListener(event -> {
            if (!event.isHandled()) return false;
            game.getGameSound().buttonClickedSoundActivate();
            game.getScreenManager().removeScreenFromMemory(ScreenType.GAME_SCREEN);
            game.setNewGame();
            game.getScreenManager().setScreen(ScreenType.GAME_SCREEN); // Restart the game
            return true;
        });

        mainMenuButton.addListener(event -> {
            if (!event.isHandled()) return false;
            game.getGameSound().buttonClickedSoundActivate();
            game.setNewGame();
            game.getScreenManager().setScreen(ScreenType.MENU_SCREEN); // Go back to the main menu
            return true;
        });

        exitButton.addListener(event -> {
            if (!event.isHandled()) return false;
            Gdx.app.exit(); // Exit the game
            return true;
        });

        // Layout the buttons in the table
        table.add(endLabel).pad(10).row();
        table.add(scoreLabel).pad(10).row();
        table.add(playAgainButton).pad(10).row();
        table.add(mainMenuButton).pad(10).row();
        table.add(exitButton).pad(10);

    }




    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Renders screen elements
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.396f, 0.263f, 0.129f, 1);
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();

        game.getBatch().setProjectionMatrix(vp.getCamera().combined);
        game.getBatch().begin();
        leaderBoard.render(game.getBatch());
        achievementsDisplay.render(game.getBatch());
        game.getBatch().end();
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
