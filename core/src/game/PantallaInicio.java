package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


import javax.xml.soap.Text;

import modelo.Texturas;

/**
 * Created by dam203 on 02/03/2018.
 */

public class PantallaInicio implements Screen, InputProcessor {

	private OrthographicCamera camara2d;
	private SpriteBatch spritebatch;
	private ShapeRenderer shapeRenderer;
	private Rectangle botones[] = new Rectangle[2];
	private float width;
	private float heigth;
	private Game game;
	private BitmapFont bitMapFont;
	private StringBuilder sBufferTitle;
	private StringBuilder sBufferPuntuacion;

	public PantallaInicio(Game game) {
		spritebatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		camara2d = new OrthographicCamera();
		width = Gdx.graphics.getWidth();
		heigth = Gdx.graphics.getHeight();
		this.game = game;
		HighScores.load();
		botones[0] = new Rectangle(width*0.2f, heigth*0.35f, width*0.2f, width*0.2f);
		botones[1] = new Rectangle(width*0.6f, heigth*0.35f, width*0.2f, width*0.2f);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/dsdigit.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = (int)(width * 0.085f) ;
		this.bitMapFont = generator.generateFont(parameter); // font size in pixels
		generator.dispose();
		sBufferTitle = new StringBuilder("Space Defender");
		sBufferPuntuacion = new StringBuilder("Máxima puntuación: 0");
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 temporal= new Vector2(screenX,screenY);
		Circle dedo = new Circle(temporal.x,temporal.y,2);
		if(Intersector.overlaps(dedo, botones[0])){
			dispose();
			game.setScreen(new PantallaJuego(game));
		}else if(Intersector.overlaps(dedo, botones[1])){
			Gdx.app.exit();
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sBufferPuntuacion.setLength(0);
		sBufferPuntuacion.append("Máxima puntuación: "+HighScores.highscores);
		spritebatch.begin();
		spritebatch.draw(Texturas.button_play, botones[0].x, botones[0].y,  botones[0].width, botones[0].height);
		spritebatch.draw(Texturas.button_exit, botones[1].x, botones[1].y,  botones[1].width, botones[1].height);
		bitMapFont.draw(spritebatch, sBufferTitle, width*0.23f, heigth * 0.9f);
		bitMapFont.draw(spritebatch, sBufferPuntuacion, width*0.15f,heigth*0.1f);
		spritebatch.end();

//		shapeRenderer.setAutoShapeType(true);
//		shapeRenderer.begin();
//		shapeRenderer.box(botones[0].x, botones[0].y, 0, botones[0].width, botones[0].height,0);
//		shapeRenderer.box(botones[1].x, botones[1].y, 0, botones[1].width, botones[1].height,0);
//		shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		camara2d.setToOrtho(false, width, heigth);
		camara2d.update();
		spritebatch.setProjectionMatrix(camara2d.combined);
		spritebatch.disableBlending();
		Gdx.input.setInputProcessor(this);

	}

	@Override
	public void pause() {
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void resume() {
		Gdx.input.setInputProcessor(this);

	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		spritebatch.dispose();
		shapeRenderer.dispose();
	}
}
