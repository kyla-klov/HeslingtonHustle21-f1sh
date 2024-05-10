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
 */



public class AchievementHandler {

    private List<Achievement> achievements;
    public AchievementHandler(){
        achievements = new ArrayList<>();
    }

    public Achievement createAchievement(String name, String description){
        Achievement newAchievement = new Achievement(name, description);
        achievements.add(newAchievement);
        return newAchievement;
    }

    public List<Achievement> getAchievements(){
        return achievements;
    }

}
