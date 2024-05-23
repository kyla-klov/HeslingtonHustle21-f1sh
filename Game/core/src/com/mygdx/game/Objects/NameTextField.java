package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Utils.ViewportAdapter;

/**
 * The NameTextField class provides a text input field that allows the player to enter their name.
 * It handles rendering the text field and processing input from the keyboard.
 */
public class NameTextField {
    private final StringBuilder inputText;
    private final BitmapFont font;
    private final Texture textField;
    private final Viewport vp;

    private String value;
    private boolean capsLockOn;
    private boolean active;

    /**
     * Constructs a NameTextField with the specified parameters.
     *
     * @param vp        the viewport to use for rendering
     * @param font      the font to use for rendering text
     * @param textField the texture of the text field
     * @param active    the initial active state of the text field
     */
    public NameTextField(Viewport vp, BitmapFont font, Texture textField, boolean active){
        this.vp = vp;
        this.font = font;
        this.textField = textField;
        this.active = active;
        inputText = new StringBuilder();
    }

    /**
     * Constructs a NameTextField with default font and texture, and specified viewport.
     *
     * @param vp the viewport to use for rendering
     */
    public NameTextField(Viewport vp) {
        this(vp, new BitmapFont(Gdx.files.internal("font.fnt")),
                new Texture(Gdx.files.internal("NameTextField.png")), true);
    }

    /**
     * Renders the text field using the specified sprite batch.
     *
     * @param batch the sprite batch used for rendering
     */
    public void render(SpriteBatch batch){
        if (!active) return;
        handleInput();
        float x = 800 - 338/2f;
        float y = 450 - 157/2f;
        ViewportAdapter.drawUI(vp, batch, textField, x, y, 338, 157);
        ViewportAdapter.drawFont(vp, font, batch, inputText.toString(), x+70, y+68);
    }

    /**
     * Handles keyboard input for the text field.
     */
    private void handleInput() {
        boolean shiftPressed = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT);
        if (Gdx.input.isKeyPressed(Input.Keys.CAPS_LOCK)){
            capsLockOn = !capsLockOn;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && inputText.length() > 0) {
            inputText.setLength(inputText.length() - 1);
        }

        for (int i = Input.Keys.A; i <= Input.Keys.Z; i++) {
            if (Gdx.input.isKeyJustPressed(i)) {
                char character = Input.Keys.toString(i).charAt(0);
                if ((shiftPressed && !capsLockOn) || (!shiftPressed && capsLockOn)) {
                    inputText.append(Character.toUpperCase(character));
                } else {
                    inputText.append(Character.toLowerCase(character));
                }
            }
        }

        for (int i = Input.Keys.NUM_0; i <= Input.Keys.NUM_9; i++) {
            if (Gdx.input.isKeyJustPressed(i)) {
                inputText.append(Input.Keys.toString(i));
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            inputText.append(" ");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            value = inputText.toString();
            active = false;
        }
    }
    /**
     * @return true if text has been entered, false otherwise
     */
    public boolean textEntered() {
        return !active;
    }

    /**
     * @return the entered text
     */
    public String getValue() {
        return value;
    }
}
