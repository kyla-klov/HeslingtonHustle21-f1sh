package com.mygdx.game.tests;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Screens.EndScreen;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.mygdx.game.Screens.EndScreen;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

@RunWith(GdxTestRunner.class)
public class EndScreenTest {
    private final HesHustle mockedGame = mock(HesHustle.class);
    private final Table mockedTable = mock(Table.class);
    private final ScreenViewport mockedSV = spy(ScreenViewport.class);
    private final Stage mockedStage = mock(Stage.class);
    @Mock private Stage stage;
    @Mock private Skin skin;
    @Mock private TextButton playAgainButton;
    @Mock private TextButton mainMenuButton;
    @Mock private TextButton exitButton;

    // creates a mock instance of the end screen
    @InjectMocks private EndScreen mockedEndScreen = mock(EndScreen.class
            );

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    // creates instances of each button

    @Test
    // tests that all buttons have been properly created.
    public void testSetupUI() {
//        doReturn(1).when(mockedEndScreen).render(1.1).;
        mockedEndScreen.render(2);
        verify(mockedStage).act(2);
        verify(stage).draw();
    }
}

