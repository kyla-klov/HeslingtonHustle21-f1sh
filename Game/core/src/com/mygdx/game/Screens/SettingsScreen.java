package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Utils.ResourceManager;
import com.mygdx.game.Utils.ScreenType;

public class SettingsScreen implements Screen {
    private final HesHustle game;
    private final Stage stage;
    private final ResourceManager resourceManager;
    //private GameMusic gameMusic;

    // Placeholder variables for volume and resolution settings
    //private static float musicVolume = 1f;
    private static float soundVolume = 1f;

    public SettingsScreen(HesHustle game) {
        this.game = game;
        this.resourceManager = new ResourceManager();
        this.stage = resourceManager.addDisposable(new Stage(new ScreenViewport()));
        //this.gameMusic = new GameMusic();
        initialiseSettings();
    }

    private void initialiseSettings() {
        Skin skin = resourceManager.addDisposable(new Skin(Gdx.files.internal("Craftacular_UI_Skin/craftacular-ui.json")));
        Skin skin2 = resourceManager.addDisposable(new Skin(Gdx.files.internal("metalui/metal-ui.json")));

        Label settingsLabel = new Label("Settings", skin, "default");
        settingsLabel.setFontScale(2.0f);

        // Music Volume Components
        Label musicVolumeLabel = new Label("Music Volume", skin);
        Slider musicVolumeSlider = new Slider(0.0f, 1.0f, 0.01f, false, skin);
        musicVolumeSlider.setValue(game.getGameMusic().getVolume());
        musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float newVolume = ((Slider) actor).getValue();
                game.getGameMusic().setVolume(newVolume);
            }
        });

        // Sound Volume Components
        Label soundVolumeLabel = new Label("Sound Volume", skin);
        Slider soundVolumeSlider = new Slider(0.0f, 1.0f, 0.01f, false, skin);
        soundVolumeSlider.setValue(soundVolume);
        soundVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundVolume = ((Slider) actor).getValue();
            }
        });

        // Resolution Selector Components using skin2
        Label resolutionLabel = new Label("Resolution", skin);
        SelectBox<String> resolutionSelectBox = new SelectBox<>(skin2);
        resolutionSelectBox.setItems("800x600", "1024x768", "1280x720", "1920x1080");
        resolutionSelectBox.setSelected("1280x720");

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
        table.add(settingsLabel).padBottom(20).row();
        table.add(musicVolumeLabel).padBottom(10).left().row();
        table.add(musicVolumeSlider).fillX().padBottom(20).row();
        table.add(soundVolumeLabel).padBottom(10).left().row();
        table.add(soundVolumeSlider).fillX().padBottom(20).row();
        table.add(resolutionLabel).padBottom(10).left().row();
        table.add(resolutionSelectBox).fillX().padBottom(20).row();
        table.add(backButton).fillX().pad(10);
        stage.addActor(table);
    }

    @Override
    public void show() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        resourceManager.disposeAll();
    }
}