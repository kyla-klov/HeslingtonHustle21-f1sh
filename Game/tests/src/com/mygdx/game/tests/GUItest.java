package com.mygdx.game.tests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.ActivityImage;
import com.mygdx.game.Utils.*;
import com.badlogic.gdx.Gdx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.mygdx.game.Objects.GUI;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class GUItest {
    private AutoCloseable closeable;
    private final Batch mockedBatch = mock(Batch.class);

    private final HesHustle mockedGame = mock(HesHustle.class);
    private final GameClock mockedClock = spy(GameClock.class);

    private final ScreenManager mockedSM = mock(ScreenManager.class);
    private final Event mockedEvent = mock(Event.class);
    private final ScreenType mockedST = mock(ScreenType.class);

    private final ActivityImage mockedImage = mock(ActivityImage.class);
    private final ActivityImage mockedStudyImage = mock(ActivityImage.class, withSettings()
            .useConstructor("Activitys/cs.png")
            .defaultAnswer(CALLS_REAL_METHODS));

    @Spy private final Event FeedDucks = new Event(1, 2, 0, -5, Event.Type.RECREATIONAL, 0, "",ScreenType.DUCK_GAME_SCREEN);
    @Spy private final Event StudyCS = new Event(3, -20, 1, -10, Event.Type.STUDY, 15, "CSBuildingStudy", mockedStudyImage);
    @Spy private final Event PlayBBall = new Event(2, -30, 0, 10, Event.Type.RECREATIONAL, 25, "", ScreenType.BASKETBALL_SCREEN);
    @Spy private final Event Sleep = new Event(8, 90, 0, 0, Event.Type.SLEEP, 0, "", mockedImage);
    @Spy private final Event EatPiazza = new Event(1, 10, 0, 0, Event.Type.EAT, 0, "", mockedImage);

    @InjectMocks private EventManager mockedEM = mock(EventManager.class, withSettings()
            .useConstructor(mockedGame, mockedClock)
            .defaultAnswer(CALLS_REAL_METHODS));

    private final Skin skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

    @Spy private Table leftTab = new Table();
    @Spy private TextButton ScoreButt = new TextButton("Score",skin);
    @Spy private TextButton DayButt = new TextButton("Day",skin);
    @Spy private TextButton TimeButt = new TextButton("Time",skin);
    @Spy private ProgressBar ngrBar = new ProgressBar(0,100,2,false,skin);

    @Spy private Table rightTab = new Table();
    @Spy private TextButton RecButt  = new TextButton("RecNo:",skin);
    @Spy private TextButton SleepButt  = new TextButton("SlpNo:",skin);
    @Spy private TextButton EatButt  = new TextButton("EatNo:",skin);
    @Spy private TextButton StudyButt  = new TextButton("StdyNo:",skin);

    @InjectMocks private GUI mockedGUI = mock(GUI.class, withSettings().
            useConstructor(mockedBatch, mockedEM, mockedClock)
            .defaultAnswer(CALLS_REAL_METHODS));
    private int rec=1,slp=1,eat=1,stdy=2;
    @Before
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        mockedGame.screenManager = mockedSM;
        mockedEM.interact("Computer\nScience\nDepartment");
        mockedEM.interact("Piazza");
        mockedEM.interact("Ducks");
        mockedEM.interact("Langwith");
        mockedEM.interact("Computer\nScience\nDepartment");
    }
    @Test
    public void testUpdate(){
        mockedGUI.update((float) 1);
        verify(ngrBar, times(1)).setValue(mockedEM.getEnergy());
        verify(ScoreButt, times(1)).setText("Score: "+ mockedEM.calcScore());
        verify(DayButt, times(1)).setText("Day: " + mockedClock.getDays());
        verify(TimeButt, times(1)).setText(mockedClock.getTime());
        assertEquals(6, TimeButt.getScaleY(), 0);
        verify(mockedGUI, times(1)).countActivitys();
        verify(RecButt, times(1)).setText("RecNo: " + this.rec);
        verify(SleepButt, times(1)).setText("SlpNo: " + this.slp);
        verify(EatButt, times(1)).setText("EatNo: " + this.eat);
        verify(StudyButt, times(1)).setText("StdyNo: " + this.stdy);
    }
    @Test
    public void testCountActivitys(){
        int[] activitys = mockedGUI.countActivitys();
        assertEquals(4, activitys.length);
        assertEquals(this.rec, activitys[0]);
        assertEquals(this.slp, activitys[1]);
        assertEquals(this.eat, activitys[2]);
        assertEquals(this.stdy, activitys[3]);
    }

    public void testCreateDraws(){
        // test left table
        verify(leftTab, times(1)).add(ScoreButt);
        verify(leftTab, times(1)).add(DayButt);
        verify(leftTab, times(1)).add(TimeButt);
        verify(leftTab, times(1)).row();

        //test right table
        verify(rightTab, times(1)).add(RecButt);
        verify(rightTab, times(1)).add(SleepButt);
        verify(rightTab, times(1)).add(EatButt);
        verify(rightTab, times(1)).add(StudyButt);

    }
    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}

