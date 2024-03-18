package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntSet;
import com.mygdx.game.HesHustle;

import java.util.Objects;

public class PlayerController extends GameObject implements InputProcessor {
    public static final float width = 32;
    public static final float height = 64;

    public enum state {
        IDLE_LEFT,
        IDLE_UP,
        IDLE_RIGHT,
        IDLE_DOWN,
        WALK_LEFT,
        WALK_UP,
        WALK_RIGHT,
        WALK_DOWN,
    }
    Anim IDLE_LEFT,
            IDLE_UP,
            IDLE_RIGHT,
            IDLE_DOWN,
            WALK_LEFT,
            WALK_UP,
            WALK_RIGHT,
            WALK_DOWN;
    public state Pstate;
    public Anim Panim;
    TextureRegion txr;
    private IntSet downKeys = new IntSet(20);
    private final static int up=Input.Keys.W;
    private final static int down=Input.Keys.S;
    private final static int left=Input.Keys.A;
    private final static int right=Input.Keys.D;
    private boolean shouldStopMoving = false;
    private Vector2 previousPosition;
    public EventManager EM;
    public Building nearBD;
    public PlayerController(float xPos, float yPos, EventManager EM)
    {

        super(xPos,yPos,width,height);
        Pstate = state.IDLE_DOWN;
        loadAnims();
        Panim = IDLE_DOWN;
        this.EM = EM;
        nearBD = null;

        previousPosition = new Vector2(xPos, yPos);
    }
    public void loadAnims() {
        IDLE_LEFT = new Anim(new Texture(Gdx.files.internal("Amelia_idle_anim_16x16.png")),12,17,24,12);
        IDLE_UP = new Anim(new Texture(Gdx.files.internal("Amelia_idle_anim_16x16.png")),6,11,24,12);
        IDLE_RIGHT = new Anim(new Texture(Gdx.files.internal("Amelia_idle_anim_16x16.png")),0,5,24,12);
        IDLE_DOWN = new Anim(new Texture(Gdx.files.internal("Amelia_idle_anim_16x16.png")),18,23,24,12);
        WALK_LEFT = new Anim(new Texture(Gdx.files.internal("Amelia_run_16x16.png")),12,17,24,12);
        WALK_UP = new Anim(new Texture(Gdx.files.internal("Amelia_run_16x16.png")),6,11,24,12);
        WALK_RIGHT = new Anim(new Texture(Gdx.files.internal("Amelia_run_16x16.png")),0,5,24,12);
        WALK_DOWN = new Anim(new Texture(Gdx.files.internal("Amelia_run_16x16.png")),18,23,24,12);
    }

    public void update (float deltaTime) {
        //Update previous position
        previousPosition.set(pos.x, pos.y);

        bounds.x = pos.x - bounds.width / 2;
        bounds.y = pos.y - bounds.height / 2;
        txr = getAnim(Pstate).GetFrame(deltaTime);
        pos = pos.mulAdd(getDir().nor(),deltaTime*200);

        EM.update(deltaTime);
    }

    public void stopMoving() {
        pos.set(previousPosition);
    }

    public Vector2 getPreviousPosition() {
        return previousPosition;
    }

    public Vector2 getDir() {
        //find overall direction of inputs and normalize vector2
        Vector2 dir = new Vector2(0,0);
        if (downKeys.contains(up)){ dir.y = 1;}
        if (downKeys.contains(down)){ dir.y = -1;}
        if (downKeys.contains(left)){ dir.x = -1;}
        if (downKeys.contains(right)){ dir.x = 1;}
        return dir;
    }
    public Anim getAnim(state Pstate)
    {
        Vector2 dir = getDir();
        if (Objects.equals(dir, new Vector2(0, 0)))
        {
            switch (Pstate)
            {
                case WALK_UP:
                case IDLE_UP:
                    return IDLE_UP;
                case WALK_DOWN:
                case IDLE_DOWN:
                    return IDLE_DOWN;
                case WALK_RIGHT:
                case IDLE_RIGHT:
                    return IDLE_RIGHT;
                case WALK_LEFT:
                case IDLE_LEFT:
                    return IDLE_LEFT;
            }
        }
        else
        {
            if (dir.x == 1){
                return WALK_RIGHT;
            } else if (dir.x == -1){
                return WALK_LEFT;
            }
            if (dir.y == 1){
            return WALK_UP;
            } else if (dir.y == -1){
            return WALK_DOWN;
            }

        }
        return null;
    }
    @Override
    public void render(Matrix4 projection, HesHustle game, ShapeRenderer shape){
        //player box
        /*
        shape.setProjectionMatrix(projection);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.CHARTREUSE);
        shape.rect(pos.x, pos.y, bounds.width, bounds.height);
        shape.end();*/
        game.batch.begin();
        game.batch.draw(txr,pos.x, pos.y, bounds.width, bounds.height);
        game.batch.end();



    }
    public void setBD(Building BD){
        nearBD = BD;
    }
    public void interact(){
        if (nearBD!=null)
        {
            EM.interact(nearBD.name);
        }

    }

    @Override
    public boolean keyDown(int keycode) {

        downKeys.add(keycode);
        if (keycode == Input.Keys.SPACE){
            interact();
        }
        if (downKeys.size >= 2){

            return onMultipleKeysDown(keycode);
        }
        return true;

    }
    public boolean onMultipleKeysDown(int keycode){
        if ((keycode==left && downKeys.contains(right)) || (keycode==right && downKeys.contains(left))) {
            downKeys.remove(left);
            downKeys.remove(right);

            return true;
        }
        else if ((keycode==up && downKeys.contains(down)) || (keycode==down && downKeys.contains(up))) {
            downKeys.remove(up);
            downKeys.remove(down);

            return true;
        } else {
            downKeys.add(keycode);

            return true;
        }

    }

    @Override
    public boolean keyUp(int keycode) {
        if (downKeys.contains(keycode))
        {
            downKeys.remove(keycode);
            return true;
        }
        else
        {
            switch(keycode)
            {
                case up:
                    downKeys.add(down);
                    break;
                case down:
                    downKeys.add(up);
                    break;
                case left:
                    downKeys.add(right);
                    break;
                case right:
                    downKeys.add(left);
                    break;
            }
        }
        return true;
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

    @Override
    public void Dispose() {

    }
}
