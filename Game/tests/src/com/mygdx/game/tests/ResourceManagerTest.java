package com.mygdx.game.tests;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Utils.ResourceManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class ResourceManagerTest {
    private final Texture mockedTexture = mock(Texture.class);
    private final ResourceManager resourceManager = new ResourceManager();
    @Before
    public void setup(){
        resourceManager.addDisposable(mockedTexture);
    }
    @Test
    public void testAddDisposable(){
        assertEquals(1, resourceManager.getDisposables().size());
        assertEquals(mockedTexture, resourceManager.getDisposables().get(0));
    }
    @Test
    public void testDisposeAll(){
        resourceManager.disposeAll();
        verify(mockedTexture).dispose();
    }
}
