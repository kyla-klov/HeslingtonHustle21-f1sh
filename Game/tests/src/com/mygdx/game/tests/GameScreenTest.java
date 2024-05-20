package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.ActivityImage;
import com.mygdx.game.Objects.GUI;
import com.mygdx.game.Objects.PlayerController;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Utils.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

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

    private final Batch mockedBatch = mock(Batch.class);

    private final ScreenManager mockedSM = mock(ScreenManager.class);
    private final Event mockedEvent = mock(Event.class);
    private final ScreenType mockedST = mock(ScreenType.class);

    private final ActivityImage mockedImage = mock(ActivityImage.class);
    private final ActivityImage mockedStudyImage = mock(ActivityImage.class, withSettings()
            .useConstructor("Activitys/cs.png")
            .defaultAnswer(CALLS_REAL_METHODS));

    @Spy private final Event FeedDucks = new Event(1, 2, 0, -5, Event.Type.RECREATIONAL, 0, "",ScreenType.DUCK_GAME_SCREEN);
    @Spy private final Event StudyCS = new Event(3, -20, 1, -10, Event.Type.STUDY, 15, "CSBuildingStudy", mockedStudyImage);
    @Spy private final Event PlayBBall = new Event(2, -30, 0, 10, Event.Type.RECREATIONAL, 25, "", ScreenType.BASKETBALL_SCREEN);
    @Spy private final Event Sleep = new Event(8, 90, 0, 0, Event.Type.SLEEP, 0, "", mockedImage);
    @Spy private final Event EatPiazza = new Event(1, 10, 0, 0, Event.Type.EAT, 0, "", mockedImage);

    @Spy @InjectMocks
    private EventManager mockedEM = mock(EventManager.class, withSettings()
            .useConstructor(mockedGame, mockedClock)
            .defaultAnswer(CALLS_REAL_METHODS));

    private final Skin skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

    @Spy private Table leftTab = new Table();
    @Spy private TextButton ScoreButt = new TextButton("Score",skin);
    @Spy private TextButton DayButt = new TextButton("Day",skin);
    @Spy private TextButton TimeButt = new TextButton("Time",skin);
    @Spy private ProgressBar ngrBar = new ProgressBar(0,100,2,false,skin);

    @Spy private Table rightTab = new Table();
    @Spy private TextButton RecButt  = new TextButton("RecNo:",skin);
    @Spy private TextButton SleepButt  = new TextButton("SlpNo:",skin);
    @Spy private TextButton EatButt  = new TextButton("EatNo:",skin);
    @Spy private TextButton StudyButt  = new TextButton("StdyNo:",skin);

    @Spy @InjectMocks private GUI mockedGUI = mock(GUI.class, withSettings().
            useConstructor(mockedBatch, mockedEM, mockedClock)
            .defaultAnswer(CALLS_REAL_METHODS));

    private final ShapeRenderer mockedShapeRenderer = mock(ShapeRenderer.class);
    private final TiledMap mockedTiledMap = spy(new TmxMapLoader().load("MAP/map1.tmx"));
    private final TiledMapRenderer mockedTiledMapRender = mock(TiledMapRenderer.class);
    private final Music mockedMusic = mock(Music.class);
    @Spy private final PlayerController player = new PlayerController(1000,1000,
            mockedEM, (TiledMapTileLayer) mockedTiledMap.getLayers().get("collisionLayer"));
    @InjectMocks
    private GameScreen gameScreen = new GameScreen(mockedGame, mockedClock,
            mockedTiledMap, mockedTiledMapRender, mockedMusic);

    @BeforeClass
    public static void setBatch(){
        mockedGame.batch = mockedSpeiteBatch;
    }
    @Before
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        mockedGame.screenManager = mockedSM;
    }

    @Test
    public void testGetNearest(){
        assertNull(gameScreen.getNearest());
        player.pos.x = 500;
        player.pos.y = 300;
        assertEquals(gameScreen.getBuildings().get(0), gameScreen.getNearest());
    }

    @Test
    public void testShow(){
        gameScreen.show();
        //Unable to test whether input processor is set to an instance of PlayerController
        //as libGDX headless ignore any attempt to set a new input processor

        //assertEquals(player, Gdx.input.getInputProcessor());
        verify(mockedMusic, times(1)).play();

    }
    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}
