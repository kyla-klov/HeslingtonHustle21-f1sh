package com.mygdx.game.Utils;

import com.mygdx.game.Objects.ActivityImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager of the game.
 * Stores Time, Energy, Events etc.
 * Helps interaction between player and buildings
 */
public class EventManager {
    public Event FeedDucks, Sleep, StudyCS, EatPiazza, PlayBBall;
    public Event curEvent = null;
    public Float TRaw, TWait;
    public Integer TSec, TMin, energy, day;
    public boolean frozen = false;
    public List<Event> playedEvents;
    private final ResourceManager resourceManager;

    public EventManager() {
        resourceManager = new ResourceManager();
        playedEvents = new ArrayList<>();
        TRaw = 0.0f;
        TSec = 0;
        TMin = 8;
        TWait = 0f;
        energy = 100;
        day = 1;
        generateEvents();
    }

    public void generateEvents() {
        FeedDucks = new Event(1, 2, 10, -5, Event.Type.RECREATIONAL, 0, "", resourceManager.addDisposable(new ActivityImage("Activitys/lakemap.png")));
        StudyCS = new Event(3, -20, 20, -10, Event.Type.STUDY, 15, "", resourceManager.addDisposable(new ActivityImage("Activitys/cs.png")));
        PlayBBall = new Event(2, -30, 50, 10, Event.Type.RECREATIONAL, 25, "", resourceManager.addDisposable(new ActivityImage("Activitys/basketballcourt.png")));
        Sleep = new Event(8, 90, 0, 0, Event.Type.SLEEP, 0, "", resourceManager.addDisposable(new ActivityImage("Activitys/langwith.png")));
        EatPiazza = new Event(1, 10, 0, 0, Event.Type.EAT, 0, "", resourceManager.addDisposable(new ActivityImage("Activitys/piazza.png")));
    }

    public void interact(String name) {

        switch (name) {
            default:
                curEvent = null;
                break;
            case "Piazza":
                curEvent = EatPiazza;
                break;
            case "Computer\nScience\nDepartment":
                curEvent = StudyCS;
                break;
            case "Langwith":
                curEvent = Sleep;
                break;
            case "Ducks":
                curEvent = FeedDucks;
                break;
            case "BasketBall":
                curEvent = PlayBBall;
                break;
        }
        assert curEvent != null;
        if (-curEvent.getEnergyCost() < energy) {
            playedEvents.add(curEvent);
            TWait = 8f;
            frozen = true;
            curEvent.getActivityImage().setActive();
            updateTime(curEvent);
            updateEnergy(curEvent);
        }
    }

    public void update(float deltaTime) {
        TRaw += deltaTime;
        TWait -= deltaTime;
        if (TWait < 0) {
            if (curEvent != null) {
                curEvent.getActivityImage().setInactive();
                curEvent = null;
            }
            frozen = false;
        }
        if (TRaw >= 0.5f) {
            TSec++;
            TRaw = 0f;
        }
        if (TSec >= 60) {
            TSec = 0;
            TMin++;
        }
        if (TMin > 23) {
            TMin -= 24;
            day++;
        }

    }

    public String getTime() {
        String z1 = "", z2 = "";
        if (TMin < 10) {
            z1 = "0";
        }
        if (TSec < 10) {
            z2 = "0";
        }

        return "Time: " + z1 + TMin + ":" + z2 + TSec;
    }

    public void updateTime(Event e) {
        if (e.getEventType() == Event.Type.SLEEP) {
            TSec = 0;
            TMin = 8;
            day++;
        } else {
            TMin += (int) Math.floor(e.getTimeCost());
            if (TMin > 23) {
                TMin -= 24;
                day++;
            }
        }

    }

    public void updateEnergy(Event e) {
        energy += e.getEnergyCost();
        if (energy < 0) {
            energy = 0;
            //passout
        } else if (energy > 100) {
            energy = 100;
        }

    }

    public int getScore() {
        int score = 0;
        int cumulativeEat = 1;
        int cumulativeSleep = 1;
        int studyCount = 0;
        int studyTotal = 0;
        int recCount = 0;
        int recTotal = 0;
        double studyDebuff = 1;
        double recDebuff = 1;
        //int fatigue = 0;
        for (Event event : playedEvents) {
            switch (event.getEventType()) {
                case EAT:
                    score += cumulativeEat;
                    cumulativeEat += cumulativeEat;
                    break;
                case SLEEP:
                    score += cumulativeSleep;
                    cumulativeSleep += cumulativeSleep;
                    break;
                case RECREATIONAL:
                    score += event.getEnjoymentStudyLevel();
                    recCount += 1;
                    score += event.getFatigue();
                    break;
                case STUDY:
                    studyTotal += event.getEnjoymentStudyLevel();
                    studyCount += 1;
                    score += event.getFatigue();
                    break;
                default:
                    score += 1;
                    break;

            }
            studyDebuff = 0.15 * (-(studyCount * studyCount) + (28 * studyCount));
            recDebuff = (double) 140 / (((recCount - 10) * (recCount - 10)) + 5);
        }

        score += (int) Math.round(studyDebuff * studyTotal);
        score += (int) Math.round(recDebuff * recTotal);


        return score;
    }
}