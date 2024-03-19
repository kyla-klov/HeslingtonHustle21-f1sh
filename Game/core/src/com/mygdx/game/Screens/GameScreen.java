package com.mygdx.game.Screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
    //Game objects
    PlayerController Player;
    Building ComSci,BBall,Duck,Piazza,Langwith;
    TiledMapRenderer TmRender;
    TiledMap tiledMap;
    EventManager EventM;
    GUI gui;
    private Music BGmusic;

    public GameScreen(final HesHustle game) {
        this.game = game;

        extendViewport = new ExtendViewport(800,800);
        shape = new ShapeRenderer();
        tiledMap = new TmxMapLoader().load("MAP/map1.tmx");
        TmRender = new OrthogonalTiledMapRenderer(tiledMap);

        this.objects = new ArrayList<GameObject>();
        this.buildings = new ArrayList<Building>();

        BGmusic = Gdx.audio.newMusic(Gdx.files.internal("XPT5HRY-video-game.mp3"));
        BGmusic.play();
        BGmusic.setLooping(true);

        create();

    }
    public void create(){
        ComSci = new Building(600,1000,100,100,"Computer\nScience\nDepartment",Boolean.TRUE);
        BBall = new Building(800,1000,100,100,"BasketBall",Boolean.TRUE);
        Duck = new Building(1000,1000,100,100,"Ducks",Boolean.TRUE);
        Langwith = new Building(1200,1000,100,100,"Langwith",Boolean.TRUE);
        Piazza = new Building(1400,1000,100,100,"Piazza",Boolean.TRUE);


        buildings.add(ComSci);
        buildings.add(BBall);
        buildings.add(Duck);
        buildings.add(Langwith);
        buildings.add(Piazza);

        EventM = new EventManager(buildings);
        Player = new PlayerController(1000,1000, EventM);
        gui = new GUI(game.batch,EventM);

        objects.add(EventM);
        objects.add(Player);
        objects.add(ComSci);
        objects.add(BBall);
        objects.add(Duck);
        objects.add(Langwith);
        objects.add(Piazza);


        Gdx.input.setInputProcessor(Player);
    }
    public void update(float delta) {
        Gdx.input.setInputProcessor(Player);
        for (GameObject gameObject : objects) {
            gameObject.update(delta);
        }

        Player.setBD(getNearest());

        gui.update(delta);

        if (checkGameOverCondition()) {
            game.setScreen(new EndScreen(game)); // Switch to EndScreen
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.1f,0.1f,0.9f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        extendViewport.apply();
        extendViewport.getCamera().position.set(Player.pos.x,Player.pos.y,0);
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);

        TmRender.setView(extendViewport.getCamera().combined, 0,0,5000,5000);
        TmRender.render();

        renderObjects();

        gui.render(extendViewport.getCamera().combined,game,shape);
        EventM.render(extendViewport.getCamera(),game,shape);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game,this));
            dispose();
        }
    }
    public void renderObjects()
    {
        for (GameObject gameObject : objects) {
            gameObject.render(extendViewport.getCamera().combined,game,shape);
        }
    }
    public Building getNearest()
    {
        Building closest = null;
        float closDis = 200f;
        for (Building bd : buildings) {
            if (Math.sqrt(Vector2.dst2(Player.pos.x,Player.pos.y,bd.pos.x,bd.pos.y)) < closDis)
            {
                closest = bd;
                closDis = (float) Math.sqrt(Vector2.dst2(Player.pos.x,Player.pos.y,bd.pos.x,bd.pos.y));
            }
        }
        return closest;
    }
    private boolean checkGameOverCondition(){
        if (EventM.day > 7)
        {
            return true;
        }
        return false;

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
