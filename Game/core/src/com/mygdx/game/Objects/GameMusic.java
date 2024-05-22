package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class GameMusic implements Disposable {
    private Music music;
    private float volume;

    public GameMusic() {
        setup();
    }

    private void setup(){
        // Load your music file
        music = Gdx.audio.newMusic(Gdx.files.internal("music_loop/Ludum Dare 30 - 01.ogg"));
        music.setLooping(true);  // If you want the music to loop
        volume = 0.5f;  // Default volume
        music.setVolume(volume);
        music.play();
    }
    public void setVolume(float volume) {
        this.volume = volume;
        music.setVolume(volume);
    }

    public float getVolume() {
        return volume;
    }

    public void pause() {
        music.pause();
    }

    public void stop() {
        music.stop();
    }

    @Override
    public void dispose() {
        music.dispose();
    }
}
