package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.HesHustle;

import java.util.concurrent.ThreadLocalRandom;

public class DuckGameScreen implements Screen {

    final HesHustle game;
    int numberDucksClicked = 0;
    int numberRounds = 5;
    boolean isDuckOnScreen = false;
    private Texture duck;
    private int duckX;
    private int duckY;
    private int duckHeight;
    private int duckWidth;

    public DuckGameScreen(HesHustle game){
        this.game = game;
        // Update this
        duck = new Texture("");
        spawnDuck();
    }

    public void spawnDuck(){
        isDuckOnScreen = true;
        int windowWidth = Gdx.graphics.getWidth();
        int windowHeight = Gdx.graphics.getHeight();
        this.duckX = ThreadLocalRandom.current().nextInt(50, windowWidth-50);
        this.duckY = ThreadLocalRandom.current().nextInt(50, windowHeight-50);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.55f, 0.7f, 1);
        game.batch.begin();

        if (isDuckOnScreen){
            game.batch.draw(duck, duckX, duckY, duckWidth, duckHeight);
        }


        game.batch.end();
    }

    public void touchDown(int worldX, int worldY, int pointer, int button) {
        worldY = Gdx.graphics.getHeight() - worldY;
        

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

    }
}
