package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.*;
import com.mygdx.game.Utils.EventManager;
import com.mygdx.game.Utils.ResourceManager;
import com.mygdx.game.Utils.ScreenType;


import java.util.ArrayList;
import java.util.List;

/**
 * Main game loop
 */
public class GameScreen implements Screen {

    private final ResourceManager resourceManager;
    private final HesHustle game;
    private final ExtendViewport extendViewport;
    private final ShapeRenderer shape;
    private final List<GameObject> objects;
    private final List<ActivityImage> activityImages;
    private final List<Building> buildings;

    //Game objects
    PlayerController Player;
    Building ComSci,BBall,Duck,Piazza,Langwith;
    TiledMapRenderer TmRender;
    TiledMap tiledMap;
    EventManager EventM;

    GUI gui;
    LightCycle LC;
    private final Music BGmusic;

    public GameScreen(final HesHustle game) {
        this.game = game;
        this.resourceManager = new ResourceManager();
        extendViewport = new ExtendViewport(1600,900);
        shape = resourceManager.addDisposable(new ShapeRenderer());
        tiledMap = resourceManager.addDisposable(new TmxMapLoader().load("MAP/map1.tmx"));
        TmRender = new OrthogonalTiledMapRenderer(tiledMap);

        this.objects = new ArrayList<>();
        this.activityImages = new ArrayList<>();
        this.buildings = new ArrayList<>();

        BGmusic = resourceManager.addDisposable(Gdx.audio.newMusic(Gdx.files.internal("XPT5HRY-video-game.mp3")));
        BGmusic.setLooping(true);


        create();

    }
    public void create(){

        // Initialize the collision layer (Will need to change 'cs' to an actual collision layer
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("collisionLayer");
        collisionLayer.setVisible(false);
        ComSci = new Building(530,380,100,100,"Computer\nScience\nDepartment");
        BBall = new Building(1450,2000,100,100,"BasketBall");
        Duck = new Building(2112,360,100,100,"Ducks");
        Langwith = new Building(1360,1375,100,100,"Langwith");
        Piazza = new Building(2550,1380,100,100,"Piazza");

        buildings.add(ComSci);//separate building list to cycle through to find closest to player
        buildings.add(BBall);
        buildings.add(Duck);
        buildings.add(Langwith);
        buildings.add(Piazza);

        EventM = new EventManager();
        Player = new PlayerController(1000,1000, EventM, collisionLayer);
        gui = new GUI(game.batch,EventM);
        LC = new LightCycle();


        //objects.add(EventM);
        activityImages.add(EventM.FeedDucks.getActivityImage());
        activityImages.add(EventM.EatPiazza.getActivityImage());
        activityImages.add(EventM.Sleep.getActivityImage());
        activityImages.add(EventM.StudyCS.getActivityImage());
        activityImages.add(EventM.PlayBBall.getActivityImage());

        objects.add(Player);

        objects.add(ComSci);
        objects.add(BBall);
        objects.add(Duck);
        objects.add(Langwith);
        objects.add(Piazza);

        Gdx.input.setInputProcessor(Player);
    }
    public void update(float delta) {

        for (GameObject gameObject : objects) {
            gameObject.update(delta);
        }

        EventM.update(delta);

        Player.setBD(getNearest());
        LC.getTime(EventM.TMin, EventM.TSec);
        gui.update(delta);

        if (checkGameOverCondition()) {
            game.setScreen(new EndScreen(game)); // Switch to EndScreen
        }


    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        extendViewport.apply();
        updateCamera();
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);

        TmRender.setView(extendViewport.getCamera().combined, 0,0,2887,2242);
        TmRender.render();


        renderObjects();
        LC.render(extendViewport.getCamera().combined,game,shape);//these could be in the objects list
        gui.render(extendViewport.getCamera().combined,game,shape);
        renderActivityImages();

                                                            // position of the projection matrix and we need it for the event render

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.screenManager.setScreen(ScreenType.PAUSE_SCREEN);
            dispose();
        }
    }
    public void renderObjects()
    {
        for (GameObject gameObject : objects) {
            gameObject.render(extendViewport.getCamera(),game,shape);
        }
    }

    public void renderActivityImages(){
        for (ActivityImage activityImage : activityImages) {
            activityImage.render(extendViewport.getCamera(),game,shape);
        }
    }

    // method updates the camera position so it follows the player but shows less out of bounds area
    public void updateCamera()
    {
        float x,y;
        float xConst = (float)1600/Gdx.graphics.getWidth(); // these constants are the ration of initial screen width to current
        float yConst = (float)900/Gdx.graphics.getHeight(); // if screen is half as wide it zooms out so its 2x smaller

        float camWidth = extendViewport.getScreenWidth()/2;
        float camHeight = extendViewport.getScreenHeight()/2;

        if (Player.pos.x > 2884 - camWidth*xConst) {
            x = 2884- camWidth*xConst;
        } else if (Player.pos.x < camWidth*xConst) {x = camWidth*xConst;} else {x=Player.pos.x;}

        if (Player.pos.y > 2238- camHeight*yConst) {
            y = 2238- camHeight*yConst;
        } else if (Player.pos.y < camHeight*yConst) {y = camHeight*yConst;} else {y=Player.pos.y;}

        extendViewport.getCamera().position.set(x,y,0);
    }

    public Building getNearest() //calculated the nearest building to the player rn
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
    public void resize(int width, int height) { //This is important for the GUI class to stay in aspect
        extendViewport.update(width,height);
        gui.stage.getViewport().update(extendViewport.getScreenWidth(), extendViewport.getScreenHeight(),true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(Player);
        BGmusic.play();
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
        resourceManager.disposeAll();
    }

}
