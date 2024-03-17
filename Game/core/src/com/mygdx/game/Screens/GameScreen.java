package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameScreen implements Screen {
    final HesHustle game;
    public ExtendViewport extendViewport;
    public ShapeRenderer shape;
    public final List<GameObject> objects;
    public final List<Building> buildings;


    public static final int TARGET_X = 500;
    public static final int TARGET_Y = 500;

    //Game objects
    PlayerController Player;
    Building ComSci,Nisa;
    TiledMapRenderer TmRender;
    TiledMap tiledMap;
    EventManager EventM;

    GUI gui;

    public GameScreen(final HesHustle game) {
        this.game = game;
        this.buildings = new ArrayList<Building>();
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
        buildings.add(ComSci);
        buildings.add(Nisa);
        EventM = new EventManager(buildings);
        Player = new PlayerController(1000,1000, EventM);
        gui = new GUI(game.batch,EventM);
        objects.add(EventM);
        objects.add(Player);
        objects.add(ComSci);
        objects.add(Nisa);


        Gdx.input.setInputProcessor(Player);

    }
    public void update(float delta) {
        for (GameObject gameObject : objects) {
            gameObject.update(delta);
        }

        Player.setBD(getNearest());
        gui.update(delta);



        if (checkGameOverCondition()) {
            game.setScreen(new EndScreen(game)); // Switch to EndScreen
        }
    }

    private boolean checkGameOverCondition(){
        // TO-DO
        return false;

    }
    @Override
    public void render(float delta) {
        update(delta);



        //ScreenUtils.clear(0, 0, 0f, 1);
        Gdx.gl.glClearColor(0.1f,0.1f,0.9f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game));
            dispose();
        }
        ScreenUtils.clear(0, 0, 0.2f, 1);
        extendViewport.apply();
        extendViewport.getCamera().position.set(Player.pos.x,Player.pos.y,0);
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);

        TmRender.setView(extendViewport.getCamera().combined, 0,0,5000,5000);
        TmRender.render();



        renderObjects();

        gui.render(extendViewport.getCamera().combined,game,shape);
        EventM.render(extendViewport.getCamera(),game,shape);
    }

    public Building getNearest()
    {
        Building closest = null;
        float closDis = 200f;
        for (Building bd : buildings) {
            if (Math.sqrt(Vector2.dst2(Player.pos.x,Player.pos.y,bd.pos.x,bd.pos.y)) <closDis)
            {
                closest = bd;
                closDis = (float) Math.sqrt(Vector2.dst2(Player.pos.x,Player.pos.y,bd.pos.x,bd.pos.y));
            }
        }

        return closest; 
    }
    public void renderObjects()
    {
        for (GameObject gameObject : objects) {
            gameObject.render(extendViewport.getCamera().combined,game,shape);
        }
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width,height);
        gui.stage.getViewport().update(extendViewport.getScreenWidth(), extendViewport.getScreenHeight(),true);
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
