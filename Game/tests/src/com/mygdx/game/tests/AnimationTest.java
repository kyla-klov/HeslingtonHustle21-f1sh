package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Objects.Animation;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(GdxTestRunner.class)
public class AnimationTest {
    private final Texture texture = new Texture(Gdx.files.internal("Amelia_idle_anim_16x16.png"));
    private final int frameTot = 24;
    private final int width = texture.getWidth() / frameTot;
    private final int height = texture.getHeight();
    private final TextureRegion textureRegion = new TextureRegion(texture, width*(12+0), 0, width, height);
    private final Animation animation = new Animation(texture,12,17,frameTot,12);
    @Test
    public void testGetFrame(){
        TextureRegion textureRegion = animation.getFrame(2);
        assertEquals(this.textureRegion.getRegionHeight() ,textureRegion.getRegionHeight(), 0);
        assertEquals(this.textureRegion.getRegionWidth(), textureRegion.getRegionWidth(), 0);
        assertEquals(this.textureRegion.getRegionX(), textureRegion.getRegionX(), 0);
        assertEquals(this.textureRegion.getRegionY(), textureRegion.getRegionY(), 0);
        assertEquals(this.textureRegion.isFlipX(), textureRegion.isFlipX());
        assertEquals(this.textureRegion.isFlipY(), textureRegion.isFlipY());

    }
}
