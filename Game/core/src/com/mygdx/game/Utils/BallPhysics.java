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

        public CollisionInfo(float score, Vector2 velocity, Vector2 position, String type) {
            this.score = score;
            this.velocity = velocity;
            this.position = position;
            this.type = type;
        }
    }
    private final float reduction = 0.4f;
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
        while (detectCollision(nextPos)){
            nextPos = ball.nextPosition(deltaTime);
        }
    }

    private boolean detectCollision(Vector2 nextPos){
        Vector2 dir = nextPos.cpy().sub(ball.getPosition()).nor();
        float grad = dir.y/dir.x;
        CollisionInfo closest = null;
        for (Collider collider : colliders) {
            for (Vector3 surface : collider.getHorSurfs()) {
                CollisionInfo collision = horizontalCollision(surface, nextPos, grad);
                if (closest == null || (collision != null && collision.score < closest.score)) {
                    closest = collision;
                }
            }
            for (Vector3 surface : collider.getVerSurfs()) {
                CollisionInfo collision = verticalCollision(surface, nextPos, grad);
                if (closest == null || (collision != null && collision.score < closest.score)) {
                    closest = collision;
                }
            }

            for (Vector2 point : collider.getPoints()) {
                CollisionInfo collision = pointCollision(point, nextPos, grad, false);
                if (closest == null || (collision != null && collision.score < closest.score)) {
                    closest = collision;
                }
            }

            if (collider.isDampedPointsActive()) {
                for (Vector2 point : collider.getDampedPoints()) {
                    CollisionInfo collision = pointCollision(point, nextPos, grad, true);
                    if (closest == null || (collision != null && collision.score < closest.score)) {
                        closest = collision;
                    }
                }
            }
        }
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
        if ((surface.y - ball.getPosition().y) * ball.getVelocity().y < 0) return null;
        float offset = ball.getRadius();
        if (ball.getVelocity().y < 0){
            offset = -offset;
        }
        float x = (surface.y - ball.getPosition().y - offset)/grad + ball.getPosition().x;
        if (x > surface.x && x < surface.x + surface.z && (ball.getPosition().y + offset - surface.y) * (nextPos.y + offset - surface.y) < 0){
            float score = Math.abs((surface.y - ball.getPosition().y - offset)/ball.getVelocity().y);
            Vector2 fixedPosition = new Vector2(x, surface.y - offset);
            Vector2 velocity = ball.getVelocity().scl(1f, -reduction);
            return new CollisionInfo(score, velocity, fixedPosition, "horizontal");
        }
        return null;
    }

    private CollisionInfo verticalCollision(Vector3 surface, Vector2 nextPos, float grad){
        if ((surface.x - ball.getPosition().x) * ball.getVelocity().x < 0) return null;
        float offset = ball.getRadius();
        if (ball.getVelocity().x < 0){
            offset = -offset;
        }
        float y = ball.getPosition().y + grad * (surface.x - ball.getPosition().x - offset);
        if (y > surface.y && y < surface.y + surface.z && (ball.getPosition().x + offset - surface.x) * (nextPos.x + offset - surface.x) < 0){
            float score = Math.abs((surface.x - ball.getPosition().x - offset)/ball.getVelocity().x);
            Vector2 fixedPosition = new Vector2(surface.x - offset, y);
            Vector2 velocity = ball.getVelocity().scl(-reduction, 1f);
            return new CollisionInfo(score, velocity, fixedPosition, "vertical");
        }

        return null;
    }


    private CollisionInfo pointCollision(Vector2 point, Vector2 nextPos, float grad, boolean damped){
        Vector2 p = point.cpy().sub(ball.getPosition());
        Vector2 intercept = new Vector2();
        float c = p.y - grad * p.x;
        float x1 = (float) (-grad * c + Math.sqrt((grad*grad + 1) * Math.pow(ball.getRadius(), 2) - c*c))/(grad*grad + 1);
        float x2 = (float) (-grad * c - Math.sqrt((grad*grad + 1) * Math.pow(ball.getRadius(), 2) - c*c))/(grad*grad + 1);
        intercept.x = x1;
        if (Math.abs(x1 - p.x) > Math.abs(x2 - p.x)) intercept.x = x2;
        intercept.y = grad * intercept.x + c;
        float dist = intercept.x - p.x;
        float distTravelled = ball.getPosition().x - nextPos.x;
        if (dist * distTravelled > 0 && Math.abs(dist) <= Math.abs(distTravelled)){
            float score = Math.abs(dist / ball.getVelocity().x);
            Vector2 fixedPosition = point.cpy().sub(intercept);
            Vector2 velocity = bounce(-intercept.x/intercept.y, damped);
            return new CollisionInfo(score, velocity, fixedPosition, "point");
        }
        return null;
    }

    private Vector2 bounce(float grad, boolean damped) {
        float reduction = this.reduction;
        if (damped) {
            reduction /= 10;
        }
        double normFactor = Math.sqrt(grad * grad + 1);
        Vector2 n = new Vector2((float) (grad / normFactor), (float) (-1 / normFactor));

        Vector2 t = new Vector2((float) (1 / normFactor), (float) (grad / normFactor));

        float v_dot_n = ball.getVelocity().dot(n);
        float v_dot_t = ball.getVelocity().dot(t);

        Vector2 newNormalComponent = n.scl(-v_dot_n * reduction);
        Vector2 newTangentialComponent = t.scl(v_dot_t);

        return newNormalComponent.add(newTangentialComponent);
    }

}
