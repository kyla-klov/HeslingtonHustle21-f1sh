package com.mygdx.game.tests;

import com.mygdx.game.Utils.Achievement;
import com.mygdx.game.Utils.AchievementHandler;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class AchievementHandlerTest {
    Achievement bronzeBaller = new Achievement("Baller", "Score at least 8 points in basketball", Achievement.Type.BRONZE, "AchievementsDisplay/BallerAchievementBronze.png");
    Achievement silverBaller = new Achievement("Baller", "Score at least 10 points in basketball", Achievement.Type.SILVER, "AchievementsDisplay/BallerAchievementSilver.png");
    Achievement goldBaller = new Achievement("Baller", "Score at least 12 points in basketball", Achievement.Type.GOLD, "AchievementsDisplay/BallerAchievementGold.png");
    Achievement bronzeDDG = new Achievement("Duck Duck Go", "Feed all of the ducks in under 17 seconds", Achievement.Type.BRONZE, "AchievementsDisplay/DuckAchievementBronze.png");
    Achievement silverDDG = new Achievement("Duck Duck Go", "Feed all of the ducks in under 16 seconds", Achievement.Type.SILVER, "AchievementsDisplay/DuckAchievementSilver.png");
    Achievement goldDDG = new Achievement("Duck Duck Go", "Feed all of the ducks in under 15 seconds", Achievement.Type.GOLD, "AchievementsDisplay/DuckAchievementGold.png");
    Achievement bronzeFTF = new Achievement("Feast to Fullest", "Eat 3 times in a day", Achievement.Type.BRONZE, "AchievementsDisplay/AchievementPlaceHolder.png");
    Achievement silverFTF = new Achievement("Feast to Fullest", "Eat 4 times in a day", Achievement.Type.SILVER, "AchievementsDisplay/AchievementPlaceHolder.png");
    Achievement goldFTF = new Achievement("Feast to Fullest", "Eat 5 times in a day", Achievement.Type.GOLD, "AchievementsDisplay/AchievementPlaceHolder.png");
    Achievement bronzeHiker = new Achievement("Hiker", "Walk at least 2500 steps", Achievement.Type.BRONZE, "AchievementsDisplay/AchievementPlaceHolder.png");
    Achievement silverHiker = new Achievement("Hiker", "Walk at least 5000 steps", Achievement.Type.SILVER,"AchievementsDisplay/AchievementPlaceHolder.png");
    Achievement goldHiker = new Achievement("Hiker", "Walk at least 10000 steps", Achievement.Type.GOLD, "AchievementsDisplay/AchievementPlaceHolder.png");
    private final AchievementHandler achievementHandler = new AchievementHandler();

    @Test
    public void testGenerateAchievements(){
        assertEquals(bronzeBaller, achievementHandler.getAchievement("Baller", Achievement.Type.BRONZE));
        assertEquals(silverBaller, achievementHandler.getAchievement("Baller", Achievement.Type.SILVER));
        assertEquals(goldBaller, achievementHandler.getAchievement("Baller", Achievement.Type.GOLD));
        assertEquals(bronzeDDG, achievementHandler.getAchievement("Duck Duck Go", Achievement.Type.BRONZE));
        assertEquals(silverDDG, achievementHandler.getAchievement("Duck Duck Go", Achievement.Type.SILVER));
        assertEquals(goldDDG, achievementHandler.getAchievement("Duck Duck Go", Achievement.Type.GOLD));
        assertEquals(bronzeFTF, achievementHandler.getAchievement("Feast to Fullest", Achievement.Type.BRONZE));
        assertEquals(silverFTF, achievementHandler.getAchievement("Feast to Fullest", Achievement.Type.SILVER));
        assertEquals(goldFTF, achievementHandler.getAchievement("Feast to Fullest", Achievement.Type.GOLD));
        assertEquals(bronzeHiker, achievementHandler.getAchievement("Hiker", Achievement.Type.BRONZE));
        assertEquals(silverHiker, achievementHandler.getAchievement("Hiker", Achievement.Type.SILVER));
        assertEquals(goldHiker, achievementHandler.getAchievement("Hiker", Achievement.Type.GOLD));
    }

    //test getting achievement that does not exist
    @Test
    public void testGetAchievements(){
        assertNull(achievementHandler.getAchievement("Bad Batch", null));
    }

}
