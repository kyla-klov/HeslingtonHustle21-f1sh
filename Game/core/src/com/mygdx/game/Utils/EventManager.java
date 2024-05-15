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
    List<Event> playedEvents;
    private final ResourceManager resourceManager;
    private final HesHustle game;
    private final GameClock gameClock;

    //Game Score Data//
    private final int[] dailyStudy;
    private final int[] dailyRecreational;
    private final List<List<Integer>> mealTimes;
    private final List<String> placesStudied;
    private int totalStudyHours;

    public EventManager(HesHustle game, GameClock gameClock) {
        this.game = game;
        this.gameClock = gameClock;
        resourceManager = new ResourceManager();
        playedEvents = new ArrayList<>();
        energy = 100;
        dailyStudy = new int[7];
        dailyRecreational = new int[7];
        mealTimes = new ArrayList<>();
        placesStudied = new ArrayList<>();
        totalStudyHours = 0;
        for (int i = 0; i < 7; i++) {
            mealTimes.add(new ArrayList<>());
        }
        generateEvents();
    }

    private void generateEvents() {
        FeedDucks = new Event(1, 2, 0, -5, Event.Type.RECREATIONAL, 0, "", ScreenType.DUCK_GAME_SCREEN);
        StudyCS = new Event(3, -20, 1, -10, Event.Type.STUDY, 15, "CSBuildingStudy", resourceManager.addDisposable(new ActivityImage("Activitys/cs.png")));
        PlayBBall = new Event(2, -30, 0, 10, Event.Type.RECREATIONAL, 25, "", ScreenType.BASKETBALL_SCREEN);
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

            this.totalStudyHours += curEvent.getStudyTime();
            if (curEvent.getStudyTime() > 0){
                this.dailyStudy[gameClock.getDays()-1]++;
                totalStudyHours += curEvent.getStudyTime();
                addStudyPlace(curEvent.getDescription());
            } else if (curEvent.getEventType() == Event.Type.EAT){
                addMeal(gameClock.getHours());
            } else if (curEvent.getEventType() == Event.Type.RECREATIONAL){
                dailyRecreational[gameClock.getDays()-1]++;
            }

            updateTime(curEvent);
            updateEnergy(curEvent);

            if (curEvent.getActivityImage() != null)
            {
                frozen = true;
                curEvent.getActivityImage().setActive();
            }
            else game.screenManager.setScreen(curEvent.getScreenType());
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
                int day = gameClock.getHours()/24;
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

    public float calcScore(){
        int s1, s2, s3, s4, s5;

        int num0s = 0;
        int num1s = 0;
        for (int study : this.dailyStudy){
            if (study == 0){
                num0s++;
            } else if (study == 1){
                num1s++;
            }
        }
        if (num0s == 0 || (num0s == 1 && num1s <= 5)){
            s1 = 100;
        } else if (num0s == 2 && num1s <= 4){
            s1 = 60;
        } else if (num0s < 7){
            s1 = 40;
        } else{
            s1 = 0;
        }

        switch(this.placesStudied.size()){
            case 0:
                s2 = 0;
                break;
            case 1:
                s2 = 60;
                break;
            case 2:
                s2 = 80;
                break;
            default:
                s2 = 100;
                break;
        }

//        int totalStudyHours = gameScreen.getTotalStudyHours();
        s3 = (this.totalStudyHours >= 28 && this.totalStudyHours <= 35) ? 100 : (this.totalStudyHours * 100 / 28);

        int notEaten = 0;
        for (List<Integer> times : this.mealTimes){
            if (times.size() < 3){
                notEaten++;
            }
        }
        notEaten = (notEaten + 1)/2;
        switch (notEaten){
            case 0:
                s4 = 100;
                break;
            case 1:
                s4 = 80;
                break;
            case 2:
                s4 = 60;
                break;
            case 3:
                s4 = 40;
                break;
            default:
                s4 = 0;
                break;
        }

        int numBad = 0;
        for (int recreate : this.dailyRecreational){
            if (recreate == 0 || recreate >= 3){
                numBad++;
            }
        }

        switch (numBad){
            case 0:
                s5 = 100;
                break;
            case 1:
                s5 = 80;
                break;
            case 2:
            case 3:
                s5 = 60;
                break;
            case 4:
            case 5:
                s5 = 40;
                break;
            case 6:
                s5 = 20;
                break;
            default:
                s5 = 0;
                break;
        }
        return (s1 + s2 + s3 + s4 + s5) / 5f;

    }

    public boolean notFrozen() {
        return !frozen;
    }

    public ArrayList<Event> listEvents(){
        ArrayList<Event> list = new ArrayList<>();
        list.add(FeedDucks);
        list.add(Sleep);
        list.add(StudyCS);
        list.add(EatPiazza);
        list.add(PlayBBall);
        return list;
    }

    public void addStudyPlace(String studyPlace){
        if (!placesStudied.contains(studyPlace)){
            placesStudied.add(studyPlace);
        }
    }

    public void addMeal(int time){
        mealTimes.get(gameClock.getDays()-1).add(time);
    }
}