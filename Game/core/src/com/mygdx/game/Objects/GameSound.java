package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

/**
 * Manages game sound effects including sound level adjustments and playing specific sounds.
 * It handles sound effects for increasing volume, decreasing volume, and button clicks.
 */
public class GameSound implements Disposable {
    private final Music buttonClickedSound;
    private float volume = 1f;


    /**
     * Initializes sound effects by loading the audio files.
     */
    public GameSound(){
        buttonClickedSound = Gdx.audio.newMusic(Gdx.files.internal("sfx/button_press.mp3"));
        setVolume(volume);
    }

    public float getVolume(){
        return volume;
    }

    public void setVolume(float volume){
        this.volume = volume;
        buttonClickedSound.setVolume(volume);
    }

    /**
     * Plays the sound effect for a button click. Stops the sound if it is already playing before restarting it.
     */
    public void buttonClickedSoundActivate(){
        if (buttonClickedSound.isPlaying()){
            buttonClickedSound.stop();
        }
        buttonClickedSound.play();
    }


    @Override
    public void dispose() {
        buttonClickedSound.dispose();
    }
}


