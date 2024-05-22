package com.mygdx.game.tests;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Objects.LeaderBoard;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class LeaderBoardTest {
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport vp = new FitViewport(160,1200, camera);
    private final LeaderBoard leaderBoard = new LeaderBoard(vp, 800 - 301/2f, 450 - 377/2f, 301, 377);

    @Test
    public void testRender(){

    }
}
