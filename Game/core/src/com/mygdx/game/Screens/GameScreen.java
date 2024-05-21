package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    private final Viewport vp;
    private final List<ActivityImage> activityImages;
    private final List<Building> buildings;
    private final GameClock gameClock;
    private final UIElements uiElements;
    private final OrthographicCamera camera;
    private EventManager eventM;
    private final NameTextField nameTextField;
    private final SpriteBatch batch;

    //Game objects
    private PlayerController player;
    private final TiledMapRenderer TmRender;
    private final TiledMap tiledMap;

    private LightCycle LC;
    private final Music BGmusic;

    private Achievement.Type hiker = null;

    public GameScreen(final HesHustle game, final GameClock gameClock, final TiledMap tiledMap,
                      final TiledMapRenderer TmRender, final Music BGmusic){
        this.game = game;
        this.batch = game.getBatch();
        this.gameClock = gameClock;
        this.camera = new OrthographicCamera();
        this.vp = new FitViewport(1600,900, camera);
        if (tiledMap != null && TmRender != null){
            this.tiledMap = resourceManager.addDisposable(tiledMap);
            this.TmRender = TmRender;
        }
        else{
            this.tiledMap = resourceManager.addDisposable(new TmxMapLoader().load("MAP/map1.tmx"));
            this.TmRender = new OrthogonalTiledMapRenderer(this.tiledMap, batch);
        }

        this.activityImages = new ArrayList<>();
        this.buildings = new ArrayList<>();

        this.BGmusic = resourceManager.addDisposable(BGmusic);
        this.BGmusic.setLooping(true);
        uiElements = new UIElements(vp, game.getAchievementHandler());
        nameTextField = new NameTextField(vp);
        create();
    }
    public GameScreen(final HesHustle game) {
        this(game, new GameClock(), null, null, Gdx.audio.newMusic(Gdx.files.internal("XPT5HRY-video-game.mp3")));
    }

    public void create(){

        // Initialize the collision layer (Will need to change 'cs' to an actual collision layer
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("collisionLayer");
        collisionLayer.setVisible(false);
        Building comSci = new Building(530, 380,"Computer\nScience\nDepartment", resourceManager.addDisposable(new Texture(Gdx.files.internal("book.png"))));
        Building BBall = new Building(1450, 2000, "BasketBall", resourceManager.addDisposable(new Texture(Gdx.files.internal("ball-of-basketball.png"))));
        Building duck = new Building(2112, 360, "Ducks", resourceManager.addDisposable(new Texture(Gdx.files.internal("duck_icon.png"))));
        Building langwith = new Building(1360, 1375, "Langwith", resourceManager.addDisposable(new Texture(Gdx.files.internal("sleep.png"))));
        Building piazza = new Building(2550, 1380, "Piazza", resourceManager.addDisposable(new Texture(Gdx.files.internal("restaurant.png"))));

        buildings.add(comSci);//separate building list to cycle through to find closest to player
        buildings.add(BBall);
        buildings.add(duck);
        buildings.add(langwith);
        buildings.add(piazza);

        eventM = new EventManager(game, gameClock);
        player = new PlayerController(1000,1000, eventM, collisionLayer);
        LC = new LightCycle();

        for (int i = 0; i < eventM.listEvents().size(); i++) {
            activityImages.add(eventM.listEvents().get(i).getActivityImage());
        }
    }
    public void update(float delta) {
        gameClock.update(delta);

        player.update(delta);

        player.setBD(getNearest());

        uiElements.update(delta);

        if (checkGameOverCondition()) {
            writeToFile();
            game.getScreenManager().setScreen(ScreenType.END_SCREEN, eventM.calcScore()); // Switch to EndScreen
        }

        int steps = (int) (player.getDistanceTravelled() / 7.5);
        if (steps >= 2500 && hiker == null){
            hiker = Achievement.Type.BRONZE;
            game.getAchievementHandler().getAchievement("Hiker", Achievement.Type.BRONZE).unlock();
        } else if (steps >= 5000 && hiker == Achievement.Type.BRONZE){
            hiker = Achievement.Type.SILVER;
            game.getAchievementHandler().getAchievement("Hiker", Achievement.Type.SILVER).unlock();
        } else if (steps >= 10000 && hiker == Achievement.Type.SILVER){
            hiker = Achievement.Type.GOLD;
            game.getAchievementHandler().getAchievement("Hiker", Achievement.Type.GOLD).unlock();
        }
    }



    @Override
    public void render(float delta) {

        if (nameTextField.textEntered()) update(delta);

        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateCamera();
        vp.apply();
        camera.update();

        batch.setProjectionMatrix(vp.getCamera().combined);

        TmRender.setView(camera);
        TmRender.render();

        batch.begin();

        for (Building building : buildings) {
            building.render(batch);
        }

        if (nameTextField.textEntered()){
            player.render(batch);
            LC.render(batch, gameClock.getHours(), gameClock.getMinutes());
        }

        uiElements.render(batch, gameClock.getTime(), gameClock.getDays(), eventM.getSleep(), eventM.getRec(), eventM.getEat(), eventM.getTotalStudyHours(), eventM.getEnergy(), eventM.calcScore());
        renderActivityImages();
        nameTextField.render(batch);
        batch.end();
        //gui.render(vp.getCamera(),game,shape);
          // position of the projection matrix, and we need it for the event render
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.getScreenManager().setScreen(ScreenType.PAUSE_SCREEN);
        }
    }

    public void renderActivityImages(){
        for (ActivityImage activityImage : activityImages) {
            if (activityImage != null) activityImage.render(vp.getCamera(),batch);
        }
    }

    // method updates the camera position, so it follows the player but shows less out-of-bounds area
    public void updateCamera()
    {
        float x,y;
        float xConst = (float)1600/Gdx.graphics.getWidth(); // these constants are the ration of initial screen width to current
        float yConst = (float)900/Gdx.graphics.getHeight(); // if screen is half as wide it zooms out so its 2x smaller

        float camWidth = (float) vp.getScreenWidth() /2;
        float camHeight = (float) vp.getScreenHeight() /2;

        if (player.getPos().x > 2884 - camWidth*xConst) {
            x = 2884- camWidth*xConst;
        } else x = Math.max(player.getPos().x, camWidth * xConst);

        if (player.getPos().y > 2238- camHeight*yConst) {
            y = 2238- camHeight*yConst;
        } else y = Math.max(player.getPos().y, camHeight * yConst);

        vp.getCamera().position.set(x,y,0);
    }

    public Building getNearest() //calculated the nearest building to the player rn
    {
        Building closest = null;
        float closDis = 200f;
        for (Building bd : buildings) {
            if (Math.sqrt(Vector2.dst2(player.getPos().x,player.getPos().y,bd.getPos().x,bd.getPos().y)) < closDis)
            {
                closest = bd;
                closDis = (float) Math.sqrt(Vector2.dst2(player.getPos().x,player.getPos().y,bd.getPos().x,bd.getPos().y));
            }
        }
        return closest;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    private boolean checkGameOverCondition(){
        return gameClock.getDays() > 7;
    }

    public void writeToFile() {
        String data = nameTextField.getValue() +  "," + eventM.calcScore() + "\n";
        FileHandle file = Gdx.files.local("storage/PlayerData.txt");
        file.writeString(data, true);
    }

    @Override
    public void resize(int width, int height) { //This is important for the GUI class to stay in aspect
        vp.update(width,height);
    }

    @Override
    public void show() {
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
