package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Class to generate animation based off spritesheet
 */
public class Animation {
    int curFrame=0;
    private final Texture SprSheet;
    private final int frameNo;
    double time,frameTime;
    int frameStart,width,height;

    /**
     * Remember 1st frame is frame 0
     * @param SprSheet .
     * @param frameStart .
     * @param frameEnd .
     * @param frameTot .
     * @param fps .
     */
    public Animation(Texture SprSheet, int frameStart, int frameEnd, int frameTot, int fps)
    {
        this.SprSheet = SprSheet;
        this.frameStart = frameStart;
        this.frameNo = frameEnd-frameStart;

        this.width = SprSheet.getWidth()/frameTot;
        this.height = SprSheet.getHeight();

        this.time = 0;
        this.frameTime= (double) 1 /fps;

    }

    /**
     * Return the current animation frame give this to your renderer
     * @param deltaTime .
     * @return .
     */
    public TextureRegion GetFrame(float deltaTime)
    {
        if (time > frameTime) {
            time = 0;
            curFrame +=1;
            if (curFrame > frameNo-1){
                curFrame = 0;
            }
        }
        time += deltaTime;
        return new TextureRegion(SprSheet,width*(frameStart+curFrame),0,width,height);
    }
}
