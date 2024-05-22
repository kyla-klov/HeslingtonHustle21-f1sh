package com.mygdx.game.tests;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.PlayerController;
import com.mygdx.game.Utils.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.lang.reflect.Field;

@RunWith(GdxTestRunner.class)
public class CollisionDetectorTest {
    private AutoCloseable closeable;
    private final HesHustle mockedGame = mock(HesHustle.class);
    private final GameClock mockedClock = spy(GameClock.class);

    @InjectMocks
    private EventManager mockedEM = mock(EventManager.class, withSettings()
            .useConstructor(mockedGame, mockedClock)
            .defaultAnswer(CALLS_REAL_METHODS));

    private final ResourceManager resourceManager = new ResourceManager() ;
    TiledMap tiledMap = resourceManager.addDisposable(new TmxMapLoader().load("MAP/map1.tmx"));
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("collisionLayer");

    TiledMapTileLayer mockedCollisionLayer = mock(TiledMapTileLayer.class);

    @InjectMocks
    private PlayerController mockedPlayer = mock(PlayerController.class, withSettings()
            .useConstructor((float) 10, (float) 10, mockedEM, mockedCollisionLayer)
            .defaultAnswer(CALLS_REAL_METHODS));

    private final CollisionDetector mockedCollisionDetector = mock(CollisionDetector.class, withSettings()
            .useConstructor(mockedPlayer, mockedCollisionLayer)
            .defaultAnswer(CALLS_REAL_METHODS));



    @Before public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    // tests that ...
    public void testCollidesRight() {
        // Mock collision layer to not block any cells
        when(mockedCollisionDetector.isCellBlocked(32, 4)).thenReturn(true);
        when(mockedCollisionLayer.getTileWidth()).thenReturn(32);
        when(mockedCollisionDetector.getX()).thenReturn(0f);
        when(mockedCollisionDetector.getY()).thenReturn(0f);

        boolean result = mockedCollisionDetector.collidesRight();

        assertTrue("Expected collision but no collision detected", result);
    }

    @Test
    public void testCollidesRight_NoCollision() {
        // Mock collision layer to not block any cells
        when(mockedCollisionDetector.isCellBlocked(0, 4)).thenReturn(true);
        when(mockedCollisionDetector.isCellBlocked(32, 36)).thenReturn(true);
        when(mockedCollisionLayer.getTileWidth()).thenReturn(32);
        when(mockedCollisionDetector.getX()).thenReturn(0f);
        when(mockedCollisionDetector.getY()).thenReturn(0f);

        boolean result = mockedCollisionDetector.collidesRight();

        assertFalse("Expected no collision but collision detected", result);
    }

    @Test
    // tests that ...
    public void testCollidesLeft() {
        // Mock collision layer to not block any cells
        when(mockedCollisionDetector.isCellBlocked(0, 4)).thenReturn(true);
        when(mockedCollisionLayer.getTileWidth()).thenReturn(32);
        when(mockedCollisionDetector.getX()).thenReturn(0f);
        when(mockedCollisionDetector.getY()).thenReturn(0f);

        boolean result = mockedCollisionDetector.collidesLeft();

        assertTrue("Expected collision but no collision detected", result);
    }

    @Test
    public void testCollidesLeft_NoCollision() {
        // Mock collision layer to not block any cells
        when(mockedCollisionDetector.isCellBlocked(32, 4)).thenReturn(true);
        when(mockedCollisionDetector.isCellBlocked(32, 36)).thenReturn(true);
        when(mockedCollisionLayer.getTileWidth()).thenReturn(32);
        when(mockedCollisionDetector.getX()).thenReturn(0f);
        when(mockedCollisionDetector.getY()).thenReturn(0f);

        boolean result = mockedCollisionDetector.collidesLeft();

        assertFalse("Expected no collision but collision detected", result);
    }

    @Test
    // tests that ...
    public void testCollidesUp() {
        // Mock collision layer to not block any cells
        when(mockedCollisionDetector.isCellBlocked(4, 64)).thenReturn(true);
        when(mockedCollisionLayer.getTileWidth()).thenReturn(32);
        when(mockedCollisionDetector.getX()).thenReturn(0f);
        when(mockedCollisionDetector.getY()).thenReturn(0f);

        boolean result = mockedCollisionDetector.collidesUp();

        assertTrue("Expected collision but no collision detected", result);
    }

    @Test
    public void testCollidesUp_NoCollision() {
        // Mock collision layer to not block any cells
        when(mockedCollisionDetector.isCellBlocked(4, 0)).thenReturn(true);
        when(mockedCollisionDetector.isCellBlocked(36, 32)).thenReturn(true);
        when(mockedCollisionLayer.getTileWidth()).thenReturn(32);
        when(mockedCollisionDetector.getX()).thenReturn(0f);
        when(mockedCollisionDetector.getY()).thenReturn(0f);

        boolean result = mockedCollisionDetector.collidesUp();

        assertFalse("Expected no collision but collision detected", result);
    }

    @Test
    // tests that ...
    public void testCollidesDown() {
        // Mock collision layer to not block any cells
        when(mockedCollisionDetector.isCellBlocked(4, 0)).thenReturn(true);
        when(mockedCollisionLayer.getTileWidth()).thenReturn(32);
        when(mockedCollisionDetector.getX()).thenReturn(0f);
        when(mockedCollisionDetector.getY()).thenReturn(0f);

        boolean result = mockedCollisionDetector.collidesDown();

        assertTrue("Expected collision but no collision detected", result);
    }

    @Test
    public void testCollidesDown_NoCollision() {
        // Mock collision layer to not block any cells
        when(mockedCollisionDetector.isCellBlocked(4, 32)).thenReturn(true);
        when(mockedCollisionDetector.isCellBlocked(36, 32)).thenReturn(true);
        when(mockedCollisionLayer.getTileWidth()).thenReturn(32);
        when(mockedCollisionDetector.getX()).thenReturn(0f);
        when(mockedCollisionDetector.getY()).thenReturn(0f);

        boolean result = mockedCollisionDetector.collidesDown();

        assertFalse("Expected no collision but collision detected", result);
    }

    @Test
    public void testIsCellBlocked() throws NoSuchFieldException, IllegalAccessException {
        Field layerField = mockedCollisionDetector.getClass().getDeclaredField("collisionLayer");
        layerField.setAccessible(true);
        layerField.set(mockedCollisionDetector, collisionLayer);

        boolean result = mockedCollisionDetector.isCellBlocked(0, 0);
        assertTrue(result);
    }

    @Test
    public void testIsCellBlocked_unblockedCell() throws NoSuchFieldException, IllegalAccessException {
        Field layerField = mockedCollisionDetector.getClass().getDeclaredField("collisionLayer");
        layerField.setAccessible(true);
        layerField.set(mockedCollisionDetector, collisionLayer);

        boolean result = mockedCollisionDetector.isCellBlocked(1000, 1000);
        assertFalse(result);
    }

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }

}
