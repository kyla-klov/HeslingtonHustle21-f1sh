package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.HesHustle;

import java.util.ArrayList;
import java.util.List;

public class LightCycle extends GameObject{
    Interpolation inter;
    float prog=0;
    float[] Col1,Col2,Col3;
    int segment = 0;

    /**
     * 2 Gradients orange->blue and orange->purple just flipped on some of them
     * lookup the LibGDX interpolation site for help the 4 Interpolations I used basically simulate a big squished sine wave
     */
    public LightCycle() {
        super(0, 0, 4000, 4000);
        Col1 = new float[]{238/255f, 130/255f, 0, 0.2f}; //orange
        Col2 = new float[]{163/255f, 190/255f, 242/255f, 0.1f};//blue
        Col3 = new float[]{113/255f, 0/255f, 143/255f, 0.3f};//purple
    }
    public void update(float delta){
    }

    public void getTime(int TMin, int TSec)
    {
        int rawTime = TMin*60 + TSec; // total time in "seconds"
        if (TMin > 5 && TMin <12)//morning to day
        {
            rawTime-=6*60;
            segment = 0;
        } else if (TMin > 11 && TMin < 18) { //day to afternoon
            rawTime-=12*60;
            segment = 1;
        } else if (TMin > 17 && TMin < 24) {//afternoon to evening
            rawTime-=18*60;
            segment = 2;
        } else {// evening to morning
            segment = 3;
        }
        prog = (float)rawTime/360;
    }

    /**
     * Calculate the resultant "color vector" then interpolation.apply(prog) will be between 0 and 1
     * @param Col1
     * @param Col2
     * @param inter
     * @return
     */
    public Color getColor(float[] Col1,float[] Col2,Interpolation inter)
    {
        float[] trans = new float[]{0,0,0,0};
        float[] fin = new float[]{0,0,0,0};
        for (int i = 0;i<4;i++)
        {
            trans[i] = Col1[i] - Col2[i];
        }
        for (int i = 0;i<4;i++)
        {
            fin[i] = Col2[i] + trans[i]* inter.apply(prog);
        }
        return new Color(fin[0],fin[1],fin[2],fin[3]);
    }


    public void render(Matrix4 projection, HesHustle game, ShapeRenderer shape) {
        shape.setProjectionMatrix(projection);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        switch(segment){
            case 0:
                inter = Interpolation.pow3Out;
                shape.setColor(getColor(Col2,Col1,inter));
                break;
            case 1:
                inter = Interpolation.pow3In;
                shape.setColor(getColor(Col1,Col2,inter));
                break;
            case 2:
                inter = Interpolation.pow3Out;
                shape.setColor(getColor(Col3,Col1,inter));
                break;
            case 3:
                inter = Interpolation.pow3In;
                shape.setColor(getColor(Col1,Col3,inter));
                break;
        }


        shape.rect(pos.x, pos.y, bounds.width, bounds.height);
        shape.end();
    }
}
