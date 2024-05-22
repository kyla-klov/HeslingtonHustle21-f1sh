package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Server;
import com.mygdx.game.Server.Data;
import com.mygdx.game.Utils.ViewportAdapter;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoard implements Disposable {
    private static final String FILE_PATH = "storage/PlayerData.txt";

    private final Texture leaderBoardTexture;
    private final Texture upArrowTexture;
    private final Texture downArrowTexture;
    private final BitmapFont font;
    private final Viewport vp;
    private final float x, y, width, height;

    private ArrayList<Data> data;
    private int page;

    public LeaderBoard(Viewport vp, BitmapFont font, float x, float y, float width, float height){
        this.vp = vp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
        page = 0;
        data = readPlayerData();
        Collections.sort(data);
        Collections.reverse(data);
        leaderBoardTexture = new Texture(Gdx.files.internal("LeaderBoard.png"));
        upArrowTexture = new Texture(Gdx.files.internal("up.png"));
        downArrowTexture = new Texture(Gdx.files.internal("down.png"));
    }
    public LeaderBoard(Viewport vp, float x, float y, float width, float height) {
        this(vp, new BitmapFont(Gdx.files.internal("font.fnt")), x, y, width, height);
//        this.vp = vp;
//        this.x = x;
//        this.y = y;
//        this.width = width;
//        this.height = height;
//        page = 0;
//        font = new BitmapFont(Gdx.files.internal("font.fnt"));
//        data = readPlayerData();
//        Collections.sort(data);
//        Collections.reverse(data);
//        leaderBoardTexture = new Texture(Gdx.files.internal("LeaderBoard.png"));
//        upArrowTexture = new Texture(Gdx.files.internal("up.png"));
//        downArrowTexture = new Texture(Gdx.files.internal("down.png"));
    }

    public void render(SpriteBatch batch){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            touchDown(Gdx.input.getX(), Gdx.input.getY());
        }
        ViewportAdapter.drawUI(vp, batch, leaderBoardTexture, x, y, width, height);
        ViewportAdapter.drawUI(vp, batch, upArrowTexture, x+250*width/301f, y+300*height/377f, 32*width/301f, 32*height/377f);
        ViewportAdapter.drawUI(vp, batch, downArrowTexture, x+250*width/301f, y+32*height/377f, 32*width/301f, 32*height/377f);
        ViewportAdapter.drawFont(vp, font, batch, "Leader board", x+60*width/301f, y+350*height/377f);
        int c = page * 5;
        int upper = c+5;
        if (data.size() < upper){
            upper = data.size();
        }
        for (int i = c; i < upper; i++) {
            Data d = data.get(i);
            String name = d.getPlayer();
            String score = "   " + d.getScore();

            if (name.length() > 8 && name.length() != 10) {
                name = name.substring(0, 8);
                name += "..";
            }
            float h = y + (300 - (i-c)*50)*height/377f;
            ViewportAdapter.drawFont(vp, font, batch, (i+1) + ". " + name, x + 50*width/301f, h);
            ViewportAdapter.drawFont(vp, font, batch, score, x + 170*width/301f, h);
        }
    }

    private ArrayList<Data> readPlayerData() {
        data = new ArrayList<>();
        FileHandle file = Gdx.files.local(FILE_PATH);
        if (file.exists()) {
            String[] lines = file.readString().split("\n");
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    float score = Float.parseFloat(parts[1]);
                    int status = Integer.parseInt(parts[2]);
                    data.add(new Data(name, score, status));
                }
            }
        }

        Server.fetchScores(data);

        return data;
    }

    public void touchDown(int screenX, int screenY) {
        Vector2 gamePos = ViewportAdapter.screenToGame(vp, screenX, screenY);
        if (ViewportAdapter.isOver(gamePos.x, gamePos.y, x + 250*width/301f, y + 300*height/377f, 32*width/301f, 32*height/377f)){
            if (page > 0) page--;
        }
        if (ViewportAdapter.isOver(gamePos.x, gamePos.y, x + 250*width/301f, y + 32*height/377f, 32*width/301f, 32*height/377f)){
            if (page < data.size()/5) page++;
        }
    }

    @Override
    public void dispose() {

    }

    public ArrayList<Data> getData() {
        return data;
    }

    @SuppressWarnings("unused")
    public int getPage() {
        return page;
    }
}
