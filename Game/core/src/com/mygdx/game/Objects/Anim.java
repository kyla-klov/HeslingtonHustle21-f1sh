package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Anim {
    private final Texture SprSheet;
    private int FrameNo;
    double time;

    public Anim(Texture SprSheet, int frameNo)
    {
        this.SprSheet = SprSheet;
        this.FrameNo = 1+FrameNo;
        this.time = 0;
    }

    public TextureRegion GetFrame(float deltaTime)
    {
        if (time > 1) {time = 0;}
        time += deltaTime;

        double frameLen = 1/FrameNo;
        int curFrame = (int) Math.floor(time/frameLen);
        TextureRegion tex = new TextureRegion(SprSheet,16,32);
        return tex;
    }
}
