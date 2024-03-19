package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.HesHustle;

public class Event extends GameObject {
    private double timeCost;
    private float realTime = 0f;
    private int energyCost,fatigue,enjoymentStudyLevel,moneyCost;
    private String description;
    private Texture txt;
    public enum type {
        EAT,
        SLEEP,
        STUDY,
        RECREATIONAL
    };
    private type eventType;

    public Event(double time, int energy, type eventType, String description, Texture txt) {


        super(0,0,10,10);


        timeCost = time;
        energyCost = energy;
        this.eventType = eventType;
        this.description = description;
        this.txt = txt;

    }

    /**
     * generates a new instance of an event, representing the possible activities a player can undertake
     *
     * @param enjoymentStudyLevel      The relative value for how good an activity is for enjoyment / study as an integer
     * @param description              A description of the event
     * @param energy                   The amoount of energy an event requires
     * @param time                     The time required for an event as a double
     * @param eventType                the type of event (sleep, eaty, study, recreational)
     * @param fatigue                  The amount of fatigue a player has after an event
     * @param txt                      The texture associated with an event
     */
    public Event(double time,int energy, int enjoymentStudyLevel, int fatigue, type eventType, String description, Texture txt) {
        // since externally there is no difference between using an int for enjoyment and one for studylevel, they are combined to a single variable whose relevance is determined by the event type.
        super(0,0,10,10);

        timeCost = time;
        energyCost = energy;
        this.enjoymentStudyLevel = enjoymentStudyLevel;
        this.fatigue = fatigue;
        this.eventType = eventType;
        this.description = description;
        this.txt = txt;
    }

    public Event(double time,int energy, int enjoymentStudyLevel, int fatigue, type eventType, int moneyCost, String description, Texture txt) {
        super(0,0,10,10);
        timeCost = time;
        energyCost = energy;
        this.enjoymentStudyLevel = enjoymentStudyLevel;
        this.fatigue = fatigue;
        this.eventType = eventType;
        this.moneyCost = moneyCost;
        this.description = description;
        this.txt = txt;
    }

    public void render(Camera projection, HesHustle game, ShapeRenderer shape)
    {
        shape.setProjectionMatrix(projection.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0,0,0.2f,0.7f));
        shape.rect((projection.position.x - projection.viewportWidth/2), (projection.position.y - projection.viewportHeight/2), projection.viewportWidth, projection.viewportHeight);
        shape.setColor(Color.BLACK);
        shape.rect((projection.position.x - projection.viewportHeight*0.9f/2), (projection.position.y - projection.viewportHeight*0.9f/2), projection.viewportHeight*0.9f, projection.viewportHeight*0.9f);
        shape.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        game.batch.begin();
        game.batch.setProjectionMatrix(projection.combined);
        game.batch.draw(new TextureRegion(txt),(projection.position.x - (projection.viewportHeight*0.85f/2)),(projection.position.y - projection.viewportHeight*0.85f/2),0,0,projection.viewportHeight*0.85f,projection.viewportHeight*0.85f,1,1,0);
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







}

