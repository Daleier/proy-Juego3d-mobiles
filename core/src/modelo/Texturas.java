package modelo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by dam203 on 01/03/2018.
 */

public class Texturas {
	public static Texture left_arrow;
	public static Texture right_arrow;
	public static Texture fondo;
	public static Texture fondoInicio;
	public static Texture button_play;
	public static Texture button_exit;

	public static void cargarTexturas() {
		FileHandle imageFileHandle = Gdx.files.internal("controles/leftarrow.png");
		left_arrow = new Texture(imageFileHandle);
		imageFileHandle = Gdx.files.internal("controles/rightarrow.png");
		right_arrow = new Texture(imageFileHandle);
		imageFileHandle = Gdx.files.internal("fondo/wallpaper.jpg");
		fondo = new Texture(imageFileHandle);
		imageFileHandle = Gdx.files.internal("fondo/wallpaper1.jpg");
		fondoInicio = new Texture(imageFileHandle);
		imageFileHandle = Gdx.files.internal("controles/buttonplay.png");
		button_play = new Texture(imageFileHandle);
		imageFileHandle = Gdx.files.internal("controles/buttonexit.png");
		button_exit = new Texture(imageFileHandle);
	}

	public static void dispose(){
		left_arrow.dispose();
		right_arrow.dispose();
		fondo.dispose();
	}
}
