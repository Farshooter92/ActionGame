package com.benstone.actiongame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.benstone.actiongame.ActionGame;
import com.benstone.actiongame.Utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.APP_WIDTH;
		config.height = Constants.APP_HEIGHT;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		new LwjglApplication(new ActionGame(), config);
	}
}
