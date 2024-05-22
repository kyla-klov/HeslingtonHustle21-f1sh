package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Utils.ResourceManager;
import com.mygdx.game.Utils.ScreenType;

public class ControlsScreen implements Screen {
    private final HesHustle game;
    private final Stage stage;
    private final ResourceManager resourceManager;

    public ControlsScreen(HesHustle game) {
        this.game = game;
        this.resourceManager = new ResourceManager();
        this.stage = resourceManager.addDisposable(new Stage(new FitViewport(1600, 900)));
        initialiseSettings();
    }

    private void initialiseSettings() {
        Gdx.input.setInputProcessor(stage);

        Skin skin = resourceManager.addDisposable(new Skin(Gdx.files.internal("Craftacular_UI_Skin/craftacular-ui.json")));

        Label controlsLabel = new Label("Controls", skin, "default");
        controlsLabel.setFontScale(2.0f);

        // Objective Label
        Label objectiveLabel = new Label("Welcome to Heslington Hustle! You are a second-year Computer Science student with exams in only 7 days. Explore the map, \n" +
                "and interact with buildings to eat, study, sleep and have fun. To get a good grade, you need to balance hours of studying with \n" +
                "self-care and recreation. Good luck!", skin);
        objectiveLabel.setWrap(true);
        objectiveLabel.setWidth(Gdx.graphics.getWidth() - 40); // Set width to screen width minus some padding

        // Key Buttons and Descriptions
        TextButton wButton = new TextButton("W", skin);
        Label wLabel = new Label("- Move Up", skin);
        TextButton aButton = new TextButton("A", skin);
        Label aLabel = new Label("- Move Left", skin);
        TextButton sButton = new TextButton("S", skin);
        Label sLabel = new Label("- Move Down", skin);
        TextButton dButton = new TextButton("D", skin);
        Label dLabel = new Label("- Move Right", skin);
        TextButton spaceBarButton = new TextButton("Space Bar", skin);
        Label spaceBarLabel = new Label("- Interact", skin);

        // Back Button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getScreenManager().setScreen(ScreenType.MENU_SCREEN);
            }
        });

        // Table setup
        Table table = new Table();
        table.setFillParent(true);
        table.add(controlsLabel).padBottom(10).colspan(2).center().row();
        table.add(objectiveLabel).padBottom(20).colspan(2).fillX().row(); // Using fillX to allow text to expand across the full width

        table.add(wButton).padRight(5);
        table.add(wLabel).padBottom(10).row();

        table.add(aButton).padRight(5);
        table.add(aLabel).padBottom(10).row();

        table.add(sButton).padRight(5);
        table.add(sLabel).padBottom(10).row();

        table.add(dButton).padRight(5);
        table.add(dLabel).padBottom(10).row();

        table.add(spaceBarButton).minWidth(140).padRight(5);
        table.add(spaceBarLabel).padBottom(10).row();

        table.add(backButton).fillX().pad(10).colspan(2).center();

        stage.addActor(table);
    }


        @Override
    public void show() {
            Gdx.input.setInputProcessor(stage);
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

    }
}
