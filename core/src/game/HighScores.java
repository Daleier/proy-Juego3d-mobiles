package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class HighScores {
	public static String highscores =  "0";
	public static String archivoHighscores = "space_defender_highscore.dat";

	public static void load() {
		FileHandle arquivo = Gdx.files.external(HighScores.archivoHighscores);
		if (!arquivo.exists()) {
			HighScores.save();
			System.out.println("Non encontrado");
		}
		highscores = arquivo.readString();
	}

	public static void engadirPuntuacion(int puntuacion) {
		int highscore = Integer.parseInt(highscores);
		if(highscore < puntuacion){
			HighScores.highscores = Integer.toString(puntuacion);
		}
	}

	private static void save() {
		FileHandle arquivo = Gdx.files.external(HighScores.archivoHighscores);
		arquivo.writeString(HighScores.highscores, false);
	}
}