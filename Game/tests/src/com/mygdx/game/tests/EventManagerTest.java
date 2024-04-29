package com.mygdx.game.tests;

import org.junit.Test;

import static org.junit.Assert.*;

import com.mygdx.game.Objects.EventManager;
import org.junit.runner.RunWith;

import java.util.Optional;

@RunWith(GdxTestRunner.class)
public class EventManagerTest {
    public EventManager eventManager = new EventManager();

    @Test
    // Tests that all the Event objects are correctly created.
    public void testGenerateEvents() {
        eventManager.generateEvents();
        assertNotNull(eventManager.FeedDucks);
        assertNotNull(eventManager.StudyCS);
        assertNotNull(eventManager.PlayBBall);
        assertNotNull(eventManager.Sleep);
        assertNotNull(eventManager.EatPiazza);
    }

    @Test
    public void testInteract() {
        eventManager.energy = 50;
        eventManager.interact("Langwith");
        assertNotNull(eventManager.curEvent);
        assertEquals((double) 100, (double) eventManager.energy, (double) 0.0);
    }


    @Test
    public void testUpdate() {
        // tests the case where TRaw + deltaTime is less than 0.5f
        eventManager.TRaw = 0.1f;
        eventManager.update(0.1f);
        assertEquals(0.2f, eventManager.TRaw, 0.01f);
        assertEquals(0L, eventManager.TSec, 0.0);
        assertEquals(8L, eventManager.TMin, 0.0);
        assertEquals(1L, eventManager.day, 0.0);

        // tests the boundary case where TRaw + deltaTime is 0.5f
        eventManager.TRaw = 0.2f;
        eventManager.update(0.3f);
        assertEquals(0.0f, eventManager.TRaw, 0.01f);
        assertEquals(1L, eventManager.TSec, 0.0);
        assertEquals(8L, eventManager.TMin, 0.0);
        assertEquals(1L, eventManager.day, 0.0);

        // tests the case where TRaw + deltaTime is greater than 0.5f
        eventManager.TRaw = 0.4f;
        eventManager.update(0.3f);
        assertEquals(0.0f, eventManager.TRaw, 0.01f);
        assertEquals(2L, eventManager.TSec, 0.0);
        assertEquals(8L, eventManager.TMin, 0.0);
        assertEquals(1L, eventManager.day, 0.0);

    }

    @Test
    public void testGetScore() {
        // Test code for score calculation
    }
}
