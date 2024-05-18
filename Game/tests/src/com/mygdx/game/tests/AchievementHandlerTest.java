package com.mygdx.game.tests;

import com.mygdx.game.Utils.Achievement;
import com.mygdx.game.Utils.AchievementHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class AchievementHandlerTest {
    private final AchievementHandler achievementHandler = new AchievementHandler();
    Achievement bronzeBaller = new Achievement("Baller", "Score at least 8 points in basketball", Achievement.Type.BRONZE);
    Achievement silverBaller = new Achievement("Baller", "Score at least 12 points in basketball", Achievement.Type.SILVER);
    Achievement goldBaller = new Achievement("Baller", "Score at least 15 points in basketball", Achievement.Type.GOLD);
    Achievement bronzeDDG = new Achievement("Duck Duck Go", "Feed all of the ducks in under 20 seconds", Achievement.Type.BRONZE);
    Achievement silverDDG = new Achievement("Duck Duck Go", "Feed all of the ducks in under 16 seconds", Achievement.Type.SILVER);
    Achievement goldDDG = new Achievement("Duck Duck Go", "Feed all of the ducks in under 12 seconds", Achievement.Type.GOLD);
    Achievement bronzeHiker = new Achievement("Hiker", "Walk at least 2500 steps", Achievement.Type.BRONZE);
    Achievement silverHiker = new Achievement("Hiker", "Walk at least 5000 steps", Achievement.Type.SILVER);
    Achievement goldHiker = new Achievement("Hiker", "Walk at least 10000 steps", Achievement.Type.GOLD);

    @Test
    public void testGenerateAchievements(){
        assertEquals(bronzeBaller, achievementHandler.getAchievement("Baller", Achievement.Type.BRONZE));
        assertEquals(silverBaller, achievementHandler.getAchievement("Baller", Achievement.Type.SILVER));
        assertEquals(goldBaller, achievementHandler.getAchievement("Baller", Achievement.Type.GOLD));
        assertEquals(bronzeDDG, achievementHandler.getAchievement("Duck Duck Go", Achievement.Type.BRONZE));
        assertEquals(silverDDG, achievementHandler.getAchievement("Duck Duck Go", Achievement.Type.SILVER));
        assertEquals(goldDDG, achievementHandler.getAchievement("Duck Duck Go", Achievement.Type.GOLD));
        assertEquals(bronzeHiker, achievementHandler.getAchievement("Hiker", Achievement.Type.BRONZE));
        assertEquals(silverHiker, achievementHandler.getAchievement("Hiker", Achievement.Type.SILVER));
        assertEquals(goldHiker, achievementHandler.getAchievement("Hiker", Achievement.Type.GOLD));
    }
}
