package com.mygdx.game.Utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Collider {
    private final List<Vector3> horSurfs;
    private final List<Vector3> vertSurfs;
    private final List<Vector2> points;
    private final List<Vector2> dampedPoints;
    private boolean dampedPointsActive;

    public Collider() {
        horSurfs = new ArrayList<>();
        vertSurfs = new ArrayList<>();
        points = new ArrayList<>();
        dampedPoints = new ArrayList<>();
    }

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
