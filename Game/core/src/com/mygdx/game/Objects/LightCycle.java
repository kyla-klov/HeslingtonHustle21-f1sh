package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class LightCycle {
    private final int size = 4000;
    private final float[] Col1, Col2, Col3;

    private float prog=0;
    private int segment = 0;

    /**
     * 2 Gradients orange->blue and orange->purple just flipped on some of them
     * lookup the LibGDX interpolation site for help the 4 Interpolations I used basically simulate a big squished sine wave
     */
    public LightCycle() {
        Col1 = new float[]{238/255f, 130/255f, 0, 0.2f}; //orange
        Col2 = new float[]{163/255f, 190/255f, 242/255f, 0.1f};//blue
        Col3 = new float[]{113/255f, 0/255f, 143/255f, 0.3f};//purple
    }

    public void getTime(int hours, int minutes)
    {
        int rawTime = hours*60 + minutes; // total time in "seconds"
        if (hours > 5 && hours <12)//morning to day
        {
            rawTime-=6*60;
            segment = 0;
        } else if (hours > 11 && hours < 18) { //day to afternoon
            rawTime-=12*60;
            segment = 1;
        } else if (hours > 17 && hours < 24) {//afternoon to evening
            rawTime-=18*60;
            segment = 2;
        } else {// evening to morning
            segment = 3;
        }
        prog = (float)rawTime/360;
    }

    /**
     * Calculate the resultant "color vector" then interpolation.apply(prog) will be between 0 and 1
     * @param Col1 .
     * @param Col2 .
     * @param inter .
     * @return .
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

    public void render(SpriteBatch batch, int hours, int minutes) {
        getTime(hours, minutes);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Interpolation inter;
        switch(segment){
            case 0:
                inter = Interpolation.pow3Out;
                batch.setColor(getColor(Col2,Col1, inter));
                break;
            case 1:
                inter = Interpolation.pow3In;
                batch.setColor(getColor(Col1,Col2, inter));
                break;
            case 2:
                inter = Interpolation.pow3Out;
                batch.setColor(getColor(Col3,Col1, inter));
                break;
            case 3:
                inter = Interpolation.pow3In;
                batch.setColor(getColor(Col1,Col3, inter));
                break;
        }

        batch.draw(new Texture(Gdx.files.internal("BlankSquare.png")), 0, 0, size, size);
        batch.setColor(1, 1, 1, 1);
    }

    public int getSize(){
        return size;
    }
}
