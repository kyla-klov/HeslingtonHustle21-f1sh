package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Utils.ViewportAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(GdxTestRunner.class)
public class ViewportAdapterTest {
    private final SpriteBatch mockedBatch = mock(SpriteBatch.class);
    private final BitmapFont mockedFont = mock(BitmapFont.class);
    private final Texture mockedTexture = mock(Texture.class);
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport vp = new FitViewport(1600,900, camera);

    @Test
    public void testDrawUI(){
        ViewportAdapter.drawUI(vp, mockedBatch, mockedTexture, 800f, 450f , 0f, 0f);
        verify(mockedBatch).draw(mockedTexture, 0f, 0f, 0f, 0f);
    }

    @Test
    public void testDrawFont(){
        ViewportAdapter.drawFont(vp, mockedFont, mockedBatch, "Bad Batch", 800f, 450f);
        verify(mockedFont).draw(mockedBatch, "Bad Batch", 0f, 0f);
    }

    @Test
    public void testToScreen(){
        assertEquals(new Vector2(0f, 0f), ViewportAdapter.toScreen(vp, 0, 0));
    }

    @Test
    public void testScreenToGame(){
        assertEquals(new Vector2(Float.NaN, Float.NaN), ViewportAdapter.screenToGame(vp, 0, 0));
    }

    @Test
    public void testIsOver(){
        assertTrue(ViewportAdapter.isOver(2, 2, 1, 1, 2, 2));
        assertFalse(ViewportAdapter.isOver(0, 2, 1, 1, 2, 2));
        assertFalse(ViewportAdapter.isOver(3, 2, 1, 1, 2, 2));
        assertFalse(ViewportAdapter.isOver(2, 0, 1, 1, 2, 2));
        assertFalse(ViewportAdapter.isOver(2, 3, 1, 1, 2, 2));


    }

    @Test
    public void testIsOverRect(){
        Rectangle rect = new Rectangle(1, 1, 2, 2);
        assertTrue(ViewportAdapter.isOver(2, 2, rect));
        assertFalse(ViewportAdapter.isOver(0, 2, rect));
        assertFalse(ViewportAdapter.isOver(3, 2, rect));
        assertFalse(ViewportAdapter.isOver(2, 0, rect));
        assertFalse(ViewportAdapter.isOver(2, 3, rect));
    }
}
