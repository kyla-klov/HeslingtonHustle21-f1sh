package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoard implements Disposable {
    private static class Data implements Comparable<Data>{
        private final float score;
        private final String name;

        public Data(String name, float score){
            this.name = name;
            this.score = score;
        }

        @Override
        public int compareTo(Data o) {
            return Float.compare(score, o.score);
        }
    }

    private static final String FILE_PATH = "storage/PlayerData.txt";

    private final Texture leaderBoardTexture;
    private final Texture upArrowTexture;
    private final Texture downArrowTexture;
    private final BitmapFont font;
    private final float x, y;

    private ArrayList<Data> data;
    private boolean topFive;

    UIElements uiElements;

    public LeaderBoard(UIElements uiElements, float x, float y) {
        this.uiElements = uiElements;
        this.x = x;
        this.y = y;
        topFive = true;
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        data = readPlayerData();
        Collections.sort(data);
        Collections.reverse(data);
        leaderBoardTexture = new Texture(Gdx.files.internal("LeaderBoard.png"));
        upArrowTexture = new Texture(Gdx.files.internal("up.png"));
        downArrowTexture = new Texture(Gdx.files.internal("down.png"));
    }

    public void render(SpriteBatch batch){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            touchDown(Gdx.input.getX(), Gdx.input.getY());
        }
        uiElements.drawUI(batch, leaderBoardTexture, x, y, 301, 377);
        uiElements.drawUI(batch, upArrowTexture, x+250, y+300, 32, 32);
        uiElements.drawUI(batch, downArrowTexture, x+250, y+32, 32, 32);
        uiElements.drawFont(font, batch, "Leaderboard", x+60, y+350);
        int c = topFive ? 0 : 5;
        for (int i = c; i < data.size(); i++) {
            Data d = data.get(i);
            uiElements.drawFont(font, batch, d.name, x + 50, y + 300 - i*50);
            uiElements.drawFont(font, batch, Float.toString(d.score), x + 170, y + 300 - i*50);
        }
    }

    private ArrayList<Data> readPlayerData() {
        data = new ArrayList<>();
        FileHandle file = Gdx.files.local(FILE_PATH);
        if (file.exists()) {
            String[] lines = file.readString().split("\n");
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    float score = Float.parseFloat(parts[1]);
                    data.add(new Data(name, score));
                }
            }
        }
        return data;
    }

    public void touchDown(int screenX, int screenY) {
        Vector2 gamePos = uiElements.screenToGame(screenX, screenY);
        if (uiElements.isPressed(gamePos.x, gamePos.y, x + 250, y + 300, 32, 32)){
            topFive = true;
        }
        if (uiElements.isPressed(gamePos.x, gamePos.y, x + 250, y + 32, 32, 32)){
            topFive = false;
        }
    }

    @Override
    public void dispose() {

    }

}
