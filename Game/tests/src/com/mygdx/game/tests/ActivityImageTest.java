package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.mygdx.game.Objects.ActivityImage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class ActivityImageTest {
    private final String csTextureFile = "Activitys/cs.png";
    private final String blackSquareFile = "BlankSquare.png";
    private final Camera mockedCamera = spy(Camera.class);
    private final SpriteBatch mockedBatch = mock(SpriteBatch.class);
    private final ActivityImage mockedImage = mock(ActivityImage.class, withSettings()
            .useConstructor(csTextureFile)
            .defaultAnswer(CALLS_REAL_METHODS));
    @Before
    public void setup(){
        mockedCamera.viewportHeight = 1f;
    }
    @Test
    public void testRender(){
        mockedImage.render(mockedCamera,mockedBatch);
        verifyNoInteractions(mockedBatch);
        ArgumentCaptor<Texture> textureArgumentCaptor = ArgumentCaptor.forClass(Texture.class);
        ArgumentCaptor<TextureRegion> textureRegionArgumentCaptor = ArgumentCaptor.forClass(TextureRegion.class);
        InOrder inOrder = inOrder(mockedBatch, Gdx.gl);
        mockedImage.setActive();
        mockedImage.render(mockedCamera, mockedBatch);

        inOrder.verify(Gdx.gl).glEnable(GL20.GL_BLEND);
        inOrder.verify(Gdx.gl).glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        inOrder.verify(mockedBatch,times(1)).setColor(new Color(0,0,0.2f,0.7f));
        inOrder.verify(mockedBatch,times(1)).draw(
                textureArgumentCaptor.capture(), eq(0f), eq(-0.5f),
                eq(0f), eq(1f)
        );
        assertEquals(blackSquareFile, ((FileTextureData) textureArgumentCaptor.getValue().getTextureData()).getFileHandle().path());

        inOrder.verify(mockedBatch,times(1)).setColor(Color.BLACK);
        inOrder.verify(mockedBatch,times(1)).draw(
                textureArgumentCaptor.capture(), eq(-0.9f/2), eq(-0.9f/2),
                eq(0.9f), eq(0.9f)
        );
        assertEquals(blackSquareFile, ((FileTextureData) textureArgumentCaptor.getAllValues().get(1).getTextureData()).getFileHandle().path());

        inOrder.verify(mockedBatch,times(1)).setColor(1,1,1,1);
        inOrder.verify(mockedBatch,times(1)).draw(textureRegionArgumentCaptor.capture() , eq(mockedCamera.position.x - (mockedCamera.viewportHeight*0.85f/2)), eq(mockedCamera.position.y - mockedCamera.viewportHeight*0.85f/2),eq(0f),eq(0f),eq(0.85f),eq(0.85f),eq(1f),eq(1f),eq(0f));
        assertEquals(csTextureFile, ((FileTextureData) textureRegionArgumentCaptor.getValue().getTexture().getTextureData()).getFileHandle().path());

    }
}
