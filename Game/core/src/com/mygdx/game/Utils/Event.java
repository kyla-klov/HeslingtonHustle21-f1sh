package com.mygdx.game.Utils;

import com.mygdx.game.Objects.ActivityImage;

public class Event {
    private final double timeCost;
    private final int energyCost;
    private final int studyTime;
    private final int fatigue;
    private final int moneyCost;
    private final String description;
    private final ActivityImage activityImage;
    private final ScreenType screenType;
    public enum Type {
        EAT,
        SLEEP,
        STUDY,
        RECREATIONAL
    }
    private final Type eventType;

    /**
     * generates a new instance of an event, representing the possible activities a player can undertake
     *
     * @param studyTime      The relative value for how good an activity is for enjoyment / study as an integer
     * @param description              A description of the event
     * @param energy                   The amoount of energy an event requires
     * @param time                     The time required for an event as a double
     * @param eventType                the type of event (sleep, eaty, study, recreational)
     * @param fatigue                  The amount of fatigue a player has after an event
     */

    public Event(double time,int energy, int studyTime, int fatigue, Type eventType, int moneyCost, String description, ActivityImage activityImage) {
        this.timeCost = time;
        this.energyCost = energy;
        this.studyTime = studyTime;
        this.fatigue = fatigue;
        this.eventType = eventType;
        this.moneyCost = moneyCost;
        this.description = description;
        this.activityImage = activityImage;
        this.screenType = null;
    }

    public Event(double time,int energy, int studyTime, int fatigue, Type eventType, int moneyCost, String description, ScreenType screenType) {
        this.timeCost = time;
        this.energyCost = energy;
        this.studyTime = studyTime;
        this.fatigue = fatigue;
        this.eventType = eventType;
        this.moneyCost = moneyCost;
        this.description = description;
        this.activityImage = null;
        this.screenType = screenType;
    }

    public ActivityImage getActivityImage() { return activityImage; }

    public ScreenType getScreenType() { return screenType; }

    public int getEnergyCost() {
        return energyCost;
    }

    public double getTimeCost() {
        return timeCost;
    }

    public int getStudyTime() {
        return studyTime;
    }

    public int getFatigue() {
        return fatigue;
    }

    public Type getEventType() {
        return eventType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null
                || this.getClass() != obj.getClass())
            return false;
        Event e = (Event)obj;
        return this.timeCost == e.getTimeCost()
                && this.energyCost == e.getEnergyCost()
                && this.studyTime == e.getStudyTime()
                && this.fatigue == e.getFatigue()
                && this.eventType == e.getEventType()
                && this.moneyCost == e.getMoneyCost()
                && this.description.equals(e.getDescription())
                && this.activityImage == e.getActivityImage()
                && this.screenType == e.getScreenType();
    }
    @SuppressWarnings("unused")
    public int getMoneyCost() {
        return moneyCost;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }
}

