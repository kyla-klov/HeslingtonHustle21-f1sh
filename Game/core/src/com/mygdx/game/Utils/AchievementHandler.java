package com.mygdx.game.Utils;
import java.util.ArrayList;
import java.util.List;

/*
Documentation on usage

The AchievementHandler can be accessed through game.achievementHandler.
To create a new achievement use game.achievementHandler.createAchievement
and pass in the name of the achievement along with a breath description.
This will return an Achievement object which you can then manipulate the
name, description and also set the achievement to gold or silver. For example
if you want to set an achievement to golf if x == 3 else silver:

    Achievement myAchievement = game.achievementHandler.createAchievement(x, y);
    if (x == 3){
        myAchievement.setGoldAchievement();
    } else {
        myAchievement.setSilverAchievement();
    }

The prepareAchievementsForDisplay can be used in the end screen to obtain an achievements name
and whether they got gold, silver or nothing. It returns a Mapping which you can loop over
 */



public class AchievementHandler {

    private final List<Achievement> achievements;
    public AchievementHandler(){
        achievements = new ArrayList<>();
        generateAchievements();
    }

    public Achievement getAchievement(String name, Achievement.Type achievementType){
        for (Achievement achievement : achievements){
            if (name.equals(achievement.getName()) && achievementType == achievement.getAchievmentType()){
                return achievement;
            }
        }
        return null;
    }

    public List<Achievement> getAchievements(){
        return achievements;
    }

    private void generateAchievements(){
        achievements.add(new Achievement("Baller", "Score at least 8 points in basketball", Achievement.Type.BRONZE, "BallerAchievementBronze.png"));
        achievements.add(new Achievement("Baller", "Score at least 12 points in basketball", Achievement.Type.SILVER, "BallerAchievementSilver.png"));
        achievements.add(new Achievement("Baller", "Score at least 15 points in basketball", Achievement.Type.GOLD, "BallerAchievementGold.png"));

        achievements.add(new Achievement("Duck Duck Go", "Feed all of the ducks in under 20 seconds", Achievement.Type.BRONZE, "DuckAchievementBronze.png"));
        achievements.add(new Achievement("Duck Duck Go", "Feed all of the ducks in under 16 seconds", Achievement.Type.SILVER, "DuckAchievementSilver.png"));
        achievements.add(new Achievement("Duck Duck Go", "Feed all of the ducks in under 12 seconds", Achievement.Type.GOLD, "DuckAchievementGold.png"));

        achievements.add(new Achievement("Hiker", "Walk at least 2500 steps", Achievement.Type.BRONZE, "AchievementPlaceHolder.png"));
        achievements.add(new Achievement("Hiker", "Walk at least 5000 steps", Achievement.Type.SILVER, "AchievementPlaceHolder.png"));
        achievements.add(new Achievement("Hiker", "Walk at least 10000 steps", Achievement.Type.GOLD, "AchievementPlaceHolder.png"));
    }
}
