package com.mygdx.game.Objects;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Objects.GameObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.Objects.GameObject;
import com.mygdx.game.Objects.Building;
import com.mygdx.game.Objects.PlayerController;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.List;

public class CollisionDetector {
    private List<GameObject> objects;
    private TiledMapTileLayer collisionLayer;
    private PlayerController player;

    public void registerCollisions(PlayerController player, TiledMapTileLayer collisionLayer) {
        this.player = player;
        this.collisionLayer = collisionLayer;
    }

    public void detectCollisions() {
        // If time map is null throw error (Testing :P)
        if (collisionLayer == null || player == null) {
            throw new NullPointerException("TileMapTileLayer is null in CollisionDetector");
        }

        // Get player info (Position, Previous position, bounds, velocity)
        float playerX = player.getPos().x, playerY = player.getPos().y;
        float prevX = player.getPrev().x, prevY = player.getPrev().y;
        float boundsX = player.getBounds().x, boundsY = player.getBounds().y;
        float boundsWidth = player.getBounds().width, boundsHeight = player.getBounds().height;
        float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
        Vector2 dir = player.getDir();

        //Collision on X/Y
        boolean collisionX = false;
        boolean collisionY = false;

        // Players direction
        if (dir.x < 0) {
            // Moving left
            System.out.println("Left");

            // Check tile top left of character
            collisionX = collisionLayer.getCell((int) (playerX / tileWidth), (int) ((playerY + boundsHeight) / tileHeight)).
                    getTile().getProperties().containsKey("blocked");
            // Check tile middle left of character (if no previous collision)
            if (!collisionX)
                collisionX = collisionLayer.getCell((int) (playerX / tileWidth), (int) ((playerY + boundsHeight / 2) / tileHeight)).
                        getTile().getProperties().containsKey("blocked");

            // Check tile bottom left of character (if no previous collision)
            if (!collisionX)
                collisionX = collisionLayer.getCell((int) (playerX / tileWidth), (int) (playerY / tileHeight)).
                        getTile().getProperties().containsKey("blocked");
        } else if (dir.x > 0) {
            // Moving right
            System.out.println("Right");

            // Check tile top right of character
            collisionX = collisionLayer.getCell((int) ((playerX + boundsWidth) / tileWidth), (int) ((playerY + boundsHeight) / tileHeight)).
                    getTile().getProperties().containsKey("blocked");
            // Check tile middle right of character (if no previous collision)
            if (!collisionX)
                collisionX = collisionLayer.getCell((int) ((playerX + boundsWidth) / tileWidth), (int) ((playerY + boundsHeight / 2) / tileHeight)).
                        getTile().getProperties().containsKey("blocked");

            // Check tile bottom right of character (if no previous collision)
            if (!collisionX)
                collisionX = collisionLayer.getCell((int) ((playerX + boundsWidth) / tileWidth), (int) (playerY / tileHeight)).
                        getTile().getProperties().containsKey("blocked");
        }

        // Deal with x collision
        if(collisionX) {
            player.stopMoving();
        }

        if (dir.y < 0) {
            // Moving down
            System.out.println("Down");

            // Check tile bottom left
            collisionY = collisionLayer.getCell((int) (playerX / tileWidth), (int) (playerY / tileHeight)).
                    getTile().getProperties().containsKey("blocked");
            // Check tile bottom middle (if no previous collision)
            if (!collisionY)
                collisionY = collisionLayer.getCell((int) ((playerX + boundsWidth / 2) / tileWidth), (int) (playerY / tileHeight)).
                        getTile().getProperties().containsKey("blocked");

            // Check tile bottom right (if no previous collision)
            if (!collisionY)
                collisionY = collisionLayer.getCell((int) ((playerX + boundsWidth) / tileWidth), (int) (playerY / tileHeight)).
                        getTile().getProperties().containsKey("blocked");
        } else if (dir.y > 0) {
            // Moving up
            System.out.println("Up");

            // Check tile top left
            collisionY = collisionLayer.getCell((int) (playerX / tileWidth), (int) ((playerY + boundsHeight) / tileHeight)).
                    getTile().getProperties().containsKey("blocked");
            // Check tile top middle (if no previous collision)
            if (!collisionY)
                collisionY = collisionLayer.getCell((int) ((playerX + boundsWidth / 2) / tileWidth), (int) ((playerY + boundsHeight) / tileHeight)).
                        getTile().getProperties().containsKey("blocked");

            // Check tile top right (if no previous collision)
            if (!collisionY)
                collisionY = collisionLayer.getCell((int) ((playerX + boundsWidth) / tileWidth), (int) ((playerY + boundsHeight) / tileHeight)).
                        getTile().getProperties().containsKey("blocked");
        }

        // Deal with y collision
        if(collisionY) {
            player.stopMoving();
        }
    }

//    private void handleCollision(GameObject obj1, GameObject obj2) {
//        // Player + Building collision
//        if (obj1 instanceof PlayerController && obj2 instanceof Building) {
//            PlayerController player = (PlayerController) obj1;
//            Building building = (Building) obj2;
//            // Stop player movement and enable an interaction
//
//        }
//
//        else if (obj1 instanceof Building && obj2 instanceof PlayerController) {
//            handleCollision(obj2, obj1);
//        }
//    }
}