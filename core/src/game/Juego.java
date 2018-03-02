package game;

import modelo.Texturas;

/**
 * Created by dam203 on 02/03/2018.
 */

public class Juego extends com.badlogic.gdx.Game {

	// TODO errores: pantalla inicio, al acabar una partida se bloquea, al acabar una partida no funcionan los botones de la pantalla de inicio
	@Override
	public void create() {
		Texturas.cargarTexturas();
		Audio.cargarAudio();
		setScreen(new PantallaInicio(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		Texturas.dispose();
		Audio.dispose();
	}
}