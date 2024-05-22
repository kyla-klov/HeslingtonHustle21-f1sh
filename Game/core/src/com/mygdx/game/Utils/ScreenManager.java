package com.mygdx.game.Utils;

import com.badlogic.gdx.Screen;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Screens.*;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    private final Map<ScreenType, Screen> screensInMemory;
    private final HesHustle game;
    private Screen curScreen;
    private ScreenType curScreenType;

    public ScreenManager(HesHustle game) {
        this.game = game;
        this.screensInMemory = new HashMap<>();
    }

    public void addScreenToMemory(ScreenType screenType) {
        if (screenType == curScreenType) {
            screensInMemory.put(screenType, curScreen);
        }
        else{
            screensInMemory.put(screenType, createScreen(screenType));
        }
    }

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
            default:
                return null;
        }
    }
}
