package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.CheckinGameScreen;
import com.mygdx.game.Utils.AchievementHandler;
import com.mygdx.game.Utils.ScreenManager;
import com.mygdx.game.Utils.ScreenType;

public class HesHustle extends Game {
	private SpriteBatch batch;
	private ScreenManager screenManager;
	private AchievementHandler achievementHandler;

	@Override
	public void create () {
        batch = new SpriteBatch();
		screenManager = new ScreenManager(this);
		achievementHandler = new AchievementHandler();
		screenManager.addScreenToMemory(ScreenType.GAME_SCREEN);
		screenManager.setScreen(ScreenType.MENU_SCREEN);
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public AchievementHandler getAchievementHandler() {
		return achievementHandler;
	}

	public SpriteBatch getBatch() {
		return batch;
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
