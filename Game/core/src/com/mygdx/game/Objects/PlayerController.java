package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntSet;
import com.mygdx.game.HesHustle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Utils.EventManager;
import com.mygdx.game.Utils.CollisionDetector;

import java.util.Objects;

/**Controller Class which the user interacts with the game through, has input processor which must be set active on each GameScreen
 *
 */
public class PlayerController extends GameObject implements InputProcessor {
    /**Width of character sprite
     */
    public static final float width = 32;
    /**Height of character sprite
     */
    public static final float height = 64;

    /**Enum of states the player character can be in
     */
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

    /**
     * Animation for each state
     */
    Animation IDLE_LEFT,
            IDLE_UP,
            IDLE_RIGHT,
            IDLE_DOWN,
            WALK_LEFT,
            WALK_UP,
            WALK_RIGHT,
            WALK_DOWN;
    /**Current state of player
     */
    public state Pstate;
    /**Current player animation
     */
    public Animation panim;
    /**Stores current texture region to be rendered
     *
     */
    TextureRegion txr;
    /**IntSet storing the key values of every key being pressed
     * (Still has trouble with more than 3 inputs)
     */
    private final IntSet downKeys = new IntSet(20);
    /**Stores the Key values of the direction keys you want to use
     */
    private final static int up=Input.Keys.W,down=Input.Keys.S,left=Input.Keys.A,right=Input.Keys.D;
    /**Event Manager used to interact with events*/
    public EventManager EM;
    /**Ref to nearest building (Activity) for interaction*/
    public Building nearBD;
    /**Detects player collision
     *
     */
    CollisionDetector collisionDetector;

    /**
     * Constructor of PlayerController
     * @param xPos Initial x position
     * @param yPos Initial y position
     * @param EM EventManager
     * @param collisionLayer Collision Layer of the Tiled Map
     */
    public PlayerController(float xPos, float yPos, EventManager EM, TiledMapTileLayer collisionLayer) {
        super(xPos,yPos,width,height);
        Pstate = state.IDLE_DOWN;
        loadAnims();
        panim = IDLE_DOWN;
        this.EM = EM;
        nearBD = null;


        // Initialize the detector
        collisionDetector = new CollisionDetector(this, collisionLayer);

    }

    /**
     * Function to generate all the Animations from the sprite sheets
     */
    private void loadAnims() {
        IDLE_LEFT = new Animation(new Texture(Gdx.files.internal("Amelia_idle_anim_16x16.png")),12,17,24,12);
        IDLE_UP = new Animation(new Texture(Gdx.files.internal("Amelia_idle_anim_16x16.png")),6,11,24,12);
        IDLE_RIGHT = new Animation(new Texture(Gdx.files.internal("Amelia_idle_anim_16x16.png")),0,5,24,12);
        IDLE_DOWN = new Animation(new Texture(Gdx.files.internal("Amelia_idle_anim_16x16.png")),18,23,24,12);
        WALK_LEFT = new Animation(new Texture(Gdx.files.internal("Amelia_run_16x16.png")),12,17,24,12);
        WALK_UP = new Animation(new Texture(Gdx.files.internal("Amelia_run_16x16.png")),6,11,24,12);
        WALK_RIGHT = new Animation(new Texture(Gdx.files.internal("Amelia_run_16x16.png")),0,5,24,12);
        WALK_DOWN = new Animation(new Texture(Gdx.files.internal("Amelia_run_16x16.png")),18,23,24,12);
    }

    /**
     * Update function extended from GameObject
     * @param deltaTime deltaTime
     */
    public void update (float deltaTime) {
        //get texture region to draw
        txr = getAnim(Pstate).GetFrame(deltaTime);
        //update position using normalised direction vector using vector addition (delta time in scalar)
        if (!EM.frozen){
            pos = pos.mulAdd(colCorrect(getDir()).nor(),deltaTime*300);
        }
    }
    public Vector2 getPos() { return pos; }

    public Rectangle getBounds() { return new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height); }


    public Vector2 getDir() {
        //find overall direction of inputs
        Vector2 dir = new Vector2(0,0);
        if (downKeys.contains(up)){
            dir.y = 1;
            Pstate = state.WALK_UP;
        }
        if (downKeys.contains(down)){
            dir.y = -1;
            Pstate = state.WALK_DOWN;
        }
        if (downKeys.contains(left)){
            dir.x = -1;
            Pstate = state.WALK_LEFT;
        }
        if (downKeys.contains(right)){
            dir.x = 1;
            Pstate = state.WALK_RIGHT;
        }
        return dir;
    }

    /**
     * Alters the direction vector to account for collisions
     * @param dir .
     * @return .
     */
    public Vector2 colCorrect(Vector2 dir)
    {
        Vector2 colDir = new Vector2(dir.x,dir.y);
        if ((dir.x==1) && collisionDetector.collidesRight()){colDir.x=0;}
        if ((dir.x==-1) && collisionDetector.collidesLeft()){colDir.x=0;}
        if ((dir.y==1) && collisionDetector.collidesUp()){colDir.y=0;}
        if ((dir.y==-1) && collisionDetector.collidesDown()){colDir.y=0;}
        return colDir;
    }

    /**
     * Method to return animation to play
     * @param Pstate .
     * @return Anim
     */
    public Animation getAnim(state Pstate)
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
    public void render(Camera projection, HesHustle game, ShapeRenderer shape){
        game.batch.begin();
        game.batch.draw(txr,pos.x, pos.y, bounds.width, bounds.height);
        game.batch.end();



    }

    /**
     * sets the nearest builiding value
     * @param BD .
     */
    public void setBD(Building BD){
        nearBD = BD;
    }
    public void interact(){
        if (nearBD!=null && !EM.frozen)
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

    /**
     * When multiple keys pressed it cancels out opposing directions, added back on key up
     * @param keycode .
     * @return .
     */
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
}
