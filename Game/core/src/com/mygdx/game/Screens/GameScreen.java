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
import com.mygdx.game.Utils.GameClock;
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
    private final GameClock gameClock;

    //Game objects
    private PlayerController Player;
    private final TiledMapRenderer TmRender;
    private final TiledMap tiledMap;

    private GUI gui;
    private LightCycle LC;
    private final Music BGmusic;

    //Game Score Data//
    private final int[] dailyStudy;
    private final int[] dailyRecreational;
    private final List<List<Integer>> mealTimes;
    private final List<String> placesStudied;
    private int totalStudyHours;


    public GameScreen(final HesHustle game) {
        this.game = game;
        this.resourceManager = new ResourceManager();
        this.gameClock = new GameClock();
        extendViewport = new ExtendViewport(1600,900);
        shape = resourceManager.addDisposable(new ShapeRenderer());
        tiledMap = resourceManager.addDisposable(new TmxMapLoader().load("MAP/map1.tmx"));
        TmRender = new OrthogonalTiledMapRenderer(tiledMap);
        dailyStudy = new int[7];
        dailyRecreational = new int[7];
        mealTimes = new ArrayList<>();
        placesStudied = new ArrayList<>();
        totalStudyHours = 0;
        for (int i = 0; i < 7; i++) {
            mealTimes.add(new ArrayList<>());
        }

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
        Building comSci = new Building(530, 380, 100, 100, "Computer\nScience\nDepartment");
        Building BBall = new Building(1450, 2000, 100, 100, "BasketBall");
        Building duck = new Building(2112, 360, 100, 100, "Ducks");
        Building langwith = new Building(1360, 1375, 100, 100, "Langwith");
        Building piazza = new Building(2550, 1380, 100, 100, "Piazza");

        buildings.add(comSci);//separate building list to cycle through to find closest to player
        buildings.add(BBall);
        buildings.add(duck);
        buildings.add(langwith);
        buildings.add(piazza);

        EventManager eventM = new EventManager(game, this, gameClock);
        Player = new PlayerController(1000,1000, eventM, collisionLayer);
        gui = new GUI(game.batch, eventM, gameClock, this);
        LC = new LightCycle();

        for (int i=0; i < eventM.listEvents().size(); i++) {
            activityImages.add(eventM.listEvents().get(i).getActivityImage());
        }

        objects.add(Player);

        objects.add(comSci);
        objects.add(BBall);
        objects.add(duck);
        objects.add(langwith);
        objects.add(piazza);

        Gdx.input.setInputProcessor(Player);
    }
    public void update(float delta) {
        gameClock.update(delta);
        for (GameObject gameObject : objects) {
            gameObject.update(delta);
        }

        Player.setBD(getNearest());

        gui.update(delta);

        if (checkGameOverCondition()) {
            game.setScreen(new EndScreen(game)); // Switch to EndScreen
        }


    }

    public float calcScore(){
        int s1, s2, s3, s4, s5;

        int num0s = 0;
        int num1s = 0;
        for (int study : dailyStudy){
            if (study == 0){
                num0s++;
            } else if (study == 1){
                num1s++;
            }
        }
        if (num0s == 0 || (num0s == 1 && num1s <= 5)){
            s1 = 100;
        } else if (num0s == 2 && num1s <= 4){
            s1 = 60;
        } else if (num0s < 7){
            s1 = 40;
        } else{
            s1 = 0;
        }

        switch(placesStudied.size()){
            case 0:
                s2 = 0;
                break;
            case 1:
                s2 = 60;
                break;
            case 2:
                s2 = 80;
                break;
            default:
                s2 = 100;
                break;
        }

        s3 = (totalStudyHours >= 28 && totalStudyHours <= 35) ? 100 : (totalStudyHours * 100 / 28);

        int notEaten = 0;
        for (List<Integer> times : mealTimes){
            if (times.size() < 3){
                notEaten++;
            }
        }
        notEaten = (notEaten + 1)/2;
        switch (notEaten){
            case 0:
                s4 = 100;
                break;
            case 1:
                s4 = 80;
                break;
            case 2:
                s4 = 60;
                break;
            case 3:
                s4 = 40;
                break;
            default:
                s4 = 0;
                break;
        }

        int numBad = 0;
        for (int recreate : dailyRecreational){
            if (recreate == 0 || recreate >= 3){
                numBad++;
            }
        }

        switch (numBad){
            case 0:
                s5 = 100;
                break;
            case 1:
                s5 = 80;
                break;
            case 2:
            case 3:
                s5 = 60;
                break;
            case 4:
            case 5:
                s5 = 40;
                break;
            default:
                s5 = 20;
        }
        return (s1 + s2 + s3 + s4 + s5) / 5f;

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
        LC.render(extendViewport.getCamera(),game,shape);//these could be in the objects list
        gui.render(extendViewport.getCamera(),game,shape);
        renderActivityImages();

                                                            // position of the projection matrix and we need it for the event render

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.screenManager.setScreen(ScreenType.PAUSE_SCREEN);
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
            if (activityImage != null) activityImage.render(extendViewport.getCamera(),game,shape);
        }
    }

    // method updates the camera position, so it follows the player but shows less out-of-bounds area
    public void updateCamera()
    {
        float x,y;
        float xConst = (float)1600/Gdx.graphics.getWidth(); // these constants are the ration of initial screen width to current
        float yConst = (float)900/Gdx.graphics.getHeight(); // if screen is half as wide it zooms out so its 2x smaller

        float camWidth = (float) extendViewport.getScreenWidth() /2;
        float camHeight = (float) extendViewport.getScreenHeight() /2;

        if (Player.pos.x > 2884 - camWidth*xConst) {
            x = 2884- camWidth*xConst;
        } else x = Math.max(Player.pos.x, camWidth * xConst);

        if (Player.pos.y > 2238- camHeight*yConst) {
            y = 2238- camHeight*yConst;
        } else y = Math.max(Player.pos.y, camHeight * yConst);

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

    public GameClock getGameClock(){
        return gameClock;
    }

    public int getTotalStudyHours(){
        return totalStudyHours;
    }

    public void setTotalStudyHours(int totalStudyHours){
        this.totalStudyHours = totalStudyHours;
    }

    public void addRecreational(){
        dailyRecreational[gameClock.getDays()-1]++;
    }

    public void addStudy(){
        dailyStudy[gameClock.getDays()-1]++;
    }

    public void addStudyPlace(String studyPlace){
        if (!placesStudied.contains(studyPlace)){
            placesStudied.add(studyPlace);
        }
    }

    public void addMeal(int time){
        mealTimes.get(gameClock.getDays()-1).add(time);
    }

    private boolean checkGameOverCondition(){
        return gameClock.getDays() > 7;

    }

    @Override
    public void resize(int width, int height) { //This is important for the GUI class to stay in aspect
        extendViewport.update(width,height);
        gui.getStage().getViewport().update(extendViewport.getScreenWidth(), extendViewport.getScreenHeight(),true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(Player);
        BGmusic.play();
    }

    @Override
    public void hide() {
        BGmusic.pause();
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
