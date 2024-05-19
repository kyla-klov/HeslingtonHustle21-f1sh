package com.mygdx.game.tests;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.LightCycle;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Utils.GameClock;
import com.mygdx.game.Utils.ScreenManager;
import com.mygdx.game.Utils.ScreenType;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;


@RunWith(GdxTestRunner.class)
public class LightCycleTest {
    private final ScreenManager mockedSM = mock(ScreenManager.class);
    private final GameScreen mockedGameScreen = mock(GameScreen.class);
    private final GameClock mockedGameClock = spy(GameClock.class);
    private final Camera mockedCamera = mock(Camera.class);
    private final HesHustle mockedGame = mock(HesHustle.class);
    private final SpriteBatch mockedSpriteBatch = mock(SpriteBatch.class);
    private final Texture mockedTexture = mock(Texture.class);
    private final LightCycle lightCycle = new LightCycle();
    private final float[] Col1 = new float[]{238/255f, 130/255f, 0, 0.2f}; //orange
    private final float[] Col2 = new float[]{163/255f, 190/255f, 242/255f, 0.1f};//blue
    private final float[] Col3 = new float[]{113/255f, 0/255f, 143/255f, 0.3f};

    @Before
    public void setup(){
        mockedGame.screenManager = mockedSM;
        mockedGame.batch = mockedSpriteBatch;
        when(mockedSM.getScreen(ScreenType.GAME_SCREEN)).thenReturn(mockedGameScreen);
        when(mockedGameScreen.getGameClock()).thenReturn(mockedGameClock);
    }
    @Test
    public void testRender(){
        lightCycle.render(mockedGame.batch, mockedGameClock.getHours(), mockedGameClock.getMinutes());

        //verify time data is acquired from gameClock of game
        //verify(mockedGameScreen).getGameClock();
        //verify(mockedGameClock).getHours();
        //verify(mockedGameClock).getMinutes();

        //verify shape is rendered correctly
        verify(mockedGame.batch).setProjectionMatrix(mockedCamera.combined);
        verify(mockedGame.batch).begin();
        verify(mockedGame.batch).draw(mockedTexture, 0, 0,
                lightCycle.getSize(), lightCycle.getSize());
        verify(mockedGame.batch).end();

        //test shape is set to right color at 8 A.M
        verify(mockedGame.batch).setColor(lightCycle.getColor(Col2, Col1, Interpolation.pow3Out));

        //test shape is set to another color at 2 P.M
        mockedGameClock.setHours(14);
        lightCycle.render(mockedGame.batch, mockedGameClock.getHours(), mockedGameClock.getMinutes());
        verify(mockedGame.batch).setColor(lightCycle.getColor(Col1, Col2, Interpolation.pow3In));

        //test shape is set to another color at 8 P.M
        mockedGameClock.setHours(20);
        lightCycle.render(mockedGame.batch, mockedGameClock.getHours(), mockedGameClock.getMinutes());
        verify(mockedGame.batch).setColor(lightCycle.getColor(Col3, Col1, Interpolation.pow3Out));

        //test shape is set to another color at 3 A.M
        mockedGameClock.setHours(3);
        lightCycle.render(mockedGame.batch, mockedGameClock.getHours(), mockedGameClock.getMinutes());
        verify(mockedGame.batch).setColor(lightCycle.getColor(Col1, Col3, Interpolation.pow3In));
    }
}
