package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Objects.Ball;
import com.mygdx.game.Utils.BallPhysics;

import java.util.ArrayList;
import java.util.List;

public class BasketBallScreen implements Screen, InputProcessor {
    List<Vector3> horSurf;
    List<Vector3> vertSurf;
    List<Rectangle> rectObjects;
    Ball ball;
    BallPhysics ballPhysics;
    ShapeRenderer shapeRenderer;

    public BasketBallScreen(HesHustle game) {
        horSurf = new ArrayList<>();
        vertSurf = new ArrayList<>();
        rectObjects = new ArrayList<>();
        horSurf.add(new Vector3(-100,0,2000));
        ball = new Ball(300, 500, 50);
        rectObjects.add(new Rectangle(1300, 300, 10, 100));
        rectObjects.add(new Rectangle(1415, 300, 10, 100));
        rectObjects.add(new Rectangle(1425, 400, 10, 100));
        for (Rectangle r : rectObjects) {
            addRectangleSurfaces(r);
        }
        ballPhysics = new BallPhysics(horSurf, vertSurf, ball);
        shapeRenderer = new ShapeRenderer();
    }

    public void addRectangleSurfaces(Rectangle obj){
        horSurf.add(new Vector3(obj.x, obj.y, obj.width));
        horSurf.add(new Vector3(obj.x, obj.y + obj.height, obj.width));
        vertSurf.add(new Vector3(obj.x, obj.y, obj.height));
        vertSurf.add(new Vector3(obj.x + obj.width, obj.y, obj.height));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ballPhysics.adjustBall(delta);
        ball.update(delta);
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // or ShapeType.Line for an outline
        for (Rectangle r : rectObjects) {
            shapeRenderer.rect(r.x, r.y, r.width, r.height);
        }
        shapeRenderer.end();
        ball.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE){
            ball.setVelocity(new Vector2(300, 800));
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
