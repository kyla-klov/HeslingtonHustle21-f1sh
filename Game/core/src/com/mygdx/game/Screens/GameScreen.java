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
import com.mygdx.game.Utils.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Main game loop
 */
public class GameScreen implements Screen {

    private final ResourceManager resourceManager = new ResourceManager();
    private final HesHustle game;
    private final ExtendViewport extendViewport;
    private final ShapeRenderer shape;
    private final List<GameObject> objects;
    private final List<ActivityImage> activityImages;
    private final List<Building> buildings;
    private final GameClock gameClock;

    //Game objects
    private PlayerController player;
    private final TiledMapRenderer TmRender;
    private final TiledMap tiledMap;

    private GUI gui;
    private LightCycle LC;
    private final Music BGmusic;

    Achievement.Type hiker = null;

    public GameScreen(final HesHustle game, final GameClock gameClock,
                      final ShapeRenderer shape, final TiledMap tiledMap,
                      final TiledMapRenderer TmRender, final Music BGmusic){
        this.game = game;
        this.gameClock = gameClock;
        extendViewport = new ExtendViewport(1600,900);
        this.shape = resourceManager.addDisposable(shape);
        if (tiledMap != null && TmRender != null){
            this.tiledMap = resourceManager.addDisposable(tiledMap);
            this.TmRender = TmRender;
        }
        else{
            this.tiledMap = resourceManager.addDisposable(new TmxMapLoader().load("MAP/map1.tmx"));
            this.TmRender = new OrthogonalTiledMapRenderer(this.tiledMap);

        }

        this.objects = new ArrayList<>();
        this.activityImages = new ArrayList<>();
        this.buildings = new ArrayList<>();

        this.BGmusic = resourceManager.addDisposable(BGmusic);
        this.BGmusic.setLooping(true);

        create();
    }
    public GameScreen(final HesHustle game) {
        this(game, new GameClock(), new ShapeRenderer(), null, null, Gdx.audio.newMusic(Gdx.files.internal("XPT5HRY-video-game.mp3")));
//        this.game = game;
//        this.gameClock = new GameClock();
//        extendViewport = new ExtendViewport(1600,900);
//        shape = resourceManager.addDisposable(new ShapeRenderer());
//        TmRender = new OrthogonalTiledMapRenderer(tiledMap);
//        dailyStudy = new int[7];
//        dailyRecreational = new int[7];
//        mealTimes = new ArrayList<>();
//        placesStudied = new ArrayList<>();
//        totalStudyHours = 0;
//        for (int i = 0; i < 7; i++) {
//            mealTimes.add(new ArrayList<>());
//        }
//
//        this.objects = new ArrayList<>();
//        this.activityImages = new ArrayList<>();
//        this.buildings = new ArrayList<>();
//
//        BGmusic = resourceManager.addDisposable(Gdx.audio.newMusic(Gdx.files.internal("XPT5HRY-video-game.mp3")));
//        BGmusic.setLooping(true);
//
//        create();

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

        EventManager eventM = new EventManager(game, gameClock);
        player = new PlayerController(1000,1000, eventM, collisionLayer);
        gui = new GUI(game.batch, eventM, gameClock);
        LC = new LightCycle();

        for (int i = 0; i < eventM.listEvents().size(); i++) {
            activityImages.add(eventM.listEvents().get(i).getActivityImage());
        }

        objects.add(player);

        Gdx.input.setInputProcessor(player);
    }
    public void update(float delta) {
        gameClock.update(delta);
        for (GameObject gameObject : objects) {
            gameObject.update(delta);
        }

        player.setBD(getNearest());

        gui.update(delta);

        if (checkGameOverCondition()) {
            game.setScreen(new EndScreen(game)); // Switch to EndScreen
        }

        int steps = (int) player.getDistanceTravelled() / 10;
        if (steps >= 2500 && hiker == null){
            System.out.println("YAY");
            hiker = Achievement.Type.BRONZE;
            game.achievementHandler.getAchievement("Hiker", Achievement.Type.BRONZE).unlock();
        } else if (steps >= 5000 && hiker == Achievement.Type.BRONZE){
            hiker = Achievement.Type.SILVER;
            game.achievementHandler.getAchievement("Hiker", Achievement.Type.SILVER).unlock();
        } else if (steps >= 10000 && hiker == Achievement.Type.SILVER){
            hiker = Achievement.Type.GOLD;
            game.achievementHandler.getAchievement("Hiker", Achievement.Type.GOLD).unlock();
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

        if (player.pos.x > 2884 - camWidth*xConst) {
            x = 2884- camWidth*xConst;
        } else x = Math.max(player.pos.x, camWidth * xConst);

        if (player.pos.y > 2238- camHeight*yConst) {
            y = 2238- camHeight*yConst;
        } else y = Math.max(player.pos.y, camHeight * yConst);

        extendViewport.getCamera().position.set(x,y,0);
    }

    public Building getNearest() //calculated the nearest building to the player rn
    {
        Building closest = null;
        float closDis = 200f;
        for (Building bd : buildings) {
            if (Math.sqrt(Vector2.dst2(player.pos.x,player.pos.y,bd.pos.x,bd.pos.y)) < closDis)
            {
                closest = bd;
                closDis = (float) Math.sqrt(Vector2.dst2(player.pos.x,player.pos.y,bd.pos.x,bd.pos.y));
            }
        }
        return closest;
    }

    public GameClock getGameClock(){
        return gameClock;
    }

    public List<Building> getBuildings() {
        return buildings;
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
        Gdx.input.setInputProcessor(player);
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
