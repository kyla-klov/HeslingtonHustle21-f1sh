package com.mygdx.game.Objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Utils.Collider;

/**
 * The BallHoop class represents a basketball hoop with collision detection capabilities.
 * It manages the position, dimensions, and collision detection for the hoop.
 */
public class BallHoop {
    private final Vector2 position;
    private final Collider collider;
    private final float hoopWidth;
    private final float edgeWidth;

    /**
     * Constructs a BallHoop with the specified position, dimensions, and height.
     *
     * @param x         the x position of the hoop
     * @param y         the y position of the hoop
     * @param hoopWidth the width of the hoop
     * @param edgeWidth the width of the edges
     * @param height    the height of the hoop
     */
    public BallHoop(float x, float y, float hoopWidth, float edgeWidth, float height) {
        this.position = new Vector2(x, y);
        this.collider = new Collider();
        this.hoopWidth = hoopWidth;
        this.edgeWidth = edgeWidth;
        collider.addBoxCollider(new Rectangle(x, y, edgeWidth, height));
        collider.addBoxCollider(new Rectangle(x + edgeWidth + hoopWidth, y, edgeWidth, height));
        collider.addBoxCollider(new Rectangle(x + hoopWidth + edgeWidth * 2, y-height*2.2f, edgeWidth*1.4f, height * 11.4f));


        Vector2[] vectors = new Vector2[] {
                new Vector2(x + hoopWidth / 110f * 20, y - height),
                new Vector2(x + (hoopWidth + edgeWidth - hoopWidth / 110f * 13), y - height),

                new Vector2(x + hoopWidth / 110f * 24, y - height / 20 * 30),
                new Vector2(x + (hoopWidth + edgeWidth - hoopWidth / 110f * 14), y - height / 20 * 30),
        };

        for (Vector2 vector : vectors) {
            collider.addDampedPoint(vector);
        }
    }

    /**
     * Checks if the ball has scored a goal by passing through the hoop.
     *
     * @param ball      the ball to check
     * @param deltaTime the time elapsed since the last update
     * @return true if the ball has scored a goal, false otherwise
     */
    public boolean isGoal(Ball ball, float deltaTime){
        if (ball.getPosition().y > position.y && ball.getPosition().x > position.x + edgeWidth && ball.getPosition().x <= position.x + edgeWidth + hoopWidth) {
            collider.activateDampedPoints();
            return ball.nextPosition(deltaTime*3).y <= position.y;
        }
        collider.deactivateDampedPoints();
        return false;
    }

    /**
     * Gets the collider for the hoop.
     *
     * @return the collider
     */
    public Collider getCollider(){
        return collider;
    }
}
