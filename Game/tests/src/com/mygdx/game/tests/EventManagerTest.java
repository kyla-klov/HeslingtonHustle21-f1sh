package com.mygdx.game.tests;

import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.ActivityImage;
import com.mygdx.game.Utils.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(GdxTestRunner.class)
public class EventManagerTest {
    public EventManager eventManager = new EventManager(new HesHustle(), new GameClock());
    HesHustle mockedGame = mock(HesHustle.class);
    GameClock mockedClock = spy(GameClock.class);

    ResourceManager mockedRM = spy(ResourceManager.class);
    ScreenManager mockedSM = mock(ScreenManager.class);
    Event mockedEvent = mock(Event.class);
    ScreenType mockedST = mock(ScreenType.class);

    ActivityImage mockedImage = mock(ActivityImage.class);
    ActivityImage mockedStudyImage = mock(ActivityImage.class, withSettings()
            .useConstructor("Activitys/cs.png")
            .defaultAnswer(CALLS_REAL_METHODS));

    @Spy Event PlayBBall = new Event(2, -30, 50, 10, Event.Type.RECREATIONAL, 25, "", ScreenType.BASKETBALL_SCREEN);
    @Spy Event StudyCS = new Event(3, -20, 20, -10, Event.Type.STUDY, 15, "", mockedStudyImage);
    @Spy Event EatPiazza = new Event(1, 10, 0, 0, Event.Type.EAT, 0, "", mockedImage);
    @Spy Event FeedDucks = new Event(1, 2, 10, -5, Event.Type.RECREATIONAL, 0, "", mockedImage);
    @Spy Event Sleep = new Event(8, 90, 0, 0, Event.Type.SLEEP, 0, "", mockedImage);

    @InjectMocks  EventManager mockedEM = mock(EventManager.class, withSettings()
            .useConstructor(mockedGame, mockedClock)
            .defaultAnswer(CALLS_REAL_METHODS));

    @Before public void setup(){
        MockitoAnnotations.initMocks(this);
        mockedGame.screenManager = mockedSM;
    }

    @Test
    // Tests that all the Event objects are correctly created.
    public void testGenerateEvents() {
        assertNotNull(eventManager.FeedDucks);
        assertNotNull(eventManager.StudyCS);
        assertNotNull(eventManager.PlayBBall);
        assertNotNull(eventManager.Sleep);
        assertNotNull(eventManager.EatPiazza);
    }

    @Test
    // tests that interacting with something correctly edits the energy value, time, playedEvents and set correct Image/Screen
    public void testInteract() {
        //check interact with non-existent event
        assertThrows(AssertionError.class, () -> mockedEM.interact(""));
        assertEquals(100, mockedEM.getEnergy(), 0);
        assertNull(mockedEM.getCurEvent());
        assertTrue(mockedEM.getPlayedEvents().isEmpty());
        verify(mockedEM, times(0)).updateEnergy(mockedEvent);
        verify(mockedEM, times(0)).updateTime(mockedEvent);

        // checks that recreation decreases energy by 30 and takes 2 hours
        mockedEM.interact("BasketBall");
//        assertEquals(70, mockedEM.getEnergy(), 0);
        assertEquals(1, mockedClock.getDays(), 0);
        assertEquals(10, mockedClock.getHours(), 0);
        assertEquals(0, mockedClock.getMinutes(), 0);
        assertEquals(PlayBBall, mockedEM.getPlayedEvents().get(0));
        assertEquals(PlayBBall, mockedEM.getCurEvent());
        verify(mockedSM, times(1)).setScreen(PlayBBall.getScreenType());
        verify(mockedEvent, times(0)).getActivityImage();
//        verify(mockedClock, times(1)).addEvent(s -> {}, 4f);;

        // checks that study decrease energy by 20 and takes 3 hours
        mockedEM.interact("Computer\nScience\nDepartment");
        assertEquals(50, mockedEM.getEnergy(), 0);
        assertEquals(1, mockedClock.getDays(), 0);
        assertEquals(13, mockedClock.getHours(), 0);
        assertEquals(0, mockedClock.getMinutes(), 0);
        assertEquals(StudyCS, mockedEM.getCurEvent());
        assertEquals(StudyCS, mockedEM.getPlayedEvents().get(1));
        verify(mockedSM, times(1)).setScreen(PlayBBall.getScreenType());
        verify(mockedSM, times(0)).setScreen(mockedST);
        verify(mockedStudyImage, times(1)).setActive();
//        verify(mockedClock, times(1)).addEvent(s -> {}, 4f);;

        // checks that sleeping will not increase energy above 100
        mockedEM.interact("Langwith");
        assertEquals(100, mockedEM.getEnergy(), 0);


        // sleeping adds 50 energy

/*        mockedEM.interact("Langwith");
        assertNotNull(mockedEM.getCurEvent());
        assertEquals((double) 100, (double) mockedEM.getEnergy(), (double) 0.0);*/
/*        // checks that sleeping will not increase energy above 100
        eventManager.getEnergy() = 70;
        eventManager.interact("Langwith");
        assertNotNull(eventManager.getCurEvent());
        assertEquals((double) 100, (double) eventManager.getEnergy(), (double) 0.0);


        eventManager.getEnergy() = 50;
        eventManager.interact("BasketBall");
        assertNotNull(eventManager.getCurEvent());
        assertEquals((double) 20, (double) eventManager.getEnergy(), (double) 0.0);

        // checks that recreation will not decrease energy if there is not enough energy available
        eventManager.getEnergy() = 10;
        eventManager.interact("BasketBall");
        assertNotNull(eventManager.getCurEvent());
        assertEquals((double) 10, (double) eventManager.getEnergy(), (double) 0.0)*/;

    }


    @Test
    public void testGetScore() {
        // this one needs fixing because im not sure what the expected score is actually supposed to be
        eventManager.interact("Ducks");
        eventManager.interact("Computer\nScience\nDepartment");
        eventManager.interact("Langwith");
        int expectedScore = 77;
        assertEquals(expectedScore, eventManager.getScore(), 0);
    }

//    @Test
//    public void testUpdate() {
//        // tests the case where TRaw + deltaTime is less than 0.5f
//        eventManager.TRaw = 0.1f;
//        eventManager.update(0.1f);
//        assertEquals(0.2f, eventManager.TRaw, 0.01f);
//        assertEquals(0L, eventManager.TSec, 0.0);
//        assertEquals(8L, eventManager.TMin, 0.0);
//        assertEquals(1L, eventManager.day, 0.0);
//
//        // tests the boundary case where TRaw + deltaTime is 0.5f
//        eventManager.TRaw = 0.2f;
//        eventManager.update(0.3f);
//        assertEquals(0.0f, eventManager.TRaw, 0.01f);
//        assertEquals(1L, eventManager.TSec, 0.0);
//        assertEquals(8L, eventManager.TMin, 0.0);
//        assertEquals(1L, eventManager.day, 0.0);
//
//        // tests the case where TRaw + deltaTime is greater than 0.5f
//        eventManager.TRaw = 0.4f;
//        eventManager.update(0.3f);
//        assertEquals(0.0f, eventManager.TRaw, 0.01f);
//        assertEquals(2L, eventManager.TSec, 0.0);
//        assertEquals(8L, eventManager.TMin, 0.0);
//        assertEquals(1L, eventManager.day, 0.0);
//
//    }
}
