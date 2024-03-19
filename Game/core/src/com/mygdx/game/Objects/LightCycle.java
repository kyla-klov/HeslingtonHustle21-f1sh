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
    float alpha;
    float prog=0;
    float[] Col1,Col2,Col3;
    int segment = 0;
    public LightCycle() {
        super(-300, -300, 3000, 3000);
        inter  = new Interpolation.Exp(100,1);
        alpha = 0;
        Col1 = new float[]{238/255f, 130/255f, 0, 0.2f};
        Col2 = new float[]{163/255f, 190/255f, 242/255f, 0.1f};
        Col3 = new float[]{113/255f, 0/255f, 143/255f, 0.3f};
    }
    public void update(float delta){
        prog+=delta*0.2;
        if (prog >1){
            prog = 0;
            segment++;
            if(segment>2){segment = 0;}
        }
        alpha = inter.apply(prog);
        Gdx.app.log("hi",Float.toString(alpha));
    }

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
                shape.setColor(getColor(Col2,Col1,inter));
                break;
            case 1:
                shape.setColor(getColor(Col3,Col2,inter));
                break;
            case 2:
                shape.setColor(getColor(Col1,Col3,inter));
                break;
        }


        shape.rect(pos.x, pos.y, bounds.width, bounds.height);
        shape.end();
    }
}
