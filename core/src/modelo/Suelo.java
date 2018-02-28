package modelo;

import com.badlogic.gdx.math.Vector3;

public class Suelo extends MovilMax {
	public Suelo(Vector3 pos, float escala, Vector3 velocidade, Vector3 velocidadeMax, float radioEsfera) {
        super(pos, escala, velocidade, velocidadeMax, radioEsfera);
    }
    public void setVelocidade(Vector3 velocidade) {
        this.velocidade = velocidade;
        //No se va a permitir que la nave avance hacia atrás ni que tampoco se detenga del todo.
        if (velocidade.z < (-Math.abs(this.velocidadeMax.z)))
            this.velocidade.z = velocidadeMax.z;
        else if (velocidade.z > -Math.abs(this.velocidadeMax.z)/8)
            this.velocidade.z = -Math.abs(this.velocidadeMax.z)/8;
    }
}
