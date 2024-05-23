package com.mygdx.game.Utils;

import com.badlogic.gdx.Screen;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Screens.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The ScreenManager class is responsible for managing different screens in the game.
 * It provides methods to add, remove, and switch between screens.
 */
public class ScreenManager {
    private final Map<ScreenType, Screen> screensInMemory;
    private final HesHustle game;
    private Screen curScreen;
    private ScreenType curScreenType;


    /**
     * Constructs a ScreenManager with the specified game instance.
     *
     * @param game the game instance
     */
    public ScreenManager(HesHustle game) {
        this.game = game;
        this.screensInMemory = new HashMap<>();
    }

    /**
     * Adds a screen to the memory if it is not already present.
     *
     * @param screenType the type of screen to add
     */
    public void addScreenToMemory(ScreenType screenType) {
        if (screenType == curScreenType) {
            screensInMemory.put(screenType, curScreen);
        }
        else{
            screensInMemory.put(screenType, createScreen(screenType));
        }
    }

    /**
     * Removes a screen from the memory.
     *
     * @param screenType the type of screen to remove
     */
    public void removeScreenFromMemory(ScreenType screenType) {
        screensInMemory.remove(screenType);
    }

    /**
     * Sets the current screen to the specified screen type.
     *
     * @param screenType the type of screen to set
     * @param args       additional arguments needed to create the screen
     */
    public void setScreen(ScreenType screenType, Object... args){
        if (curScreen != null && !screensInMemory.containsKey(curScreenType)) {
            curScreen.dispose();
        }
        curScreenType = screenType;
        if ((curScreen = screensInMemory.get(screenType)) == null){
            curScreen = createScreen(screenType, args);
        }
        game.setScreen(curScreen);
    }

    /**
     * Creates a new screen based on the specified screen type and additional arguments.
     *
     * @param screenType the type of screen to create
     * @param args       additional arguments needed to create the screen
     * @return the created screen
     */
    private Screen createScreen(ScreenType screenType, Object... args){
        switch (screenType){
            case SLEEP_SCREEN:
                    return new SleepGameScreen(game);
            case COOKIE_SCREEN:
                return new CookieCatcherScreen(game);
            case MENU_SCREEN:
                return new MenuScreen(game);
            case GAME_SCREEN:
                return new GameScreen(game);
            case PAUSE_SCREEN:
                return new PauseScreen(game);
            case END_SCREEN:
                return new EndScreen(game, (float) args[0]);
            case BASKETBALL_SCREEN:
                return new BasketBallScreen(game);
            case DUCK_GAME_SCREEN:
                return new DuckGameScreen(game);
            case LEADERBOARD_SCREEN:
                return new LeaderBoardScreen(game);
            case CHECKIN_CODE_SCREEN:
                return new CheckinGameScreen(game, (EventManager) args[0]);
            case CONTROLS_SCREEN:
                return new ControlsScreen(game);
            case SETTINGS_SCREEN:
                return new SettingsScreen(game);
            default:
                return null;
        }
    }
}
