package com.mygdx.game.Utils;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Objects.PlayerController;

/**
 * Uses the collision layer in the TileMap file to generate collisions
 */
public class CollisionDetector {
    private TiledMapTileLayer collisionLayer;
    private PlayerController player;
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
        boolean collides = false;

        for (float step = 4; step < getBoundsWidth()-4; step += (collisionLayer.getTileWidth()-8) / 4) {
            if (collides = isCellBlocked(getX() + getBoundsWidth(), getY() + step)) {
                break;
            }
        }
        return collides;
    }

    public boolean collidesLeft() {
        boolean collides = false;

        for (float step = 4; step < getBoundsWidth()-4; step += (collisionLayer.getTileWidth()-8) / 4) {
            if (collides = isCellBlocked(getX(), getY() + step)) {
                break;
            }
        }

        return collides;
    }

    public boolean collidesUp() {
        boolean collides = false;

        for (float step = 4; step < getBoundsWidth()-4; step += (collisionLayer.getTileWidth()-8) / 4) {
            if (collides = isCellBlocked(getX() + step, getY() + getBoundsHeight())) {
                break;
            }
        }

        return collides;
    }

    public boolean collidesDown() {
        boolean collides = false;

        for (float step = 4; step < getBoundsWidth()-4; step += (collisionLayer.getTileWidth()-8) / 4) {
            if (collides = isCellBlocked(getX() + step, getY())) {
                break;
            }
        }

        return collides;
    }
}