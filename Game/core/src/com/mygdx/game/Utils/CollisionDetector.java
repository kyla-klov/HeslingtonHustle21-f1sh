package com.mygdx.game.Utils;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Objects.PlayerController;

/**
 * Uses the collision layer in the TileMap file to generate collisions
 */
public class CollisionDetector {
    private final TiledMapTileLayer collisionLayer;
    private final PlayerController player;

    public CollisionDetector(PlayerController player, TiledMapTileLayer collisionLayer) {
        this.player = player;
        this.collisionLayer = collisionLayer;
    }

    // Getter methods for player information
    public float getX() {
        return player.getPos().x;
    }

    public float getY() {
        return player.getPos().y;
    }

    public float getBoundsWidth() {
        return player.getBounds().width;
    }

    public float getBoundsHeight() {
        return 32; // Assuming default height
    }


    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    public boolean collidesRight() {
        for (float step = 4; step < getBoundsWidth()-4; step += (float) (collisionLayer.getTileWidth() - 8) / 4) {
            if (isCellBlocked(getX() + getBoundsWidth(), getY() + step)) {
                return true;
            }
        }
        return false;
    }

    public boolean collidesLeft() {
        for (float step = 4; step < getBoundsWidth()-4; step += (float) (collisionLayer.getTileWidth() - 8) / 4) {
            if (isCellBlocked(getX(), getY() + step)) {
                return true;
            }
        }
        return false;
    }

    public boolean collidesUp() {
        for (float step = 4; step < getBoundsWidth()-4; step += (float) (collisionLayer.getTileWidth() - 8) / 4) {
            if (isCellBlocked(getX() + step, getY() + getBoundsHeight())) {
                return true;
            }
        }
        return false;
    }

    public boolean collidesDown() {
        for (float step = 4; step < getBoundsWidth()-4; step += (float) (collisionLayer.getTileWidth() - 8) / 4) {
            if (isCellBlocked(getX() + step, getY())) {
                return true;
            }
        }
        return false;
    }
}