package com.mygdx.game.tests;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.HesHustle;
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
    private final String textureFile = "Activitys/cs.png";
    private final Camera mockedCamera = spy(Camera.class);
    private final ShapeRenderer mockedShape = mock(ShapeRenderer.class);
    private final SpriteBatch mockedBatch = mock(SpriteBatch.class);
    private final HesHustle mockedGame = mock(HesHustle.class);
    private ActivityImage mockedImage = mock(ActivityImage.class, withSettings()
            .useConstructor(textureFile)
            .defaultAnswer(CALLS_REAL_METHODS));
    @Before
    public void setup(){
        mockedGame.batch = mockedBatch;
        mockedCamera.viewportHeight = 1f;
    }
    @Test
    public void testRender(){
        mockedImage.render(mockedCamera, mockedGame, mockedShape);
        verifyNoInteractions(mockedShape);
        verifyNoInteractions(mockedBatch);

        ArgumentCaptor<TextureRegion> arg = ArgumentCaptor.forClass(TextureRegion.class);
        InOrder inOrder = inOrder(mockedShape);
        mockedImage.setActive();
        mockedImage.render(mockedCamera, mockedGame, mockedShape);
        inOrder.verify(mockedShape,times(1)).setProjectionMatrix(mockedCamera.combined);
        inOrder.verify(mockedShape,times(1)).begin(ShapeRenderer.ShapeType.Filled);
        inOrder.verify(mockedShape,times(1)).setColor(new Color(0,0,0.2f,0.7f));
        inOrder.verify(mockedShape,times(1)).rect((mockedCamera.position.x - mockedCamera.viewportWidth/2), (mockedCamera.position.y - 1f/2), mockedCamera.viewportWidth, 1f);
        inOrder.verify(mockedShape,times(1)).setColor(Color.BLACK);
        inOrder.verify(mockedShape,times(1)).rect((mockedCamera.position.x - 0.9f/2), (mockedCamera.position.y - 0.9f/2), 0.9f, 0.9f);
        inOrder.verify(mockedShape,times(1)).end();
        verify(mockedBatch,times(1)).begin();
        verify(mockedBatch,times(1)).setProjectionMatrix(mockedCamera.combined);
        verify(mockedBatch,times(1)).draw(arg.capture() , eq(mockedCamera.position.x - (mockedCamera.viewportHeight*0.85f/2)), eq(mockedCamera.position.y - mockedCamera.viewportHeight*0.85f/2),eq(0f),eq(0f),eq(0.85f),eq(0.85f),eq(1f),eq(1f),eq(0f));
        assertEquals(textureFile, ((FileTextureData) arg.getValue().getTexture().getTextureData()).getFileHandle().path());
        verify(mockedBatch,times(1)).end();

    }
}
