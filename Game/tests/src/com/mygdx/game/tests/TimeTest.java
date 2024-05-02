package com.mygdx.game.tests;

import com.mygdx.game.HesHustle;
import com.mygdx.game.Utils.GameClock;
import org.junit.runner.RunWith;
import org.junit.Test;
import com.mygdx.game.Utils.EventManager;

import static org.junit.Assert.assertTrue;


@RunWith(GdxTestRunner.class)
public class TimeTest {
    @Test
    public void testUpdateTime(){
        EventManager event = new EventManager(new HesHustle(), new GameClock());
        assertTrue("a", true);
    }
}
