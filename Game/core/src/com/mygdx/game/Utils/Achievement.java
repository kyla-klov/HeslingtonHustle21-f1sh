package com.mygdx.game.Utils;

public class Achievement {
    private String description;
    private String name;
    private boolean achievedGold = false;
    private boolean achievedSilver = false;
    private boolean achievedBronze = false;

    public Achievement(String Name, String Description){
        this.description = Description;
        this.name = Name;
    }

    // If we have achieved silver then we switch to achieving gold (vice versa for setSilverAchievement)
    public void setGoldAchievement(){
        achievedGold = true;
        achievedSilver = false;
        achievedBronze = false;
    }

    public void setSilverAchievement(){
        achievedSilver = true;
        achievedGold = false;
        achievedBronze = false;
    }

    public void setBronzeAchievement(){
        achievedBronze = true;
        achievedGold = false;
        achievedSilver = false;
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

    public boolean isBronzeAchieved(){
        return achievedBronze;
    }
}
