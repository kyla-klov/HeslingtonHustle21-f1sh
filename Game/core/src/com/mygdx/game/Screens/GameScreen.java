package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.Building;
import com.mygdx.game.Objects.GameObject;
import com.mygdx.game.Objects.PlayerController;
import com.mygdx.game.Objects.TiledTest;

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

    TiledTest map;
    TiledMapRenderer TmRender;
    TiledMap tiledMap;


    public GameScreen(final HesHustle game) {
        this.game = game;
        extendViewport = new ExtendViewport(800,800);
        extendViewport.getCamera().position.set(800,400,0);
        shape = new ShapeRenderer();
        this.objects = new ArrayList<GameObject>();
        tiledMap = new TmxMapLoader().load("MAP/map1.tmx");
        TmRender = new OrthogonalTiledMapRenderer(tiledMap);
        create();
    }
    public void create(){
        ComSci = new Building(200,600,100,100,"Computer\nScience\nDepartment",Boolean.TRUE);
        Nisa = new Building(400,400,100,100,"Nisa",Boolean.TRUE);
        Player = new PlayerController(1000,1000);

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

        TmRender.setView(extendViewport.getCamera().combined, 0,0,5000,5000);
        TmRender.render();

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
