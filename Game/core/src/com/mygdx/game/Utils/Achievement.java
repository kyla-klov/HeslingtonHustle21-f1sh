package com.mygdx.game.Utils;

public class Achievement {
    private String description;
    private String name;
    private boolean achievedGold = false;
    private boolean achievedSilver = false;

    public Achievement(String Name, String Description){
        this.description = Description;
        this.name = Name;
    }

    // If we have achieved silver then we switch to achieving gold (vice versa for setSilverAchievement)
    public void setGoldAchievement(){
        achievedGold = true;
        if (achievedSilver){
            achievedSilver = false;
        }
    }

    public void setSilverAchievement(){
        achievedSilver = true;
        if (achievedGold){
            achievedGold = false;
        }
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public boolean isGoldAchieved(){
        return achievedGold;
    }

    public boolean isSilverAchieved(){
        return achievedSilver;
    }
}
