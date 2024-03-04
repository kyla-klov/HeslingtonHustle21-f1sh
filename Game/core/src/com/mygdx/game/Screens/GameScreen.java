package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.Building;
import com.mygdx.game.Objects.GameObject;
import com.mygdx.game.Objects.PlayerController;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    final HesHustle game;
    public ExtendViewport extendViewport;
    public ShapeRenderer shape;
    public final List<GameObject> objects;

    //Game objects
    PlayerController Player;
    Building ComSci,Nisa;



    public GameScreen(final HesHustle game) {
        this.game = game;
        extendViewport = new ExtendViewport(800,800);
        extendViewport.getCamera().position.set(800,400,0);
        shape = new ShapeRenderer();
        this.objects = new ArrayList<GameObject>();

        create();
    }
    public void create(){
        ComSci = new Building(100,400,100,100,"Computer\nScience\nDepartment",Boolean.TRUE);
        Nisa = new Building(400,400,100,100,"Nisa",Boolean.TRUE);
        Player = new PlayerController(400,240);

        objects.add(ComSci);
        objects.add(Nisa);
        objects.add(Player);
    }
    public void update(float delta) {
        for (GameObject gameObject : objects) {
            gameObject.update(delta);
        }
    }
    @Override
    public void render(float delta) {
        update(delta);


        ScreenUtils.clear(0, 0, 0.2f, 1);
        extendViewport.apply();
        extendViewport.getCamera().position.set(Player.pos.x,Player.pos.y,0);
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);

        renderObjects();



    }
    public void renderObjects()
    {
        game.batch.begin();
        for (GameObject gameObject : objects) {
            gameObject.render(extendViewport.getCamera().combined,game,shape);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width,height);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }

}
