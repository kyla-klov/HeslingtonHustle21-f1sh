package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.mygdx.game.Objects.Animation;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class AnimationTest {
    private final String textureFile = "Amelia_idle_anim_16x16.png";
    private final Texture texture = new Texture(Gdx.files.internal(textureFile));
    private final int frameTot = 24;
    private final int width = texture.getWidth() / frameTot;
    private final int height = texture.getHeight();
    private final Animation animation = new Animation(texture,12,17,frameTot,12);

    //test the function return the same first frame regardless of
    @Test
    public void testGetFirstFrame(){
        TextureRegion textureRegion1 = animation.getFrame(0.08f);
        assertEquals(width, textureRegion1.getRegionWidth(), 0);
        assertEquals(height, textureRegion1.getRegionHeight(), 0);
        assertEquals(width*12, textureRegion1.getRegionX(),0);
        assertEquals(0, textureRegion1.getRegionY(), 0);
        assertFalse(textureRegion1.isFlipX());
        assertFalse(textureRegion1.isFlipY());
        assertEquals(textureFile, ((FileTextureData) textureRegion1.getTexture().getTextureData()).getFileHandle().path());

        TextureRegion textureRegion2 = animation.getFrame(0.09f);
        assertEquals(width, textureRegion2.getRegionWidth(), 0);
        assertEquals(height, textureRegion2.getRegionHeight(), 0);
        assertEquals(width*12, textureRegion2.getRegionX(),0);
        assertEquals(0, textureRegion2.getRegionY(), 0);
        assertFalse(textureRegion2.isFlipX());
        assertFalse(textureRegion2.isFlipY());
        assertEquals(textureFile, ((FileTextureData) textureRegion2.getTexture().getTextureData()).getFileHandle().path());

    }
    @Test
    public void testGetFrame(){
        animation.getFrame(0.08f);

        //test time < 1/12
        TextureRegion textureRegion = animation.getFrame(0.1f);
        assertEquals(width, textureRegion.getRegionWidth(), 0);
        assertEquals(height, textureRegion.getRegionHeight(), 0);
        assertEquals(width*12, textureRegion.getRegionX(),0);
        assertEquals(0, textureRegion.getRegionY(), 0);
        assertFalse(textureRegion.isFlipX());
        assertFalse(textureRegion.isFlipY());
        assertEquals(textureFile, ((FileTextureData) textureRegion.getTexture().getTextureData()).getFileHandle().path());

        //test time > 1/12
        TextureRegion textureRegion2 = animation.getFrame(2f);
        assertEquals(width, textureRegion2.getRegionWidth(), 0);
        assertEquals(height, textureRegion2.getRegionHeight(), 0);
        assertEquals(width*13, textureRegion2.getRegionX(),0);
        assertEquals(0, textureRegion2.getRegionY(), 0);
        assertFalse(textureRegion2.isFlipX());
        assertFalse(textureRegion2.isFlipY());
        assertEquals(textureFile, ((FileTextureData) textureRegion2.getTexture().getTextureData()).getFileHandle().path());

        //test curFrame <= 4
        animation.getFrame(2f);
        animation.getFrame(2f);
        TextureRegion textureRegion3 = animation.getFrame(2f);
        assertEquals(width, textureRegion3.getRegionWidth(), 0);
        assertEquals(height, textureRegion3.getRegionHeight(), 0);
        assertEquals(width*16, textureRegion3.getRegionX(),0);
        assertEquals(0, textureRegion3.getRegionY(), 0);
        assertFalse(textureRegion3.isFlipX());
        assertFalse(textureRegion3.isFlipY());
        assertEquals(textureFile, ((FileTextureData) textureRegion3.getTexture().getTextureData()).getFileHandle().path());

        //test curFrame > 4
        TextureRegion textureRegion4 = animation.getFrame(2f);
        assertEquals(width, textureRegion4.getRegionWidth(), 0);
        assertEquals(height, textureRegion4.getRegionHeight(), 0);
        assertEquals(width*12, textureRegion4.getRegionX(),0);
        assertEquals(0, textureRegion4.getRegionY(), 0);
        assertFalse(textureRegion4.isFlipX());
        assertFalse(textureRegion4.isFlipY());
        assertEquals(textureFile, ((FileTextureData) textureRegion4.getTexture().getTextureData()).getFileHandle().path());
    }
}
