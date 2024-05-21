package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Utils.ResourceManager;
import com.mygdx.game.Utils.ViewportAdapter;

public class EnergyBar implements Disposable {
    private final Texture centre;
    private final Texture leftEdge;
    private final Texture rightEdge;
    private final Texture holderCentre;
    private final Texture holderLeft;
    private final Texture holderRight;

    private final ResourceManager resourceManager;
    private final Viewport vp;

    private final float x, y, width, height, edgeWidth;

    public EnergyBar(Viewport vp, float x, float y, float width, float height, float edgeWidth){
        this.vp = vp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.edgeWidth = edgeWidth;
        resourceManager = new ResourceManager();
        centre = resourceManager.addDisposable(new Texture("EnergyBar/meter_bar_center-repeating_blue.png"));
        leftEdge = resourceManager.addDisposable(new Texture("EnergyBar/meter_bar_left_edge_blue.png"));
        rightEdge = resourceManager.addDisposable(new Texture("EnergyBar/meter_bar_right_edge_blue.png"));
        holderCentre = resourceManager.addDisposable(new Texture("EnergyBar/meter_bar_holder_center-repeating_blue.png"));
        holderLeft = resourceManager.addDisposable(new Texture("EnergyBar/meter_bar_holder_left_edge_blue.png"));
        holderRight = resourceManager.addDisposable(new Texture("EnergyBar/meter_bar_holder_right_edge_blue.png"));
    }

    public void render(SpriteBatch batch, float energy){
        ViewportAdapter.drawUI(vp, batch, holderLeft, x, y, edgeWidth, height);
        ViewportAdapter.drawUI(vp, batch, holderCentre, x+edgeWidth, y, width-edgeWidth*2, height);
        ViewportAdapter.drawUI(vp, batch, holderRight, x+width-edgeWidth, y, edgeWidth, height);

        Vector2 blCorner = ViewportAdapter.toScreen(vp, x + edgeWidth * 0.41f, y);
        Vector2 trCorner = ViewportAdapter.toScreen(vp, x + width - edgeWidth * 0.41f, y + height);
        int scissorX = (int) blCorner.x;
        int scissorY = (int) blCorner.y;
        int scissorWidth = (int) ((trCorner.x - blCorner.x) * energy/100f);
        int scissorHeight = (int) (trCorner.y - blCorner.y);

        batch.draw(centre, 0, 0, 0, 0);
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
        ViewportAdapter.drawUI(vp, batch, leftEdge, x, y, edgeWidth, height);
        ViewportAdapter.drawUI(vp, batch, centre, x+edgeWidth, y, width-edgeWidth*2, height);
        ViewportAdapter.drawUI(vp, batch, rightEdge, x+width-edgeWidth, y, edgeWidth, height);
        batch.draw(centre, 0, 0, 0, 0);
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
    }

    @Override
    public void dispose() {
        resourceManager.disposeAll();
    }
}
