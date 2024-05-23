package com.mygdx.game.Utils;

import java.util.Objects;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;


/**
 * The Achievement class represents an achievement in the game.
 * Each achievement has a name, description, type, texture, and an unlocked status.
 */
public class Achievement implements Disposable {
    /**
     * Enum representing the type of the achievement (BRONZE, SILVER, GOLD).
     */
    public enum Type{
        BRONZE, SILVER, GOLD
    }

    private final String description;
    private final String name;
    private final Type achievementType;
    private final Texture achievementTexture;

    private boolean unlocked;


    /**
     * Constructs an Achievement with the specified name, description, type, and image path.
     *
     * @param name            the name of the achievement
     * @param description     the description of the achievement
     * @param achievementType the type of the achievement (BRONZE, SILVER, GOLD)
     * @param imgPath         the path to the image representing the achievement
     */
    public Achievement(String name, String description, Type achievementType, String imgPath){
        this.description = description;
        this.name = name;
        this.achievementType = achievementType;
        this.achievementTexture = new Texture(imgPath);
        unlocked = false;
    }


    /**
     * Returns the type of the achievement.
     *
     * @return the type of the achievement
     */
    public Type getAchievmentType(){
        return achievementType;
    }

    /**
     * Returns the name of the achievement.
     *
     * @return the name of the achievement
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the description of the achievement.
     *
     * @return the description of the achievement
     */
    public String getDescription(){
        return description;
    }

    /**
     * Unlocks the achievement.
     */
    public void unlock(){
        unlocked = true;
    }

    /**
     * Returns whether the achievement is unlocked.
     *
     * @return true if the achievement is unlocked, false otherwise
     */
    public boolean isUnlocked(){
        return unlocked;
    }

    /**
     * Returns the texture of the achievement.
     *
     * @return the texture of the achievement
     */
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

    @Override
    public void dispose(){
        achievementTexture.dispose();
    }

}
