package com.mygdx.game.Utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * The Collider class represents a collection of surfaces and points that can be used to detect collisions.
 * It supports adding box colliders, individual surfaces, and damped points.
 */
public class Collider {
    private final List<Vector3> horSurfs;
    private final List<Vector3> vertSurfs;
    private final List<Vector2> points;
    private final List<Vector2> dampedPoints;
    private boolean dampedPointsActive;


    /**
     * Constructs a Collider with empty lists of surfaces and points.
     */
    public Collider() {
        horSurfs = new ArrayList<>();
        vertSurfs = new ArrayList<>();
        points = new ArrayList<>();
        dampedPoints = new ArrayList<>();
    }

    /**
     * Adds a box collider to the collection.
     *
     * @param boxCollider the rectangle representing the box collider
     */
    public void addBoxCollider(Rectangle boxCollider){
        horSurfs.add(new Vector3(boxCollider.x, boxCollider.y, boxCollider.width));
        horSurfs.add(new Vector3(boxCollider.x, boxCollider.y + boxCollider.height, boxCollider.width));
        vertSurfs.add(new Vector3(boxCollider.x, boxCollider.y, boxCollider.height));
        vertSurfs.add(new Vector3(boxCollider.x + boxCollider.width, boxCollider.y, boxCollider.height));

        points.add(new Vector2(boxCollider.x, boxCollider.y));
        points.add(new Vector2(boxCollider.x, boxCollider.y + boxCollider.height));
        points.add(new Vector2(boxCollider.x + boxCollider.width, boxCollider.y));
        points.add(new Vector2(boxCollider.x + boxCollider.width, boxCollider.y + boxCollider.height));
    }

    /**
     * Adds a surface to the collection.
     *
     * @param surface the vector representing the surface
     * @param isHor   true if the surface is horizontal, false if vertical
     */
    public void addSurface(Vector3 surface, boolean isHor){
        points.add(new Vector2(surface.x, surface.y));
        if (isHor){
            horSurfs.add(surface);
            points.add(new Vector2(surface.x + surface.z, surface.y));
        }
        else{
            vertSurfs.add(surface);
            points.add(new Vector2(surface.x, surface.y + surface.z));
        }
    }

    /**
     * Adds a damped point to the collection.
     *
     * @param point the vector representing the damped point
     */
    public void addDampedPoint(Vector2 point){
        dampedPoints.add(point);
    }

    public List<Vector3> getHorSurfs(){
        return this.horSurfs;
    }

    public List<Vector3> getVerSurfs(){
        return this.vertSurfs;
    }

    public List<Vector2> getPoints(){
        return this.points;
    }

    public List<Vector2> getDampedPoints(){
        return this.dampedPoints;
    }

    public void activateDampedPoints() {
        dampedPointsActive = true;
    }

    public void deactivateDampedPoints() {
        dampedPointsActive = false;
    }

    public boolean isDampedPointsActive() {
        return dampedPointsActive;
    }
}
