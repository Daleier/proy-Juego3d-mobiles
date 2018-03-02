package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {

	public static Sound explosionNave;
	public static Sound explosionAsteroide;
	public static Sound disparo;
	public static Music musicaFondo;

	public static void cargarAudio() {
		explosionNave = Gdx.audio.newSound(Gdx.files.internal("sonido/boom8.wav"));
		explosionAsteroide = Gdx.audio.newSound(Gdx.files.internal("sonido/chuncky_explosion.mp3"));
		disparo = Gdx.audio.newSound(Gdx.files.internal("sonido/laser1.wav"));
		musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("sonido/orbital_colossus.mp3"));
		musicaFondo.setLooping(true);
	}


	public static void dispose() {
		explosionAsteroide.dispose();
		explosionNave.dispose();
		disparo.dispose();
		musicaFondo.dispose();
	}
}