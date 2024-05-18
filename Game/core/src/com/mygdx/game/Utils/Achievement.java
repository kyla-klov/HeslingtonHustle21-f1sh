package com.mygdx.game.Utils;

import java.util.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Achievement {
    public enum Type{
        BRONZE, SILVER, GOLD
    }

    private final String description;
    private final String name;
    private final Type achievementType;
    private Texture achievementTexture;

    private boolean unlocked;

    public Achievement(String name, String description, Type achievementType, String imgPath){
        this.description = description;
        this.name = name;
        this.achievementType = achievementType;
        this.achievementTexture = new Texture(imgPath);
        unlocked = false;
    }

    public Type getAchievmentType(){
        return achievementType;
    }

    public String getName(){
        return name;
    }

    @SuppressWarnings("unused")
    public String getDescription(){
        return description;
    }

    public void unlock(){
        unlocked = true;
    }

    @SuppressWarnings("unused")
    public boolean isUnlocked(){
        return unlocked;
    }

    public Texture getAchievementTexture(){
        return achievementTexture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achievement)) return false;
        Achievement that = (Achievement) o;
        return isUnlocked() == that.isUnlocked() && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getName(), that.getName()) && achievementType == that.achievementType;
    }

}
