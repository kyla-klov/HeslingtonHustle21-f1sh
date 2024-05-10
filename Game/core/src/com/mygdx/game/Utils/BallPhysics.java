package com.mygdx.game.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Objects.Ball;

import java.util.ArrayList;
import java.util.List;

public class BallPhysics {
    private static class CollisionInfo {
        private final float score;
        private final Vector2 velocity;
        private final Vector2 position;
        private final String type;

        //A class for storing information about a collision
        public CollisionInfo(float score, Vector2 velocity, Vector2 position, String type) {
            this.score = score;
            this.velocity = velocity;
            this.position = position;
            this.type = type;
        }
    }
    private final float reduction = .4f;
    private final List<Collider> colliders;
    private final Ball ball;

    public BallPhysics(Ball ball){
        this.colliders = new ArrayList<>();
        this.ball = ball;
        ball.setAcceleration(new Vector2(ball.getAcceleration().x, -2000));
    }

    public void addCollider(Collider collider){
        colliders.add(collider);
    }

    public void adjustBall(float deltaTime){
        Vector2 nextPos = ball.nextPosition(deltaTime);

        //Iterate until no collisions are detected
        while (detectCollision(nextPos)){
            nextPos = ball.nextPosition(deltaTime);
        }
    }

    private boolean detectCollision(Vector2 nextPos){
        Vector2 dir = nextPos.cpy().sub(ball.getPosition()).nor(); //direction vector of ball's trajectory
        float grad = dir.y/dir.x; //gradient of ball's trajectory

        CollisionInfo closest = null;
        //Loops through all stored colliders and finds closest collision
        for (Collider collider : colliders) {
            //checks for collisions with collider's horizontal surfaces
            for (Vector3 surface : collider.getHorSurfs()) {
                CollisionInfo collision = horizontalCollision(surface, nextPos, grad);
                if (closest == null || (collision != null && collision.score < closest.score)) {
                    closest = collision;
                }
            }

            //checks for collisions with collider's vertical surfaces
            for (Vector3 surface : collider.getVerSurfs()) {
                CollisionInfo collision = verticalCollision(surface, nextPos, grad);
                if (closest == null || (collision != null && collision.score < closest.score)) {
                    closest = collision;
                }
            }

            //checks for collisions with collider's points
            for (Vector2 point : collider.getPoints()) {
                CollisionInfo collision = pointCollision(point, nextPos, grad, false);
                if (closest == null || (collision != null && collision.score < closest.score)) {
                    closest = collision;
                }
            }

            //checks for collisions with collider's damped points
            if (collider.isDampedPointsActive()) {
                for (Vector2 point : collider.getDampedPoints()) {
                    CollisionInfo collision = pointCollision(point, nextPos, grad, true);
                    if (closest == null || (collision != null && collision.score < closest.score)) {
                        closest = collision;
                    }
                }
            }
        }

        //If closest collision was found, update ball's data
        if (closest != null){
            if (closest.type.equals("point")){
                closest.position.sub(dir);
            }
            ball.setPosition(closest.position);
            ball.setVelocity(closest.velocity);
            return true;
        }
        return false;
    }

    private CollisionInfo horizontalCollision(Vector3 surface, Vector2 nextPos, float grad){
        //Calculates the offset, which is just the distance from the centre of the ball to the impact point
        //Offset magnitude is always just the balls radius
        float offset = ball.getRadius();
        if (ball.getVelocity().y < 0){
            offset = -offset;
        }

        //return null if ball does not pass the surfaces y position
        if ((ball.getPosition().y + offset - surface.y) * (nextPos.y + offset - surface.y) > 0){
            return null;
        }

        //Calculates the x coordinate of when the ball passes the surfaces y coordinate
        float x = (surface.y - ball.getPosition().y - offset)/grad + ball.getPosition().x;

        //If the ball passes the wall and the y coordinate lies on the surface then there is a collision
        if (x > surface.x && x < surface.x + surface.z){
            float score = Math.abs((surface.y - ball.getPosition().y - offset)/ball.getVelocity().y); //Score for determining priority
            Vector2 fixedPosition = new Vector2(x, surface.y - offset); //Repositions the ball at point of collision
            Vector2 velocity = ball.getVelocity().scl(1f, -reduction); //New velocity of the ball
            return new CollisionInfo(score, velocity, fixedPosition, "horizontal");
        }
        return null;
    }

    private CollisionInfo verticalCollision(Vector3 surface, Vector2 nextPos, float grad){
        //Calculates the offset, which is just the distance from the centre of the ball to the impact point
        //Offset magnitude is always just the balls radius
        float offset = ball.getRadius();
        if (ball.getVelocity().x < 0){
            offset = -offset;
        }

        //return null if ball does not pass the surfaces x position
        if ((ball.getPosition().x + offset - surface.x) * (nextPos.x + offset - surface.x) > 0){
            return null;
        }

        //Calculates the y coordinate of when the ball passes the surfaces x coordinate
        float y = ball.getPosition().y + grad * (surface.x - ball.getPosition().x - offset);

        //If the ball passes the wall and the y coordinate lies on the surface then there is a collision
        if (y > surface.y && y < surface.y + surface.z){
            float score = Math.abs((surface.x - ball.getPosition().x - offset)/ball.getVelocity().x); //Score for determining priority
            Vector2 fixedPosition = new Vector2(surface.x - offset, y); //Repositions the ball at point of collision
            Vector2 velocity = ball.getVelocity().scl(-reduction, 1f); //New velocity of the ball
            return new CollisionInfo(score, velocity, fixedPosition, "vertical");
        }

        return null;
    }


    private CollisionInfo pointCollision(Vector2 point, Vector2 nextPos, float grad, boolean damped){
        //Centre the ball at (0, 0)
        Vector2 p = point.cpy().sub(ball.getPosition());
        Vector2 intercept = new Vector2();

        //Apply balls trajectory to the point p and calculate y-intercept
        float c = p.y - grad * p.x;

        //Calculate the 2 x coordinate where this linear line intercepts the ball
        float x1 = (float) (-grad * c + Math.sqrt((grad*grad + 1) * Math.pow(ball.getRadius(), 2) - c*c))/(grad*grad + 1);
        float x2 = (float) (-grad * c - Math.sqrt((grad*grad + 1) * Math.pow(ball.getRadius(), 2) - c*c))/(grad*grad + 1);

        //Calculate which intercept is closest to the point p
        intercept.x = x1;
        if (Math.abs(x1 - p.x) > Math.abs(x2 - p.x)) intercept.x = x2;
        intercept.y = grad * intercept.x + c; //y coordinate of point of interception

        //Calculate the distance the ball would have to travel to collide with p
        float dist = intercept.x - p.x;
        //Calculate the actual distance the ball has travelled
        float distTravelled = ball.getPosition().x - nextPos.x;

        //If the ball has travelled at least dist, then they have collided
        if (dist * distTravelled > 0 && Math.abs(dist) <= Math.abs(distTravelled)){
            float score = Math.abs(dist / ball.getVelocity().x); //Score for determining priority
            Vector2 fixedPosition = point.cpy().sub(intercept); //Repositions the ball at point of collision
            Vector2 velocity = bounce(-intercept.x/intercept.y, damped); //New velocity of the ball
            return new CollisionInfo(score, velocity, fixedPosition, "point");
        }
        return null;
    }

    private Vector2 bounce(float grad, boolean damped) {
        float reduction = this.reduction;
        if (damped) {
            reduction /= 10; //Reduce reduction if the point is damped
        }

        //Calculate the normal vector from the collision point
        Vector2 n = new Vector2(grad, -1);
        n.nor(); //Normalise the vector

        //Calculate the tangent vector from the collision point
        Vector2 t = new Vector2(1, grad);
        t.nor(); //Normalise the vector

        //Calculate the components of the velocity in the normal and tangent directions
        float v_dot_n = ball.getVelocity().dot(n);
        float v_dot_t = ball.getVelocity().dot(t);

        //Apply the changes to these components and calculate new velocity
        Vector2 newNormalComponent = n.scl(-v_dot_n * reduction);
        Vector2 newTangentialComponent = t.scl(v_dot_t);

        //Return the added components
        return newNormalComponent.add(newTangentialComponent);
    }

}
