package com.mygdx.game.tests;

import com.mygdx.game.Objects.ActivityImage;
import com.mygdx.game.Utils.Event;
import com.mygdx.game.Utils.ScreenType;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;

public class EventTest {
    @Test
    public void testConstructor(){
        ActivityImage mockedActivityImage = mock(ActivityImage.class);
        Event event1 = new Event(1, 2, 10, -5, Event.Type.RECREATIONAL, 0, "", mockedActivityImage);
        assertEquals(1, event1.getTimeCost(), 0);
        assertEquals(2, event1.getEnergyCost(), 0);
        assertEquals(10, event1.getStudyTime(), 0);
        assertEquals(-5, event1.getFatigue(), 0);
        assertEquals(Event.Type.RECREATIONAL, event1.getEventType());
        assertEquals(0, event1.getMoneyCost());
        assertEquals("", event1.getDescription());
        assertEquals(mockedActivityImage, event1.getActivityImage());
        assertNull(event1.getScreenType());

        ScreenType mockedST = mock(ScreenType.class);
        Event event2 = new Event(2, -30, 50, 10, Event.Type.RECREATIONAL, 25, "", mockedST);
        assertEquals(2, event2.getTimeCost(), 0);
        assertEquals(-30, event2.getEnergyCost(), 0);
        assertEquals(50, event2.getStudyTime(), 0);
        assertEquals(10, event2.getFatigue(), 0);
        assertEquals(Event.Type.RECREATIONAL, event2.getEventType());
        assertEquals(25, event2.getMoneyCost());
        assertEquals("", event2.getDescription());
        assertNull(event2.getActivityImage());
        assertEquals(mockedST, event2.getScreenType());
    }
}
