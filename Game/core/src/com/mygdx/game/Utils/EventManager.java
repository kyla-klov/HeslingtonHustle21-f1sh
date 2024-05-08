package com.mygdx.game.Utils;

import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.ActivityImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager of the game.
 * Stores Time, Energy, Events etc.
 * Helps interaction between player and buildings
 */
public class EventManager {
    Event FeedDucks, Sleep, StudyCS, EatPiazza, PlayBBall;

    Event curEvent = null;

    Integer energy;
    boolean frozen = false;
    public List<Event> playedEvents;
    private final ResourceManager resourceManager;
    private final HesHustle game;
    private final GameClock gameClock;
    public EventManager(HesHustle game, GameClock gameClock) {
        this.game = game;
        this.gameClock = gameClock;
        resourceManager = new ResourceManager();
        playedEvents = new ArrayList<>();
        energy = 100;
        generateEvents();
    }

    private void generateEvents() {
        FeedDucks = new Event(1, 2, 10, -5, Event.Type.RECREATIONAL, 0, "", resourceManager.addDisposable(new ActivityImage("Activitys/lakemap.png")));
        StudyCS = new Event(3, -20, 20, -10, Event.Type.STUDY, 15, "", resourceManager.addDisposable(new ActivityImage("Activitys/cs.png")));
        PlayBBall = new Event(2, -30, 50, 10, Event.Type.RECREATIONAL, 25, "", ScreenType.BASKETBALL_SCREEN
        );
        Sleep = new Event(8, 90, 0, 0, Event.Type.SLEEP, 0, "", resourceManager.addDisposable(new ActivityImage("Activitys/langwith.png")));
        EatPiazza = new Event(1, 10, 0, 0, Event.Type.EAT, 0, "", resourceManager.addDisposable(new ActivityImage("Activitys/piazza.png")));
    }

    public Event getCurEvent() {
        return curEvent;
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
            frozen = true;
            if (curEvent.getActivityImage() != null) curEvent.getActivityImage().setActive();
            else game.screenManager.setScreen(curEvent.getScreenType());
            updateTime(curEvent);
            updateEnergy(curEvent);
        }
        gameClock.addEvent(s -> {
            if (curEvent != null && curEvent.getActivityImage() != null) {
                curEvent.getActivityImage().setInactive();
                curEvent = null;
            }
            frozen = false;
        }, 4f);
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

    public void updateTime(Event e) {
        if (e.getEventType() == Event.Type.SLEEP) {
            gameClock.setMinutes(0);
            gameClock.setHours(8);
            gameClock.setDays(gameClock.getDays() + 1);
        } else {
            gameClock.setHours(gameClock.getHours() + (int) Math.floor(e.getTimeCost()));
            if (gameClock.getHours() >= 24) {
                int day = (int) Math.floor(gameClock.getHours()/24);
                gameClock.setHours(gameClock.getHours() - (24 * day));
                gameClock.setDays(gameClock.getDays() + day);
            }
        }

    }

    public Integer getEnergy() {
        return energy;
    }

    public List<Event> getPlayedEvents() {
        return playedEvents;
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

    public boolean isFrozen() {
        return frozen;
    }

    public ArrayList<Event> listEvents(){
        ArrayList<Event> list = new ArrayList<Event>();
        list.add(FeedDucks);
        list.add(Sleep);
        list.add(StudyCS);
        list.add(EatPiazza);
        list.add(PlayBBall);
        return list;
    }
}