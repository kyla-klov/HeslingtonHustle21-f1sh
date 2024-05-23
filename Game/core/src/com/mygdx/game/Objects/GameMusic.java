package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

/**
 * The GameMusic class manages the background music for the game.
 * It handles the setup, playback, volume control, and disposal of the music resource.
 */
public class GameMusic implements Disposable {
    private Music music;
    private float volume;

    /**
     * Constructs a GameMusic object and sets up the music.
     */
    public GameMusic() {
        setup();
    }

    /**
     * Sets up the music by loading the music file, setting it to loop, and setting the default volume.
     */
    private void setup(){
        // Load your music file
        music = Gdx.audio.newMusic(Gdx.files.internal("music_loop/Ludum Dare 30 - 01.ogg"));
        music.setLooping(true);  // If you want the music to loop
        volume = 0.5f;  // Default volume
        music.setVolume(volume);
        music.play();
    }


    /**
     * @param volume the new volume level (0.0 to 1.0)
     */
    public void setVolume(float volume) {
        this.volume = volume;
        music.setVolume(volume);
    }

    /**
     * @return the current volume level
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Pauses the music playback.
     */
    public void pause() {
        music.pause();
    }

    /**
     * Stops the music playback.
     */
    public void stop() {
        music.stop();
    }

    /**
     * Disposes of the music resource.
     */
    @Override
    public void dispose() {
        music.dispose();
    }
}
