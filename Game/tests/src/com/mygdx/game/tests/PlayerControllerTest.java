package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.ActivityImage;
import com.mygdx.game.Objects.Animation;
import com.mygdx.game.Objects.PlayerController;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Utils.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;

@RunWith(GdxTestRunner.class)
public class PlayerControllerTest {
    private AutoCloseable closeable;

    private final HesHustle mockedGame = mock(HesHustle.class);
    private final GameScreen mockedGameScreen = mock(GameScreen.class);
    private final GameClock mockedClock = spy(GameClock.class);

    private final ScreenManager mockedSM = mock(ScreenManager.class);
    private final Event mockedEvent = mock(Event.class);
    private final ScreenType mockedST = mock(ScreenType.class);

    private final ActivityImage mockedImage = mock(ActivityImage.class);
    private final ActivityImage mockedStudyImage = mock(ActivityImage.class, withSettings()
            .useConstructor("Activitys/cs.png")
            .defaultAnswer(CALLS_REAL_METHODS));

    @Spy private final Event PlayBBall = new Event(2, -30, 50, 10, Event.Type.RECREATIONAL, 25, "", ScreenType.BASKETBALL_SCREEN);
    @Spy private final Event StudyCS = new Event(3, -20, 20, -10, Event.Type.STUDY, 15, "", mockedStudyImage);
    @Spy private final Event EatPiazza = new Event(1, 10, 0, 0, Event.Type.EAT, 0, "", mockedImage);
    @Spy private final Event FeedDucks = new Event(1, 2, 10, -5, Event.Type.RECREATIONAL, 0, "", mockedImage);
    @Spy private final Event Sleep = new Event(8, 90, 0, 0, Event.Type.SLEEP, 0, "", mockedImage);

    @InjectMocks
    private EventManager mockedEM = mock(EventManager.class, withSettings()
            .useConstructor(mockedGame, mockedClock)
            .defaultAnswer(CALLS_REAL_METHODS));

    private final ResourceManager resourceManager = new ResourceManager() ;
    TiledMap tiledMap = resourceManager.addDisposable(new TmxMapLoader().load("MAP/map1.tmx"));
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("collisionLayer");

    @Spy private final Animation IDLE_Left = new Animation(new Texture(Gdx.files.internal("Amelia_idle_anim_16x16.png")),12,17,24,12);
    @InjectMocks
    private PlayerController mockedPlayer = mock(PlayerController.class, withSettings()
            .useConstructor((float) 10, (float) 10, mockedEM, collisionLayer)
            .defaultAnswer(CALLS_REAL_METHODS));

    @Before
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        mockedGame.screenManager = mockedSM;
    }
    @Test
    public void testColCorrect(){
        mockedPlayer.pos.x = 1;


    }
    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}
