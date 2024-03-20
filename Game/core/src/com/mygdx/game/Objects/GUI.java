package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HesHustle;

import javax.swing.text.View;

public class GUI extends GameObject{
    private Skin skin;
    public Stage stage;
    private Table root;
    Table topLeft,topRight;
    Table botLeft,botRight;
    ProgressBar nrgBar;
    int prog = 0;
    TextButton TimeButt,ScoreButt,DayButt;
    TextButton RecButt,EatButt,StudyButt,SleepButt;
    EventManager EM;
    public GUI(Batch batch, EventManager EM) {
        super(0,0,0,0);
        this.EM = EM;
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        stage = new Stage(new ExtendViewport(400,400),batch);
        root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        //stage.setDebugAll(true);
        createTables();
        createDraws();
        formatTables();

    }

    private void createTables() {
        topLeft = new Table();
        topRight = new Table();
        botLeft = new Table();
        botRight = new Table();
    }
    private void formatTables(){
        root.add(topLeft).left().grow();
        root.add(topRight).grow();
        root.row();
        root.add(botLeft).grow();
        root.add(botRight).grow().row();
    }

    public void createDraws(){
        ScoreButt = new TextButton("Score",skin);
        DayButt = new TextButton("Day",skin);
        TimeButt = new TextButton("Time",skin);
        topLeft.add(ScoreButt).pad(4).row();
        topLeft.add(DayButt).pad(4).row();
        topLeft.add(TimeButt).pad(4);
        TextButton txtbutt = new TextButton("Energy",skin);
        topLeft.row();
        topLeft.add(txtbutt).pad(4);
        nrgBar = new ProgressBar(0,100,2,false,skin);
        topLeft.add(nrgBar).pad(2);
        topLeft.pad(25);
        topLeft.align(Align.topLeft);

        RecButt  = new TextButton("RecNo:",skin);
        SleepButt  = new TextButton("SlpNo:",skin);
        EatButt  = new TextButton("EatNo:",skin);
        StudyButt  = new TextButton("StdyNo:",skin);
        topRight.add(RecButt);
        topRight.add(SleepButt);
        topRight.add(EatButt);
        topRight.add(StudyButt);
        topRight.align(Align.top);

    }
    public void update(float deltaTime){
        nrgBar.setValue(EM.energy);
        ScoreButt.setText("Score: " + EM.Score());
        DayButt.setText("Day: " + EM.day);
        TimeButt.setText(EM.getTime());
        TimeButt.scaleBy(5);
        int [] count = countActivitys();
        RecButt.setText("RecNo: " + count[0]);
        SleepButt.setText("SlpNo: " + count[1]);
        EatButt.setText("EatNo: " + count[2]);
        StudyButt.setText("StdyNo: " + count[3]);

    }
    public int[] countActivitys(){
        int rec=0,slp=0,eat=0,stdy=0;
        for (Event e : EM.playedEvents)
        {
            switch (e.getEventType())
            {
                case EAT:
                    eat++;
                    break;
                case SLEEP:
                    slp++;
                    break;
                case STUDY:
                    stdy++;
                    break;
                case RECREATIONAL:
                    rec++;
                    break;
            }
        }
        return new int[] {rec,slp,eat,stdy};
    }

    public void render(Matrix4 projection, HesHustle game, ShapeRenderer shape)
    {
        stage.act();
        stage.draw();
    }

}
