package com.benstone.actiongame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.benstone.actiongame.Screens.PlayScreen;
import com.benstone.actiongame.Screens.SplashScreen;

public class ActionGame extends Game {

	// Core Game Screens to switch between during apps lifecycle
	public PlayScreen playScreen;

	@Override
	public void create ()
	{
		playScreen = new PlayScreen(this);

		// Get the game rolling
		setScreen(new SplashScreen(this));
	}

	///////////////////////////////////////////////////////////////////////////
	//						Core Game Loop									 //
	///////////////////////////////////////////////////////////////////////////

	@Override
	public void render ()
	{
		super.render();
	}

	///////////////////////////////////////////////////////////////////////////
	//						Maintenance Methods								 //
	///////////////////////////////////////////////////////////////////////////

	@Override
	public void dispose()
	{
		super.dispose();
		// If it implements the Disposable interface then it should be disposed.
	}

	///////////////////////////////////////////////////////////////////////////
	//								Getters									 //
	///////////////////////////////////////////////////////////////////////////

	public PlayScreen getPlayScreen() {
		return playScreen;
	}
}
