package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Utils.ResourceManager;

import java.util.Objects;

public class ActivityImage extends GameObject {
    private final Texture txt;
    private final ResourceManager resourceManager;
    private boolean active;

    public ActivityImage(String txt) {
        super(0,0,10,10);
        this.resourceManager = new ResourceManager();
        this.txt = resourceManager.addDisposable(new Texture(txt));
        this.active = false;
    }

    public void setActive(){
        active = true;
    }

    public void setInactive(){
        active = false;
    }

    public Texture getTxt() {
        return txt;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public boolean isActive() {
        return active;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityImage)) return false;
        ActivityImage that = (ActivityImage) o;
        return isActive() == that.isActive() && Objects.equals(getTxt().hashCode(), that.getTxt().hashCode()) && Objects.equals(getResourceManager(), that.getResourceManager());
    }*/

    @Override
    public void render(Camera projection, HesHustle game, ShapeRenderer shape) {
        if (!active) return;
        shape.setProjectionMatrix(projection.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND); // ALLOWS TRANSPARENCY
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //Draw transparent background
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0,0,0.2f,0.7f));
        shape.rect((projection.position.x - projection.viewportWidth/2), (projection.position.y - projection.viewportHeight/2), projection.viewportWidth, projection.viewportHeight);
        //draw black outline
        shape.setColor(Color.BLACK);
        shape.rect((projection.position.x - projection.viewportHeight*0.9f/2), (projection.position.y - projection.viewportHeight*0.9f/2), projection.viewportHeight*0.9f, projection.viewportHeight*0.9f);
        shape.end();
        //Draw the Image
        game.batch.begin();
        game.batch.setProjectionMatrix(projection.combined);
        game.batch.draw(new TextureRegion(txt),(projection.position.x - (projection.viewportHeight*0.85f/2)),(projection.position.y - projection.viewportHeight*0.85f/2),0,0,projection.viewportHeight*0.85f,projection.viewportHeight*0.85f,1,1,0);
        game.batch.end();
    }

    @Override
    public void dispose() {
        resourceManager.disposeAll();
    }
}
