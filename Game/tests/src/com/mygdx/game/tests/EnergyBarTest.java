package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Objects.EnergyBar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class EnergyBarTest {
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport vp = new FitViewport(160,1200, camera);
    private final EnergyBar energyBar = new EnergyBar(vp, 80, 600, 270, 50, 27);

    @Test
    public void testRender(){
        ArgumentCaptor<Texture> argumentCaptor = ArgumentCaptor.forClass(Texture.class);
        List<String> expectedArgs = List.of(
                "EnergyBar/meter_bar_holder_left_edge_blue.png",
                "EnergyBar/meter_bar_holder_center-repeating_blue.png",
                "EnergyBar/meter_bar_holder_right_edge_blue.png",
                "EnergyBar/meter_bar_center-repeating_blue.png",
                "EnergyBar/meter_bar_left_edge_blue.png",
                "EnergyBar/meter_bar_center-repeating_blue.png",
                "EnergyBar/meter_bar_right_edge_blue.png",
                "EnergyBar/meter_bar_center-repeating_blue.png"
                );
        SpriteBatch mockedBatch = mock(SpriteBatch.class);
        InOrder inOrder = inOrder(mockedBatch, Gdx.gl);
        energyBar.render(mockedBatch, 50f);
        inOrder.verify(mockedBatch).draw(argumentCaptor.capture(), eq(0f), eq(0f), eq(27f), eq(50f));
        inOrder.verify(mockedBatch).draw(argumentCaptor.capture(), eq(27f), eq(0f), eq(270f-54f), eq(50f));
        inOrder.verify(mockedBatch).draw(argumentCaptor.capture(), eq(270f-27f), eq(0f), eq(27f), eq(50f));
        inOrder.verify(mockedBatch).draw(argumentCaptor.capture(), eq(0f), eq(0f), eq(0f), eq(0f));
        inOrder.verify(Gdx.gl).glEnable(GL20.GL_SCISSOR_TEST);
        inOrder.verify(Gdx.gl).glScissor(0, 0, 0, 0);
        inOrder.verify(mockedBatch).draw(argumentCaptor.capture(), eq(0f), eq(0f), eq(27f), eq(50f));
        inOrder.verify(mockedBatch).draw(argumentCaptor.capture(), eq(27f), eq(0f), eq(270f-54f), eq(50f));
        inOrder.verify(mockedBatch).draw(argumentCaptor.capture(), eq(270f-27f), eq(0f), eq(27f), eq(50f));
        inOrder.verify(mockedBatch).draw(argumentCaptor.capture(), eq(0f), eq(0f), eq(0f), eq(0f));
        inOrder.verify(Gdx.gl).glDisable(GL20.GL_SCISSOR_TEST);
        List<Texture> args = argumentCaptor.getAllValues();
        assertEquals(expectedArgs.size(), args.size());
        for (int i = 0; i < args.size(); i++) {
            assertEquals(expectedArgs.get(i), ((FileTextureData) args.get(i).getTextureData()).getFileHandle().path());
        }
    }
}
