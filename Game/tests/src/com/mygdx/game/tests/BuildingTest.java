package com.mygdx.game.tests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Objects.Building;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class BuildingTest {
    private final SpriteBatch mockedBatch = mock(SpriteBatch.class);
    private final Texture mockedTexture = mock(Texture.class);
    private final Building comSci = new Building(530, 380,"Computer\nScience\nDepartment", mockedTexture);
    @Test
    public void testRender(){
        comSci.render(mockedBatch);
        verify(mockedBatch).draw(mockedTexture, 530, 380, 64, 64);

    }

}
