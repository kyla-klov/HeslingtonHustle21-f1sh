package com.mygdx.game.tests;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Objects.NameTextField;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(GdxTestRunner.class)
public class NameTextFieldTest {
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport vp = new FitViewport(0,0, camera);
    private final BitmapFont mockedFont = mock(BitmapFont.class);
    private final Texture mockedTexture = mock(Texture.class);
    private final NameTextField nameTextField = new NameTextField(vp, mockedFont, mockedTexture, true);

    @Test
    public void testRender(){
        SpriteBatch mockedBatch = mock(SpriteBatch.class);
        NameTextField inactiveNameTextField = new NameTextField(vp, mockedFont, mockedTexture, false);
        float x = 631f;
        float y = 371.5f;

        //verify no interaction with ViewportAdapter when inactive
        inactiveNameTextField.render(mockedBatch);
        verify(mockedBatch, times(0)).draw(any(Texture.class), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockedFont, times(0)).draw(any(SpriteBatch.class), anyString(), anyFloat(), anyFloat());

        //verify interactions with ViewportAdapter when active
        nameTextField.render(mockedBatch);
        verify(mockedBatch).draw(mockedTexture, x, y, 338, 157);
        verify(mockedFont).draw(mockedBatch, "", x+70, y+68);
        nameTextField.render(mockedBatch);
//        assertEquals("T", nameTextField.getValue());
        assertFalse(nameTextField.textEntered());
    }
}
