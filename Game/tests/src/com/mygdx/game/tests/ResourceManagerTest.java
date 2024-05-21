package com.mygdx.game.tests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Utils.ResourceManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import java.util.ArrayList;

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
