package com.mygdx.game.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameClock {
    private float cappedTime;
    private int minutes;
    private int hours = 8;
    private int days = 1;
    private final List<Consumer<String>> eventQueue;
    private final List<Float> eventTimers;

    public GameClock() {
        eventQueue = new ArrayList<>();
        eventTimers = new ArrayList<>();
    }

    public void update(float deltaTime) {
        updateTime(deltaTime);
        executeEvents(deltaTime);
    }

    private void updateTime(float deltaTime){
        cappedTime += deltaTime;
        if (cappedTime >= 0.5f) {
            minutes++;
            cappedTime = 0f;
        }
        if (minutes >= 60) {
            minutes = 0;
            hours++;
        }
        if (hours >= 24) {
            hours -= 24;
            days++;
        }
    }

    private void executeEvents(float deltaTime) {
        for (int i = 0; i < eventTimers.size(); i++) {
            eventTimers.set(i, eventTimers.get(i) - deltaTime);
            if (eventTimers.get(i) <= 0) {
                eventQueue.get(i).accept(null);
                eventQueue.set(i, null);
                eventTimers.set(i, null);
            }
        }

        while (eventTimers.contains(null)){
            eventTimers.remove(null);
            eventQueue.remove(null);
        }
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours(){
        return hours;
    }

    public int getDays(){
        return days;
    }

    public void setMinutes(int minutes){
        this.minutes = minutes;
    }

    public void setHours(int hours){
        this.hours = hours;
    }

    public void setDays(int days){
        this.days = days;
    }

    public String getTime(){
        return String.format("Time: %02d:%02d", hours, minutes);
    }

    public void addEvent(Consumer<String> event, float timer) {
        eventQueue.add(event);
        eventTimers.add(timer);
    }
}