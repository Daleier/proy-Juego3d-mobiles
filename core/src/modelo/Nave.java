package modelo;

import com.badlogic.gdx.math.Vector3;

public class Nave extends MovilMax {
	public Nave(Vector3 pos, float escala, Vector3 velocidade, Vector3 velocidadeMax, float radioEsfera) {
        super(pos, escala, velocidade, velocidadeMax, radioEsfera);
    }
    public void setVelocidade(Vector3 velocidade) {
        this.velocidade = velocidade;
        if ((velocidade.x < (-Math.abs(this.velocidadeMax.x))) || (velocidade.x > Math.abs(this.velocidadeMax.x)))
            this.velocidade.x = velocidadeMax.x;
        if ((velocidade.y < (-Math.abs(this.velocidadeMax.y))) || (velocidade.y > Math.abs(this.velocidadeMax.y)))
            this.velocidade.y = velocidadeMax.y;
    }
}
