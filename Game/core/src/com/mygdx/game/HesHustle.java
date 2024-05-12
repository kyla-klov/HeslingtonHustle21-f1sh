package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.BasketBallScreen;
import com.mygdx.game.Screens.DuckGameScreen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Utils.AchievementHandler;
import com.mygdx.game.Utils.ScreenManager;
import com.mygdx.game.Utils.ScreenType;

public class HesHustle extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public ScreenManager screenManager;
	public AchievementHandler achievementHandler;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		screenManager = new ScreenManager(this);
		achievementHandler = new AchievementHandler();
		screenManager.addScreenToMemory(ScreenType.GAME_SCREEN);
		screenManager.setScreen(ScreenType.MENU_SCREEN);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
