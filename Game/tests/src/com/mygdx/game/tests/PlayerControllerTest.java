package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.Animation;
import com.mygdx.game.Objects.PlayerController;
import com.mygdx.game.Utils.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class PlayerControllerTest {
    private AutoCloseable closeable;

    private final HesHustle mockedGame = mock(HesHustle.class);
    private final GameClock mockedClock = spy(GameClock.class);

    private final ScreenManager mockedSM = mock(ScreenManager.class);


    @InjectMocks
    private EventManager mockedEM = mock(EventManager.class, withSettings()
            .useConstructor(mockedGame, mockedClock)
            .defaultAnswer(CALLS_REAL_METHODS));

    private final ResourceManager resourceManager = new ResourceManager() ;
    TiledMap tiledMap = resourceManager.addDisposable(new TmxMapLoader().load("MAP/map1.tmx"));
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("collisionLayer");

    @Mock
    CollisionDetector mockCollisionDetector = mock(CollisionDetector.class);

    @Mock
    TextureRegion mockedTxr = mock(TextureRegion.class);

    @InjectMocks
    private PlayerController mockedPlayer = mock(PlayerController.class, withSettings()
            .useConstructor((float) 10, (float) 10, mockedEM, collisionLayer)
            .defaultAnswer(CALLS_REAL_METHODS));

    @Mock
    SpriteBatch mockedBatch = mock(SpriteBatch.class);

    @Mock
    Animation mockedAnimation = mock(Animation.class);

    @Mock
    private PlayerController.state mockedState = mock(PlayerController.state.class);

    @Mock
    private Input input;
    
    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        closeable = MockitoAnnotations.openMocks(this);
        when(mockedGame.getScreenManager()).thenReturn(mockedSM);
        Gdx.input = input;

        Field collisionField = mockedPlayer.getClass().getDeclaredField("collisionDetector");
        collisionField.setAccessible(true);
        collisionField.set(mockedPlayer, mockCollisionDetector);

        Field txrField = mockedPlayer.getClass().getDeclaredField("txr");
        txrField.setAccessible(true);
        txrField.set(mockedPlayer, mockedTxr);

        Field stateField = mockedPlayer.getClass().getDeclaredField("Pstate");
        stateField.setAccessible(true);
        stateField.set(mockedPlayer, mockedState);
    }

    @Test
    public void testUpdate_NoInteraction() {
        // Mock methods
        doReturn(mockedAnimation).when(mockedPlayer).getAnim(mockedState);
        when(mockedAnimation.getFrame(anyFloat())).thenReturn(mockedTxr);
        when(Gdx.input.isKeyPressed(Input.Keys.SPACE)).thenReturn(false);
        when(mockedPlayer.getDir()).thenReturn(new Vector2(1, 0));
        when(mockedPlayer.colCorrect(any(Vector2.class))).thenReturn(new Vector2(1, 0));
        when(mockedEM.notFrozen()).thenReturn(true);

        // Call update method
        mockedPlayer.update(1.0f);

        // Verify interactions and state changes
        verify(mockedPlayer).getAnim(mockedState);
        verify(mockedAnimation).getFrame(1.0f);
        verify(Gdx.input).isKeyPressed(Input.Keys.SPACE);
        verify(mockedPlayer, never()).interact();
        verify(mockedEM).notFrozen();
        assertEquals(new Vector2(310, 10), mockedPlayer.getPos());
        assertEquals(300, mockedPlayer.getDistanceTravelled(), 0.01);
    }

    @Test
    public void testUpdate_Interaction() {
        // Mock methods
        doReturn(mockedAnimation).when(mockedPlayer).getAnim(mockedState);
        when(mockedAnimation.getFrame(anyFloat())).thenReturn(mockedTxr);
        when(Gdx.input.isKeyPressed(Input.Keys.SPACE)).thenReturn(true);
        when(mockedPlayer.getDir()).thenReturn(new Vector2(1, 0));
        when(mockedPlayer.colCorrect(any(Vector2.class))).thenReturn(new Vector2(1, 0));
        when(mockedEM.notFrozen()).thenReturn(false);

        // Call update method
        mockedPlayer.update(1.0f);

        // Verify interactions and state changes
        verify(mockedPlayer).interact();

    }

    @Test
    public void testGetDir_NoInput() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(false);

        Vector2 result = mockedPlayer.getDir();
        assertEquals(new Vector2(0, 0), result);
    }

    @Test
    public void testGetDir_Up() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(false);

        Vector2 result = mockedPlayer.getDir();
        assertEquals(new Vector2(0, 1), result);
    }

    @Test
    public void testGetDir_Down() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(false);

        Vector2 result = mockedPlayer.getDir();
        assertEquals(new Vector2(0, -1), result);
    }

    @Test
    public void testGetDir_Left() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(false);

        Vector2 result = mockedPlayer.getDir();
        assertEquals(new Vector2(-1, 0), result);
    }

    @Test
    public void testGetDir_Right() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);

        Vector2 result = mockedPlayer.getDir();
        assertEquals(new Vector2(1, 0), result);
    }

    @Test
    public void testGetDir_UpRight() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);

        Vector2 result = mockedPlayer.getDir();
        assertEquals(new Vector2(1, 1), result);
    }

    @Test
    public void testGetDir_UpLeft() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(false);

        Vector2 result = mockedPlayer.getDir();
        assertEquals(new Vector2(-1, 1), result);
    }

    @Test
    public void testGetDir_DownRight() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);

        Vector2 result = mockedPlayer.getDir();
        assertEquals(new Vector2(1, -1), result);
    }

    @Test
    public void testGetDir_DownLeft() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(false);

        Vector2 result = mockedPlayer.getDir();
        assertEquals(new Vector2(-1, -1), result);
    }

    @Test
    public void testGetDir_LeftRight() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);

        Vector2 result = mockedPlayer.getDir();
        assertEquals(new Vector2(0, 0), result);
    }

    @Test
    public void testGetDir_UpDown() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(false);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(false);

        Vector2 result = mockedPlayer.getDir();
        assertEquals(new Vector2(0, 0), result);
    }

    @Test
    public void testColCorrect_NoCollisions() {
        // Configure the mock to return false for all collision checks
        when(mockCollisionDetector.collidesRight()).thenReturn(false);
        when(mockCollisionDetector.collidesLeft()).thenReturn(false);
        when(mockCollisionDetector.collidesUp()).thenReturn(false);
        when(mockCollisionDetector.collidesDown()).thenReturn(false);

        Vector2 dir = new Vector2(1, 1);
        Vector2 result = mockedPlayer.colCorrect(dir);

        assertEquals(new Vector2(1, 1), result);
    }

    @Test
    public void testColCorrect_CollidesRight() {
        // Configure the mock to simulate a collision on the right
        when(mockCollisionDetector.collidesRight()).thenReturn(true);
        when(mockCollisionDetector.collidesLeft()).thenReturn(false);
        when(mockCollisionDetector.collidesUp()).thenReturn(false);
        when(mockCollisionDetector.collidesDown()).thenReturn(false);

        Vector2 dir = new Vector2(1, 0);
        Vector2 result = mockedPlayer.colCorrect(dir);

        assertEquals(new Vector2(0, 0), result);
    }

    @Test
    public void testColCorrect_CollidesLeft() {
        // Configure the mock to simulate a collision on the left
        when(mockCollisionDetector.collidesRight()).thenReturn(false);
        when(mockCollisionDetector.collidesLeft()).thenReturn(true);
        when(mockCollisionDetector.collidesUp()).thenReturn(false);
        when(mockCollisionDetector.collidesDown()).thenReturn(false);

        Vector2 dir = new Vector2(-1, 0);
        Vector2 result = mockedPlayer.colCorrect(dir);

        assertEquals(new Vector2(0, 0), result);
    }

    @Test
    public void testColCorrect_CollidesUp() {
        // Configure the mock to simulate a collision above
        when(mockCollisionDetector.collidesRight()).thenReturn(false);
        when(mockCollisionDetector.collidesLeft()).thenReturn(false);
        when(mockCollisionDetector.collidesUp()).thenReturn(true);
        when(mockCollisionDetector.collidesDown()).thenReturn(false);

        Vector2 dir = new Vector2(0, 1);
        Vector2 result = mockedPlayer.colCorrect(dir);

        assertEquals(new Vector2(0, 0), result);
    }

    @Test
    public void testColCorrect_CollidesDown() {
        // Configure the mock to simulate a collision below
        when(mockCollisionDetector.collidesRight()).thenReturn(false);
        when(mockCollisionDetector.collidesLeft()).thenReturn(false);
        when(mockCollisionDetector.collidesUp()).thenReturn(false);
        when(mockCollisionDetector.collidesDown()).thenReturn(true);

        Vector2 dir = new Vector2(0, -1);
        Vector2 result = mockedPlayer.colCorrect(dir);

        assertEquals(new Vector2(0, 0), result);
    }

    @Test
    public void testColCorrect_MultipleCollisions() {
        // Configure the mock to simulate collisions in multiple directions
        when(mockCollisionDetector.collidesRight()).thenReturn(true);
        when(mockCollisionDetector.collidesLeft()).thenReturn(true);
        when(mockCollisionDetector.collidesUp()).thenReturn(true);
        when(mockCollisionDetector.collidesDown()).thenReturn(true);

        Vector2 dir = new Vector2(1, 1);
        Vector2 result = mockedPlayer.colCorrect(dir);

        assertEquals(new Vector2(0, 0), result);
    }

    @Test
    public void testGetAnim_IdleState() {
        // Mock getDir to return (0, 0)
        when(mockedPlayer.getDir()).thenReturn(new Vector2(0, 0));

        // Test each idle state
        assertEquals(mockedPlayer.IDLE_UP, mockedPlayer.getAnim(PlayerController.state.IDLE_UP));
        assertEquals(mockedPlayer.IDLE_DOWN, mockedPlayer.getAnim(PlayerController.state.IDLE_DOWN));
        assertEquals(mockedPlayer.IDLE_RIGHT, mockedPlayer.getAnim(PlayerController.state.IDLE_RIGHT));
        assertEquals(mockedPlayer.IDLE_LEFT, mockedPlayer.getAnim(PlayerController.state.IDLE_LEFT));
    }

    @Test
    public void testGetAnim_WalkState() {
        // Mock getDir to return (0, 0)
        when(mockedPlayer.getDir()).thenReturn(new Vector2(0, 0));

        // Test each walk state
        assertEquals(mockedPlayer.IDLE_UP, mockedPlayer.getAnim(PlayerController.state.WALK_UP));
        assertEquals(mockedPlayer.IDLE_DOWN, mockedPlayer.getAnim(PlayerController.state.WALK_DOWN));
        assertEquals(mockedPlayer.IDLE_RIGHT, mockedPlayer.getAnim(PlayerController.state.WALK_RIGHT));
        assertEquals(mockedPlayer.IDLE_LEFT, mockedPlayer.getAnim(PlayerController.state.WALK_LEFT));
    }

    @Test
    public void testGetAnim_MovingRight() {
        // Mock getDir to return (1, 0)
        when(mockedPlayer.getDir()).thenReturn(new Vector2(1, 0));

        assertEquals(mockedPlayer.WALK_RIGHT, mockedPlayer.getAnim(PlayerController.state.WALK_RIGHT));
    }

    @Test
    public void testGetAnim_MovingLeft() {
        // Mock getDir to return (-1, 0)
        when(mockedPlayer.getDir()).thenReturn(new Vector2(-1, 0));

        assertEquals(mockedPlayer.WALK_LEFT, mockedPlayer.getAnim(PlayerController.state.WALK_LEFT));
    }

    @Test
    public void testGetAnim_MovingUp() {
        // Mock getDir to return (0, 1)
        when(mockedPlayer.getDir()).thenReturn(new Vector2(0, 1));

        assertEquals(mockedPlayer.WALK_UP, mockedPlayer.getAnim(PlayerController.state.WALK_UP));
    }

    @Test
    public void testGetAnim_MovingDown() {
        // Mock getDir to return (0, -1)
        when(mockedPlayer.getDir()).thenReturn(new Vector2(0, -1));

        assertEquals(mockedPlayer.WALK_DOWN, mockedPlayer.getAnim(PlayerController.state.WALK_DOWN));
    }

    @Test
    public void testGetAnim_NoDirectionMatch() {
        // Mock getDir to return a non-matching direction
        when(mockedPlayer.getDir()).thenReturn(new Vector2(2, 2));

        assertNull(mockedPlayer.getAnim(PlayerController.state.WALK_UP));
    }

    @Test
    public void testRender() {
        // Call the render method

        mockedBatch.begin();
        mockedPlayer.render(mockedBatch);
        mockedBatch.end();
        // Verify that the draw method was called with the correct parameters
        verify(mockedBatch).draw(mockedTxr, mockedPlayer.getPos().x, mockedPlayer.getPos().y, mockedPlayer.getWidth(), mockedPlayer.getHeight());
    }

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}
