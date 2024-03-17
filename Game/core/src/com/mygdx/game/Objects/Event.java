package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.HesHustle;

public class Event extends GameObject {
    private double timeCost;
    private float realTime = 0f;
    private int energyCost,fatigue,enjoymentStudyLevel,moneyCost;
    private String description;
    public enum type {
        EAT,
        SLEEP,
        STUDY,
        RECREATIONAL
    };

    private type eventType;

    public Event(double time,int energy, type eventType,String description) {


        super(0,0,10,10);


        timeCost = time;
        energyCost = energy;
        this.eventType = eventType;
        this.description = description;

    }


    public Event(double time,int energy, int enjoymentStudyLevel, int fatigue, type eventType, String description) {
        // since externally there is no difference between using an int for enjoyment and one for studylevel, they are combined to a single variable whose relevance is determined by the event type.
        super(0,0,10,10);

        timeCost = time;
        energyCost = energy;
        this.enjoymentStudyLevel = enjoymentStudyLevel;
        this.fatigue = fatigue;
        this.eventType = eventType;
        this.description = description;
    }

    public Event(double time,int energy, int enjoymentStudyLevel, int fatigue, type eventType, int moneyCost, String description) {
        super(0,0,10,10);
        timeCost = time;
        energyCost = energy;
        this.enjoymentStudyLevel = enjoymentStudyLevel;
        this.fatigue = fatigue;
        this.eventType = eventType;
        this.moneyCost = moneyCost;
        this.description = description;
    }

    public void render(Camera projection, HesHustle game, ShapeRenderer shape)
    {
        Gdx.app.log("","space works");
        shape.setProjectionMatrix(projection.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.rect((projection.position.x - projection.viewportWidth*0.85f/2), (projection.position.y - projection.viewportHeight*0.85f/2), projection.viewportWidth*0.85f, projection.viewportHeight*0.85f);
        shape.end();

        game.batch.begin();
        game.font.draw(game.batch, "hahah", projection.position.x, projection.position.y);
        game.batch.end();
    }


    public void setEnergyCost(int energyCost) {
        this.energyCost = energyCost;
    }

    public void setTimeCost(double timeCost) {

    }

    public int getEnergyCost() {
        return energyCost;
    }

    public double getTimeCost() {
        return timeCost;
    }

    public int getEnjoymentStudyLevel() {
        return enjoymentStudyLevel;
    }

    public int getFatigue() {
        return fatigue;
    }

    public type getEventType() {
        return eventType;
    }

    public int getMoneyCost() {
        return moneyCost;
    }

    public String getDescription() {
        return description;
    }

    /**
     * returns the score for the game based on the list of events
     *
     * @params playedEvents, a list of events that have occured during the game
     * @returns score, an integer representing the players geades
     */
    /
    public static int Score(List<Event> playedEvents) {
        int score = 0;
        int cumulativeEat = 1;
        int cumulativeSleep = 1;
        int fatigue = 0;
        int studyCount = 0;
        int studyTotal = 0;
        int recCount = 0;
        int recTotal = 0;
        double studyDebuff = 1;
        double recDebuff = 1;
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
        return (int)((score / 692)* 10 - ());
    }



    /*
    this class allows for the interfacing of the time and energy classes
    it holds the event name a link to the event constants and the values it holds for scoring
    in the primary gameplay loop the number if events and their nature is stored and then
    the results are used to output score after 7 days*/

}

