package com.mygdx.game.tests;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Utils.Event;
import com.badlogic.gdx.Gdx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.mygdx.game.Objects.GUI;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class GUItest {
    private AutoCloseable closeable;

    @Before
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testa(){

    }
    @Test
    public void testb(){

    }
    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}
