package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.Building;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class BuildingTest {
    private final Building comSci = new Building(530, 380, 100, 100, "Computer\nScience\nDepartment");
    private final Camera mockedCamera = spy(Camera.class);
    private final ShapeRenderer mockedShape = mock(ShapeRenderer.class);
    private final SpriteBatch mockedBatch = mock(SpriteBatch.class);
    private final HesHustle mockedGame = mock(HesHustle.class);

    @Before
    public void setup(){
        mockedGame.batch = mockedBatch;
        mockedGame.font = mock(BitmapFont.class, withSettings()
                .useConstructor(Gdx.files.internal("font.fnt"))
                .defaultAnswer(CALLS_REAL_METHODS));
    }

    @Test
    public void testRender(){
        comSci.render(mockedCamera, mockedGame,mockedShape);
        InOrder inOrder = inOrder(mockedShape, mockedBatch, mockedGame.font);
        inOrder.verify(mockedShape, times(1)).setProjectionMatrix(mockedCamera.combined);
        inOrder.verify(mockedShape).begin(ShapeRenderer.ShapeType.Filled);
        inOrder.verify(mockedShape).setColor(Color.RED);
        inOrder.verify(mockedShape).rect(comSci.pos.x, comSci.pos.y, 100, 100);
        inOrder.verify(mockedShape).end();
        inOrder.verify(mockedBatch).begin();
        inOrder.verify(mockedGame.font).draw(mockedBatch, comSci.name,
                comSci.pos.x+3, comSci.pos.y + comSci.bounds.height -3);
        inOrder.verify(mockedBatch).end();

    }

}
