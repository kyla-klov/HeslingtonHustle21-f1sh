package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Objects.LeaderBoard;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class LeaderBoardTest {
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport vp = new FitViewport(1299,523, camera);
    private final BitmapFont mockedFont = mock(BitmapFont.class);
    private final LeaderBoard leaderBoard = new LeaderBoard(vp, mockedFont, 800 - 301/2f, 450 - 377/2f, 301, 377);

    @BeforeClass
    public static void checkPlayerDataExist(){
        assertTrue("PlayerData.txt can't be found in test environment", Gdx.files.local("storage/PlayerData.txt").exists());
    }

    @Test
    public void testReadPlayerData(){
        assertEquals(2, leaderBoard.getData().size());
        assertEquals("Tech", leaderBoard.getData().get(0).getPlayer());
        assertEquals(99f, leaderBoard.getData().get(0).getScore(), 0);
        assertEquals(2, leaderBoard.getData().get(0).getStatus(), 0);
        assertEquals("Hemlock", leaderBoard.getData().get(1).getPlayer());
        assertEquals(29.6f, leaderBoard.getData().get(1).getScore(), 0);
        assertEquals(1, leaderBoard.getData().get(1).getStatus(), 0);
    }

    @Test
    public void testRender() {
        ArgumentCaptor<Texture> argumentCaptor = ArgumentCaptor.forClass(Texture.class);
        List<String> expectedArgs = List.of(
                "LeaderBoard.png",
                "up.png",
                "down.png"
        );
        SpriteBatch mockedBatch = mock(SpriteBatch.class);
        InOrder inOrder = inOrder(mockedBatch, mockedFont);
        leaderBoard.render(mockedBatch);
        inOrder.verify(mockedBatch).draw(argumentCaptor.capture(), eq(0f), eq(0f), eq(301f), eq(377f));
        inOrder.verify(mockedBatch).draw(argumentCaptor.capture(), eq(250f), eq(300f), eq(32f), eq(32f));
        inOrder.verify(mockedBatch).draw(argumentCaptor.capture(), eq(250f), eq(32f), eq(32f), eq(32f));
        inOrder.verify(mockedFont).draw(mockedBatch, "Leader board", 60f, 350f);
        inOrder.verify(mockedFont).draw(mockedBatch, "1. Tech", 50f, 300f);
        inOrder.verify(mockedFont).draw(mockedBatch, "99.0", 170f, 300f);
        inOrder.verify(mockedFont).draw(mockedBatch, "2. Hemlo", 50f, 250f);
        inOrder.verify(mockedFont).draw(mockedBatch, " 29.6", 170f, 250f);
    }
    @Test
    public void testTouchDown(){
//        leaderBoard.touchDown(0, 0);
//        assertEquals(0, leaderBoard.getPage(), 0);
//        vp.setWorldHeight(1);
//        vp.setWorldHeight(1);
//        leaderBoard.touchDown(200, 200);
//        assertEquals(0, leaderBoard.getPage(), 0);
    }

}
