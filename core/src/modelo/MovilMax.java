package modelo;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Sphere;

public class MovilMax extends Elemento3D {


    protected Vector3 velocidadeMax;
	private Sphere esfera;


    public MovilMax(Vector3 pos, float escala, Vector3 velocidade, Vector3 velocidadeMax, float radioEsfera) {
        super(pos, escala, velocidade);
        this.velocidadeMax = velocidadeMax;
        this.esfera = new Sphere(posicion, radioEsfera);
    }

    public Vector3 getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(Vector3 velocidade) {
        this.velocidade = velocidade;
    }

    public Vector3 getVelocidadeMax() {
        return velocidadeMax;
    }

    public void setVelocidadeMax(Vector3 velocidadeMax) {
        this.velocidadeMax = velocidadeMax;
    }

    public Sphere getEsfera() {
        return esfera;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (this.esfera!=null){
           this.esfera.center.set(posicion);
           //Utiles.imprimirLog("MovilMax", "update", "Posición esfera: ("+this.esfera.center.x+","+this.esfera.center.y+","+this.esfera.center.z+")"+" Radio: "+ this.esfera.radius);
           //Utiles.imprimirLog("MovilMax", "update", "Posición elemento3D: ("+this.posicion.x+","+this.posicion.y+","+this.posicion.z+")");
        }
    }
}
