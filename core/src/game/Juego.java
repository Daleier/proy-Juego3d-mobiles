package game;

import com.badlogic.gdx.Game;

import modelo.Texturas;

/**
 * Created by dam203 on 02/03/2018.
 */

public class Juego extends com.badlogic.gdx.Game {

	@Override
	public void create() {
		Texturas.cargarTexturas();
		setScreen(new PantallaInicio(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		Texturas.dispose();
	}
}