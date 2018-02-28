package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import controlador.Controlador;
import modelo.MovilMax;
import modelo.Mundo;
import modelo.Nave;


public class Game extends com.badlogic.gdx.Game implements InputProcessor {
	private Mundo meuMundo;

    private PerspectiveCamera camara3d;
    private ModelBatch modelBatch;
    private Environment environment;
    private ModelInstance instanceNave, instanceSuelo, instanceEnemigo, instanceDisparo;

    private Controlador controlador;
    private SpriteBatch spritebatch;
    private BitmapFont bitMapFont;
    private StringBuilder sbufferAciertos;
    private StringBuilder sbufferVidas;
    private StringBuilder sbufferCronometro;

	private int width;
    private int height;

    @Override
    public void create() {
        this.meuMundo = new Mundo();

        AssetManager assets = new AssetManager();
        assets.load("modelos/fondo/lowpolymountains.obj", Model.class);
        assets.load("modelos/ship/ship.obj", Model.class);
        assets.load("modelos/spaceinvader/invader1.obj", Model.class);
        assets.finishLoading();

        Model modelSuelo = assets.get("modelos/fondo/lowpolymountains.obj", Model.class);
        Model modelNave = assets.get("modelos/ship/ship.obj", Model.class);
        Model modelEnemigo = assets.get("modelos/spaceinvader/invader1.obj", Model.class);
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
        this.instanceSuelo = new ModelInstance(modelSuelo);
        this.instanceEnemigo = new ModelInstance(modelEnemigo);
        this.instanceDisparo = new ModelInstance(modelDisparo);

        //Modificar el color del suelo.
        for (int i = 0; i < this.instanceSuelo.materials.size; i++)
            this.instanceSuelo.materials.get(i).set(ColorAttribute.createDiffuse(Color.BROWN));

        this.controlador = new Controlador(this.meuMundo);
        spritebatch = new SpriteBatch();
		sbufferAciertos = new StringBuilder();
		sbufferVidas = new StringBuilder();
		sbufferCronometro = new StringBuilder();
		bitMapFont = new BitmapFont();
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        controlador.update(Gdx.graphics.getDeltaTime());
		Mundo.cronometro -= Gdx.graphics.getDeltaTime();

        modelBatch.begin(camara3d);

        this.instanceNave.transform.set(this.meuMundo.getNave().matriz);
        modelBatch.render(instanceNave, environment);
        if (this.meuMundo.getDisparo().getVelocidade().z>0) {
            this.instanceDisparo.transform.set(this.meuMundo.getDisparo().matriz);
            modelBatch.render(instanceDisparo, environment);
        }

        for (MovilMax s : this.meuMundo.getSuelos()) {
            this.instanceSuelo.transform.set(s.matriz);
            modelBatch.render(instanceSuelo, environment);
        }
        for (MovilMax e : this.meuMundo.getEnemigos()) {
            this.instanceEnemigo.transform.set(e.matriz);
            modelBatch.render(instanceEnemigo, environment);
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
		bitMapFont.setColor(Color.YELLOW);
		bitMapFont.draw(spritebatch, sbufferAciertos,
				10, 20);
		bitMapFont.draw(spritebatch, sbufferVidas,
				this.width-175, 20);
		bitMapFont.setColor(Color.RED);
		bitMapFont.draw(spritebatch, sbufferCronometro,
				(this.width/2) - 5, this.height-20);
        spritebatch.end();
        Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
		// hace nave vulnerable despues de 1 segundo desde la ultima vez que colisiona con un enemigo
		if(Nave.invulnerable && Nave.getTiempo_ultimo_impacto() > 1 + Mundo.getCronometro()){
			Nave.invulnerable=false;
		}
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


    public void pause() {

        Gdx.input.setInputProcessor(null);
    }

    public void resume() {

        Gdx.input.setInputProcessor(this);
    }

}
