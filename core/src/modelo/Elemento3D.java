package modelo;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Elemento3D {

    public Matrix4 matriz;
    public Vector3 posicion;
    public float escala;
    public Vector3 velocidade;
    private Vector3 temp;

    public Elemento3D(Vector3 pos, float escala, Vector3 velocidade) {
        matriz = new Matrix4();
        posicion = pos;
        this.escala = escala;
        this.velocidade = velocidade;

        temp = new Vector3();

    }

    public void update(float delta) {

        temp.set(velocidade);
        posicion.add(temp.scl(delta));


        matriz.idt();
        matriz.translate(posicion);
        matriz.scl(escala);


    }
}