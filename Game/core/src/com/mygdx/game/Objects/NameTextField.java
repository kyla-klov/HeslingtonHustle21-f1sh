package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NameTextField {
    private final StringBuilder inputText;
    private final BitmapFont font;
    private final Texture textField;
    private final UIElements uiElements;

    private String value;
    private boolean capsLockOn;
    private boolean active;

    public NameTextField(UIElements uiElements) {
        this.uiElements = uiElements;
        inputText = new StringBuilder();
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        textField = new Texture(Gdx.files.internal("NameTextField.png"));
        active = true;
    }

    public void render(SpriteBatch batch){
        if (!active) return;
        handleInput();
        float x = 800 - 338/2f;
        float y = 450 - 157/2f;
        uiElements.drawUI(batch, textField, x, y, 338, 157);
        uiElements.drawFont(font, batch, inputText.toString(), x+70, y+68);
    }

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

    public boolean textEntered() {
        return !active;
    }

    public String getValue() {
        return value;
    }
}
