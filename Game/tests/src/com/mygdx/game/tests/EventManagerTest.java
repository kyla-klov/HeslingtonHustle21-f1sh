package com.mygdx.game.tests;

import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.ActivityImage;
import com.mygdx.game.Utils.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(GdxTestRunner.class)
public class EventManagerTest {
    private AutoCloseable closeable;
    private final HesHustle mockedGame = mock(HesHustle.class);
    private final GameClock mockedClock = spy(GameClock.class);

    private final ScreenManager mockedSM = mock(ScreenManager.class);
    private final Event mockedEvent = mock(Event.class);
    private final ScreenType mockedST = mock(ScreenType.class);

    private final ActivityImage mockedImage = mock(ActivityImage.class);


    @Spy private final Event FeedDucks = new Event(1, 2, 0, -5, Event.Type.RECREATIONAL, 0, "",ScreenType.DUCK_GAME_SCREEN);
    @Spy private final Event StudyCS = new Event(3, -20, 1, -10, Event.Type.STUDY, 15, "CSBuildingStudy", ScreenType.CHECKIN_CODE_SCREEN);
    @Spy private final Event PlayBBall = new Event(2, -30, 0, 10, Event.Type.RECREATIONAL, 25, "", ScreenType.BASKETBALL_SCREEN);
    @Spy private final Event Sleep = new Event(8, 90, 0, 0, Event.Type.SLEEP, 0, "", mockedImage);
    @Spy private final Event EatPiazza = new Event(1, 10, 0, 0, Event.Type.EAT, 0, "", ScreenType.COOKIE_SCREEN);

    @InjectMocks private EventManager mockedEM = mock(EventManager.class, withSettings()
            .useConstructor(mockedGame, mockedClock)
            .defaultAnswer(CALLS_REAL_METHODS));


    @Before public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        when(mockedGame.getScreenManager()).thenReturn(mockedSM);
    }

/*    @Test
    // Tests that all the Event objects are correctly created.
    public void testGenerateEvents() {
        assertNotNull(eventManager.FeedDucks);
        assertNotNull(eventManager.StudyCS);
        assertNotNull(eventManager.PlayBBall);
        assertNotNull(eventManager.Sleep);
        assertNotNull(eventManager.EatPiazza);
    }*/

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
        verify(mockedSM, times(1)).setScreen(PlayBBall.getScreenType(), mockedEM);
        verify(PlayBBall, times(1)).getActivityImage();
//        verify(mockedClock, times(1)).addEvent(s -> {}, 4f);;

        // checks that study decrease energy by 20 and takes 3 hours
        mockedEM.interact("Computer\nScience\nDepartment");
        assertEquals(50, mockedEM.getEnergy(), 0);
        assertEquals(1, mockedClock.getDays(), 0);
        assertEquals(13, mockedClock.getHours(), 0);
        assertEquals(0, mockedClock.getMinutes(), 0);
        assertEquals(StudyCS, mockedEM.getCurEvent());
        assertEquals(StudyCS, mockedEM.getPlayedEvents().get(1));
        verify(StudyCS,times(1)).getActivityImage();
        verify(mockedSM, times(1)).setScreen(StudyCS.getScreenType(), mockedEM);
        verify(mockedSM, times(0)).setScreen(mockedST);
//        verify(mockedClock, times(1)).addEvent(s -> {}, 4f);

    }

    @Test
    public void testUpdateEnergy(){
        // Basic deduction check
        mockedEM.updateEnergy(StudyCS);
        assertEquals(80, mockedEM.getEnergy(), 0);

        // checks that recreation will not decrease energy if there is not enough energy available
        mockedEM.updateEnergy(PlayBBall);
        mockedEM.updateEnergy(PlayBBall);
        mockedEM.updateEnergy(PlayBBall);
        assertEquals(0, mockedEM.getEnergy(), 0);

        // sleeping adds 90 energy
        mockedEM.updateEnergy(Sleep);
        assertEquals(90, mockedEM.getEnergy(), 0);

        // checks that sleeping will not increase energy above 100
        mockedEM.updateEnergy(Sleep);
        assertEquals(100, mockedEM.getEnergy(), 0);

    }

    @Test
    public void testUpdateTime(){
        Event bigtime1 = new Event(24, 2, 10, -5, Event.Type.RECREATIONAL, 0, "", mockedImage);
        mockedEM.updateTime(bigtime1);
        assertEquals(2, mockedClock.getDays(),0);
        assertEquals(8, mockedClock.getHours(), 0);

        Event bigtime2 = new Event(49, 2, 10, -5, Event.Type.RECREATIONAL, 0, "", mockedImage);
        mockedEM.updateTime(bigtime2);
        assertEquals(4, mockedClock.getDays(),0);
        assertEquals(9, mockedClock.getHours(), 0);
        mockedEM.updateTime(Sleep);
    }
    @Test
    public void testMaxCalcScore1() {
        for (int day = 1; day <= 7; day++) {
            mockedEM.interact("Ducks");
            mockedEM.interact("Computer\nScience\nDepartment");
            mockedEM.addStudyHours(4); //Studies for 4 hours
            mockedEM.interact("Piazza");
            mockedEM.interact("Piazza");
            mockedEM.interact("Piazza");
            mockedEM.interact("Langwith");
        }
        int expectedScore = 100;
        assertEquals(expectedScore, mockedEM.calcScore(), 0);
    }

    @Test
    public void testMaxCalcScore2() {
        for (int day = 1; day <= 5; day++) {
            mockedEM.interact("Ducks");
            mockedEM.interact("Computer\nScience\nDepartment");
            mockedEM.addStudyHours(4); //Studies for 4 hours
            mockedEM.interact("Piazza");
            mockedEM.interact("Piazza");
            mockedEM.interact("Piazza");
            mockedEM.interact("Langwith");
        }

        mockedEM.interact("Ducks");
        mockedEM.interact("Piazza");
        mockedEM.interact("Piazza");
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");

        mockedEM.interact("Ducks");
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(8); //Studies for 4 hours
        mockedEM.interact("Piazza");
        mockedEM.interact("Piazza");
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");
        int expectedScore = 100;
        assertEquals(expectedScore, mockedEM.calcScore(), 0);
    }

    @Test
    public void testDoNothingScore(){
        float expectedScore = 0;
        assertEquals(expectedScore, mockedEM.calcScore(), 0);
    }

    @Test
    public void testNoStudyScore() {
        for (int day = 1; day <= 7; day++) {
            mockedEM.interact("Ducks");
            mockedEM.interact("Piazza");
            mockedEM.interact("Piazza");
            mockedEM.interact("Piazza");
            mockedEM.interact("Langwith");
        }
        int expectedScore = 50;
        assertEquals(expectedScore, mockedEM.calcScore(), 0);
    }

    @Test
    public void testNoEatScore() {
        for (int day = 1; day <= 7; day++) {
            mockedEM.interact("Ducks");
            mockedEM.interact("Computer\nScience\nDepartment");
            mockedEM.addStudyHours(4); //Studies for 4 hours
            mockedEM.interact("Langwith");
        }
        int expectedScore = 75;
        assertEquals(expectedScore, mockedEM.calcScore(), 0);
    }

    @Test
    public void testStudyOnceScore() {
        for (int day = 1; day <= 6; day++) {
            mockedEM.interact("Ducks");
            mockedEM.interact("Piazza");
            mockedEM.interact("Piazza");
            mockedEM.interact("Piazza");
            mockedEM.interact("Langwith");
        }
        mockedEM.interact("Ducks");
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(4); //Studies for 4 hours
        mockedEM.interact("Piazza");
        mockedEM.interact("Piazza");
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");
        float expectedScore = 63.5f;
        assertEquals(expectedScore, mockedEM.calcScore(), 0);
    }


    @Test
    public void testRandomCalcScore1() {
        mockedEM.interact("Ducks");
        mockedEM.interact("Ducks");
        mockedEM.interact("Ducks");
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");

        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(2);
        mockedEM.interact("Piazza");
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");

        mockedEM.interact("BasketBall");
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(8);
        mockedEM.interact("Langwith");

        mockedEM.interact("Ducks");
        mockedEM.interact("Ducks");
        mockedEM.interact("Piazza");
        mockedEM.interact("Ducks");
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");

        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(2);
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");

        mockedEM.interact("BasketBall");
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(8);
        mockedEM.interact("Langwith");

        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(2);
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");

        float expectedScore = 44.5f;
        assertEquals(expectedScore, mockedEM.calcScore(), 0);
    }

    @Test
    public void testRandomCalcScore2() {
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");

        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(2);
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");

        mockedEM.interact("BasketBall");
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(8);
        mockedEM.interact("Langwith");

        mockedEM.interact("Ducks");
        mockedEM.interact("Ducks");
        mockedEM.interact("Piazza");
        mockedEM.interact("Ducks");
        mockedEM.interact("Piazza");
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");

        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(2);
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");

        mockedEM.interact("BasketBall");
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(8);
        mockedEM.interact("Langwith");

        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.addStudyHours(2);
        mockedEM.interact("Piazza");
        mockedEM.interact("Langwith");

        float expectedScore = 54.5f;
        assertEquals(expectedScore, mockedEM.calcScore(), 0);
    }


    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}
