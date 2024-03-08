package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Anim {
    private Texture SprSheet;
    private int FrameNo;
    int time;

    public Anim(Texture SprSheet, int frameNo)
    {
        this.SprSheet = SprSheet;
        this.FrameNo = FrameNo;
        this.time = 0;
    }

    public TextureRegion GetFrame(float deltaTime)
    {
        time += deltaTime;
        if (time > 1) {time = 0;}

        return null;
    }
}
