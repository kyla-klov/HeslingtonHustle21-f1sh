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
    @Spy private final Music audio = Gdx.audio.newMusic(Gdx.files.internal("music_loop/Ludum Dare 30 - 01.ogg"));;
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
    public void testDecremetVolume(){
        //test whether volume is decreased when musicLevel is larger than 0
        mockedGameMusic.decrementVolume();
        assertEquals(3, mockedGameMusic.getMusicLevel(), 0);
        verify(audio,times(1)).setVolume(0.075f);

        //verify volume not changed when musicLevel is 0
        mockedGameMusic.decrementVolume();
        mockedGameMusic.decrementVolume();
        mockedGameMusic.decrementVolume();
        mockedGameMusic.decrementVolume();
        assertEquals(0, mockedGameMusic.getMusicLevel(), 0);
        verify(audio,times(1)).setVolume(0.025f);

        reset(audio);
    }
    @Test
    public void testIncrementVolume(){
        //verify volume not changed when musicLevel is 0
        mockedGameMusic.incrementVolume();
        verify(audio, times(0)).setVolume(anyFloat());

        //test whether volume is increased when musicLevel is smaller than 4
        mockedGameMusic.decrementVolume();
        mockedGameMusic.decrementVolume();
        mockedGameMusic.incrementVolume();
        assertEquals(3, mockedGameMusic.getMusicLevel(), 0);
        verify(audio, times(1)).setVolume(0.075f);

        reset(audio);
    }

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }

}
