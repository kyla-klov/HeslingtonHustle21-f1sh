package com.mygdx.game.Objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Utils.Collider;

public class BallHoop {
    private final Vector2 position;
    private final Collider collider;
    private final float hoopWidth;
    private final float edgeWidth;

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
                new Vector2(x + (hoopWidth + edgeWidth - hoopWidth / 110f * 14), y - height),

                new Vector2(x + hoopWidth / 110f * 24, y - height / 20 * 30),
                new Vector2(x + (hoopWidth + edgeWidth - hoopWidth / 110f * 18), y - height / 20 * 30),

                new Vector2(x + hoopWidth / 110f * 24, y - height / 20 * 40),
                new Vector2(x + (hoopWidth + edgeWidth - hoopWidth / 110f * 18.5f), y - height / 20 * 40),

                new Vector2(x + hoopWidth / 110f * (hoopWidth / 110f * 26), y - height / 20 * 50),
                new Vector2(x + (hoopWidth + edgeWidth - hoopWidth / 110f * 20), y - height / 20 * 50),

                new Vector2(x + hoopWidth / 110f * 27, y - height / 20 * 70),
                new Vector2(x + (hoopWidth + edgeWidth - hoopWidth / 110f * 21), y - height / 20 * 70)
        };

        for (Vector2 vector : vectors) {
            collider.addDampedPoint(vector);
        }
    }

    public boolean isGoal(Ball ball, float deltaTime){
        if (ball.getPosition().y > position.y && ball.getPosition().x > position.x + edgeWidth && ball.getPosition().x <= position.x + edgeWidth + hoopWidth) {
            collider.activateDampedPoints();
            return ball.nextPosition(deltaTime*3).y <= position.y;
        }
        collider.deactivateDampedPoints();
        return false;
    }

    public Collider getCollider(){
        return collider;
    }
}
