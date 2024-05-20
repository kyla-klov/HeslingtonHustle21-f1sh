package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Utils.EventManager;
import com.mygdx.game.Utils.CollisionDetector;

import java.util.Objects;

/**Controller Class which the user interacts with the game through, has input processor which must be set active on each GameScreen
 *
 */
public class PlayerController {
    /**Width of character sprite
     */
    private final float width = 32;
    /**Height of character sprite
     */
    private final float height = 64;

    public Vector2 pos;

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

    /**Event Manager used to interact with events*/
    public EventManager EM;
    /**Ref to nearest building (Activity) for interaction*/
    public Building nearBD;

    private float distanceTravelled;
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
        pos = new Vector2(xPos,yPos);
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
        txr = getAnim(Pstate).getFrame(deltaTime);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            interact();
        }
        //update position using normalised direction vector using vector addition (delta time in scalar)
        if (EM.notFrozen()){
            Vector2 newPos = pos.cpy().mulAdd(colCorrect(getDir()).nor(),deltaTime*300);
            float distance = pos.cpy().sub(newPos).len();
            distanceTravelled += distance;
            pos = newPos;

        }
    }
    public Vector2 getPos() { return pos; }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    public Vector2 getDir() {
        //find overall direction of inputs
        Vector2 dir = new Vector2(0,0);
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            dir.y++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            dir.y--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            dir.x--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            dir.x++;
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
    public void render(SpriteBatch batch){
        batch.draw(txr,pos.x, pos.y, width, height);
    }

    /**
     * sets the nearest builiding value
     * @param BD .
     */
    public void setBD(Building BD){
        nearBD = BD;
    }
    public void interact(){
        if (nearBD!=null && EM.notFrozen())
        {
            EM.interact(nearBD.name);
        }

    }

    public float getDistanceTravelled(){
        return distanceTravelled;
    }

}
