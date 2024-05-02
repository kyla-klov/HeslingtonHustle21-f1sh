package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Utils.Event;
import com.mygdx.game.Utils.EventManager;
import com.mygdx.game.Utils.GameClock;

public class GUI extends GameObject{
    private Skin skin;
    public Stage stage;
    private Table root;
    private GameClock gameClock;
    Table leftTab, rightTab;
    ProgressBar nrgBar;
    TextButton TimeButt,ScoreButt,DayButt;
    TextButton RecButt,EatButt,StudyButt,SleepButt;
    EventManager EM;
    public GUI(Batch batch, EventManager EM, GameClock gameClock) {
        super(0,0,0,0);
        this.EM = EM;
        this.gameClock = gameClock;
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
        leftTab = new Table();
        rightTab = new Table();
    }
    private void formatTables(){
        root.add(leftTab).left().grow();
        root.add(rightTab).grow();
        root.row();
    }

    public void createDraws(){
        ScoreButt = new TextButton("Score",skin);
        DayButt = new TextButton("Day",skin);
        TimeButt = new TextButton("Time",skin);
        leftTab.add(ScoreButt).pad(4).row();
        leftTab.add(DayButt).pad(4).row();
        leftTab.add(TimeButt).pad(4);
        TextButton txtbutt = new TextButton("Energy",skin);
        leftTab.row();
        leftTab.add(txtbutt).pad(4);
        nrgBar = new ProgressBar(0,100,2,false,skin);
        leftTab.add(nrgBar).pad(2);
        leftTab.pad(25);
        leftTab.align(Align.topLeft);

        RecButt  = new TextButton("RecNo:",skin);
        SleepButt  = new TextButton("SlpNo:",skin);
        EatButt  = new TextButton("EatNo:",skin);
        StudyButt  = new TextButton("StdyNo:",skin);
        rightTab.add(RecButt);
        rightTab.add(SleepButt);
        rightTab.add(EatButt);
        rightTab.add(StudyButt);
        rightTab.align(Align.top);

    }
    public void update(float deltaTime){
        nrgBar.setValue(EM.energy);
        ScoreButt.setText("Score: " + EM.getScore());
        DayButt.setText("Day: " + gameClock.getDays());
        TimeButt.setText(gameClock.getTime());
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
