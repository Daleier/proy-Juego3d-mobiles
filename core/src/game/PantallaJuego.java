package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import javax.naming.ldap.Control;
import javax.print.attribute.standard.MediaSize;

import controlador.Controlador;
import modelo.Enemigo;
import modelo.MovilMax;
import modelo.Mundo;
import modelo.Nave;
import modelo.Texturas;


public class PantallaJuego implements InputProcessor, Screen {
	private Mundo meuMundo;

    private PerspectiveCamera camara3d;
    private ModelBatch modelBatch;
    private Environment environment;
    private ModelInstance instanceNave, instanceSuelo, instanceEnemigo0, instanceEnemigo1, instanceDisparo;

    private Controlador controlador;
    private SpriteBatch spritebatch;
    private BitmapFont bitMapFont;
	private BitmapFont bitMapFontCrono;
	private StringBuilder sbufferAciertos;
    private StringBuilder sbufferVidas;
    private StringBuilder sbufferCronometro;
	private float width;
    private float height;
    private Vector2 leftArrowPosition;
	private Vector2 rightArrowPosition;
	private Game game;
	// TODO añadir sonido

    public PantallaJuego(Game game) {
    	this.game = game;
        this.meuMundo = new Mundo();
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		Texturas.cargarTexturas();
		AssetManager assets = new AssetManager();
        assets.load("modelos/spaceship/spaceship.obj", Model.class);
		assets.load("modelos/asteroid/asteroid.obj", Model.class);
        assets.load("modelos/asteroid/a2.obj", Model.class);
        assets.finishLoading();

        Model modelNave = assets.get("modelos/spaceship/spaceship.obj", Model.class);
        Model modelEnemigo0 = assets.get("modelos/asteroid/asteroid.obj", Model.class);
        Model modelEnemigo1 = assets.get("modelos/asteroid/a2.obj", Model.class);
        //Creación de una esfera para el disparo.
        ModelBuilder modelBuilder = new ModelBuilder();
        Model modelDisparo = modelBuilder.createSphere(5f, 5f, 10f, 10, 10,
                new Material(ColorAttribute.createDiffuse(Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        camara3d = new PerspectiveCamera();
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0f, -1f, 1f));

        this.instanceNave = new ModelInstance(modelNave);
//        this.instanceSuelo = new ModelInstance(modelSuelo);
        this.instanceEnemigo0 = new ModelInstance(modelEnemigo0);
        this.instanceEnemigo1 = new ModelInstance(modelEnemigo1);
        this.instanceDisparo = new ModelInstance(modelDisparo);

        this.controlador = new Controlador(this.meuMundo);
        spritebatch = new SpriteBatch();
		sbufferAciertos = new StringBuilder();
		sbufferVidas = new StringBuilder();
		sbufferCronometro = new StringBuilder();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/dsdigit.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = (int)(width * 0.03f) ;
		this.bitMapFont = generator.generateFont(parameter); // font size in pixels
		parameter.size = (int) (width * 0.04f);
		this.bitMapFontCrono = generator.generateFont(parameter);
		bitMapFontCrono.setColor(Color.RED);
		generator.dispose();

		leftArrowPosition = new Vector2(0, height/2);
		rightArrowPosition = new Vector2(width-(width*0.1f),height/2);
		Gdx.input.setInputProcessor(this);
    }

	@Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);

        controlador.update(Gdx.graphics.getDeltaTime());
		Mundo.cronometro -= Gdx.graphics.getDeltaTime();

		if(Mundo.cronometro <= 0 || Nave.getVidas_restantes() <= 0){
			finJuego();
		}
        modelBatch.begin(camara3d);
		spritebatch.begin();
		spritebatch.draw(Texturas.fondo,0,-height * 0.1f);
		spritebatch.end();
		this.instanceNave.transform.set(this.meuMundo.getNave().matriz);
        modelBatch.render(instanceNave, environment);
        if (this.meuMundo.getDisparo().getVelocidade().z>0) {
            this.instanceDisparo.transform.set(this.meuMundo.getDisparo().matriz);
            modelBatch.render(instanceDisparo, environment);
        }

        for (Enemigo e : this.meuMundo.getEnemigos()) {
            if(e.getTextura() < 0.5){
				e.escala=0.4f;
				this.instanceEnemigo0.transform.set(e.matriz);
                modelBatch.render(instanceEnemigo0, environment);
            }else{
				e.escala = 10f;
				this.instanceEnemigo1.transform.set(e.matriz);
                modelBatch.render(instanceEnemigo1, environment);
            }

        }

        modelBatch.end();

        // vidas
		sbufferCronometro.setLength(0);
		sbufferCronometro.append(Mundo.getCronometroInt());
		sbufferVidas.setLength(0);
		sbufferVidas.append("VIDAS RESTANTES: "+ Nave.getVidas_restantes());
		sbufferAciertos.setLength(0);
		sbufferAciertos.append("ENEMIGOS ELIMINADOS: "+ Nave.getAciertos());
		spritebatch.begin();
		spritebatch.draw(Texturas.left_arrow,leftArrowPosition.x,leftArrowPosition.y, width*0.1f, height *0.1f);
		spritebatch.draw(Texturas.right_arrow, rightArrowPosition.x, rightArrowPosition.y, width*0.1f, height * 0.1f);
		bitMapFont.setColor(Color.YELLOW);
		bitMapFont.draw(spritebatch, sbufferAciertos,
				10, height-(height * 0.95f));
		bitMapFont.draw(spritebatch, sbufferVidas,
				this.width-(width*0.27f), height-(height * 0.95f));
		bitMapFontCrono.draw(spritebatch, sbufferCronometro,
				(this.width/2) - 5, this.height-(height*0.05f));
        spritebatch.end();
        Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
		// hace nave vulnerable despues de 1 segundo desde la ultima vez que colisiona con un enemigo
		if(Nave.invulnerable && Nave.getTiempo_ultimo_impacto() > 1 + Mundo.getCronometro()){
			Nave.invulnerable=false;
		}
    }

    private void finJuego(){
		HighScores.engadirPuntuacion(Nave.getAciertos());
		game.setScreen(new PantallaInicio(game));
		dispose();
	}

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / (float) height;
        camara3d.viewportWidth = aspectRatio * 1f;
        camara3d.viewportHeight = 1f;
        camara3d.far = 1200f;
        camara3d.near = 0.1f;
        camara3d.position.set(0, 120f, -100f);
        camara3d.lookAt(0, 50, 0);
        camara3d.update();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
    	Texturas.dispose();
    	bitMapFont.dispose();
    	spritebatch.dispose();
        modelBatch.dispose();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                this.controlador.pulsarTecla(Controlador.Keys.ARRIBA);
                break;
            case Input.Keys.DOWN:
                this.controlador.pulsarTecla(Controlador.Keys.ABAIXO);
                break;
            case Input.Keys.LEFT:
                this.controlador.pulsarTecla(Controlador.Keys.ESQUERDA);
                break;
            case Input.Keys.RIGHT:
                this.controlador.pulsarTecla(Controlador.Keys.DEREITA);
                break;
            case Input.Keys.SPACE:
                this.controlador.pulsarTecla(Controlador.Keys.ESPAZO);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                this.controlador.liberarTecla(Controlador.Keys.ARRIBA);
                break;
            case Input.Keys.DOWN:
                this.controlador.liberarTecla(Controlador.Keys.ABAIXO);
                break;
            case Input.Keys.LEFT:
                this.controlador.liberarTecla(Controlador.Keys.ESQUERDA);
                break;
            case Input.Keys.RIGHT:
                this.controlador.liberarTecla(Controlador.Keys.DEREITA);
                break;
            case Input.Keys.SPACE:
                this.controlador.liberarTecla(Controlador.Keys.ESPAZO);
                break;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 temporal= new Vector3(screenX,screenY,0);
		Circle dedo = new Circle(temporal.x,temporal.y,2);
		if(Intersector.overlaps(dedo,
				new Rectangle(leftArrowPosition.x,leftArrowPosition.y-height*0.1f,
				width*0.14f, height*0.1f))){
			this.controlador.pulsarTecla(Controlador.Keys.ESQUERDA);
		}else if (Intersector.overlaps(dedo,
				new Rectangle(rightArrowPosition.x,rightArrowPosition.y-height*0.1f,
				width*0.22f, height*0.1f) )){
			this.controlador.pulsarTecla(Controlador.Keys.DEREITA);
		}else{
			this.controlador.pulsarTecla(Controlador.Keys.ESPAZO);
		}


		return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.controlador.liberarTecla(Controlador.Keys.ESPAZO);
		this.controlador.liberarTecla(Controlador.Keys.DEREITA);
		this.controlador.liberarTecla(Controlador.Keys.ESQUERDA);
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


    public void pause() {

        Gdx.input.setInputProcessor(null);
    }

    public void resume() {

        Gdx.input.setInputProcessor(this);
    }


	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

}
