package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Objects.GameMusic;
import com.mygdx.game.Objects.GameSound;
import com.mygdx.game.Utils.AchievementHandler;
import com.mygdx.game.Utils.ScreenManager;
import com.mygdx.game.Utils.ScreenType;

public class HesHustle extends Game {
	private SpriteBatch batch;
	private ScreenManager screenManager;
	private AchievementHandler achievementHandler;
	private GameMusic gameMusic;
	private GameSound gameSound;
	private boolean fullScreen;

	@Override
	public void create () {
        batch = new SpriteBatch();
		screenManager = new ScreenManager(this);
		gameMusic = new GameMusic(); // Initialize and start playing here
		gameSound = new GameSound();
		setNewGame();
		screenManager.setScreen(ScreenType.MENU_SCREEN);
		fullScreen = false;
	}

	public GameMusic getGameMusic() {
		return gameMusic;
	}

	public void setNewGame(){
		if (achievementHandler!=null) achievementHandler.dispose();
		achievementHandler = new AchievementHandler();
		screenManager.removeScreenFromMemory(ScreenType.GAME_SCREEN);
		screenManager.addScreenToMemory(ScreenType.GAME_SCREEN);
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

	//For tests
	@SuppressWarnings("unused")
	public void setScreenManager(ScreenManager screenManager) {
		this.screenManager = screenManager;
	}

	//For tests
	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public boolean getFullScreen() {
		return fullScreen;
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}

	public GameSound getGameSound() {
		return gameSound;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameMusic.dispose();
	}

}
