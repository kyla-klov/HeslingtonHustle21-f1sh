package com.mygdx.game.Utils;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

/**
 * The AchievementHandler class manages a collection of achievements.
 * It provides methods to retrieve achievements and handles the disposal of resources.
 */
public class AchievementHandler implements Disposable {

    private final List<Achievement> achievements;

    /**
     * Constructs an AchievementHandler and initializes the list of achievements.
     */
    public AchievementHandler(){
        achievements = new ArrayList<>();
        generateAchievements();
    }

    /**
     * Retrieves an achievement based on its name and type.
     *
     * @param name the name of the achievement
     * @param achievementType the type of the achievement
     * @return the achievement with the specified name and type, or null if not found
     */
    public Achievement getAchievement(String name, Achievement.Type achievementType){
        for (Achievement achievement : achievements){
            if (name.equals(achievement.getName()) && achievementType == achievement.getAchievmentType()){
                return achievement;
            }
        }
        return null;
    }

    /**
     * Returns the list of achievements.
     *
     * @return the list of achievements
     */
    public List<Achievement> getAchievements(){
        return achievements;
    }

    /**
     * Generates the list of achievements and adds them to the collection.
     */
    private void generateAchievements(){
        achievements.add(new Achievement("Baller", "Score at least 8 points in basketball", Achievement.Type.BRONZE, "AchievementsDisplay/BallerAchievementBronze.png"));
        achievements.add(new Achievement("Baller", "Score at least 10 points in basketball", Achievement.Type.SILVER, "AchievementsDisplay/BallerAchievementSilver.png"));
        achievements.add(new Achievement("Baller", "Score at least 12 points in basketball", Achievement.Type.GOLD, "AchievementsDisplay/BallerAchievementGold.png"));

        achievements.add(new Achievement("Duck Duck Go", "Feed all of the ducks in under 17 seconds", Achievement.Type.BRONZE, "AchievementsDisplay/DuckAchievementBronze.png"));
        achievements.add(new Achievement("Duck Duck Go", "Feed all of the ducks in under 16 seconds", Achievement.Type.SILVER, "AchievementsDisplay/DuckAchievementSilver.png"));
        achievements.add(new Achievement("Duck Duck Go", "Feed all of the ducks in under 15 seconds", Achievement.Type.GOLD, "AchievementsDisplay/DuckAchievementGold.png"));

        achievements.add(new Achievement("Feast to Fullest", "Eat 3 times in a day", Achievement.Type.BRONZE, "AchievementsDisplay/FTFBronze.png"));
        achievements.add(new Achievement("Feast to Fullest", "Eat 4 times in a day", Achievement.Type.SILVER, "AchievementsDisplay/FTFSilver.png"));
        achievements.add(new Achievement("Feast to Fullest", "Eat 5 times in a day", Achievement.Type.GOLD, "AchievementsDisplay/FTFGold.png"));

        achievements.add(new Achievement("Hiker", "Walk at least 2500 steps", Achievement.Type.BRONZE, "AchievementsDisplay/HikerBronze.png"));
        achievements.add(new Achievement("Hiker", "Walk at least 5000 steps", Achievement.Type.SILVER, "AchievementsDisplay/HikerSilver.png"));
        achievements.add(new Achievement("Hiker", "Walk at least 10000 steps", Achievement.Type.GOLD, "AchievementsDisplay/HikerGold.png"));
    }

    @Override
    public void dispose() {
        for (Achievement achievement : achievements){
            achievement.dispose();
        }
    }
}
