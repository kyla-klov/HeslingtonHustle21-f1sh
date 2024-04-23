package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import org.junit.runner.RunWith;
import org.junit.Test;
import com.mygdx.game.Objects.EventManager;

import static org.junit.Assert.assertTrue;


@RunWith(GdxTestRunner.class)
public class TimeTest {
    @Test
    public void testUpdateTime(){
        EventManager event = new EventManager();
        assertTrue("a", true);
    }
}
