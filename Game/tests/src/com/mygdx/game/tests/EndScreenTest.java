package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Screens.EndScreen;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

@RunWith(GdxTestRunner.class)
public class EndScreenTest {
    private AutoCloseable closeable;

    // creates skins for buttons
    Skin mockedSkin = mock(Skin.class, withSettings()
        .useConstructor(Gdx.files.internal("Craftacular_UI_Skin/craftacular-ui.json"))
        .defaultAnswer(CALLS_REAL_METHODS));

    // creates instances of each button
    TextButton mockedPlayAgainButton = mock(TextButton.class, withSettings()
            .useConstructor("Play Again", mockedSkin)
            .defaultAnswer(CALLS_REAL_METHODS));
    TextButton mockedMainMenuButton = mock(TextButton.class, withSettings()
            .useConstructor("Main Menu", mockedSkin)
            .defaultAnswer(CALLS_REAL_METHODS));
    TextButton mockedExitButton = mock(TextButton.class, withSettings()
            .useConstructor("Exit", mockedSkin)
            .defaultAnswer(CALLS_REAL_METHODS));

    // creates dependencies for mockedStage
    private final HesHustle mockedGame = mock(HesHustle.class);
    private final Matrix4 mockedMatrix4 = spy(Matrix4.class);
    private final Color mockedColor = spy(Color.class);
    private final Batch mockedBatch = mock(Batch.class);
    private final Camera mockedCamera = spy(Camera.class);
    private final ScreenViewport mockedSV = mock(ScreenViewport.class, withSettings()
            .useConstructor(mockedCamera)
            .defaultAnswer(CALLS_REAL_METHODS));

    // creates stage instance
    private final Stage mockedStage = mock(Stage.class, withSettings()
            .useConstructor(mockedSV, mockedBatch)
            .defaultAnswer(CALLS_REAL_METHODS));

    // creates a mock instance of the end screen
    private final EndScreen mockedEndScreen = mock(EndScreen.class, withSettings()
            .useConstructor(mockedGame, mockedStage,
                    mockedPlayAgainButton, mockedMainMenuButton,
                    mockedExitButton)
            .defaultAnswer(CALLS_REAL_METHODS));

    @Before
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        when(mockedBatch.getTransformMatrix()).thenReturn(mockedMatrix4);
        when(mockedBatch.getColor()).thenReturn(mockedColor);
        mockedBatch.setTransformMatrix(mockedMatrix4);
    }

    @Test
    public void testRender() {
//        doReturn(1).when(mockedEndScreen).render(1.1).;
        mockedEndScreen.render(2);
        verify(mockedStage, times(1)).act(1/30f);
        verify(mockedStage, times(1)).draw();
    }

    @Test
    // tests that all buttons have been properly created with the correct text, skin and
    // placed in the correct location.
    public void textSetUpUI(){
        assertEquals(1, mockedStage.getActors().size, 0);
        Table table = (Table) mockedStage.getActors().get(0);
        assertEquals(3, table.getCells().size);

        TextButton playAgainButton = (TextButton) ((Table) table).getCells().get(0).getActor();
        assertNotNull(playAgainButton);
        assertEquals(mockedPlayAgainButton, playAgainButton);
        assertEquals(20, playAgainButton.getPadY(), 0);
        verify(mockedPlayAgainButton).addListener(any(EventListener.class));

        TextButton mainMenuButton = (TextButton) ((Table) table).getCells().get(1).getActor();
        assertNotNull(mainMenuButton);
        assertEquals(mockedMainMenuButton, mainMenuButton);
        assertEquals(20, mainMenuButton.getPadY(), 0);
        verify(mockedMainMenuButton).addListener(any(EventListener.class));

        TextButton exitButton = (TextButton) ((Table) table).getCells().get(2).getActor();
        assertNotNull(exitButton);
        assertEquals(mockedExitButton, exitButton);
        assertEquals(20, exitButton.getPadY(), 0);
        verify(mockedExitButton).addListener(any(EventListener.class));
    }

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}

