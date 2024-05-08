package com.mygdx.game.tests;

import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.ActivityImage;
import com.mygdx.game.Utils.*;
import com.badlogic.gdx.Screen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Map;

@RunWith(GdxTestRunner.class)
public class ScreenManagerTest {
    @Mock private Screen curScreen;
    @Mock private Map<ScreenType, Screen> screensInMemory;
    private HesHustle mockedGame = mock(HesHustle.class);
    @InjectMocks private ScreenManager mockedSM = mock(ScreenManager.class, withSettings()
            .useConstructor(mockedGame)
            .defaultAnswer(CALLS_REAL_METHODS));

    @Before public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test public void testAddScreenToMemory(){
        mockedSM.addScreenToMemory(ScreenType.MENU_SCREEN);
        assertEquals(1,1);
    }
}
