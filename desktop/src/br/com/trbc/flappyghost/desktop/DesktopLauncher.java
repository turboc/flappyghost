package br.com.trbc.flappyghost.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import br.com.trbc.flappyghost.FlappyGhost;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = FlappyGhost.WIDTH;
        config.height= FlappyGhost.HEIGHT;
        config.title = FlappyGhost.TITULO;

        new LwjglApplication(new FlappyGhost(), config);
	}
}
