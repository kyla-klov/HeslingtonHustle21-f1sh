package com.mygdx.game.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class GameMusic implements Disposable {
    private Music music;
    private float volume;

    public GameMusic() {
        this("music_loop/Ludum Dare 30 - 01.ogg");
        // Load your music file

//        music = Gdx.audio.newMusic(Gdx.files.internal("music_loop/Ludum Dare 30 - 01.ogg"));
//        music.setLooping(true);  // If you want the music to loop
//        volume = 0.5f;  // Default volume
//        music.setVolume(volume);
//        music.play();
    }

    public GameMusic(String filePath){
        // Load your music file
        music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
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

    public void play() {
        if (!music.isPlaying()) {
            music.play();
        }
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
