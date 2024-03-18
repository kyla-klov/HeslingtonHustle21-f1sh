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
    Table top,topLeft,topMid,topRight;
    Table middle,midLeft,midMid,midRight;
    Table bottom,botLeft,botMid,botRight;
    ProgressBar pb,nrgBar;
    int prog =0;
    TextButton TimeButt,ScoreButt;

    EventManager EM;
    String timeStr;
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

        //code to test table locations comment out create draws first
/*        TextButton txtbutt = new TextButton("topLeft",skin);
        topLeft.add(txtbutt).grow();
        txtbutt = new TextButton("topMid",skin);
        topMid.add(txtbutt).grow();
        txtbutt = new TextButton("topRight",skin);
        topRight.add(txtbutt).grow();
        txtbutt = new TextButton("midLeft",skin);
        midLeft.add(txtbutt).grow();
        txtbutt = new TextButton("midMid",skin);
        midMid.add(txtbutt).grow();
        txtbutt = new TextButton("midRight",skin);
        midRight.add(txtbutt).grow();
        txtbutt = new TextButton("botLeft",skin);
        botLeft.add(txtbutt).grow();
        txtbutt = new TextButton("botMid",skin);
        botMid.add(txtbutt).grow();
        txtbutt = new TextButton("botRight",skin);
        botRight.add(txtbutt).grow();*/
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
        TimeButt = new TextButton("Time",skin);
        topLeft.add(ScoreButt).pad(4).row();
        topLeft.add(TimeButt).pad(4);
        TextButton txtbutt = new TextButton("Energy",skin);
        topLeft.row();
        topLeft.add(txtbutt).pad(4);
        nrgBar = new ProgressBar(0,100,2,false,skin);
        topLeft.add(nrgBar).pad(2);
        topLeft.pad(25);
        topLeft.align(Align.topLeft);
/*        txtbutt = new TextButton("topRight",skin);
        topRight.add(txtbutt);
        txtbutt = new TextButton("botLeft",skin);
        botLeft.add(txtbutt);
        txtbutt = new TextButton("botRight",skin);
        botRight.add(txtbutt);*/




    }
    public void update(float deltaTime){
        prog+=1;
        nrgBar.setValue(prog);
        if (prog == 100 ){
            prog = 0;
        }
        ScoreButt.setText("Score: " + EM.Score());
        TimeButt.setText(EM.getTime());
        TimeButt.scaleBy(5);

    }

    public void render(Matrix4 projection, HesHustle game, ShapeRenderer shape)
    {
        stage.act();
        stage.draw();
    }

}
