package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.HesHustle;

import java.util.List;
import java.util.Objects;

public class EventManager extends GameObject{
    Event event1,event2,event3,eatingA,eatingB,studying,studyCatchUp;
    public Float TRaw;
    Integer TSec, TMin;
    Integer day;
    List<Building> buildings;
    public EventManager(List<Building> buildings) {
        super(0,0,0,0);
        this.buildings = buildings;
        TRaw = 0.0f;
        TSec = 0;
        TMin = 0;
        day = 1;
        generateEvents();
    }
    public void generateEvents() {
        event1 = new Event( 1, 2, 10,-5,Event.type.RECREATIONAL, "");
        event2 = new Event( 1, 2,20,-10, Event.type.RECREATIONAL, 15, "");
        event3 = new Event(2, 2,50,10, Event.type.RECREATIONAL, 25, "");
        eatingA = new Event(1, -10, Event.type.EAT, "");
        eatingB = new Event(0.5, -5, Event.type.EAT, "");
        studying = new Event( 2.5 , 100, 10, 10, Event.type.STUDY, "");
        studyCatchUp = new Event( 5 , 200, 20, 20, Event.type.STUDY, "");

    }
    public void interact(String name)
    {
        switch (name){
            case "Nisa": Gdx.app.log("hi",name);
            case "Computer\nScience\nDepartment": Gdx.app.log("hi",name);
            //case "Nisa": Gdx.app.log("hi",name);
        }
    }
    @Override
    public void update(float deltaTime) {
        TRaw += deltaTime;
        if (TRaw >= 0.3){
            TSec++;
            TRaw = 0f;
        }
        if (TSec>=60){
            TSec = 0;
            TMin++;
        }

    }
    @Override
    public void render(Matrix4 projection, HesHustle game, ShapeRenderer shape)
    {
        game.batch.begin();
        game.font.draw(game.batch, Integer.toString(TSec) , 1000, 1000);
        game.font.draw(game.batch, Integer.toString(TMin)+":" , 975, 1000);
        game.batch.end();
    }


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
