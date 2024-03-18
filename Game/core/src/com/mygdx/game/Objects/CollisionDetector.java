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
    private TiledMapTileLayer tileMapLayer;
    private PlayerController player;

    public void registerCollisions(PlayerController player, TiledMapTileLayer tileMapLayer) {
        this.player = player;
        this.tileMapLayer = tileMapLayer;
    }

    public void detectCollisions() {
        // If time map is null throw error (Testing :P)
        if (tileMapLayer == null || player == null) {
            throw new NullPointerException("TileMapTileLayer is null in CollisionDetector");
        }

        // Get player info (Position, Previous position, bounds, velocity)
        float playerX = player.getPos().x;
        float playerY = player.getPos().y;
        float prevX = player.getPrev().x;
        float prevY = player.getPrev().y;
        float boundsX = player.getBounds().x;
        float boundsY = player.getBounds().y;
        Vector2 dir = player.getDir();

        // Players direction
        if (dir.x > 0) {
            // Moving right
            System.out.println("Right");
        } else if (dir.x < 0) {
            // Moving left
            System.out.println("Left");
        }

        if (dir.y > 0) {
            // Moving up
            System.out.println("Up");
        } else if (dir.y < 0) {
            // Moving down
            System.out.println("Down");
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