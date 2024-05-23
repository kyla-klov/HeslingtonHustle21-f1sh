package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Objects.AchievementsDisplay;
import com.mygdx.game.Utils.Achievement;
import com.mygdx.game.Utils.AchievementHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.AdditionalMatchers;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class AchievementDisplayTest {
    private AchievementHandler achievementHandler = new AchievementHandler();
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport vp = new FitViewport(2400,740, camera);
    private final BitmapFont mockedFont = mock(BitmapFont.class);
    private final AchievementsDisplay achievementsDisplay = new AchievementsDisplay(vp, mockedFont, achievementHandler, 1200, 370);
    private final SpriteBatch mockedBatch = mock(SpriteBatch.class);

    @Before
    public void setup(){
        Gdx.input = mock(Input.class);
        vp.setScreenWidth(120);
        vp.setScreenHeight(5);
        when(Gdx.input.getX()).thenReturn(4);
        when(Gdx.input.getY()).thenReturn(-2);
//        achievementHandler.getAchievement("Duck Duck Go", Achievement.Type.GOLD).unlock();
    }
    @Test
    public void testRender(){
        achievementsDisplay.render(mockedBatch);
        verifyNoInteractions(mockedBatch);
        verifyNoInteractions(mockedFont);

        ArgumentCaptor<Texture> argumentCaptor1 = ArgumentCaptor.forClass(Texture.class);
        ArgumentCaptor<Texture> argumentCaptor2 = ArgumentCaptor.forClass(Texture.class);
        ArrayList<String> expectedArgs = new ArrayList<>(Arrays.asList(
                "AchievementsDisplay/AchievementsBackground.png",
                "AchievementsDisplay/ScrollBar2.png",
                "AchievementsDisplay/HighlightSelected.png",
                "AchievementsDisplay/check-mark.png",
                "AchievementsDisplay/lock-padlock-symbol-for-security-interface.png",
                "AchievementsDisplay/HighlightSelected.png",
                "AchievementsDisplay/HighlightSelected.png"
        ));
        expectedArgs.ensureCapacity(500);
        InOrder inOrder = inOrder(mockedBatch, Gdx.gl, mockedFont);

        achievementsDisplay.show();
        achievementsDisplay.render(mockedBatch);
        inOrder.verify(mockedBatch).draw(argumentCaptor1.capture(), eq(0f), eq(0f), eq(349f), eq(414f));
        inOrder.verify(mockedBatch).draw(argumentCaptor1.capture(), eq(349f*0.87f), eq(414f*0.67f), eq(13f), eq(67f));
        inOrder.verify(mockedBatch,times(2)).draw(argumentCaptor1.capture(), AdditionalMatchers.eq(349f * 0.3f, 0.01f), eq(414f*0.88f), eq(25f), eq(25f));
        inOrder.verify(mockedBatch).draw(argumentCaptor1.capture(), AdditionalMatchers.eq(349f * 0.65f, 0.01f), eq(414f*0.88f), eq(25f), eq(25f));
        inOrder.verify(mockedBatch).draw(argumentCaptor1.capture(), eq(0f), eq(0f), eq(0f), eq(0f));
        inOrder.verify(Gdx.gl).glEnable(GL20.GL_SCISSOR_TEST);
        inOrder.verify(Gdx.gl).glScissor(0,0,17,2);
        inOrder.verify(mockedBatch).draw(argumentCaptor1.capture(), eq(0f), eq(0f), eq(0f), eq(0f));
        inOrder.verify(Gdx.gl).glDisable(GL20.GL_SCISSOR_TEST);
        List<Texture> args = argumentCaptor1.getAllValues();
        assertEquals(expectedArgs.size(), args.size());
        for (int i = 0; i < args.size(); i++) {
            assertEquals(expectedArgs.get(i), ((FileTextureData) args.get(i).getTextureData()).getFileHandle().path());
        }
        verifyNoInteractions(mockedFont);

        expectedArgs.add(6, "AchievementsDisplay/DuckAchievementGold.png");
        achievementHandler.getAchievement("Duck Duck Go", Achievement.Type.GOLD).unlock();
        achievementsDisplay.render(mockedBatch);
        inOrder.verify(mockedBatch).draw(argumentCaptor2.capture(), eq(0f), eq(0f), eq(349f), eq(414f));
        inOrder.verify(mockedBatch).draw(argumentCaptor2.capture(), eq(349f*0.87f), eq(414f*0.67f), eq(13f), eq(67f));
        inOrder.verify(mockedBatch,times(2)).draw(argumentCaptor2.capture(), AdditionalMatchers.eq(349f * 0.3f, 0.01f), eq(414f*0.88f), eq(25f), eq(25f));
        inOrder.verify(mockedBatch).draw(argumentCaptor2.capture(), AdditionalMatchers.eq(349f * 0.65f, 0.01f), eq(414f*0.88f), eq(25f), eq(25f));
        inOrder.verify(mockedBatch).draw(argumentCaptor2.capture(), eq(0f), eq(0f), eq(0f), eq(0f));
        inOrder.verify(Gdx.gl).glEnable(GL20.GL_SCISSOR_TEST);
        inOrder.verify(Gdx.gl).glScissor(0,0,17,2);
        inOrder.verify(mockedBatch).draw(argumentCaptor2.capture(), eq((349-210)/2f), anyFloat(), eq(210f), eq(70f));
        inOrder.verify(mockedBatch).draw(argumentCaptor2.capture(), eq(0f), eq(0f), eq(0f), eq(0f));
        inOrder.verify(Gdx.gl).glDisable(GL20.GL_SCISSOR_TEST);
        inOrder.verify(mockedFont).draw(eq(mockedBatch), eq("Duck Duck Go"), eq((349-210)/2f-250), anyFloat());
        inOrder.verify(mockedFont).draw(eq(mockedBatch), eq("Feed all of the ducks in under 15 seconds"), eq((349-210)/2f - 250), anyFloat());
        args = argumentCaptor2.getAllValues();
        assertEquals(expectedArgs.size(), args.size());
        for (int i = 0; i < args.size(); i++) {
            assertEquals(expectedArgs.get(i), ((FileTextureData) args.get(i).getTextureData()).getFileHandle().path());
        }
    }
}
