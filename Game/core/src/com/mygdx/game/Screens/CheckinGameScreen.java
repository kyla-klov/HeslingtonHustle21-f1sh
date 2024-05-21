package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HesHustle;
import com.mygdx.game.Utils.EventManager;
import com.mygdx.game.Utils.ResourceManager;
import com.mygdx.game.Utils.ScreenType;
import com.mygdx.game.Utils.ViewportAdapter;

import java.util.Random;

/**
 * The CheckinGameScreen class implements a mini-game for the player to increase their study hours.
 * Players are shown a number that they need to memorize and then type it correctly to succeed.
 */
public class CheckinGameScreen implements Screen {
    private final HesHustle game;
    private final BitmapFont font;
    private final ResourceManager resourceManager;
    private final Viewport vp;
    private final OrthographicCamera camera;
    private final StringBuilder inputText;
    private final SpriteBatch batch;
    private final EventManager eventManager;

    private int duration; //Number of rounds
    private int round; //Keeps track of the current round
    private int phase; //Keeps track of the game phases
    private float timer; //Keeps track of time
    private String message; //Displays a warning message to player when set
    private String guess; //Holds the players guess
    private String answer; //Holds the actual answer

    /**
     * Constructor for the CheckinGameScreen.
     * Initializes the game screen, fonts, camera, and other necessary components.
     * @param game The game instance.
     */
    public CheckinGameScreen(HesHustle game, EventManager eventManager) {
        this.game = game;
        this.eventManager = eventManager;
        resourceManager = new ResourceManager();
        batch = game.getBatch();
        camera = new OrthographicCamera();
        vp = new FitViewport(1600, 900, camera);
        vp.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        inputText = new StringBuilder();
        message = "";
        phase = 0;
        round = 0;
        answer = generateRandomNumber(5);
    }

    @Override
    public void show() {
        // This method is called when the screen is shown.
    }

    /**
     * Render method that draws the game screen.
     * @param delta Time elapsed since the last frame.
     */
    @Override
    public void render(float delta) {
        if (round == duration && phase != 0) {
            gameOver();
        }

        if (phase == 1) {
            timer += delta;
            if (timer >= 4f) {
                phase++;
                timer = 0;
            }
        } else if (phase == 4) {
            timer += delta;
            if (timer >= 1.5f) {
                phase = 1;
                timer = 0;
                answer = generateRandomNumber(round + 5);
            }
        }

        handleInput();

        ScreenUtils.clear(0.396f, 0.263f, 0.129f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        vp.apply();
        batch.begin();
        if (phase == 0) {
            ViewportAdapter.drawFont(vp, font, batch, "Enter study duration", 650, 500);
            ViewportAdapter.drawFont(vp, font, batch, inputText.toString(), 775, 450);
            ViewportAdapter.drawFont(vp, font, batch, message, 500, 350);
        } else if (phase == 1) {
            ViewportAdapter.drawFont(vp, font, batch, "Remember the Check-in code!", 650, 500);
            ViewportAdapter.drawFont(vp, font, batch, answer, 775, 450);
        } else if (phase == 2) {
            ViewportAdapter.drawFont(vp, font, batch, "Enter the Check-in code", 650, 500);
            ViewportAdapter.drawFont(vp, font, batch, inputText.toString(), 775, 450);
        } else if (phase == 3) {
            if (!answer.equals(guess)) {
                gameOver();
            }
            phase++;
            round++;
        } else if (phase == 4) {
            ViewportAdapter.drawFont(vp, font, batch, "Correct!", 750, 500);
        }
        batch.end();
    }

    /**
     * Handles user input for the game.
     */
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && inputText.length() > 0) {
            inputText.setLength(inputText.length() - 1);
        }

        for (int i = Input.Keys.NUM_0; i <= Input.Keys.NUM_9; i++) {
            if (Gdx.input.isKeyJustPressed(i) && (phase == 0 || phase == 2)) {
                inputText.append(Input.Keys.toString(i));
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (phase == 2) {
                phase++;
                guess = inputText.toString();
                inputText.setLength(0);
            } else {
                int value = 0;
                if (inputText.length() > 0) value = Integer.parseInt(inputText.toString());
                if (value == 0 || value >= 5) {
                    message = "Please enter a number between 1 and 5";
                } else {
                    phase++;
                    duration = value;
                    inputText.setLength(0);
                }
            }
        }
    }

    /**
     * Generates a random number string of specified length.
     * @param length The length of the random number string.
     * @return A string containing random digits.
     */
    private String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomDigit = random.nextInt(10); // Generates a random integer between 0 and 9
            sb.append(randomDigit);
        }

        return sb.toString();
    }

    /**
     * Handles the end of the game and transitions to another screen.
     */
    private void gameOver() {
        eventManager.addStudyHours(round );
        game.getScreenManager().setScreen(ScreenType.GAME_SCREEN);
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void pause() {
        // This method is called when the game is paused.
    }

    @Override
    public void resume() {
        // This method is called when the game is resumed.
    }

    @Override
    public void hide() {
        // This method is called when the screen is hidden.
    }

    @Override
    public void dispose() {
        resourceManager.disposeAll();
    }
}