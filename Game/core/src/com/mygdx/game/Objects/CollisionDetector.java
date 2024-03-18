package com.mygdx.game.Objects;

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
    private TiledMapTileLayer tileMapLayer;

    public void registerObjects(List<GameObject> objects, TiledMapTileLayer tileMapLayer) {
        this.objects = objects;
        this.tileMapLayer = tileMapLayer;
    }

    public void detectCollisions() {
        for (GameObject object : objects) {
            if (object instanceof PlayerController) {
                PlayerController player = (PlayerController) object;
                // Get the player's bounding box
                float playerX = player.pos.x;
                float playerY = player.pos.y;
                float playerWidth = player.width;
                float playerHeight = player.height;

                // Calculate the tiles that the player overlaps
                int startX = (int) (playerX / tileMapLayer.getTileWidth());
                int endX = (int) ((playerX + playerWidth) / tileMapLayer.getTileWidth());
                int startY = (int) (playerY / tileMapLayer.getTileHeight());
                int endY = (int) ((playerY + playerHeight) / tileMapLayer.getTileHeight());

                // Check for collisions with blocked tiles
                for (int y = startY; y <= endY; y++) {
                    for (int x = startX; x <= endX; x++) {
                        if (tileMapLayer.getCell(x, y) != null && tileMapLayer.getCell(x, y).getTile() != null &&
                                tileMapLayer.getCell(x, y).getTile().getProperties().containsKey("blocked")) {
                            // Trigger collision response in player
                            System.out.println("Collision");
                        }
                    }
                }
            }
        }
    }

    private void handleCollision(GameObject obj1, GameObject obj2) {
        // Player + Building collision
        if (obj1 instanceof PlayerController && obj2 instanceof Building) {
            PlayerController player = (PlayerController) obj1;
            Building building = (Building) obj2;
            // Stop player movement and enable an interaction

        }

        else if (obj1 instanceof Building && obj2 instanceof PlayerController) {
            handleCollision(obj2, obj1);
        }
    }
}