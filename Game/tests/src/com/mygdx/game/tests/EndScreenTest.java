package com.mygdx.game.tests;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Screens.EndScreen;
import org.junit.Test;

import static org.junit.Assert.*;

import com.mygdx.game.Screens.EndScreen;
import org.junit.runner.RunWith;

import java.util.Optional;

@RunWith(GdxTestRunner.class)
public class EndScreenTest {
    // creates a mock instance of the end screen
    public EndScreen endScreen = new EndScreen(null); // Passing null for game since it's not used in this test

    // creates instances of each button
    TextButton playAgainButton = endScreen.getPlayAgainButton();
    TextButton mainMenuButton = endScreen.getMainMenuButton();
    TextButton exitButton = endScreen.getExitButton();

    @Test
    // tests that all buttons have been properly created.
    public void testGenerateEvents() {
        assertNotNull("Play Again button event listener is not set", playAgainButton.getListeners().first());
        assertNotNull("Main Menu button event listener is not set", mainMenuButton.getListeners().first());
        assertNotNull("Exit button event listener is not set", exitButton.getListeners().first());
    }
}

