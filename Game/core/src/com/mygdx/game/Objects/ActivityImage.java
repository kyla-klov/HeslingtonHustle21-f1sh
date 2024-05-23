package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Utils.ResourceManager;

/**
 * The ActivityImage class represents an image that can be rendered when an activity event is triggered.
 */
public class ActivityImage implements Disposable {
    private final Texture txt;
    private final ResourceManager resourceManager;
    private boolean active;

    /**
     * Constructs an ActivityImage with the specified texture file path.
     *
     * @param txt the file path of the texture
     */
    public ActivityImage(String txt) {
        this.resourceManager = new ResourceManager();
        this.txt = resourceManager.addDisposable(new Texture(txt));
        this.active = false;
    }

    /**
     * Sets the image to active state, making it visible.
     */
    public void setActive(){
        active = true;
    }

    /**
     * Sets the image to inactive state, making it invisible.
     */
    public void setInactive(){
        active = false;
    }

    /**
     * Gets the texture of the image.
     *
     * @return the texture of the image
     */
    @SuppressWarnings("unused")
    public Texture getTxt() {
        return txt;
    }

    /**
     * Gets the resource manager for this image.
     *
     * @return the resource manager
     */
    @SuppressWarnings("unused")
    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    /**
     * Checks if the image is active.
     *
     * @return true if the image is active, false otherwise
     */
    @SuppressWarnings("unused")
    public boolean isActive() {
        return active;
    }

    /**
     * Renders the image using the specified camera and sprite batch.
     * The image is rendered with a transparent background and a black outline if it is active.
     *
     * @param projection the camera used for rendering
     * @param batch      the sprite batch used for rendering
     */
    public void render(Camera projection, SpriteBatch batch) {
        if (!active) return;
        //batch.setProjectionMatrix(projection.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND); // ALLOWS TRANSPARENCY
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //Draw transparent background
        batch.setColor(new Color(0,0,0.2f,0.7f));
        batch.draw(new Texture(Gdx.files.internal("BlankSquare.png")), (projection.position.x - projection.viewportWidth/2), (projection.position.y - projection.viewportHeight/2), projection.viewportWidth, projection.viewportHeight);
        //draw black outline
        batch.setColor(Color.BLACK);
        batch.draw(new Texture(Gdx.files.internal("BlankSquare.png")), (projection.position.x - projection.viewportHeight*0.9f/2), (projection.position.y - projection.viewportHeight*0.9f/2), projection.viewportHeight*0.9f, projection.viewportHeight*0.9f);

        batch.setColor(1, 1, 1, 1);
        batch.draw(new TextureRegion(txt),(projection.position.x - (projection.viewportHeight*0.85f/2)),(projection.position.y - projection.viewportHeight*0.85f/2),0,0,projection.viewportHeight*0.85f,projection.viewportHeight*0.85f,1,1,0);
    }

    /**
     * Disposes of all resources used by this image.
     */
    @Override
    public void dispose() {
        resourceManager.disposeAll();
    }
}
