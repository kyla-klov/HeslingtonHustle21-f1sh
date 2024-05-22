package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.Objects.GameMusic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class GameMusicTest {
    // getVolume() method of LibGDX Music Class always return 0 in testing
    private AutoCloseable closeable;
    @Spy private final Music music = Gdx.audio.newMusic(Gdx.files.internal("music_loop/Ludum Dare 30 - 01.ogg"));;
    @InjectMocks GameMusic mockedGameMusic = spy(GameMusic.class);

    @Before
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testMusicSetUp(){
        assertTrue(Gdx.files.internal("music_loop/Ludum Dare 30 - 01.ogg").exists());
    }
    
    @Test
    public void testPause(){
        mockedGameMusic.pause();
        verify(music).pause();
    }

    @Test
    public void testStop(){
        mockedGameMusic.stop();
        verify(music).stop();
    }

    @Test
    public void testDispose(){
        mockedGameMusic.dispose();
        verify(music).dispose();
    }
    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }

}
