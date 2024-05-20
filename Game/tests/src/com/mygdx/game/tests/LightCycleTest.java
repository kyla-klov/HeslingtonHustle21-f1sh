package com.mygdx.game.tests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.math.Interpolation;
import com.mygdx.game.Objects.LightCycle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class LightCycleTest {
    private final String blackSquareFile = "BlankSquare.png";
    private final SpriteBatch mockedSpriteBatch = mock(SpriteBatch.class);
    private final Texture mockedTexture = mock(Texture.class);
    private final LightCycle mockedLightCycle = spy(LightCycle.class);
    private final float[] Col1 = new float[]{238/255f, 130/255f, 0, 0.2f}; //orange
    private final float[] Col2 = new float[]{163/255f, 190/255f, 242/255f, 0.1f};//blue
    private final float[] Col3 = new float[]{113/255f, 0/255f, 143/255f, 0.3f};
    private InOrder inOrder = inOrder(mockedSpriteBatch);
    private ArgumentCaptor<Texture> arg = ArgumentCaptor.forClass(Texture.class);
    private final float x = 0f, y = 0f;
    private final float size = mockedLightCycle.getSize();

    //test batch color at noon and 5 P.M.
    @Test
    public void testRenderSeg1() {
        mockedLightCycle.render(mockedSpriteBatch, 12, 0);
        verify(mockedLightCycle).getTime(12, 0);
        inOrder.verify(mockedSpriteBatch).setColor(mockedLightCycle.getColor(Col1, Col2, Interpolation.pow3In));
        inOrder.verify(mockedSpriteBatch).draw(arg.capture(), eq(x), eq(y),
                eq(size), eq(size));
        assertEquals(blackSquareFile, ((FileTextureData) arg.getValue().getTextureData()).getFileHandle().path());
        inOrder.verify(mockedSpriteBatch).setColor(1, 1, 1, 1);

        mockedLightCycle.render(mockedSpriteBatch, 17, 0);
        verify(mockedLightCycle).getTime(17, 0);
        inOrder.verify(mockedSpriteBatch).setColor(mockedLightCycle.getColor(Col1, Col2, Interpolation.pow3In));
        inOrder.verify(mockedSpriteBatch).draw(arg.capture(), eq(x), eq(y),
                eq(size), eq(size));
        assertEquals(blackSquareFile, ((FileTextureData) arg.getValue().getTextureData()).getFileHandle().path());
        inOrder.verify(mockedSpriteBatch).setColor(1, 1, 1, 1);
    }

    //test batch color at 6 P.M. and 11 P.M.
    @Test
    public void testRenderSeg2() {
        mockedLightCycle.render(mockedSpriteBatch, 18, 0);
        verify(mockedLightCycle).getTime(18, 0);
        inOrder.verify(mockedSpriteBatch).setColor(mockedLightCycle.getColor(Col3, Col1, Interpolation.pow3Out));
        inOrder.verify(mockedSpriteBatch).draw(arg.capture(), eq(x), eq(y),
                eq(size), eq(size));
        assertEquals(blackSquareFile, ((FileTextureData) arg.getValue().getTextureData()).getFileHandle().path());
        inOrder.verify(mockedSpriteBatch).setColor(1, 1, 1, 1);

        mockedLightCycle.render(mockedSpriteBatch, 23, 0);
        verify(mockedLightCycle).getTime(23, 0);
        inOrder.verify(mockedSpriteBatch).setColor(mockedLightCycle.getColor(Col3, Col1, Interpolation.pow3Out));
        inOrder.verify(mockedSpriteBatch).draw(arg.capture(), eq(x), eq(y),
                eq(size), eq(size));
        assertEquals(blackSquareFile, ((FileTextureData) arg.getValue().getTextureData()).getFileHandle().path());
        inOrder.verify(mockedSpriteBatch).setColor(1, 1, 1, 1);
    }

    //test batch color at midnight and 5 A.M.
    @Test
    public void testRenderSeg3() {
        mockedLightCycle.render(mockedSpriteBatch, 24, 0);
        verify(mockedLightCycle).getTime(24, 0);
        inOrder.verify(mockedSpriteBatch).setColor(mockedLightCycle.getColor(Col1, Col3, Interpolation.pow3In));
        inOrder.verify(mockedSpriteBatch).draw(arg.capture(), eq(x), eq(y),
                eq(size), eq(size));
        assertEquals(blackSquareFile, ((FileTextureData) arg.getValue().getTextureData()).getFileHandle().path());
        inOrder.verify(mockedSpriteBatch).setColor(1, 1, 1, 1);

        mockedLightCycle.render(mockedSpriteBatch, 0, 0);
        verify(mockedLightCycle).getTime(0, 0);
        inOrder.verify(mockedSpriteBatch).setColor(mockedLightCycle.getColor(Col1, Col3, Interpolation.pow3In));
        inOrder.verify(mockedSpriteBatch).draw(arg.capture(), eq(x), eq(y),
                eq(size), eq(size));
        assertEquals(blackSquareFile, ((FileTextureData) arg.getValue().getTextureData()).getFileHandle().path());
        inOrder.verify(mockedSpriteBatch).setColor(1, 1, 1, 1);

        mockedLightCycle.render(mockedSpriteBatch, 5, 0);
        verify(mockedLightCycle).getTime(5, 0);
        inOrder.verify(mockedSpriteBatch).setColor(mockedLightCycle.getColor(Col1, Col3, Interpolation.pow3In));
        inOrder.verify(mockedSpriteBatch).draw(arg.capture(), eq(x), eq(y),
                eq(size), eq(size));
        assertEquals(blackSquareFile, ((FileTextureData) arg.getValue().getTextureData()).getFileHandle().path());
        inOrder.verify(mockedSpriteBatch).setColor(1, 1, 1, 1);
    }

    //test batch color at 6 A.M. and 11 A.M.
    @Test
    public void testRenderSeg0() {
        mockedLightCycle.render(mockedSpriteBatch, 6, 0);
        verify(mockedLightCycle).getTime(6, 0);
        inOrder.verify(mockedSpriteBatch).setColor(mockedLightCycle.getColor(Col2, Col1, Interpolation.pow3Out));
        inOrder.verify(mockedSpriteBatch).draw(arg.capture(), eq(x), eq(y),
                eq(size), eq(size));
        assertEquals(blackSquareFile, ((FileTextureData) arg.getValue().getTextureData()).getFileHandle().path());
        inOrder.verify(mockedSpriteBatch).setColor(1, 1, 1, 1);

        mockedLightCycle.render(mockedSpriteBatch, 11, 0);
        verify(mockedLightCycle).getTime(11, 0);
        inOrder.verify(mockedSpriteBatch).setColor(mockedLightCycle.getColor(Col2, Col1, Interpolation.pow3Out));
        inOrder.verify(mockedSpriteBatch).draw(arg.capture(), eq(x), eq(y),
                eq(size), eq(size));
        assertEquals(blackSquareFile, ((FileTextureData) arg.getValue().getTextureData()).getFileHandle().path());
        inOrder.verify(mockedSpriteBatch).setColor(1, 1, 1, 1);
    }
}
