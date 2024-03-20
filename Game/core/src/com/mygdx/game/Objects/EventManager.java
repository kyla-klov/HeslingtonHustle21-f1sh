package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.HesHustle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

public class EventManager extends GameObject{
    Event FeedDucks,Sleep,StudyCS,EatPiazza,PlayBBall;
    Event curEvent = null;
    public Float TRaw,Twait;
    public Integer TSec, TMin,energy, day;
    private int money;
    boolean frozen = false;

    List<Building> buildings;
    List<Event> playedEvents;

    public EventManager(List<Building> buildings) {
        super(0,0,0,0);
        this.buildings = buildings;
        playedEvents = new ArrayList<>();
        TRaw = 0.0f;
        TSec = 0;
        TMin = 8;
        Twait = 0f; 
        energy = 100;
        day = 1;
        generateEvents();
    }
    public void generateEvents() {


        FeedDucks = new Event( 1, 2, 10,-5,Event.type.RECREATIONAL, "",new Texture("Activitys/lakemap.png"));
        StudyCS = new Event( 1, -20,20,-10, Event.type.STUDY, 15, "",new Texture("Activitys/cs.png"));
        PlayBBall = new Event(2, -30,50,10, Event.type.RECREATIONAL, 25, "",new Texture("Activitys/basketballcourt.png"));
        Sleep = new Event(8, 90, Event.type.SLEEP, "",new Texture("Activitys/langwith.png"));
        EatPiazza = new Event(1, 10, Event.type.EAT, "",new Texture("Activitys/piazza.png"));


    }
    public void interact(String name)
    {
        switch (name){
            default:
                curEvent = null;
                break;
            case "Piazza":
                curEvent = EatPiazza;
                playedEvents.add(EatPiazza);
                Twait = 8f;
                break;
            case "Computer\nScience\nDepartment":
                curEvent = StudyCS;
                playedEvents.add(StudyCS);
                Twait = 8f;
                break;
            case "Langwith":
                curEvent = Sleep;
                playedEvents.add(Sleep);
                Twait = 8f;
                break;
            case "Ducks":
                curEvent = FeedDucks;
                playedEvents.add(FeedDucks);
                Twait = 8f;
                break;
            case "BasketBall":
                curEvent = PlayBBall;
                playedEvents.add(PlayBBall);
                Twait = 8f;
                break;
        }
        assert curEvent != null;
        frozen = true;
        updateTime(curEvent);
        updateEnergy(curEvent);
    }
    @Override
    public void update(float deltaTime) {
        TRaw += deltaTime;
        Twait -= deltaTime;
        if (Twait<0)
        {
            curEvent = null;
            frozen = false;
        }
        if (TRaw >= 0.5f){
            TSec++;
            TRaw = 0f;
        }
        if (TSec>=60){
            TSec = 0;
            TMin++;
        }
        if (TMin > 23 ) {
            TMin -= 24;
            day++;
        }

    }

    public String getTime()
    {
        String forTime = "";
        String z1 = "",z2 = "";
        if (TMin < 10) {z1="0";}
        if (TSec < 10) {z2="0";}

        return "Time: " + z1 +TMin + ":" + z2 +TSec;
    }


    public void render(Camera projection, HesHustle game, ShapeRenderer shape)
    {
        if(curEvent!=null){
            curEvent.render(projection, game, shape);
        }

    }

    /**
     * returns the score for the game based on the list of events
     *
     * @return score an integer representing the players geades
     */

    public void updateTime(Event e)
    {
        if (e.getEventType() == Event.type.SLEEP)
        {
            TSec = 0;
            TMin = 8;
            day++;
        }
        else {
            TMin += (int) Math.floor(e.getTimeCost());
            if (TMin > 23 ) {
                TMin -= 24;
                day++;
            }
        }

    }
    public void updateEnergy(Event e)
    {
        energy += e.getEnergyCost();
        if (energy<0)
        {
            //pass out or something
        } else if (energy>100)
        {
            energy=100;
        }

    }

    public int Score() {
        int score = 0;
        int cumulativeEat = 1;
        int cumulativeSleep = 1;
        int studyCount = 0;
        int studyTotal = 0;
        int recCount = 0;
        int recTotal = 0;
        double studyDebuff = 1;
        double recDebuff = 1;
        int fatigue = 0;
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
            recDebuff = (double) 140 / (((recCount - 10) * (recCount- 10)) + 5);
        }

        score += (int) Math.round(studyDebuff * studyTotal);
        score += (int) Math.round(recDebuff * recTotal);

        // 692 is the theoretical max a score can be, excluding debuffs and assuming 14 study sessions and 10 recreational session
        return (int) score;
        // / 692)* 10);
    }


//    public void addEvent(String event){
//        // todo add the money and time functions to event manager
//        switch (event.toLowerCase()){
//            case "a":
//                currentEvent = event1;
//                isComplete = 1;
//                break;
//            case "b":
//                currentEvent = event2;
//                if(money - currentEvent.getMoneyCost() > 0){
//                    isComplete = 1;
//                    money -=  currentEvent.getMoneyCost();
//                }
//                break;
//            case "c":
//                currentEvent = event3;
//                if(money - currentEvent.getMoneyCost() > 0){
//                    isComplete = 1;
//                    money -=  currentEvent.getMoneyCost();
//                }
//                break;
//            case "d":
//                currentEvent = eatingA;
//                isComplete = 1;
//                break;
//            case "e":
//                currentEvent = eatingB;
//                isComplete = 1;
//                break;
//            case "f":
//                currentEvent = studying;
//                isComplete = 1;
//                break;
//            case "g":
//                currentEvent = studyCatchUp;
//                isComplete = 1;
//                break;
//            case "h":
//                currentEvent = new Event((TMin + (TSec / 60)), - energy, Event.type.SLEEP, "");
//                isComplete = 1;
//                break;
//            default:
//                System.out.println("invalid input");
//
//        }
//        if(currentEvent != null){
//            if((TMin + (TSec / 60)) >= currentEvent.getTimeCost() && energy >= currentEvent.getEnergyCost()) {
//                TMin += (currentEvent.getTimeCost());
//                energy += (currentEvent.getEnergyCost());
//                if((TMin + (TSec / 60)) == 960 ){
//                    TMin = 0;
//                    TSec = 0;
//                    day += 1;
//                } else if (energy == 0) {
//                    energy = 30;
//                    days += 1;
//                }
//                playedEvents.add(currentEvent);
//            }
//        }
//    }

    /**
     * Adds a valid event to the list of events that occured, based on the user input
     *
     * @param event the name of the event that occured
     * */
    /*public void addEvent(String event){
        switch (event.toLowerCase()){
            case "a":
                currentEvent = event1;
                isComplete = 1;
                break;
            case "b":
                currentEvent = event2;
                if(money - currentEvent.getMoneyCost() > 0){
                    isComplete = 1;
                    money -=  currentEvent.getMoneyCost();
                }
                break;
            case "c":
                currentEvent = event3;
                if(money - currentEvent.getMoneyCost() > 0){
                    isComplete = 1;
                    money -=  currentEvent.getMoneyCost();
                }
                break;
            case "d":
                currentEvent = eatingA;
                isComplete = 1;
                break;
            case "e":
                currentEvent = eatingB;
                isComplete = 1;
                break;
            case "f":
                currentEvent = studying;
                isComplete = 1;
                break;
            case "g":
                currentEvent = studyCatchUp;
                isComplete = 1;
                break;
            case "h":
                currentEvent = new Event((TMin + (TSec / 60)), - energy, Event.type.SLEEP, "");
                isComplete = 1;
                break;
            default:
                System.out.println("invalid input");

        }
        if(currentEvent != null){
            if((TMin + (TSec / 60)) >= currentEvent.getTimeCost() && energy >= currentEvent.getEnergyCost()) {
                TMin += (currentEvent.getTimeCost());
                energy += (currentEvent.getEnergyCost());
                if((TMin + (TSec / 60)) == 960 ){
                    TMin = 0;
                    TSec = 0;
                    day += 1;
                } else if (energy == 0) {
                    energy = 30;
                    days += 1;
                }
                playedEvents.add(currentEvent);
            }
        }



    }*/



    /*
        while (!time.isComplete()){

            // by replacing outputs with functions, it becomes easier to modify code to suit specific engines
            statusOutput( time,  plCharacter);
            int isComplete = 0;
            Event currentEvent = new Event();
            while(isComplete == 0){
                String event = input();
                // the switch statement uses placeholder names, however they can be named anything and it will not affect code operation
                switch (event.toLowerCase()){
                    case "a":
                        currentEvent = event1;
                        isComplete = 1;
                        break;
                    case "b":
                        currentEvent = event2;
                        if(plCharacter.getMoney() - currentEvent.getMoneyCost() > 0){
                            isComplete = 1;
                            plCharacter.addMoney(- currentEvent.getMoneyCost());
                        }
                        break;
                    case "c":
                        currentEvent = event3;
                        if(plCharacter.getMoney() - currentEvent.getMoneyCost() > 0){
                            isComplete = 1;
                            plCharacter.addMoney(- currentEvent.getMoneyCost());
                        }
                        break;
                    case "d":
                        currentEvent = eatingA;
                        isComplete = 1;
                        break;
                    case "e":
                        currentEvent = eatingB;
                        isComplete = 1;
                        break;
                    case "f":
                        currentEvent = studying;
                        isComplete = 1;
                        break;
                    case "g":
                        currentEvent = studyCatchUp;
                        isComplete = 1;
                        break;
                    case "h":
                        currentEvent = new Event(time.getHours(), - plCharacter.getEnergy(), Event.type.SLEEP, "");
                        isComplete = 1;
                        break;
                    default:
                        System.out.println("invalid input");

                }

            }

            // while this appears redundant, it is to catch any errors that may produce a current event = null
            if(currentEvent != null){
                if(time.checkTime(currentEvent.getTimeCost()) && plCharacter.checkEnergy(currentEvent.getEnergyCost())) {
                    time.decreaseHours(currentEvent.getTimeCost());
                    plCharacter.decreaseEnergy(currentEvent.getEnergyCost());
                    if(time.getHours() == 0 ){
                        time.resetHours();
                        time.decreaseDays();
                    } else if (plCharacter.getEnergy() == 0) {
                        plCharacter.resetEnergy();
                        time.decreaseDays();

                    }


                    playedEvents.add(currentEvent);
                }
            }

        }
        System.out.println(playedEvents);
        int score = Score(playedEvents);
        System.out.println("Your score is " + score);

        File file = new File("scores.txt");
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("scores.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            myWriter.write(name + " score: " + score + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */


}
