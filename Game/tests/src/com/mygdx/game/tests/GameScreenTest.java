package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.ActivityImage;
import com.mygdx.game.Objects.NameTextField;
import com.mygdx.game.Objects.PlayerController;
import com.mygdx.game.Objects.UIElements;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Server;
import com.mygdx.game.Utils.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class GameScreenTest {
    private AutoCloseable closeable;

    private static final SpriteBatch mockedSpeiteBatch = mock(SpriteBatch.class);
    private static final HesHustle mockedGame = mock(HesHustle.class);
    private final GameClock mockedClock = spy(GameClock.class);
    private final OrthographicCamera camera = new OrthographicCamera();
    private final FitViewport vp = new FitViewport(1600,900, camera);
    private final UIElements mockedUiElements = mock(UIElements.class);
    private final NameTextField mockedNameTextField = mock(NameTextField.class);
    private static final AchievementHandler mockedAchievementHandler = spy(AchievementHandler.class);
//    private final Batch mockedBatch = mock(Batch.class);

    private final ScreenManager mockedSM = mock(ScreenManager.class);
    private final Event mockedEvent = mock(Event.class);
    private final ScreenType mockedST = mock(ScreenType.class);

    private final ActivityImage mockedImage = mock(ActivityImage.class);
    private final ActivityImage mockedStudyImage = mock(ActivityImage.class, withSettings()
            .useConstructor("Activitys/cs.png")
            .defaultAnswer(CALLS_REAL_METHODS));

    @Spy private final Event FeedDucks = new Event(1, 2, 0, -5, Event.Type.RECREATIONAL, 0, "",ScreenType.DUCK_GAME_SCREEN);
    @Spy private final Event StudyCS = new Event(3, -20, 1, -10, Event.Type.STUDY, 15, "CSBuildingStudy", ScreenType.CHECKIN_CODE_SCREEN);
    @Spy private final Event PlayBBall = new Event(2, -30, 0, 10, Event.Type.RECREATIONAL, 25, "", ScreenType.BASKETBALL_SCREEN);
    @Spy private final Event Sleep = new Event(8, 90, 0, 0, Event.Type.SLEEP, 0, "", ScreenType.SLEEP_SCREEN);
    @Spy private final Event EatPiazza = new Event(1, 10, 0, 0, Event.Type.EAT, 0, "", ScreenType.COOKIE_SCREEN);

    @Spy @InjectMocks
    private EventManager mockedEM = mock(EventManager.class, withSettings()
            .useConstructor(mockedGame, mockedClock)
            .defaultAnswer(CALLS_REAL_METHODS));

    private final Skin skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

    private final ShapeRenderer mockedShapeRenderer = mock(ShapeRenderer.class);
    private final TiledMap mockedTiledMap = spy(new TmxMapLoader().load("MAP/map1.tmx"));
    private final TiledMapRenderer mockedTiledMapRender = mock(TiledMapRenderer.class);
    @Spy private final PlayerController player = new PlayerController(1000,1000,
            mockedEM, (TiledMapTileLayer) mockedTiledMap.getLayers().get("collisionLayer"));
    @InjectMocks
    private GameScreen gameScreen = new GameScreen(mockedGame, mockedClock,
            mockedTiledMap, mockedTiledMapRender, camera, vp, mockedUiElements, mockedNameTextField);

    @BeforeClass
    public static void setBatch(){
        mockedGame.setBatch(mockedSpeiteBatch);
        when(mockedGame.getBatch()).thenReturn(mockedSpeiteBatch);
        when(mockedGame.getAchievementHandler()).thenReturn(mockedAchievementHandler);
    }
    @Before
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        when(mockedGame.getScreenManager()).thenReturn(mockedSM);

    }

    @Test
    //Only test whether player get the right achievement when they walk certain amount of steps
    public void testUpdate(){
        //test no achievement
        gameScreen.update(1f);
        verify(mockedGame, never()).getAchievementHandler();

        //test bronze achievement
        when(player.getDistanceTravelled()).thenReturn(18750f);
        gameScreen.update(1f);
        assertTrue(mockedGame.getAchievementHandler().getAchievement("Hiker", Achievement.Type.BRONZE).isUnlocked());
        when(player.getDistanceTravelled()).thenReturn(37499f);
        gameScreen.update(1f);
        assertFalse(mockedGame.getAchievementHandler().getAchievement("Hiker", Achievement.Type.SILVER).isUnlocked());

        //test silver achievement
        when(player.getDistanceTravelled()).thenReturn(37500f);
        gameScreen.update(1f);
        assertTrue(mockedGame.getAchievementHandler().getAchievement("Hiker", Achievement.Type.SILVER).isUnlocked());
        when(player.getDistanceTravelled()).thenReturn(74999f);
        gameScreen.update(1f);
        assertFalse(mockedGame.getAchievementHandler().getAchievement("Hiker", Achievement.Type.GOLD).isUnlocked());

        //test gold achievement
        when(player.getDistanceTravelled()).thenReturn(75000f);
        gameScreen.update(1f);
        assertTrue(mockedGame.getAchievementHandler().getAchievement("Hiker", Achievement.Type.GOLD).isUnlocked());
    }

    @Test
    public void testRenderAfterEnterName(){
        when(mockedNameTextField.textEntered()).thenReturn(true);
        InOrder inOrder = inOrder(mockedSpeiteBatch, Gdx.gl, player, mockedClock,
                mockedTiledMapRender, mockedUiElements, mockedNameTextField);
        gameScreen.render(1f);
        inOrder.verify(mockedClock).update(1f);
        inOrder.verify(player).update(1f);
        inOrder.verify(player).setBD(null);
        inOrder.verify(mockedUiElements).update(1f);
        inOrder.verify(Gdx.gl).glClearColor(0f,0f,0f,1);
        inOrder.verify(Gdx.gl).glClear(GL20.GL_COLOR_BUFFER_BIT);
        inOrder.verify(mockedSpeiteBatch).setProjectionMatrix(vp.getCamera().combined);
        inOrder.verify(mockedTiledMapRender).setView(camera);
        inOrder.verify(mockedTiledMapRender).render();
        inOrder.verify(mockedSpeiteBatch).begin();
        inOrder.verify(mockedUiElements).render(mockedSpeiteBatch, mockedClock.getTime(),
                mockedClock.getDays(), mockedEM.getSleep(), mockedEM.getRec(),
                mockedEM.getEat(), mockedEM.getTotalStudyHours(), mockedEM.getEnergy(),
                mockedEM.calcScore()
        );
        inOrder.verify(mockedNameTextField).render(mockedSpeiteBatch);
        inOrder.verify(mockedSpeiteBatch).end();
    }
    @Test
    public void testRenderBeforeEnterName(){
        InOrder inOrder = inOrder(mockedSpeiteBatch, Gdx.gl, player, mockedClock,
                mockedTiledMapRender, mockedUiElements, mockedNameTextField);
        gameScreen.render(1f);
        inOrder.verify(Gdx.gl).glClearColor(0f,0f,0f,1);
        inOrder.verify(Gdx.gl).glClear(GL20.GL_COLOR_BUFFER_BIT);
        inOrder.verify(mockedSpeiteBatch).setProjectionMatrix(vp.getCamera().combined);
        inOrder.verify(mockedTiledMapRender).setView(camera);
        inOrder.verify(mockedTiledMapRender).render();
        inOrder.verify(mockedSpeiteBatch).begin();
        inOrder.verify(mockedUiElements).render(mockedSpeiteBatch, mockedClock.getTime(),
                mockedClock.getDays(), mockedEM.getSleep(), mockedEM.getRec(),
                mockedEM.getEat(), mockedEM.getTotalStudyHours(), mockedEM.getEnergy(),
                mockedEM.calcScore()
        );
        inOrder.verify(mockedNameTextField).render(mockedSpeiteBatch);
        inOrder.verify(mockedSpeiteBatch).end();
    }

    @Test
    public void testGetNearest(){
        assertNull(gameScreen.getNearest());
        player.getPos().x = 500;
        player.getPos().y = 300;
        assertEquals(gameScreen.getBuildings().get(0), gameScreen.getNearest());
    }

    @Test
    public void testWriteToFile(){
        String data = "";
        boolean sucess = false;
        when(mockedNameTextField.getValue()).thenReturn("The Senate");
        when(mockedEM.calcScore()).thenReturn(66f);
        gameScreen.writeToFile();
        FileHandle file = Gdx.files.local("storage/PlayerData.txt");
        if (file.exists()) {
            String[] lines = file.readString().split("\n");
            for (String line : lines) {
                if(line.equals("The Senate,66.0,0")) {
                    sucess = true;
                    file.writeString(data, false);
                    break;
                }
                else data += line + "\n";
            }

        }
        assertTrue(sucess);
    }

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}
