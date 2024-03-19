package com.mygdx.game.Objects;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Objects.GameObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.Objects.GameObject;
import com.mygdx.game.Objects.Building;
import com.mygdx.game.Objects.PlayerController;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import java.util.List;

public class CollisionDetector {
    private List<GameObject> objects;
    private TiledMapTileLayer collisionLayer;
    private PlayerController player;

    public void registerCollisions(PlayerController player, TiledMapTileLayer collisionLayer) {
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

        for (float step = 0; step < getBoundsHeight(); step += collisionLayer.getTileHeight() / 4) {
            if (collides = isCellBlocked(getX() + getBoundsWidth(), getY() + step)) {
                break;
            }
        }

        return collides;
    }

    public boolean collidesLeft() {
        boolean collides = false;

        for (float step = 0; step < getBoundsHeight(); step += collisionLayer.getTileHeight() / 4) {
            if (collides = isCellBlocked(getX(), getY() + step)) {
                break;
            }
        }

        return collides;
    }

    public boolean collidesTop() {
        boolean collides = false;

        for (float step = 0; step < getBoundsWidth(); step += collisionLayer.getTileWidth() / 4) {
            if (collides = isCellBlocked(getX() + step, getY() + getBoundsHeight())) {
                break;
            }
        }

        return collides;
    }

    public boolean collidesBottom() {
        boolean collides = false;

        for (float step = 0; step < getBoundsWidth(); step += collisionLayer.getTileWidth() / 4) {
            if (collides = isCellBlocked(getX() + step, getY())) {
                break;
            }
        }

        return collides;
    }



    public void detectCollisions() {
        // If time map is null throw error (Testing :P)
        if (collisionLayer == null || player == null) {
            throw new NullPointerException("TileMapTileLayer is null in CollisionDetector");
        }

        // Get player info (Position, Previous position, bounds, velocity)
        Vector2 dir = player.getDir();


        //Collision on X/Y
        boolean collisionX = false;
        boolean collisionY = false;

        // Players direction
        if (dir.x < 0) {
            // Moving left
            collisionX = collidesLeft();
        } else if (dir.x > 0) {
            // Moving right
            System.out.println("Right");
            collisionX = collidesRight();
        }

// Deal with x collision
        if (collisionX) {
            player.stopMoving();
        }

        if (dir.y < 0) {
            collisionY = collidesBottom();
        } else if (dir.y > 0) {
            // Moving up
            collisionY = collidesTop();
        }
// Deal with y collision
        if (collisionY) {
            player.stopMoving();
        }
    }
}