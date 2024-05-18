package com.mygdx.game.tests;

import com.mygdx.game.Utils.GameClock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Consumer;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class GameClockTest {
    private final GameClock gameClock = new GameClock();
    private final Consumer<String> event = f -> {assert true;};

    @Before
    public void setup(){
        gameClock.addEvent(event, 0.5f);
    }

    @Test
    public void testUpdate(){
        gameClock.update(0.49f);
        assertEquals(0, gameClock.getMinutes(), 0);
        assertEquals(8, gameClock.getHours(), 0);
        assertEquals(1, gameClock.getDays(), 0);
        assertEquals(0.5f - 0.49f, gameClock.getEventTimers().get(0), 0);
        assertEquals(event, gameClock.getEventQueue().get(0));
        assertEquals(1, gameClock.getEventTimers().size(), 0);
        assertEquals(1, gameClock.getEventQueue().size(), 0);

        gameClock.update(0.1f);
        assertEquals(1, gameClock.getMinutes(), 0);
        assertEquals(8, gameClock.getHours(), 0);
        assertEquals(1, gameClock.getDays(), 0);
        assertEquals(0, gameClock.getEventTimers().size(), 0);
        assertEquals(0, gameClock.getEventQueue().size(), 0);

        gameClock.setMinutes(59);
        gameClock.update(0.6f);
        assertEquals(0, gameClock.getMinutes(), 0);
        assertEquals(9, gameClock.getHours(), 0);
        assertEquals(1, gameClock.getDays(), 0);

        gameClock.setHours(23);
        gameClock.setMinutes(59);
        gameClock.update(0.6f);
        assertEquals(0, gameClock.getMinutes(), 0);
        assertEquals(0, gameClock.getHours(), 0);
        assertEquals(2, gameClock.getDays(), 0);
    }

    @Test
    public void testAddEvents(){
        assertEquals(0.5f, gameClock.getEventTimers().get(0), 0);
        assertEquals(event, gameClock.getEventQueue().get(0));
        assertEquals(1, gameClock.getEventTimers().size(), 0);
        assertEquals(1, gameClock.getEventQueue().size(), 0);
    }

    @Test
    public void testGetTime(){
        assertEquals("Time: 08:00",gameClock.getTime());
        gameClock.setMinutes(11);
        assertEquals("Time: 08:11",gameClock.getTime());
        gameClock.setHours(11);
        assertEquals("Time: 11:11",gameClock.getTime());
        gameClock.setMinutes(9);
        assertEquals("Time: 11:09",gameClock.getTime());
    }
}
