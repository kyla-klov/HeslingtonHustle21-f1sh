package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Objects.AchievementsDisplay;
import com.mygdx.game.Objects.EnergyBar;
import com.mygdx.game.Objects.UIElements;
import com.mygdx.game.Utils.AchievementHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class UiElementTest {
    private AchievementHandler achievementHandler = new AchievementHandler();
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport vp = new FitViewport(0,0, camera);
    private final EnergyBar mockedEB = mock(EnergyBar.class);
    private final BitmapFont.BitmapFontData mockedBFD = mock(BitmapFont.BitmapFontData.class);
    private final BitmapFont mockedFont = mock(BitmapFont.class);
    private final AchievementsDisplay mockedAD = mock(AchievementsDisplay.class);
    private final UIElements uiElements = new UIElements(vp, achievementHandler, mockedEB, mockedAD, mockedFont);

    @Before public void setup(){
        when(mockedFont.getData()).thenReturn(mockedBFD);
    }
    @Test
    public void testUpdate(){
        uiElements.update(0.9f);
        assertEquals(1475.15f, uiElements.getCurX(),0);
        assertEquals(774.75f, uiElements.getCurY(), 0);
        uiElements.update(2f);
        assertEquals(1490, uiElements.getCurX(),0);
        assertEquals(750, uiElements.getCurY(), 0);
    }

    @Test
    public void testRender(){
        SpriteBatch mockedBatch = mock(SpriteBatch.class);
        InOrder inOrder = inOrder(mockedBatch, mockedFont, mockedEB, mockedAD, mockedEB, mockedBFD);
        ArgumentCaptor<Texture> args = ArgumentCaptor.forClass(Texture.class);
        uiElements.render(mockedBatch, "10:10", 2, 1, 2, 3, 1, 95, 22);
        inOrder.verify(mockedEB).render(mockedBatch, 95);
        inOrder.verify(mockedAD).render(mockedBatch);
        inOrder.verify(mockedBatch).draw(args.capture(), eq(1450f), eq(800f), eq(50f), eq(50f));
        inOrder.verify(mockedBatch).draw(args.capture(), eq(0f), eq(0f), eq(50f), eq(50f));
        inOrder.verify(mockedBFD).setScale(1.4f);
        inOrder.verify(mockedFont).draw(mockedBatch, "Score: 22.0", 100f, 825f);
        inOrder.verify(mockedBFD).setScale(1f);
        inOrder.verify(mockedFont).draw(mockedBatch, "10:10", 1200f, 840f);
        inOrder.verify(mockedFont).draw(mockedBatch, "Day: 2", 1050f, 840f);
        inOrder.verify(mockedFont).draw(mockedBatch, "Sleep Count: 1", 100f, 780f);
        inOrder.verify(mockedFont).draw(mockedBatch, "Rec Count: 2", 100f, 750f);
        inOrder.verify(mockedFont).draw(mockedBatch, "Eat Count: 3", 100f, 720f);
        inOrder.verify(mockedFont).draw(mockedBatch, "Study Hours: 1", 100f, 690f);
        assertEquals(2, args.getAllValues().size(), 0);
        assertEquals("badge.png", ((FileTextureData)args.getAllValues().get(0).getTextureData()).getFileHandle().path());
        assertEquals("tap.png", ((FileTextureData)args.getAllValues().get(1).getTextureData()).getFileHandle().path());
    }
}
