package com.mygdx.game.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The GameClock class manages the in-game time and events that are triggered based on time.
 * It maintains the current time in hours, minutes, and days, and allows for scheduling events.
 */
public class GameClock {
    private float cappedTime;
    private float rawTime;
    private int minutes;
    private int hours = 8;
    private int days = 1;
    private final List<Consumer<String>> eventQueue;
    private final List<Float> eventTimers;

    /**
     * Constructs a GameClock with initial time set to 8:00 on day 1.
     * Initializes the event queue and timers.
     */
    public GameClock() {
        eventQueue = new ArrayList<>();
        eventTimers = new ArrayList<>();
    }

    /**
     * Updates the game clock and executes scheduled events.
     *
     * @param deltaTime the time elapsed since the last update
     */
    public void update(float deltaTime) {
        if (deltaTime < 0){
            return;
        }
        updateTime(deltaTime);
        executeEvents(deltaTime);
    }

    /**
     * Updates the in-game time.
     *
     * @param deltaTime the time elapsed since the last update
     */
    private void updateTime(float deltaTime){
        cappedTime += deltaTime;
        rawTime += deltaTime;
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

    /**
     * Executes scheduled events based on the elapsed time.
     *
     * @param deltaTime the time elapsed since the last update
     */
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

    public float getRawTime(){
        return rawTime;
    }

    /**
     * Returns the formatted current time as a string in the format "Time: HH:MM".
     *
     * @return the formatted current time
     */
    public String getTime(){
        String hrs;
        String mins;
        if (hours < 10){hrs = 0 + Integer.toString(hours);}
        else {hrs = Integer.toString(hours);}
        if (minutes < 10){mins = 0 + Integer.toString(minutes);}
        else {mins = Integer.toString(minutes);}
        return "Time: " + hrs + ":" + mins;
    }

    /**
     * Adds an event to be executed after a specified time.
     *
     * @param event the event to add
     * @param timer the time after which the event should be executed
     */
    public void addEvent(Consumer<String> event, float timer) {
        eventQueue.add(event);
        eventTimers.add(timer);
    }

    /**
     * Returns the event queue.
     *
     * @return the event queue
     */
    public List<Consumer<String>> getEventQueue() {
        return eventQueue;
    }

    /**
     * Returns the event timers.
     *
     * @return the event timers
     */
    public List<Float> getEventTimers() {
        return eventTimers;
    }
}
